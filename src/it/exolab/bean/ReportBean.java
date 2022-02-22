package it.exolab.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;

import it.exolab.constants.Constants;
import it.exolab.dto.Articolo;
import it.exolab.dto.IndirizzoDiSpedizione;
import it.exolab.dto.Ordine;
import it.exolab.pojo.CartaDiCreditoPOJO;
import it.exolab.pojo.DettagliOrdinePOJO;
import it.exolab.pojo.IndirizzoDiSpedizionePOJO;
import it.exolab.pojo.OrdinePOJO;
import it.exolab.pojo.OrdineReport;
import it.exolab.pojo.UtentePOJO;


@SuppressWarnings("deprecation")
@ManagedBean
@SessionScoped
public class ReportBean implements Serializable {

	static Logger log = LogManager.getLogger(ReportBean.class);

	private static final long serialVersionUID = -8491051956398169240L;	

	@ManagedProperty ("#{ordiniBean}")
	private OrdiniBean ordiniBean;

	private StreamedContent file;

	@PostConstruct
	public void init() {
		file = null;
	}

	public void generateReportUtenteExcel() {
		OrdineReport data = ordiniBean.getOrdineEDettagliReport();
		String filename = data.getOrdine().getAcquirente().getNome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getAcquirente().getCognome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getId() + Constants.File.XLSX;
		file = convertByteToStreamedContent(reportUtenteExcel(),filename, Constants.ContentTypes.XLSX);
	}
	
	public void generateReportUtentePDF() {
		OrdineReport data = ordiniBean.getOrdineEDettagliReport();
		String filename = data.getOrdine().getAcquirente().getNome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getAcquirente().getCognome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getId() + Constants.File.PDF;
		file = convertByteToStreamedContent(reportUtentePDF(), filename, Constants.ContentTypes.PDF);
	}

	public void generateReportAllOrdiniExcel() {
		String filename = Constants.ExcelReport.TITLE_FOR_ALL_ORDINI + Constants.File.XLSX;
		file = convertByteToStreamedContent(reportAllOrdiniExcel(),filename,Constants.ContentTypes.XLSX);
	}
	
	public void generateReportAllOrdiniPDF() {
		String filename = Constants.ExcelReport.TITLE_FOR_ALL_ORDINI + Constants.File.PDF;
		file = convertByteToStreamedContent(reportAllOrdiniPDF(), filename, Constants.ContentTypes.PDF);
	}

	public StreamedContent convertByteToStreamedContent(byte[] byteFile, String filename, String contentType) {

		InputStream is = new ByteArrayInputStream(byteFile);
		

		StreamedContent file = DefaultStreamedContent.builder()
				.name(filename)
				.contentType(contentType)
				.stream(() -> is)
				.build();

		log.info("Content type: " + file.getContentType());

		return file;

	}

	public byte[] reportUtenteExcel() {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			Integer maxColNum = Constants.ExcelReport.FIELDS_FOR_CUSTOMER.size();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();

			XSSFCellStyle subTitleStyle = generateSubTitleStyle(workbook);
			XSSFCellStyle fieldStyle = generateFieldStile(workbook);

			/*Header*/
			generateHeader(Constants.ExcelReport.TITLE_FOR_CUSTOMER, sheet, workbook, maxColNum);

			/*Subtitles*/
			generateSubtitles(sheet, Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER_AND_ORDINI, Constants.ExcelReport.ROW_NUMBER_OF_SUBTITLES, subTitleStyle );

			/*Campi*/
			generateFields(sheet, Constants.ExcelReport.FIELDS_FOR_CUSTOMER, fieldStyle );

			/*Riempimento*/
			Integer rowNum = 2;
			OrdineReport reportData = ordiniBean.getOrdineEDettagliReport();
			XSSFRow valueRow = sheet.createRow(++rowNum);
			XSSFCell columnsCell [] = new XSSFCell[maxColNum];

			int count = 0;
			for (DettagliOrdinePOJO dettagliOrdine : reportData.getDettagliOrdine()) {
				if(count == 0) {
					OrdinePOJO ordineData = reportData.getOrdine();
					fillRow(valueRow,columnsCell,ordineData,dettagliOrdine,true);
					columnsCell = new XSSFCell[maxColNum];
				}else {
					XSSFRow articoliRow = sheet.createRow(++rowNum);
					fillArticoliRow(articoliRow,columnsCell,dettagliOrdine);
				}
				count++;
			}

			autoSizeColumns(sheet,maxColNum);

			workbook.write(bos);
			workbook.close();
			bos.close();


		}catch(Exception e) {  
			log.info(e.getMessage(), e);
		}

		return bos.toByteArray();
	}


	private byte[] reportAllOrdiniExcel() {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			int maxColNum = Constants.ExcelReport.FIELDS_FOR_ALL_ORDINI.size();
			int maxColNumDettagliOrdine = Constants.ExcelReport.FIELDS_FOR_ARTICOLI_ACQUISTATI.size();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet0 = workbook.createSheet();
			XSSFSheet sheet1 = workbook.createSheet();
			
			workbook.setSheetName(workbook.getSheetIndex(sheet0), "Resoconto Ordini");
			workbook.setSheetName(workbook.getSheetIndex(sheet1), "Dettagli Ordini");

			/*Styles*/
			XSSFCellStyle subtileStyle = generateSubTitleStyle(workbook);
			XSSFCellStyle fieldStyle = generateFieldStile(workbook);

			generateHeader(Constants.ExcelReport.TITLE_FOR_ALL_ORDINI, sheet0, workbook, maxColNum); //genera header foglio 0
			generateHeader(Constants.ExcelReport.TITLE_FOR_ARTICOLI_ACQUISTATI,sheet1,workbook,maxColNumDettagliOrdine); //genera header foglio 1

			/*Genera Subtitles foglio 0*/
			generateSubtitles(sheet0, Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER_AND_ORDINI, Constants.ExcelReport.ROW_NUMBER_OF_ALL_ORDINI, subtileStyle);
			/*Genera Subtitels foglio 1*/
			generateSubtitles(sheet1, Constants.ExcelReport.SUBTITLES_FOR_ARTICOLI_ACQUISTATI, Constants.ExcelReport.ROW_NUMBER_FOR_ARTICOLI_ACQUISTATI,subtileStyle);

			generateFields(sheet0 , Constants.ExcelReport.FIELDS_FOR_ALL_ORDINI, fieldStyle); //Genera campi foglio 0
			generateFields(sheet1, Constants.ExcelReport.FIELDS_FOR_ARTICOLI_ACQUISTATI, fieldStyle); //Genera campi foglio 1

			List<OrdineReport> reportOrdini = ordiniBean.getAllOrdiniReport();

			int rowNum = 2;
			int rowNumDettagliOrdine = 2;

			/*Riempimento foglio 0*/
			XSSFCell columnsCell [] = new XSSFCell[maxColNum];
			XSSFCell columnsCellSheet1 [] = new XSSFCell[maxColNumDettagliOrdine];

			for (OrdineReport report : reportOrdini) {

				OrdinePOJO ordineData = report.getOrdine();
				XSSFRow valueRow = sheet0.createRow(++rowNum);
				fillRow(valueRow,columnsCell,ordineData,null,false);
				columnsCell[16] = valueRow.createCell(16);
				columnsCell[16].setCellValue(Constants.ExcelReport.INFO_ARTICOLI_ACQUISTATI);
				columnsCell = new XSSFCell[maxColNum];

				for (DettagliOrdinePOJO dettagliOrdine : report.getDettagliOrdine()) {
					/*inserisci dettagli ordine*/
					XSSFRow dettagliOrdineRow = sheet1.createRow(++rowNumDettagliOrdine);
					fillArticoliRowSheetDiverso(dettagliOrdineRow, columnsCellSheet1, dettagliOrdine);
					columnsCellSheet1 = new XSSFCell[maxColNum];
				}
			}

			autoSizeColumns(sheet0, maxColNum);
			autoSizeColumns(sheet1, maxColNumDettagliOrdine);

			workbook.write(bos);
			workbook.close();
			bos.close();

		} catch ( Exception e ) {
			log.info(e.getMessage(), e);
		}

		return bos.toByteArray();

	}

	private void generateHeader( String headerText, XSSFSheet sheet, XSSFWorkbook workbook, int maxColNum ) {

		XSSFCellStyle titleStyle = generateTitleStyle(workbook); //style

		XSSFRow titleRow = sheet.createRow(0);
		XSSFCell titleCell = titleRow.createCell(0);
		titleCell.setCellValue(headerText);
		titleCell.setCellStyle(titleStyle);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxColNum-1));

	}

	public void generateSubtitles ( XSSFSheet sheet, List<String> subtitles, int[] rowNumbers, XSSFCellStyle subTitleStyle  ) {

		int rowNum = 0;

		XSSFRow subtitleRow = sheet.createRow(++rowNum);
		for (String subtitleText  : subtitles) {

			XSSFCell subtitleCell;

			int startColumn;
			if(subtitles.indexOf(subtitleText) == 0) {
				subtitleCell = subtitleRow.createCell(0); 
				startColumn = 0;
			}else {
				startColumn = rowNumbers[subtitles.indexOf(subtitleText)-1] + 1;
				subtitleCell = subtitleRow.createCell(startColumn);
			}

			int endColumn = rowNumbers[subtitles.indexOf(subtitleText)];

			subtitleCell.setCellValue(subtitleText);

			if(endColumn-startColumn > 0) {
				CellRangeAddress rangeAddress = new CellRangeAddress(rowNum, rowNum, startColumn, endColumn);
				sheet.addMergedRegion(rangeAddress);
			}

			subtitleCell.setCellStyle(subTitleStyle);

		}

	}

	private void generateFields(XSSFSheet sheet, List<String> fields, XSSFCellStyle fieldStyle) {

		int colNum = 0;

		XSSFRow fieldRow = sheet.createRow(2);			
		for (String fieldText : fields) {
			XSSFCell fieldCell = fieldRow.createCell(colNum++);
			fieldCell.setCellValue(fieldText);
			fieldCell.setCellStyle(fieldStyle);
		}

	}

	private void fillRow(XSSFRow row, XSSFCell[] columnsCell, OrdinePOJO ordineData, DettagliOrdinePOJO dettagliOrdine, Boolean fillArticoliRow) {	

		/*Instazio le celle*/
		for(int i = 0; i<16; i++) {			
			columnsCell[i] = row.createCell(i);
		}

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		columnsCell[0].setCellValue(ordineData.getId());
		columnsCell[1].setCellValue(ordineData.getAcquirente().getNome());
		columnsCell[2].setCellValue(ordineData.getAcquirente().getCognome());
		columnsCell[3].setCellValue(ordineData.getAcquirente().getEmail());
		columnsCell[4].setCellValue(ordineData.getTotaleOrdine());
		columnsCell[5].setCellValue(formatter.format(ordineData.getDataOrdine()));
		columnsCell[6].setCellValue(formatter.format(ordineData.getDataConsegna()));
		columnsCell[7].setCellValue(ordineData.getIndirizzoDiSpedizione().getIndirizzoDiRiferimento().getVia());
		columnsCell[8].setCellValue(ordineData.getIndirizzoDiSpedizione().getIndirizzoDiRiferimento().getNCivico());
		columnsCell[9].setCellValue(ordineData.getIndirizzoDiSpedizione().getIndirizzoDiRiferimento().getCap());

		if(ordineData.getIndirizzoDiSpedizione().getScala().isEmpty()) {
			columnsCell[10].setCellValue("n/d");
		}else {
			columnsCell[10].setCellValue(ordineData.getIndirizzoDiSpedizione().getScala());
		}

		columnsCell[11].setCellValue(ordineData.getIndirizzoDiSpedizione().getInterno());
		columnsCell[12].setCellValue(ordineData.getCartaDiCredito().getNumeroCarta());
		columnsCell[13].setCellValue(ordineData.getCartaDiCredito().getDataScadenza());
		columnsCell[14].setCellValue(ordineData.getCartaDiCredito().getCVV());
		columnsCell[15].setCellValue(ordineData.getCartaDiCredito().getNominativoProprietario());

		if(fillArticoliRow) {
			fillArticoliRow(row,columnsCell,dettagliOrdine);
		}
	}

	private void fillArticoliRow(XSSFRow row, XSSFCell[] columnsCell, DettagliOrdinePOJO dettagliOrdine) {
		for(int i = 16; i<23; i++ ) {
			columnsCell[i] = row.createCell(i);
		}

		columnsCell[16].setCellValue(dettagliOrdine.getArticoloAcquistato().getTitolo());
		columnsCell[17].setCellValue(dettagliOrdine.getArticoloAcquistato().getMarchio());
		columnsCell[18].setCellValue(dettagliOrdine.getArticoloAcquistato().getPrezzoUnitario());
		columnsCell[19].setCellValue(dettagliOrdine.getArticoloAcquistato().getColore());
		columnsCell[20].setCellValue(dettagliOrdine.getArticoloAcquistato().getDescrizione());
		columnsCell[21].setCellValue(dettagliOrdine.getArticoloAcquistato().getCategoriaDiAppartenenza().getNome_categoria());
		columnsCell[22].setCellValue(dettagliOrdine.getQuantitaArticolo());

	}

	private void fillArticoliRowSheetDiverso(XSSFRow row, XSSFCell[] columnsCell,
			DettagliOrdinePOJO dettagliOrdine) {

		for ( int i = 0; i < 8; i++ ) {
			columnsCell[i] = row.createCell(i);
		}

		columnsCell[0].setCellValue(dettagliOrdine.getOrdineDiRiferimento().getId_ordine());
		columnsCell[1].setCellValue(dettagliOrdine.getArticoloAcquistato().getTitolo());
		columnsCell[2].setCellValue(dettagliOrdine.getArticoloAcquistato().getMarchio());
		columnsCell[3].setCellValue(dettagliOrdine.getArticoloAcquistato().getPrezzoUnitario());
		columnsCell[4].setCellValue(dettagliOrdine.getArticoloAcquistato().getColore());
		columnsCell[5].setCellValue(dettagliOrdine.getArticoloAcquistato().getDescrizione());
		columnsCell[6].setCellValue(dettagliOrdine.getArticoloAcquistato().getCategoriaDiAppartenenza().getNome_categoria());
		columnsCell[7].setCellValue(dettagliOrdine.getQuantitaArticolo());



	}

	private void autoSizeColumns(XSSFSheet sheet, Integer maxColNum) {
		for (int col = 0; col < maxColNum; col++) {				
			sheet.autoSizeColumn(col); //aggiusta il width delle colonne automaticamente
		}
	}

	private XSSFCellStyle generateTitleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setBorderLeft(BorderStyle.MEDIUM);
		style.setBorderRight(BorderStyle.MEDIUM);
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 25);
		font.setBold(true);
		style.setFont(font);       
		return style;
	}

	public XSSFCellStyle generateSubTitleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 18);
		font.setBold(true);
		style.setFont(font);       
		return style;
	}

	public XSSFCellStyle generateFieldStile(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 14);
		font.setBold(true);
		style.setFont(font);       
		return style;
	}
	
	private byte[] reportUtentePDF() {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, bos);
			doc.addTitle("Il mio primo PDF");
			
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bos.toByteArray();
	}
	
	private byte[] reportAllOrdiniPDF() {
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, bos);
			
			/*Meta dati*/
			generateTitlePDF(doc, Constants.PDFReport.TITLE_FOR_ALL_ORDINI, Constants.PDFReport.SUBJECT_FOR_ALL_ORDINI, "Francesco Rossi" );
			
			doc.open();
			
			/*Title page*/
			Paragraph preface = new Paragraph(Constants.PDFReport.TITLE_FOR_ALL_ORDINI, Constants.Fonts.CAT_FONT);
			preface.setAlignment(Element.ALIGN_CENTER);
			
			addEmptyLine(preface, 1);
		
			preface.add(new Paragraph(Constants.PDFReport.TESTO_DESCRIZIONE_ALL_ORDINI,Constants.Fonts.MEDIUM_BOLD));
			
			 addEmptyLine(preface, 3);
			
			 Paragraph body = new Paragraph();  
			 
			 doc.add(preface);
			
			doc.close();
			
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bos.toByteArray();
	}
	
    private void generateTitlePDF(Document doc, String title, String subject, String author) {
    	doc.addTitle(title);
		doc.addSubject(subject);
		doc.addAuthor(author);
		doc.addCreator(Constants.PDFReport.CREATOR);
	}

	private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }


	public OrdiniBean getOrdiniBean() {
		return ordiniBean;
	}

	public void setOrdiniBean(OrdiniBean ordiniBean) {
		this.ordiniBean = ordiniBean;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public StreamedContent getFile() {
		return file;
	}

}

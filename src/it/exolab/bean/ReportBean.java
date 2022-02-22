package it.exolab.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
	
	public void generateReportUtente() {
		file = convertByteToStreamedContent(reportUtente());
	}

	public byte[] reportUtente() {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			Integer rowNum = 0;
			Integer colNum = 0;
			Integer maxColNum = Constants.ExcelReport.FIELDS_FOR_CUSTOMER.size();

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();

			/*Styles*/
			XSSFCellStyle titleStyle = generateTitleStyle(workbook);
			XSSFCellStyle subTitleStyle = generateSubTitleStyle(workbook);
			XSSFCellStyle fieldStyle = generateFieldStile(workbook);

			/*Header*/
			XSSFRow titleRow = sheet.createRow(rowNum);
			XSSFCell titleCell = titleRow.createCell(colNum);
			titleCell.setCellValue(Constants.ExcelReport.TITLE_FOR_CUSTOMER);
			titleCell.setCellStyle(titleStyle);
			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, maxColNum-1));

			/*Subtitles*/
			XSSFRow subtitleRow = sheet.createRow(++rowNum);
			for (String subtitleText  : Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER) {

				XSSFCell subtitleCell;

				int startColumn;
				if(Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER.indexOf(subtitleText) == 0) {
					subtitleCell = subtitleRow.createCell(0); 
					startColumn = 0;
				}else {
					startColumn = Constants.ExcelReport.ROW_NUMBER_OF_SUBTITLES[Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER.indexOf(subtitleText)-1] + 1;
					subtitleCell = subtitleRow.createCell(startColumn);
				}

				int endColumn = Constants.ExcelReport.ROW_NUMBER_OF_SUBTITLES[Constants.ExcelReport.SUBTITLES_FOR_CUSTOMER.indexOf(subtitleText)];

				subtitleCell.setCellValue(subtitleText);
				CellRangeAddress rangeAddress = new CellRangeAddress(rowNum, rowNum, startColumn, endColumn);
				sheet.addMergedRegion(rangeAddress);
				subtitleCell.setCellStyle(subTitleStyle);

			}

			colNum = 0; //riinizia dalla prima colonna

			/*Campi*/
			XSSFRow fieldRow = sheet.createRow(++rowNum);			
			for (String fieldText : Constants.ExcelReport.FIELDS_FOR_CUSTOMER) {
				XSSFCell fieldCell = fieldRow.createCell(colNum++);
				fieldCell.setCellValue(fieldText);
				fieldCell.setCellStyle(fieldStyle);
			}

			colNum = 0; //riinizializza a 0 numero di colonne

			/*Riempimento*/
			OrdineReport reportData = ordiniBean.getOrdineEDettagliReport();
			XSSFRow valueRow = sheet.createRow(++rowNum);
			XSSFCell columnsCell [] = new XSSFCell[maxColNum];
			
			int count = 0;
			for (DettagliOrdinePOJO dettagliOrdine : reportData.getDettagliOrdine()) {
				if(count == 0) {
					OrdinePOJO ordineData = reportData.getOrdine();
					fillFirstRow(valueRow,columnsCell,ordineData,dettagliOrdine);
					columnsCell = new XSSFCell[maxColNum];
				}else {
					XSSFRow articoliRow = sheet.createRow(++rowNum);
					fillArticoliRow(articoliRow,columnsCell,dettagliOrdine);
				}
				count++;
			}

			for (int col = 0; col < maxColNum; col++) {				
				sheet.autoSizeColumn(col); //aggiusta il width delle colonne automaticamente
			}

			workbook.write(bos);
			workbook.close();
			bos.close();


		}catch(Exception e) {  
			log.info(e.getMessage(), e);
		}
		
		return bos.toByteArray();
	}

	private void fillFirstRow(XSSFRow row, XSSFCell[] columnsCell, OrdinePOJO ordineData, DettagliOrdinePOJO dettagliOrdine) {	
		
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
		
		fillArticoliRow(row,columnsCell,dettagliOrdine);

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

	public StreamedContent convertByteToStreamedContent(byte[] byteFile) {

		InputStream is = new ByteArrayInputStream(byteFile);
		OrdineReport data = ordiniBean.getOrdineEDettagliReport();

		String filename = data.getOrdine().getAcquirente().getNome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getAcquirente().getCognome() + Constants.Caratteri.UNDERSCORE + data.getOrdine().getId() + Constants.File.XLSX;

		StreamedContent file = DefaultStreamedContent.builder()
				.name(filename)
				.contentType(Constants.ContentTypes.XLSX)
				.stream(() -> is)
				.build();

		return file;

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

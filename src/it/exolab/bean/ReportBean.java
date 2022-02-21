package it.exolab.bean;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Map;

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
import it.exolab.dto.Ordine;
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
		file = convertByteToStreamedContent(generateReportPerUtente());
	}

	public byte[] generateReportPerUtente() {

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {

			Integer rowNum = 0;
			Integer colNum = 0;

			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();

			/*-------Modelli utili------*/
			UtentePOJO utente = ordiniBean.getSessionBean().getLoggedUser();
			Ordine ordine = ordiniBean.getAddOrdine();
			Map<Articolo,Integer> dettagliOrdine = ordiniBean.getCarrelloBean().getArticoliEQuantita();

			/*Styles*/
			XSSFCellStyle subTitleStyle = generateSubTitleStyle(workbook);
			XSSFCellStyle dateFormat = generateDateFormatStyle(workbook);

			/*Header*/
			Header header = sheet.getHeader();
			header.setCenter("Report ordine");
			
			/*Subtitles*/
			XSSFRow row = sheet.createRow(rowNum);			
			for( colNum = 0 ; colNum < 7; colNum ++ ) {
				XSSFCell cell = row.createCell(colNum);

				cell.setCellStyle(subTitleStyle);

				switch ( colNum ) {
				case 0:
					cell.setCellValue("Id Ordine");
					break;
				case 1:
					cell.setCellValue("Nome Cliente");
					break;
				case 2:
					cell.setCellValue("Cognome Cliente");
					break;
				case 3:
					cell.setCellValue("Email Cliente");
					break;
				case 4:
					cell.setCellValue("Totale Ordine");
					break;
				case 5:
					cell.setCellValue("Data Ordine");
					break;
				case 6:
					cell.setCellValue("Data Consegna Prevista");
				}


				sheet.autoSizeColumn(colNum); //aggiusta il width della colonna automaticamente

			}

			/*Riempimento primo stage*/
			XSSFRow row1 = sheet.createRow(++rowNum);		
			for(Integer col = 0; col<colNum; col++) {
				XSSFCell cell = row1.createCell(col);


				switch ( col ) {
				case 0:
					cell.setCellValue(ordine.getId_ordine());
					break;
				case 1:
					cell.setCellValue(utente.getNome());
					break;
				case 2:
					cell.setCellValue(utente.getCognome());
					break;
				case 3:
					cell.setCellValue(utente.getEmail());
					break;
				case 4:
					cell.setCellValue(ordine.getTotale_ordine());
					break;
				case 5:
					cell.setCellValue(ordine.getData_ordine());
					cell.setCellStyle(dateFormat);
					break;
				case 6:
					cell.setCellValue(ordine.getData_consegna());
					cell.setCellStyle(dateFormat);
				}
			}			


			workbook.write(bos);
			workbook.close();
			bos.close();


		}catch(Exception e) {  
			log.info(e.getMessage(), e);
		}


		return bos.toByteArray();
	}


	public XSSFCellStyle generateSubTitleStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		style.setBorderTop(BorderStyle.MEDIUM);
		style.setBorderBottom(BorderStyle.MEDIUM);
		style.setAlignment(HorizontalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 15);
		font.setBold(true);
		style.setFont(font);       
		return style;
	}

	private XSSFCellStyle generateDateFormatStyle(XSSFWorkbook workbook) {
		XSSFCellStyle style = workbook.createCellStyle();
		CreationHelper creationHelper = workbook.getCreationHelper();
		style.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));
		return style;
	}

	public StreamedContent convertByteToStreamedContent(byte[] byteFile) {

		InputStream is = new ByteArrayInputStream(byteFile);

		UtentePOJO utente = ordiniBean.getSessionBean().getLoggedUser();
		Ordine ordine = ordiniBean.getAddOrdine();

		String filename = utente.getNome() + Constants.Caratteri.UNDERSCORE + utente.getCognome() + Constants.Caratteri.UNDERSCORE + ordine.getId_ordine() + Constants.File.XLSX;

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

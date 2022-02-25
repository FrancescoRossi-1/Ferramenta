package it.exolab.constants;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.poi.xssf.usermodel.XSSFCell;

import com.lowagie.text.Font;


public class Constants {

	public class Regex {
		public static final String CHECK_NOT_NUMBERS = ".+[0-9].+";
		public static final String CHECK_EMAIL = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		public static final String CHECK_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
		public static final String CHECK_CODICE_FISCALE = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
		public static final String CHECK_NOT_LETTERS = ".+[A-Za-z].+";
		public static final String CHECK_NUMERO_CARTA = "(^4[0-9]{12}(?:[0-9]{3})?$)|(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|2720)[0-9]{12}$)|(3[47][0-9]{13})|(^3(?:0[0-5]|[68][0-9])[0-9]{11}$)|(^6(?:011|5[0-9]{2})[0-9]{12}$)|(^(?:2131|1800|35\\d{3})\\d{11}$)";
	}

	public class ExceptionMessages {
		public static final String FORMATO_ERRATO = "Hai inserito un formato errato per il campo ";
		public static final String UTENTE_ESISTENTE = "Sei già registrato, loggati";
		public static final String CAMPO_RICHIESTO = "Non hai inserito il seguente campo richiesto: ";
		public static final String UNKNOWN_ERROR = "Si è verificato un'errore sconosciuto, riprova";
		public static final String UTENTE_NON_ESISTENTE = "Hai inserito una combinazione di credenziali errata.";
		public static final String NOT_SUPPORTED_IMAGE = "Hai inserito un immagine non supportata, le estensioni supportate sono jpg, png e gif";
		public static final String FILE_TOO_BIG = "Hai inserito un immagine troppo pesante, le dimensioni massime supportate sono di 65Kb";
		public static final String ARTICOLO_ESISTENTE = "Hai inserito un articolo già presente , riprova.";
		public static final String CATEGORIA_ESISENTE = "Hai inserito una categoria già presente, riprova";
		public static final String QUANTITA_ARTICOLI_MINORE = "Non puoi inserire nel tuo carrello meno di un articolo, riprova.";
		public static final String QUANTITA_ARTICOLI_MAGGIORE = "Hai inserito una quantità di articoli maggiore di quella disponibile, riprova.";
		public static final String INDIRIZZO_DI_SPEDIZIONE_ESISTENTE = "Hai inserito un indirizzo di spedizione già presente nel tuo account, ricontrolla.";
		public static final String METODO_DI_PAGAMENTO_ESISTENTE = "Hai inserito un metodo di pagamento già presente nel tuo account, ricontolla.";
		public static final String QUANTITA_ARTICOLO_MINORE = "Non puoi inserire meno di un'articolo, riprova.";
		public static final String QUANTITA_ARTICOLO_MAGGIORE = "Non puoi inserire più di 150 articoli, riprova.";
	}

	public class Messages {
		public static final String REGISTRAZIONE_AVVENUTA = "Registrazione avvenuta con successo!";
		public static final String LOGIN_AVVENUTO = "Login effettuato con successo";
		public static final String SUCCESFULLY_INSTERTED_PRODUCT = "Articolo inserito con successo!";
		public static final String DELETE_ARTICOLO_SUCCESS = "Articolo eliminato con successo!";
		public static final String SUCCESFULLY_INSTERTED_CATEGORY = "Categoria inserita con successo!";
		public static final String DELETE_CATEGORIA_SUCCESS = "Categoria eliminata con successo!";
		public static final String SUCCESSFULLY_INSERTED_PRODUCT_SHOPPING_CART = "Articolo inserito con successo nel carrello.";
		public static final String DELETE_ARTICOLO_FROM_CARRELLO = "Articolo rimosso dal carrello con successo.";
		public static final String INSERT_INDIRIZZO_SPEDIZIONE_SUCCESS = "Indirizzo di spedizione aggiunto con successo.";
		public static final String DELETE_INDIRIZZO_SPEDIZIONE_SUCCESS = "Indirizzo di spedizione rimosso con successo.";
		public static final String INSERT_METODO_PAGAMENTO_SUCCESS = "Metodo di pagamento inserito con successo.";
		public static final String DELETE_METODO_DI_PAGAMENTO_SUCCESS = "Metodo di pagamento rimosso con successo.";
	}

	public class Tabs {
		public static final String TAB_HOMEPAGE = "tabHome";
		public static final String TAB_LOGIN = "tabLogin";
		public static final String TAB_SIGNUP = "tabRegistrazione";
		public static final String TAB_ARTICOLI = "tabArticoli";
		public static final String TAB_AREA_RISERVATA = "tabAreaRiservata";
		public static final String TAB_CARRELLO = "tabCarrello";
		public static final String TAB_ORDINE = "tabOrdine";
		public static final String TAB_LOGOUT = "tabLogout";
		public static final int ID_TAB_HOMEPAGE = 0;
		public static final int ID_TAB_LOGIN = 1;
		public static final int ID_TAB_SIGNUP = 2;
		public static final int ID_TAB_ARTICOLI = 1;
		public static final int ID_TAB_AREA_RISERVATA = 2;
		public static final int ID_TAB_CARRELLO_UTENTE = 2;
		public static final int ID_TAB_CARRELLO_ADMIN = 3;
		public static final int ID_TAB_ORDINE_UTENTE = 3;
		public static final int ID_TAB_ORDINE_ADMIN = 4;
		public static final int ID_TAB_LOGOUT_UTENTE = 4;
		public static final int ID_TAB_LOGOUT_ADMIN = 5;
	}

	public static class File {
		public static final String XHTML = ".xhtml";
		public static final String XLSX = ".xlsx";
		public static final String PDF = ".pdf";
		public static final String[] SUPPORTED_IMAGE_EXTENSIONS = { "jpg", "png", "gif" };
		public static final Double MAX_SUPPORTED_DIMENSION = 65534.00; //64KB
	}

	public static class Paths {
		public static final ServletContext SERVLET_CONTEXT = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();	
		public static final String RESOURCE_PATH = SERVLET_CONTEXT.getRealPath("resources");
		public static final String RESOUSCE_PATH_REPORT_ORDINE = RESOURCE_PATH + "/reports/ordini";

	}

	public static class  Ordini {
		public static final List<String> STATI_ORDINE = new ArrayList<>( Arrays.asList("Ordinato" , "In spedizione" , "Spedito" , "Consegnato" , "Annullato"));
		public static final List<String> CIRCUITI_ACCETTATI = new ArrayList<>(Arrays.asList("VISA","AMERICAN EXPRESS","MASTER CARD"));
		public static final Long FOUR_DAYS_IN_MILLISECONDS = 345600000L;
	}

	public class ContentTypes {
		public static final String XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		public static final String PDF = "application/pdf";
	}

	public class Caratteri {
		public static final String UNDERSCORE = "_";
	}

	public static class Fonts {
		public static final Font CAT_FONT = new Font(Font.TIMES_ROMAN, 22, Font.BOLD);
		public static final Font MEDIUM_BOLD = new Font(Font.TIMES_ROMAN, 15, Font.BOLD);
		public static final Font RED_FONT = new Font(Font.TIMES_ROMAN, 12, Font.NORMAL, Color.RED);
		public static Font SUB_FONT = new Font(Font.TIMES_ROMAN, 16, Font.BOLD);
		public static final Font SMALL_BOLD = new Font(Font.TIMES_ROMAN, 12, Font.BOLD);
	}
	
	public static class PDFReport {
		public static final String TITLE_FOR_ALL_ORDINI = "Resoconto Ordini";
		public static final String TITLE_FOR_ORDINE = "Resoconto Ordine";
		public static final String CREATOR = "Francesco Rossi";
		public static final String SUBJECT_FOR_ALL_ORDINI = "Resosconto degli ordini di tutti i clienti.";
		public static final String SUBJECT_FOR_ORDINE = "Resosconto del Suo ordine";
		public static final String TESTO_DESCRIZIONE_ALL_ORDINI = "In questo documento troverai tutti gli ordini effettuati dai clienti con i loro relativi dettagli.";
		public static final String TESTO_DESCRIZIONE_ORDINE = "In questo documento troverà il resoconto del suo ordine.";
		public static final List<String> TABLE_TITLES_ALL_ORDINI_AND_ORDINI = new ArrayList<>(Arrays.asList("Ordine","Cliente","Articoli Acquistati"));
		public static final int NUMERO_CAMPI_ORDINE = 3;
		public static final int NUMERO_CAMPI_CLIENTE = 3;
		public static final int NUMERO_CAMPI_ARTICOLI = 8;
		
	}

	public static class ExcelReport {
		public static final String TITLE_FOR_CUSTOMER = "Resoconto Ordine";
		public static final String TITLE_FOR_ALL_ORDINI = "Resoconto Ordini";
		public static final String TITLE_FOR_ARTICOLI_ACQUISTATI = "Dettagli Ordini";
		public static final List<String> SUBTITLES_FOR_CUSTOMER_AND_ORDINI = new ArrayList<>(Arrays.asList("Cliente", "Ordine", "Indirizzo di Spedizione", "Metodo di Pagamento", "Articoli Acquistati"));
		public static final List<String> SUBTITLES_FOR_ARTICOLI_ACQUISTATI = new ArrayList<>(Arrays.asList("Ordine", "Articoli Acquistati"));
		public static final List<String> FIELDS_FOR_ARTICOLI_ACQUISTATI = new ArrayList<>(Arrays.asList("ID Ordine", "Nome", "Marca", "Prezzo", "Colore", "Descrizione", "Categoria", "Quantità"));
		public static final int[] ROW_NUMBER_FOR_ARTICOLI_ACQUISTATI = { 0, 7 };
		public static final List<String> FIELDS_FOR_CUSTOMER = new ArrayList<>(Arrays.asList("ID Ordine", "Nome", "Cognome", "Email", "Totale Ordine", "Data", "Data consegna", "Via", "Numero Civico", "CAP", "Scala", "Interno", "Numero Carta", "Data Scadenza", "CVV", "Intestatario", "Nome", "Marca", "Prezzo", "Colore", "Descrizione", "Categoria", "Quantità" ));	
		public static final int [] ROW_NUMBER_OF_SUBTITLES = { 3, 6, 11, 15, 21 };
		public static final List<String> FIELDS_FOR_ALL_ORDINI = new ArrayList<>(Arrays.asList("ID Ordine", "Nome", "Cognome", "Email", "Totale Ordine", "Data", "Data consegna", "Via", "Numero Civico", "CAP", "Scala", "Interno", "Numero Carta", "Data Scadenza", "CVV", "Intestatario", "INFO" ));
		public static final int [] ROW_NUMBER_OF_ALL_ORDINI = { 3, 6, 11, 15, 15 };
		public static final String INFO_ARTICOLI_ACQUISTATI = "Per consultare gli articoli acquistati rivolgersi al foglio 2";
	}
	
	public class DateFormats {

		public static final String DDMMYYYY = "dd/MM/yyyy";
		
	}

}

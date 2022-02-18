package it.exolab.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

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
		public static final String UTENTE_ESISTENTE = "Sei gi� registrato, loggati";
		public static final String CAMPO_RICHIESTO = "Non hai inserito il seguente campo richiesto: ";
		public static final String UNKNOWN_ERROR = "Si � verificato un'errore sconosciuto, riprova";
		public static final String UTENTE_NON_ESISTENTE = "Hai inserito una combinazione di credenziali errata.";
		public static final String NOT_SUPPORTED_IMAGE = "Hai inserito un immagine non supportata, le estensioni supportate sono jpg, png e gif";
		public static final String FILE_TOO_BIG = "Hai inserito un immagine troppo pesante, le dimensioni massime supportate sono di 65Kb";
		public static final String ARTICOLO_ESISTENTE = "Hai inserito un articolo gi� presente , riprova.";
		public static final String CATEGORIA_ESISENTE = "Hai inserito una categoria gi� presente, riprova";
		public static final String QUANTITA_ARTICOLI_MINORE = "Non puoi inserire nel tuo carrello meno di un articolo, riprova.";
		public static final String QUANTITA_ARTICOLI_MAGGIORE = "Hai inserito una quantit� di articoli maggiore di quella disponibile, riprova.";
		public static final String INDIRIZZO_DI_SPEDIZIONE_ESISTENTE = "Hai inserito un indirizzo di spedizione gi� presente nel tuo account, ricontrolla.";
		public static final String METODO_DI_PAGAMENTO_ESISTENTE = "Hai inserito un metodo di pagamento gi� presente nel tuo account, ricontolla.";
		public static final String QUANTITA_ARTICOLO_MINORE = "Non puoi inserire meno di un'articolo, riprova.";
		public static final String QUANTITA_ARTICOLO_MAGGIORE = "Non puoi inserire pi� di 150 articoli, riprova.";
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
		public static final String TAB_CATEGORIE = "tabCategorie";
		public static final String TAB_ARTICOLI = "tabArticoli";
		public static final String TAB_AREA_RISERVATA = "tabAreaRiservata";
		public static final String TAB_CARRELLO = "tabCarrello";
		public static final String TAB_ORDINE = "tabOrdine";
		public static final String TAB_LOGOUT = "tabLogout";
		public static final int ID_TAB_HOMEPAGE = 0;
		public static final int ID_TAB_LOGIN = 1;
		public static final int ID_TAB_SIGNUP = 2;
		public static final int ID_TAB_CATEGORIE = 1;
		public static final int ID_TAB_ARTICOLI = 2;
		public static final int ID_TAB_AREA_RISERVATA = 3;
		public static final int ID_TAB_CARRELLO = 4;
		public static final int ID_TAB_ORDINE = 5;
		public static final int ID_TAB_LOGOUT = 6;
	}
	
	public static class File {
		public static final String XHTML = ".xhtml";
		public static final String XLSX = ".xlsx";
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
	}
	
	public class Caratteri {
		public static final String UNDERSCORE = "_";
	}
	
}

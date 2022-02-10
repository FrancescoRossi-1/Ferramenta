package it.exolab.constants;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

public class Constants {

	public class Regex {
		public static final String CHECK_NOT_NUMBERS = ".+[0-9].+";
		public static final String CHECK_EMAIL = "^[\\w\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		public static final String CHECK_PASSWORD = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
		public static final String CHECK_CODICE_FISCALE = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";
		public static final String CHECK_NOT_LETTERS = ".+[A-Za-z].+";
	}

	public class ExceptionMessages {
		public static final String FORMATO_ERRATO = "Hai inserito un formato errato per il campo ";
		public static final String UTENTE_ESISTENTE = "Sei già registrato, loggati";
		public static final String CAMPO_RICHIESTO = "Non hai inserito il seguente campo richiesto: ";
		public static final String UNKNOWN_ERROR = "Si è verificato un'errore sconosciuto, riprova";
		public static final String UTENTE_NON_ESISTENTE = "Hai inserito una combinazione di credenziali errata.";
		public static final String NOT_SUPPORTED_IMAGE = "Hai inserito un immagine non supportata, le estensioni supportate sono jpg, png e gif";
		public static final String FILE_TOO_BIG = "Hai inserito un immagine troppo pesante, le dimensioni massime supportate sono di 65Kb";
		public static final String ARTICOLO_ESISTENTE = "Hai inserito un articolo già presente nel magazzino, riprova.";
		public static final String CATEGORIA_ESISENTE = "Hai inserito una categoria già presente, riprova";
		public static final String QUANTITA_ARTICOLI_MINORE = "Non puoi inserire nel tuo carrello meno di un articolo, riprova.";
		public static final String QUANTITA_ARTICOLI_MAGGIORE = "Hai inserito una quantità di articoli maggiore di quella disponibile, riprova.";
	}
	
	public class Messages {
		public static final String REGISTRAZIONE_AVVENUTA = "Registrazione avvenuta con successo!";
		public static final String LOGIN_AVVENUTO = "Login effettuato con successo";
		public static final String SUCCESFULLY_INSTERTED_PRODUCT = "Articolo inserito con successo!";
		public static final String DELETE_ARTICOLO_SUCCESS = "Articolo eliminato con successo!";
		public static final String SUCCESFULLY_INSTERTED_CATEGORY = "Categoria inserita con successo!";
		public static final String DELETE_CATEGORIA_SUCCESS = "Categoria eliminata con successo!";
	}
	
	public class Tabs {
		public static final String TAB_HOMEPAGE = "tabHome";
		public static final String TAB_LOGIN = "tabLogin";
		public static final String TAB_SIGNUP = "tabRegistrazione";
		public static final String TAB_CATEGORIE = "tabCategorie";
		public static final String TAB_ARTICOLI = "tabArticoli";
		public static final String TAB_AREA_RISERVATA = "tabAreaRiservata";
		public static final String TAB_CARRELLO = "tabCarrello";
		public static final String TAB_LOGOUT = "tabLogout";
		public static final int ID_TAB_HOMEPAGE = 0;
		public static final int ID_TAB_LOGIN = 1;
		public static final int ID_TAB_SIGNUP = 2;
		public static final int ID_TAB_CATEGORIE = 1;
		public static final int ID_TAB_ARTICOLI = 2;
		public static final int ID_TAB_AREA_RISERVATA = 3;
		public static final int ID_TAB_CARRELLO = 4;
		public static final int ID_TAB_LOGOUT = 5;
	}
	
	public static class File {
		public static final String XHTML = ".xhtml";
		public static final String[] SUPPORTED_IMAGE_EXTENSIONS = { "jpg", "png", "gif" };
		public static final Double MAX_SUPPORTED_DIMENSION = 65534.00; //64KB
	}
	
	public static class Paths {
		public static final ServletContext SERVLET_CONTEXT = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();		
	}

}

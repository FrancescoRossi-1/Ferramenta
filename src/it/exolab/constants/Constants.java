package it.exolab.constants;

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
	}
	
	public class Messages {
		public static final String REGISTRAZIONE_AVVENUTA = "Registrazione avvenuta con successo!";
		public static final String LOGIN_AVVENUTO = "Login effettuato con successo";
	}
	
	public class Tabs {
		public static final String TAB_HOMEPAGE = "tabHome";
		public static final String TAB_LOGIN = "tabLogin";
		public static final String TAB_SIGNUP = "tabRegistrazione";
		public static final String TAB_CATEGORIE = "tabCategorie";
		public static final String TAB_ARTICOLI = "tabArticoli";
		public static final String TAB_LOGOUT = "tabLogout";
		public static final String TAB_AREA_RISERVATA = "tabAreaRiservata";
		public static final int ID_TAB_HOMEPAGE = 0;
		public static final int ID_TAB_LOGIN = 1;
		public static final int ID_TAB_SIGNUP = 2;
		public static final int ID_TAB_CATEGORIE = 1;
		public static final int ID_TAB_ARTICOLI = 2;
		public static final int ID_TAB_AREA_RISERVATA = 3;
		public static final int ID_TAB_LOGOUT = 4;
	}
	
	public class FileExtension {
		public static final String XHTML = ".xhtml";
	}

}

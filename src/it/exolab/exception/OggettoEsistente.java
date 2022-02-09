package it.exolab.exception;

import it.exolab.constants.Constants;
import it.exolab.dto.Articolo;
import it.exolab.dto.Categoria;
import it.exolab.dto.Utente;

public class OggettoEsistente extends Exception {

	private static final long serialVersionUID = 5707927686717223383L;

	public OggettoEsistente(Object obj) {
		
		super(getModifiedMessage(obj));
		

	}
	
	private static String getModifiedMessage(Object obj) {
		if( obj instanceof Utente ) {
			return Constants.ExceptionMessages.UTENTE_ESISTENTE;
		}
		
		if( obj instanceof Articolo ) {
			return Constants.ExceptionMessages.ARTICOLO_ESISTENTE;
		}
		
		if( obj instanceof Categoria ) {
			return Constants.ExceptionMessages.CATEGORIA_ESISENTE;
		}
		
		return "";
	}

}

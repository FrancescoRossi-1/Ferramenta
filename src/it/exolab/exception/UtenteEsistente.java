package it.exolab.exception;

import it.exolab.constants.Constants;

public class UtenteEsistente extends Exception {

	private static final long serialVersionUID = 5707927686717223383L;
	
	public UtenteEsistente() {
		super(Constants.ExceptionMessages.UTENTE_ESISTENTE);
	}

}

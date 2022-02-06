package it.exolab.exception;

import it.exolab.constants.Constants;

public class UtenteNonEsistente extends Exception {

	private static final long serialVersionUID = 1278849099634464265L;

	public UtenteNonEsistente() {
		super(Constants.ExceptionMessages.UTENTE_NON_ESISTENTE);
	}
	
}

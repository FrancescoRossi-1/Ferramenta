package it.exolab.exception;

import it.exolab.constants.Constants;

public class FormatoErrato extends Exception {

	private static final long serialVersionUID = 3037935682121911025L;

	public FormatoErrato(String field) {
		super(Constants.ExceptionMessages.FORMATO_ERRATO + field);
	}
	
}

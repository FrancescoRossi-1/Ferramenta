package it.exolab.exception;

import it.exolab.constants.Constants;

public class CampoRichiesto extends Exception {

	private static final long serialVersionUID = 463139788528272075L;
	
	public CampoRichiesto(String field) {
		super(Constants.ExceptionMessages.CAMPO_RICHIESTO + field);
	}

	
}

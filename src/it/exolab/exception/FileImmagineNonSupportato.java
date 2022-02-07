package it.exolab.exception;

import it.exolab.constants.Constants;

public class FileImmagineNonSupportato extends Exception {

	private static final long serialVersionUID = 1L;

	public FileImmagineNonSupportato() {
		super(Constants.ExceptionMessages.NOT_SUPPORTED_IMAGE);
	}
	
}

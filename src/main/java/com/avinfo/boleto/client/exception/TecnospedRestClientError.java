package com.avinfo.boleto.client.exception;

public class TecnospedRestClientError extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8913289451635644785L;

	public TecnospedRestClientError() {
	}

	public TecnospedRestClientError(String message) {
		super(message);
	}

	public TecnospedRestClientError(Throwable cause) {
		super(cause);
	}

	public TecnospedRestClientError(String message, Throwable cause) {
		super(message, cause);
	}

	public TecnospedRestClientError(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

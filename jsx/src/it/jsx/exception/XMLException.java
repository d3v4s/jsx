package it.jsx.exception;

public class XMLException extends java.lang.Exception {
	private static final long serialVersionUID = 7129461355834142096L;

	public XMLException() {
	}

	public XMLException(String message) {
		super(message);
	}

	public XMLException(Throwable cause) {
		super(cause);
	}

	public XMLException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

package it.jsx.exception;

/**
 * Class for generic JSX file exception
 * @author Andrea Serra
 *
 */
public class JSXLockException extends Exception {
	private static final long serialVersionUID = 3897240434851969596L;

	public JSXLockException() {
	}

	public JSXLockException(String message) {
		super(message);
	}

	public JSXLockException(Throwable cause) {
		super(cause);
	}

	public JSXLockException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSXLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

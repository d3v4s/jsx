package exception;

/**
 * Class for XML exception
 * @author Andrea Serra
 *
 */
public class JSXException extends Exception {
	private static final long serialVersionUID = -1243194780849295228L;

	public JSXException() {
	}

	public JSXException(String message) {
		super(message);
	}

	public JSXException(Throwable cause) {
		super(cause);
	}

	public JSXException(String message, Throwable cause) {
		super(message, cause);
	}

	public JSXException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

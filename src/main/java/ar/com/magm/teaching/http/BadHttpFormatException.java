package ar.com.magm.teaching.http;

public class BadHttpFormatException extends Exception {


	public BadHttpFormatException() {
		super();
	}

	public BadHttpFormatException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadHttpFormatException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadHttpFormatException(String message) {
		super(message);
	}

	public BadHttpFormatException(Throwable cause) {
		super(cause);
	}

	private static final long serialVersionUID = -1816636977692523539L;

}

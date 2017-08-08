package ar.com.magm.teaching.expression;

public class ExpressionParseException extends Exception {


	private static final long serialVersionUID = -4851894181653825357L;

	public ExpressionParseException() {
	}

	public ExpressionParseException(String message) {
		super(message);
	}

	public ExpressionParseException(Throwable cause) {
		super(cause);
	}

	public ExpressionParseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExpressionParseException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}

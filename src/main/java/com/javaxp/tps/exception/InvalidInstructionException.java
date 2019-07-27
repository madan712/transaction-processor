package com.javaxp.tps.exception;

/**
 * The Class InvalidInstructionException.
 */
public class InvalidInstructionException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid instruction exception.
	 *
	 * @param e the e
	 */
	public InvalidInstructionException(Exception e) {
		super(e);
	}

	/**
	 * Instantiates a new invalid instruction exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public InvalidInstructionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

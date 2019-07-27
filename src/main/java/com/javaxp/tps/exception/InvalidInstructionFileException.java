package com.javaxp.tps.exception;

/**
 * The Class InvalidInstructionFileException.
 */
public class InvalidInstructionFileException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new invalid instruction file exception.
	 *
	 * @param message the message
	 */
	public InvalidInstructionFileException(String message) {
		super(message);
	}
	
	/**
	 * Instantiates a new invalid instruction file exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public InvalidInstructionFileException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

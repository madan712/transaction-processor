package com.javaxp.tps.exception;

/**
 * The Class TransactionExecutionException.
 */
public class TransactionExecutionException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new transaction execution exception.
	 *
	 * @param message the message
	 */
	public TransactionExecutionException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new transaction execution exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public TransactionExecutionException(String message, Throwable throwable) {
		super(message, throwable);
	}
}

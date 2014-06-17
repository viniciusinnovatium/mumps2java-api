package br.com.innovatium.mumps2java.dataaccess.exception;

public class SQLExecutionException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6411215259586116124L;

	public SQLExecutionException(String message, Throwable cause){
		super(message, cause);
	}
}

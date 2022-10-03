package com.meli.mutants.rest.services;

/**
 * Excepcion controlada para las excepciones que se pueden presentar en el proceso de verificacion de mutantes.
 * @author Jhon Osorio
 *
 */
public class MutantsException extends Exception {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 1434937867642298158L;
	
	
	/**
	 * Constructor.
	 * @param message - mensaje relacionado.
	 */
	public MutantsException(String message){
		super(message);
	}

}

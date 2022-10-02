package com.meli.mutans.rest.services;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que mapea la entrada json del servicio.
 * 
 * @author Jhon Osorio
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MutantAdn implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 5075504446310933462L;

	/**
	 * Adn
	 */
	protected String[] dna;

	/**
	 * Get
	 * @return
	 */
	public String[] getDna() {
		return dna;
	}

	/**
	 * Set
	 * @param dna
	 */
	public void setDna(String[] dna) {
		this.dna = dna;
	}

}

package com.meli.mutants.rest.services;

import java.io.Serializable;
import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que mapea la entrada json del servicio.
 * 
 * @author Jhon Osorio
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MutantDna implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 5075504446310933462L;

	/**
	 * Dna
	 */
	protected String[] dna;

	/**
	 * Get
	 * 
	 * @return
	 */
	public String[] getDna() {
		return dna;
	}

	/**
	 * Set
	 * 
	 * @param dna
	 */
	public void setDna(String[] dna) {
		this.dna = dna;
	}

	/**
	 * Get
	 * 
	 * @return
	 */
	public Integer getKey() {
		Integer key = null;
		if (this.dna != null) {
			key = Arrays.asList(this.dna).hashCode();
		}
		return key;
	}

}

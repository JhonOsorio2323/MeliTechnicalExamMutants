package com.meli.mutants.rest.services.stats;

import java.io.Serializable;

/**
 * Respuesta con el resultado de las estadisticas de verificacion de dna
 * mutantes.
 * 
 * @author Jhon Osorio
 *
 */
public class MutantsStatsResult implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 2683307554992033673L;

	/**
	 * Indica el número de adns mutantes verificados.
	 */
	protected Integer count_mutant_dna;

	/**
	 * Indica el número de adns Humans verificados.
	 */
	protected Integer count_human_dna;

	/**
	 * Porcentaje.
	 */
	protected Double ratio;

	/**
	 * @return the count_mutant_dna
	 */
	public Integer getCount_mutant_dna() {
		return count_mutant_dna;
	}

	/**
	 * @param count_mutant_dna the count_mutant_dna to set
	 */
	public void setCount_mutant_dna(Integer count_mutant_dna) {
		this.count_mutant_dna = count_mutant_dna;
	}

	/**
	 * @return the count_human_dna
	 */
	public Integer getCount_human_dna() {
		return count_human_dna;
	}

	/**
	 * @param count_human_dna the count_human_dna to set
	 */
	public void setCount_human_dna(Integer count_human_dna) {
		this.count_human_dna = count_human_dna;
	}

	/**
	 * @return the ratio
	 */
	public Double getRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}

}

package com.meli.mutants.rest.repository;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

/**
 * Tabla que guarda las verificaciones realizadas por el proceso de mutantes.
 * 
 * @author Jhon Osorio
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@DynamoDbBean
public class MutantDnaTable implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -900628519283840637L;

	/**
	 * Id de la tabla
	 */
	protected Integer key;

	/**
	 * Indica si el dna fue validado como mutante.
	 */
	protected Boolean mutant;

	/**
	 * Dna
	 */
	protected List<String> dna;

	/**
	 * Key
	 */
	@DynamoDbPartitionKey
	@DynamoDbAttribute("DnaKey")
	public Integer getKey() {
		return key;
	}

	/**
	 * Key
	 */
	public void setKey(Integer key) {
		this.key = key;
	}

	/**
	 * Mutant
	 */
	@DynamoDbAttribute("Mutant")
	public Boolean getMutant() {
		return this.mutant;
	}

	/**
	 * Mutant
	 */
	public void setMutant(Boolean mutant) {
		this.mutant = mutant;
	}

	/**
	 * Variable
	 */
	@DynamoDbAttribute("Dna")
	public List<String> getDna() {
		return this.dna;
	}

	/**
	 * Variable
	 */
	public void setDna(List<String> dna) {
		this.dna = dna;
	}

}

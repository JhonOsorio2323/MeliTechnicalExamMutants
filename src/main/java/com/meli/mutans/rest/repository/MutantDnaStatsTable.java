package com.meli.mutans.rest.repository;

import java.io.Serializable;

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
public class MutantDnaStatsTable implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = -1474120496305426245L;

	/**
	 * Id de la tabla
	 */
	protected String key;

	/**
	 * Indica el número de adns mutantes verificados.
	 */
	protected Integer numberDnaMutants;
	
	/**
	 * Indica el número de adns Humans verificados.
	 */
	protected Integer numberDnaHumans;


	/**
	 * Key
	 */
	@DynamoDbPartitionKey
	@DynamoDbAttribute("DnaStatsKey")
	public String getKey() {
		return key;
	}

	/**
	 * Key
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * Mutant
	 */
	@DynamoDbAttribute("NumberDnaMutants")
	public Integer getNumberDnaMutants() {
		return this.numberDnaMutants;
	}

	/**
	 * Mutant
	 */
	public void setNumberDnaMutants(Integer number) {
		this.numberDnaMutants = number;
	}

	/**
	 * Humans
	 */
	@DynamoDbAttribute("NumberDnaHumans")
	public Integer getNumberDnaHumans() {
		return this.numberDnaHumans;
	}

	/**
	 * Humans
	 */
	public void setNumberDnaHumans(Integer number) {
		this.numberDnaHumans = number;
	}

}

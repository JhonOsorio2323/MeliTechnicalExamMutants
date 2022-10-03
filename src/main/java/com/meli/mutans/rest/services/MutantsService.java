package com.meli.mutans.rest.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meli.mutans.rest.repository.MutantDnaTable;
import com.meli.mutans.rest.repository.MutantsRepository;

/**
 * Servicio para exponer metodos con funcionalidad para la validación dna
 * mutante.
 * 
 * @author Jhon Osorio
 *
 */
@Service
public class MutantsService {

	/** Servicio para las estadisticas de verificación del dna */
	@Autowired
	MutantsStatsService mutantsStatsService;

	/**
	 * Repositorio.
	 */
	private final MutantsRepository mutantsRepository;

	/**
	 * Constructor.
	 */
	public MutantsService(MutantsRepository dataRepository) {
		this.mutantsRepository = dataRepository;
	}

	/**
	 * Método para validar si un adn pertenece a un mutante.
	 * 
	 * @param dnaInformation - informacion con la cadena adn requerida.
	 * @return true si el adn pertenece a un mutante.
	 */
	private boolean isMutant(MutantDna dnaInformation) {

		String[] dnaArray = dnaInformation.getDna();
		int arrayLength = dnaArray.length;
		for (int indexRow = 0; indexRow < arrayLength; indexRow++) {
			String row = dnaArray[indexRow];
			// Se valida horizontalmente si se tiene una cadena mutante.
			if (containsDnaMutant(row)) {
				return true;
			}

			// se valida por columna y diagonal
			for (int indexColumn = 0; indexColumn < row.length(); indexColumn++) {
				String columnCharacter = row.charAt(indexColumn) + "";
				String nextRow1 = getRow(indexRow + 1, dnaArray);
				String nextRow2 = getRow(indexRow + 2, dnaArray);
				String nextRow3 = getRow(indexRow + 3, dnaArray);

				if (containsDnaMutant(columnCharacter + getColumn(indexColumn, nextRow1)
						+ getColumn(indexColumn, nextRow2) + getColumn(indexColumn, nextRow3))
						|| containsDnaMutant(columnCharacter + getColumn(indexColumn + 1, nextRow1)
								+ getColumn(indexColumn + 2, nextRow2) + getColumn(indexColumn + 3, nextRow3))) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * Proceso para validar si un adn pertenece a un mutante.
	 * 
	 * @param dnaInformation - informacion con la cadena adn requerida.
	 * @return true si el adn pertenece a un mutante.
	 * @throws MutantsException - se lanza cuando se presenta algun error dentro del
	 *                          procesamiento.
	 */
	public boolean processMutantDnaValidation(MutantDna dnaInformation) throws MutantsException {

		if (dnaInformation == null || dnaInformation.getDna() == null || dnaInformation.getDna().length == 0) {
			throw new MutantsException("Dna not informed or type is invalid.");
		}

		boolean dnaMutant = false;
		Integer dnaKey = dnaInformation.getKey();
		MutantDnaTable dnaBd = mutantsRepository.findDnaById(dnaKey);
		if (dnaBd == null) {
			dnaMutant = isMutant(dnaInformation);
			dnaBd = new MutantDnaTable();
			dnaBd.setKey(dnaKey);
			dnaBd.setDna(Arrays.asList(dnaInformation.getDna()));
			dnaBd.setMutant(dnaMutant);
			mutantsRepository.createDna(dnaBd);
			mutantsStatsService.processStats(dnaMutant, dnaInformation);
		} else {
			dnaMutant = dnaBd.getMutant();
		}

		return dnaMutant;

	}

	/**
	 * Obtiene la fila del arreglo.
	 */
	private String getRow(int rowNumber, String[] array) {
		if (rowNumber > 0 && array != null && rowNumber < array.length) {
			return array[rowNumber];
		}
		return "";
	}

	/**
	 * Obtiene la fila
	 */
	private String getColumn(int columnNumber, String row) {
		if (columnNumber > 0 && row != null && columnNumber < row.length()) {
			return "" + row.charAt(columnNumber);
		}
		return "";
	}

	/**
	 * Indicia si una cadena contiene dna mutante.
	 */
	private boolean containsDnaMutant(String dna) {
		if (dna == null) {
			return false;
		}
		// Descomentar esta linea si no importa si la cadena de adn viene en minusculas.
		// dna = dna.toUpperCase();
		String dnaT = "TTTT";
		String dnaG = "GGGG";
		String dnaC = "CCCC";
		String dnaA = "AAAA";
		return dna.contains(dnaT) || dna.contains(dnaG) || dna.contains(dnaC) || dna.contains(dnaA);
	}

}

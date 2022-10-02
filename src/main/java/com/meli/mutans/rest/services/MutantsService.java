package com.meli.mutans.rest.services;

import org.springframework.stereotype.Service;

@Service
public class MutantsService {

	/**
	 * Método para validar si un adn pertenece a un mutante.
	 * 
	 * @param informacionAdn - informacion con la cadena adn requerida.
	 * @return
	 * @return true si el adn pertenece a un mutante.
	 * @throws MutantsException - se lanza cuando se presenta algun error dentro del
	 *                          procesamiento.
	 */
	public boolean isMutant(MutantAdn informacionAdn) throws MutantsException {

		if (informacionAdn == null || informacionAdn.getDna() == null || informacionAdn.getDna().length == 0) {
			throw new MutantsException("Adn not informed or type is invalid.");
		}
		String[] adnArray = informacionAdn.getDna();
		int arrayLength = adnArray.length;
		int nroLetrasConsecutivas = 4;
		// Para tener cuatro letras consecutivas, el tamaño de array debe ser minimo de
		// 4.
		if (arrayLength < nroLetrasConsecutivas) {
			return false;
		}
		for (int i = 0; i < arrayLength; i++) {
			String row = adnArray[i];
			// Se valida horizontalmente si se tiene una cadena mutante.
			if (containsDnaMutant(row)) {
				return true;
			}

			// se valida por columna
			for (int j = 0; j < row.length() - 3; j++) {
				String caracterColumna = row.charAt(j) + "";
				String filaSiguiente1 = getRow(i + 1, adnArray);
				String filaSiguiente2 = getRow(i + 2, adnArray);
				String filaSiguiente3 = getRow(i + 3, adnArray);

				if (containsDnaMutant(caracterColumna + getColumn(j, filaSiguiente1) + getColumn(j, filaSiguiente2)
						+ getColumn(j, filaSiguiente3))
						|| containsDnaMutant(caracterColumna + getColumn(j + 1, filaSiguiente1)
								+ getColumn(j + 2, filaSiguiente2) + getColumn(j + 3, filaSiguiente3))) {
					return true;
				}

			}
		}
		return false;
	}

	/**
	 * Obtiene la fila
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
	 * 
	 * @param dna
	 * @return
	 */
	private boolean containsDnaMutant(String dna) {
		if (dna == null) {
			return false;
		}
		// Descomentar esta linea si no importa si la cadena de adn viene en minusculas.
		// dna = dna.toUpperCase();
		String adnT = "TTTT";
		String adnG = "GGGG";
		String adnC = "CCCC";
		String adnA = "AAAA";
		return dna.contains(adnT) || dna.contains(adnG) || dna.contains(adnC) || dna.contains(adnA);
	}

}

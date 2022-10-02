package com.meli.mutans.rest.services;

import org.springframework.stereotype.Service;

@Service
public class MutantsStatsService {

	/**
	 * Método para asignar estadisticas de uso del servicio de validacion de adn.
	 * @param informacionAdn - informacion con la cadena adn requerida.
	 * @param adnMutant si el adn pertenece a un mutante.
	 */
	public void processStats(boolean adnMutant, MutantAdn adnInformation) {
		
		//TODO registrar la información en dynamo.
		
	}


}

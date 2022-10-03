package com.meli.mutants.rest.services.stats;

import org.springframework.stereotype.Service;

import com.meli.mutants.rest.repository.MutantDnaStatsTable;
import com.meli.mutants.rest.repository.MutantsStatsRepository;
import com.meli.mutants.rest.services.MutantDna;
import com.meli.mutants.rest.services.MutantsException;

/**
 * Servicio para las estadisticas de las validaciones de dna mutante.
 * @author Jhon Osorio
 *
 */
@Service
public class MutantsStatsService {

	/**
	 * Repositorio.
	 */
	private final MutantsStatsRepository mutantsRepository;

	/**
	 * Constructor.
	 */
	public MutantsStatsService(MutantsStatsRepository dataRepository) {
		this.mutantsRepository = dataRepository;
	}

	/**
	 * Método para asignar estadisticas de uso del servicio de validacion de adn.
	 * 
	 * @param informacionAdn - informacion con la cadena adn requerida.
	 * @param adnMutant      si el adn pertenece a un mutante.
	 * @throws MutantsException
	 */
	public void processStats(boolean adnMutant, MutantDna adnInformation) throws MutantsException {

		MutantDnaStatsTable stats = mutantsRepository.findCreateDna();
		if (adnMutant) {
			stats.setNumberDnaMutants(stats.getNumberDnaMutants() + 1);
		} else {
			stats.setNumberDnaHumans(stats.getNumberDnaHumans() + 1);
		}
		mutantsRepository.updateDna(stats);
	}

	/**
	 * Método para encontrar las estadisticas de uso del servicio de validacion de adn.
	 * 
	 * @param informacionAdn - informacion con la cadena adn requerida.
	 * @param adnMutant      si el adn pertenece a un mutante.
	 * @throws MutantsException
	 */
	public MutantsStatsResult findStats() throws MutantsException {

		MutantDnaStatsTable stats = mutantsRepository.findCreateDna();
		double ratio = 0d;
		if (stats.getNumberDnaHumans() > 0 && stats.getNumberDnaMutants() >= 0) {
			//Se realiza la conversion a double para que no pierda precision decimal.
			ratio = (double) stats.getNumberDnaMutants() / (double) stats.getNumberDnaHumans();
		}

		MutantsStatsResult mutantsStatsResult = new MutantsStatsResult();
		mutantsStatsResult.setCount_mutant_dna(stats.getNumberDnaMutants());
		mutantsStatsResult.setCount_human_dna(stats.getNumberDnaHumans());
		mutantsStatsResult.setRatio(ratio);
		return mutantsStatsResult;

	}

}

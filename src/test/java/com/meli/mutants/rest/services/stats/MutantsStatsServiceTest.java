package com.meli.mutants.rest.services.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.meli.mutants.rest.controllers.MutantsStatsController;
import com.meli.mutants.rest.repository.MutantDnaStatsTable;
import com.meli.mutants.rest.repository.MutantsStatsRepository;
import com.meli.mutants.rest.services.MutantsException;

/**
 * Clase de pruebas para el servicio de validacion de dna mutante.
 * 
 * @author Jhon Osorio
 *
 */
@SpringBootTest
public class MutantsStatsServiceTest {

	@Autowired
	private MutantsStatsController controller;

	@Mock
	private MutantsStatsRepository mutantsStatsRepository;

	/** Variable */
	String nameVariableRepository = "mutantsRepository";

	/** Variable */
	String nameVariableStatsService = "mutantsService";

	/**
	 * Prueba el consumo del servicio de estadisticas. Valida los campos del
	 * servicio y el ratio dependiendo del número de humanos y mutantes validados.
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testStatsDna() throws IOException, MutantsException {
		HttpServletResponse response = new MockHttpServletResponse();

		MutantsStatsService initialStatsService = (MutantsStatsService) ReflectionTestUtils.getField(controller,
				nameVariableStatsService);
		MutantsStatsRepository initialStatsRepository = (MutantsStatsRepository) ReflectionTestUtils
				.getField(initialStatsService, nameVariableRepository);
		try {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, mutantsStatsRepository);
			MutantDnaStatsTable statsTable = new MutantDnaStatsTable();
			int humansCount = 20;
			int mutantsCount = 10;
			statsTable.setNumberDnaHumans(humansCount);
			statsTable.setNumberDnaMutants(mutantsCount);
			Mockito.when(mutantsStatsRepository.findCreateDna()).thenReturn(statsTable);
			MutantsStatsResult resultado = controller.mutantsStats(response);
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).findCreateDna();
			assertEquals(HttpServletResponse.SC_OK, response.getStatus());
			assertEquals(humansCount, resultado.getCount_human_dna());
			assertEquals(mutantsCount, resultado.getCount_mutant_dna());
			assertEquals((double) mutantsCount / (double) humansCount, resultado.getRatio());
		} finally {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, initialStatsRepository);
		}
	}

	/**
	 * Prueba que se muestre el mensaje de error si ocurre un evento inesperado en el servicio.
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testStatsDnaError() throws IOException, MutantsException {
		HttpServletResponse response = new MockHttpServletResponse();

		String errorMessage = "read register bd error";
		MutantsStatsService initialStatsService = (MutantsStatsService) ReflectionTestUtils.getField(controller,
				nameVariableStatsService);
		MutantsStatsRepository initialStatsRepository = (MutantsStatsRepository) ReflectionTestUtils
				.getField(initialStatsService, nameVariableRepository);
		try {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, mutantsStatsRepository);
			Mockito.when(mutantsStatsRepository.findCreateDna()).thenThrow(new MutantsException(errorMessage));
			controller.mutantsStats(response);
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).findCreateDna();
			assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
			assertEquals(errorMessage, ((MockHttpServletResponse) response).getErrorMessage());
		} finally {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, initialStatsRepository);
		}
	}
}

package com.meli.mutants.rest.services.stats;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;

import com.meli.mutants.rest.controllers.MutantsController;
import com.meli.mutants.rest.repository.MutantDnaStatsTable;
import com.meli.mutants.rest.repository.MutantsRepository;
import com.meli.mutants.rest.repository.MutantsStatsRepository;
import com.meli.mutants.rest.services.MutansSearchMethod;
import com.meli.mutants.rest.services.MutantDna;
import com.meli.mutants.rest.services.MutantsException;
import com.meli.mutants.rest.services.MutantsService;

/**
 * Clase de pruebas para el servicio de validacion de dna mutante.
 * 
 * @author Jhon Osorio
 *
 */
@SpringBootTest
public class MutantsUpdateStatsServiceTest {

	@Autowired
	private MutantsController controller;

	@Mock
	private MutantsService service;

	@Mock
	private MutantsStatsService statsService;

	@Mock
	private MutantsRepository mutantsRepository;

	@Mock
	private MutantsStatsRepository mutantsStatsRepository;

	/** Variable */
	String nameVariableRepository = "mutantsRepository";

	/** Variable */
	String nameVariableService = "mutantsService";

	/** Variable */
	String nameVariableStatsService = "mutantsStatsService";

	/**
	 * Prueba la creacion del registro de estadisticas y la actualizacion con el
	 * nuevo registro para cuando el dna detectado es mutante
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdStatsRegisterIfNotExistsWithMutantDna() throws IOException, MutantsException {
		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		MutantDna dna = new MutantDna();
		String[] dnaArray = { "ATTT", "ACCC", "AGGG", "ACGC" };
		dna.setDna(dnaArray);

		MutantsService initialService = (MutantsService) ReflectionTestUtils.getField(controller, nameVariableService);
		MutantsStatsService initialStatsService = (MutantsStatsService) ReflectionTestUtils.getField(initialService,
				nameVariableStatsService);
		MutantsRepository initialRepository = (MutantsRepository) ReflectionTestUtils.getField(initialService,
				nameVariableRepository);
		MutantsStatsRepository initialStatsRepository = (MutantsStatsRepository) ReflectionTestUtils
				.getField(initialStatsService, nameVariableRepository);
		try {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, mutantsStatsRepository);
			ReflectionTestUtils.setField(initialService, nameVariableRepository, mutantsRepository);
			Mockito.when(mutantsRepository.findDnaById(dna.getKey())).thenReturn(null);
			MutantDnaStatsTable statsTable = new MutantDnaStatsTable();
			int humansCount = 10;
			int mutantsCount = 20;
			statsTable.setNumberDnaHumans(humansCount);
			statsTable.setNumberDnaMutants(mutantsCount);
			Mockito.when(mutantsStatsRepository.findCreateDna()).thenReturn(statsTable);
			Mockito.doNothing().when(mutantsStatsRepository).updateDna(statsTable);

			controller.mutants(response, request, dna, MutansSearchMethod.DEFAULT.getCode());
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).findCreateDna();
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).updateDna(statsTable);

			assertEquals(HttpServletResponse.SC_OK, response.getStatus());
			assertEquals(humansCount, statsTable.getNumberDnaHumans());
			assertEquals(mutantsCount + 1, statsTable.getNumberDnaMutants());
		} finally {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, initialStatsRepository);
			ReflectionTestUtils.setField(initialService, nameVariableRepository, initialRepository);
		}
	}

	/**
	 * Prueba la creacion del registro de estadisticas y la actualizacion con el
	 * nuevo registro para cuando el dna detectado es humano
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdStatsRegisterIfNotExistsWithHumanDna() throws IOException, MutantsException {
		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		MutantDna dna = new MutantDna();
		String[] dnaArray = { "CTTT", "ACCC", "AGGG", "ACGC" };
		dna.setDna(dnaArray);

		MutantsService initialService = (MutantsService) ReflectionTestUtils.getField(controller, nameVariableService);
		MutantsStatsService initialStatsService = (MutantsStatsService) ReflectionTestUtils.getField(initialService,
				nameVariableStatsService);
		MutantsRepository initialRepository = (MutantsRepository) ReflectionTestUtils.getField(initialService,
				nameVariableRepository);
		MutantsStatsRepository initialStatsRepository = (MutantsStatsRepository) ReflectionTestUtils
				.getField(initialStatsService, nameVariableRepository);
		try {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, mutantsStatsRepository);
			ReflectionTestUtils.setField(initialService, nameVariableRepository, mutantsRepository);
			Mockito.when(mutantsRepository.findDnaById(dna.getKey())).thenReturn(null);
			MutantDnaStatsTable statsTable = new MutantDnaStatsTable();
			int humansCount = 10;
			int mutantsCount = 20;
			statsTable.setNumberDnaHumans(humansCount);
			statsTable.setNumberDnaMutants(mutantsCount);
			Mockito.when(mutantsStatsRepository.findCreateDna()).thenReturn(statsTable);
			Mockito.doNothing().when(mutantsStatsRepository).updateDna(statsTable);

			controller.mutants(response, request, dna, MutansSearchMethod.DEFAULT.getCode());
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).findCreateDna();
			Mockito.verify(mutantsStatsRepository, Mockito.times(1)).updateDna(statsTable);

			assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, response.getStatus());
			assertEquals(humansCount + 1, statsTable.getNumberDnaHumans());
			assertEquals(mutantsCount, statsTable.getNumberDnaMutants());
		} finally {
			ReflectionTestUtils.setField(initialStatsService, nameVariableRepository, initialStatsRepository);
			ReflectionTestUtils.setField(initialService, nameVariableRepository, initialRepository);
		}
	}
}

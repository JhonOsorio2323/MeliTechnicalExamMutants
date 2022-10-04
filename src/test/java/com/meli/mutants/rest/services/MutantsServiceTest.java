package com.meli.mutants.rest.services;

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
import com.meli.mutants.rest.repository.MutantDnaTable;
import com.meli.mutants.rest.repository.MutantsRepository;
import com.meli.mutants.rest.services.stats.MutantsStatsService;

/**
 * Clase de pruebas para el servicio de validacion de dna mutante.
 * 
 * @author Jhon Osorio
 *
 */
@SpringBootTest
public class MutantsServiceTest {

	@Autowired
	private MutantsController controller;

	@Mock
	private MutantsService service;

	@Mock
	private MutantsStatsService statsService;

	@Mock
	private MutantsRepository mutantsRepository;

	/** Variable */
	String nameVariableRepository = "mutantsRepository";

	/** Variable */
	String nameVariableService = "mutantsService";

	/** Variable */
	String nameVariableStatsService = "mutantsStatsService";

	/**
	 * Prueba el mensaje de error cuando no se envian los parametros requeridos para
	 * el procesamiento.
	 */
	@Test
	public void testDnaInformationRequired() throws IOException {
		String errorMessage = "Dna not informed or type is invalid.";
		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		controller.mutants(response, request, null, null);

		// Se valida con parámetro nulo.
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
		assertEquals(errorMessage, ((MockHttpServletResponse) response).getErrorMessage());

		response = new MockHttpServletResponse();

		// Se valida con info adn vacia
		MutantDna dna = new MutantDna();
		controller.mutants(response, request, dna, null);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
		assertEquals(errorMessage, ((MockHttpServletResponse) response).getErrorMessage());

		response = new MockHttpServletResponse();

		// Se valida con info adn vacia
		dna = new MutantDna();
		String[] dnaArray = {};
		dna.setDna(dnaArray);
		controller.mutants(response, request, dna, null);
		assertEquals(HttpServletResponse.SC_BAD_REQUEST, response.getStatus());
		assertEquals(errorMessage, ((MockHttpServletResponse) response).getErrorMessage());

	}

	/**
	 * Prueba la respuesta con dna mutant confirmado.
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaValidationResponseMutant() throws IOException, MutantsException {

		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		MutantsService initialService = (MutantsService) ReflectionTestUtils.getField(controller, nameVariableService);
		try {
			ReflectionTestUtils.setField(controller, nameVariableService, service);

			MutantDna dna = new MutantDna();
			String[] dnaArray = {};
			dna.setDna(dnaArray);

			Mockito.when(service.processMutantDnaValidation(dna, null)).thenReturn(true);
			controller.mutants(response, request, dna, null);
			Mockito.verify(service, Mockito.times(1)).processMutantDnaValidation(dna, null);
			assertEquals(HttpServletResponse.SC_OK, response.getStatus());
		} finally {
			ReflectionTestUtils.setField(controller, nameVariableService, initialService);
		}
	}

	/**
	 * Prueba la respuesta con dna humano confirmado.
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaValidationResponseHuman() throws IOException, MutantsException {
		String errorMessage = "ADN informed is not mutant";
		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		MutantsService initialService = (MutantsService) ReflectionTestUtils.getField(controller, nameVariableService);
		try {
			ReflectionTestUtils.setField(controller, nameVariableService, service);

			MutantDna dna = new MutantDna();
			String[] dnaArray = {};
			dna.setDna(dnaArray);

			Mockito.when(service.processMutantDnaValidation(dna, null)).thenReturn(false);
			controller.mutants(response, request, dna, null);
			Mockito.verify(service, Mockito.times(1)).processMutantDnaValidation(dna, null);
			assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
			assertEquals(errorMessage, ((MockHttpServletResponse) response).getErrorMessage());
		} finally {
			ReflectionTestUtils.setField(controller, nameVariableService, initialService);
		}
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado horizontalmente.
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenHorizontalDnaMutantIncludes()
			throws IOException, MutantsException {

		String[] dnaArray = { "AAAT", "AAAA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, null, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna es humano
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenDnaHumanIncludes() throws IOException, MutantsException {
		String[] dnaArray = { "AAAT" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_FORBIDDEN, null, null);
	}

	/**
	 * Prueba que se retorne el registro que ya fue procesado en la primer
	 * validacion y no se cree uno nuevo
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaReturnBdRegisterIfExists() throws IOException, MutantsException {
		String[] dnaArray = { "AAAT", "TTTG" };
		MutantDnaTable tableRow = new MutantDnaTable();
		tableRow.setMutant(false);
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_FORBIDDEN, null, tableRow);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado horizontalmente por el metodo recursivo
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenHorizontalDnaMutantIncludesRecursiveMethod()
			throws IOException, MutantsException {
		String[] dnaArray = { "AAAT", "AAAA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.RECURSIVE, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado horizontalmente por el metodo combinado
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenHorizontalDnaMutantIncludesCombinedMethod()
			throws IOException, MutantsException {
		String[] dnaArray = { "AAAT", "AAAA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.COMBINED, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado diagonal por el metodo combinado
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenDiagonalDnaMutantIncludesCombinedMethod()
			throws IOException, MutantsException {
		String[] dnaArray = { "ATTT", "CACC", "GGAG", "TCGA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.COMBINED, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado diagonal por el metodo recursivo
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenDiagonalDnaMutantIncludesRecursiveMethod()
			throws IOException, MutantsException {
		String[] dnaArray = { "ATTT", "CACC", "GGAG", "TCGA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.RECURSIVE, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado diagonal por el metodo default
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenDiagonalDnaMutantIncludes() throws IOException, MutantsException {
		String[] dnaArray = { "ATTT", "CACC", "GGAG", "TCGA" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.DEFAULT, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado vertical por el metodo default
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenVerticalDnaMutantIncludes() throws IOException, MutantsException {
		String[] dnaArray = { "ATTT", "ACCC", "AGGG", "ACGC" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.DEFAULT, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado vertical por el metodo recursivo
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenVerticalDnaMutantIncludesRecursiveMethod()
			throws IOException, MutantsException {

		String[] dnaArray = { "ATTT", "ACCC", "AGGG", "ACGC" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.RECURSIVE, null);
	}

	/**
	 * Prueba la creación del registro en bd cuando no existe y el dna fue
	 * encontrado vertical por el metodo combinado
	 * 
	 * @throws MutantsException
	 */
	@Test
	public void testDnaCreateBdRegisterIfNotExistsWhenVerticalDnaMutantIncludesCombinedMethod()
			throws IOException, MutantsException {
		String[] dnaArray = { "ATTT", "ACCC", "AGGG", "ACGC" };
		executeDnaCreateBdRegisterIfNotExists(dnaArray, HttpServletResponse.SC_OK, MutansSearchMethod.COMBINED, null);
	}

	/**
	 * Ejecuta el proceso para crear el registro de bd.
	 */
	private void executeDnaCreateBdRegisterIfNotExists(String[] dnaArray, int responseCode,
			MutansSearchMethod typeSearch, MutantDnaTable table) throws MutantsException, IOException {
		HttpServletResponse response = new MockHttpServletResponse();
		MockHttpServletRequest request = new MockHttpServletRequest();

		MutantDna dna = new MutantDna();

		dna.setDna(dnaArray);

		MutantsService initialService = (MutantsService) ReflectionTestUtils.getField(controller, nameVariableService);
		MutantsStatsService initialStatsService = (MutantsStatsService) ReflectionTestUtils.getField(initialService,
				nameVariableStatsService);
		MutantsRepository initialRepository = (MutantsRepository) ReflectionTestUtils.getField(initialService,
				nameVariableRepository);
		boolean validateCreationTable = table == null;
		try {
			ReflectionTestUtils.setField(initialService, nameVariableRepository, mutantsRepository);
			ReflectionTestUtils.setField(initialService, nameVariableStatsService, statsService);
			Mockito.when(mutantsRepository.findDnaById(dna.getKey())).thenReturn(table);
			if (validateCreationTable) {
				Mockito.doNothing().when(mutantsRepository).createDna(Mockito.any());
			}

			controller.mutants(response, request, dna, typeSearch == null ? null : typeSearch.getCode());
			if (validateCreationTable) {
				Mockito.verify(mutantsRepository, Mockito.times(1)).createDna(Mockito.any());
			}
			assertEquals(responseCode, response.getStatus());
		} finally {
			ReflectionTestUtils.setField(initialService, nameVariableRepository, initialRepository);
			ReflectionTestUtils.setField(initialService, nameVariableStatsService, initialStatsService);
		}
	}

}

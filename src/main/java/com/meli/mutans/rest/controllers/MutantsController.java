package com.meli.mutans.rest.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutans.rest.services.MutantAdn;
import com.meli.mutans.rest.services.MutantsException;
import com.meli.mutans.rest.services.MutantsService;
import com.meli.mutans.rest.services.MutantsStatsService;

/**
 * Controlador que sirve para exponer el servicio y los métodos para la
 * verificacion de mutantes.
 */
@RestController
@RequestMapping("/mutant")
public class MutantsController {

	@Autowired
	MutantsService mutantsService;

	@Autowired
	MutantsStatsService mutantsStatsService;

	/**
	 * método que valida si el adn es de un humano o de un mutante. Se mapea como
	 * tipo post, no se adiciona el path para que sea el metodo por defecto. Consume
	 * un tipo de dato tipo Json. Responde ok - status 200 cuando es un mutante
	 * Responde 403 forbidden cuando es un humano.
	 * 
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void mutants(HttpServletResponse response, HttpServletRequest request,@RequestBody MutantAdn adnInformation)
			throws IOException {

		try {
			boolean adnMutant = mutantsService.isMutant(adnInformation);
			processResponse(response, adnMutant);
			mutantsStatsService.processStats(adnMutant, adnInformation);
		} catch (MutantsException e) {
			invalidateRequest(response, e);
		}
	}

	/**
	 * invalida la petición cuando no es autorizada por error en datos.
	 * 
	 * @param exception
	 */
	private void invalidateRequest(HttpServletResponse response, MutantsException exception) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, exception.getMessage());
	}

	/**
	 * Procesa la respuesta del servicio.
	 */
	private void processResponse(HttpServletResponse response, boolean adnMutant) throws IOException {
		if (!adnMutant) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "ADN informed is not mutant");
		}
	}

}

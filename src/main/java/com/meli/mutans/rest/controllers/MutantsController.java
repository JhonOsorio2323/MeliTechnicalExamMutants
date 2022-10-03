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

import com.meli.mutans.rest.services.MutantDna;
import com.meli.mutans.rest.services.MutantsException;
import com.meli.mutans.rest.services.MutantsService;

/**
 * Controlador que sirve para exponer el servicio y los métodos para la
 * verificacion de mutantes.
 */
@RestController
@RequestMapping("/mutant")
public class MutantsController {

	/** Servicio para la verificación del dna */
	@Autowired
	MutantsService mutantsService;

	/**
	 * método que valida si el adn es de un humano o de un mutante. Se mapea como
	 * tipo post, no se adiciona el path para que sea el metodo por defecto. Consume
	 * un tipo de dato tipo Json. Responde ok - status 200 cuando es un mutante
	 * Responde 403 forbidden cuando es un humano.
	 * 
	 */
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public void mutants(HttpServletResponse response, HttpServletRequest request, @RequestBody MutantDna dnaInformation)
			throws IOException {
		try {
			processResponse(response, mutantsService.processMutantDnaValidation(dnaInformation));
		} catch (MutantsException e) {
			invalidateRequest(response, e.getMessage());
		}
	}

	/**
	 * invalida la petición cuando no es autorizada por error en datos.
	 * 
	 * @param exception - excepcion generada.
	 */
	private void invalidateRequest(HttpServletResponse response, String message) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
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

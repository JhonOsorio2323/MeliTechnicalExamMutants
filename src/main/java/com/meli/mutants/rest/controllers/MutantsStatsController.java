package com.meli.mutants.rest.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meli.mutants.rest.services.MutantsException;
import com.meli.mutants.rest.services.stats.MutantsStatsResult;
import com.meli.mutants.rest.services.stats.MutantsStatsService;

/**
 * Clase que expone el servicio para las estadisticas de verificacion.
 * @author Jhon Osorio
 *
 */
@RestController
@RequestMapping("/stats")
public class MutantsStatsController {
	
	/** Servicio para la verificación del dna */
	@Autowired
	MutantsStatsService mutantsService;
	
	@GetMapping
	public MutantsStatsResult mutantsStats(HttpServletResponse response) throws IOException {
		try {
			return mutantsService.findStats();
		} catch (MutantsException e) {
			invalidateRequest(response, e.getMessage());
		}
		return null;
	}
	
	/**
	 * invalida la petición cuando no es autorizada por error en datos.
	 * 
	 * @param exception - excepcion generada.
	 */
	private void invalidateRequest(HttpServletResponse response, String message) throws IOException {
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, message);
	}

}

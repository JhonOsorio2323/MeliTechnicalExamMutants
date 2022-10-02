package com.meli.mutans.rest.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class MutantsStatsController {
	
	@GetMapping
	public String mutants() {
		return "Estadisticas";
	}

}

package com.meli.mutans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase para inicializar la aplicación mediante spring boot.
 * @author Jhon Osorio
 *
 */
@SpringBootApplication
public class MeliTechnicalExamMutantsApplication {

	/**
	 * Método principal para inicializar la app.
	 * @param args - argumentos de la aplicación.
	 */
	public static void main(String[] args) {
		// Se inicializa la ejecucion mediante spring boot.
		SpringApplication.run(MeliTechnicalExamMutantsApplication.class, args);
	}

}

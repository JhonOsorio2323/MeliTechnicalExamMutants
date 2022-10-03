package com.meli.mutants;

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
		//Se deshabilitan las tareas de desarrollo para que no se presenten errores con el classloader de dynamodb
		System.setProperty("spring.devtools.restart.enabled", "false");
		// Se inicializa la ejecucion mediante spring boot.
		SpringApplication.run(MeliTechnicalExamMutantsApplication.class, args);
	}

}

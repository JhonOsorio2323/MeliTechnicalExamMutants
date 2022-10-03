package com.meli.mutants.rest.services;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumerado para el metodo de busqueda del proceso de verificacion de dna
 * mutant.
 * 
 * @author Jhon Osorio
 *
 */
public enum MutansSearchMethod {

	DEFAULT("Loop Search", 1), RECURSIVE("Recursive Search", 2), COMBINED("Loop Recursive Search", 3);

	/** Mapa que contiene los metodos. */
	private static Map<Integer, MutansSearchMethod> mapMethods = new HashMap<>();

	static {
		for (MutansSearchMethod method : MutansSearchMethod.values()) {
			mapMethods.put(method.code, method);
		}
	}

	/**
	 * Nombre del método
	 */
	private String name;

	/**
	 * Código que lo identifica.
	 */
	private int code;

	/**
	 * Constructor
	 */
	private MutansSearchMethod(String name, int code) {
		this.name = name;
		this.code = code;
	}

	/**
	 * get
	 */
	public String getName() {
		return name;
	}

	/**
	 * get
	 */
	public int getCode() {
		return code;
	}

	/**
	 * Obtiene el metodo de busqueda, si no existe, se asigna el por defecto.
	 */
	public static MutansSearchMethod valueOf(Integer methodCode) {
		if(methodCode == null) {
			return DEFAULT;
		}
		MutansSearchMethod mutansSearchMethod = mapMethods.get(methodCode);
		if(mutansSearchMethod == null) {
			return DEFAULT;
		}
		return mutansSearchMethod;
	}

}

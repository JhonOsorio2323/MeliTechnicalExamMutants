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

	DEFAULT(1), RECURSIVE(2), COMBINED(3);

	/** Mapa que contiene los metodos. */
	private static Map<Integer, MutansSearchMethod> mapMethods = new HashMap<>();

	static {
		for (MutansSearchMethod method : MutansSearchMethod.values()) {
			mapMethods.put(method.code, method);
		}
	}

	/**
	 * Código que lo identifica.
	 */
	private int code;

	/**
	 * Constructor
	 */
	private MutansSearchMethod(int code) {
		this.code = code;
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

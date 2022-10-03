package com.meli.mutants.rest.repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.meli.mutants.rest.services.MutantsException;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * repositorio para el proceso de validación de mutantes.
 * 
 * @author Jhon Osorio
 *
 */
@Repository
public class MutantsRepository {

	/**
	 * Cliente para conexión.
	 */
	private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;

	/**
	 * Tabla de bd.
	 */
	private final DynamoDbAsyncTable<MutantDnaTable> dnaTable;

	/**
	 * Constructor.
	 */
	public MutantsRepository(DynamoDbEnhancedAsyncClient asyncClient) {
		this.enhancedAsyncClient = asyncClient;
		this.dnaTable = enhancedAsyncClient.table(MutantDnaTable.class.getSimpleName(),
				TableSchema.fromBean(MutantDnaTable.class));

	}

	/** Obtiene la clave primaria para consultar en bd. */
	private CompletableFuture<MutantDnaTable> getDnaByID(Integer id) {
		return dnaTable.getItem(getKeyBuild(id));
	}

	/** Crea el registro en bd. */
	public void createDna(MutantDnaTable dnaTableData) {
		dnaTable.putItem(dnaTableData).thenApply(c -> dnaTableData);
	}

	/**
	 * Obtiene el dna de bd si existe.
	 */
	public MutantDnaTable findDnaById(Integer dnaKey) throws MutantsException {
		try {
			return getDnaByID(dnaKey).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new MutantsException("Error ocurred while processing request. Contact admin.");
		}
	}

	/**
	 * Obtiene la clave primaria para realizar la busqueda.
	 */
	private Key getKeyBuild(Integer id) {
		return Key.builder().partitionValue(id).build();
	}

}

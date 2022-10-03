package com.meli.mutans.rest.repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.meli.mutans.rest.services.MutantsException;

import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * repositorio para las estadisticas de validaciones de dna.
 * 
 * @author Jhon Osorio
 *
 */
@Repository
public class MutantsStatsRepository {

	/**
	 * Key Tabla
	 */
	private String keyTable = "MUTANTS_STATS";

	/**
	 * Cliente para conexión.
	 */
	private final DynamoDbEnhancedAsyncClient enhancedAsyncClient;

	/**
	 * Tabla de bd.
	 */
	private final DynamoDbAsyncTable<MutantDnaStatsTable> dnaTable;

	/**
	 * Constructor.
	 */
	public MutantsStatsRepository(DynamoDbEnhancedAsyncClient asyncClient) {
		this.enhancedAsyncClient = asyncClient;
		this.dnaTable = enhancedAsyncClient.table(MutantDnaStatsTable.class.getSimpleName(),
				TableSchema.fromBean(MutantDnaStatsTable.class));

	}

	/** Obtiene la clave primaria para consultar en bd. */
	private CompletableFuture<MutantDnaStatsTable> getDnaByID(String id) {
		return dnaTable.getItem(getKeyBuild(id));
	}

	/** Crea el registro en bd. */
	public MutantDnaStatsTable findCreateDna() throws MutantsException {
		MutantDnaStatsTable stats = findDnaById();
		if (stats == null) {
			MutantDnaStatsTable statsNew = new MutantDnaStatsTable();
			statsNew.setKey(keyTable);
			statsNew.setNumberDnaHumans(0);
			statsNew.setNumberDnaMutants(0);
			dnaTable.putItem(statsNew).thenApply(c -> statsNew);
			stats = statsNew;
		}
		return stats;
	}

	/** Crea el registro en bd. */
	public void updateDna(MutantDnaStatsTable dnaTableData) {
		dnaTable.updateItem(dnaTableData);
	}

	/**
	 * Obtiene el dna de bd si existe.
	 */
	private MutantDnaStatsTable findDnaById() throws MutantsException {
		try {
			return getDnaByID(keyTable).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new MutantsException("Error ocurred while processing request. Contact admin.");
		}
	}

	/**
	 * Obtiene la clave primaria para realizar la busqueda.
	 */
	private Key getKeyBuild(String id) {
		return Key.builder().partitionValue(id).build();
	}

}

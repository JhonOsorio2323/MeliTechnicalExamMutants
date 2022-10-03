package com.meli.mutans.rest.repository.dynamodb;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedAsyncClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient;

/**
 * Clase que configura la base de datos dynamo.
 * @author Jhon Osorio
 *
 */
@Configuration
public class DynamoDbConfig {

	@Value("${aws.access_key:}")
	private String akey;

	@Value("${aws.secret_key:}")
	private String skey;

	@Value("${aws.region:}")
	private String region;

	/**
	 * Constructor.
	 */
	public DynamoDbConfig() {
		super();
	}

	/**
	 * Se inicializa el cliente.
	 */
    @Bean
    DynamoDbAsyncClient getDynamoDbAsyncClient() {

        AwsBasicCredentials awsCreds = AwsBasicCredentials.create(akey, skey);
        return DynamoDbAsyncClient.builder().region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(awsCreds)).build();
    }

    /**
     * Se construye el cliente.
     */
    @Bean
    DynamoDbEnhancedAsyncClient getDynamoDbEnhancedAsyncClient() {
        return DynamoDbEnhancedAsyncClient.builder().dynamoDbClient(getDynamoDbAsyncClient()).build();
    }

}

package com.intnet.network;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jRepositories
public class NetworkServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetworkServiceApplication.class, args);
	}

}

package com.intnet.network.core.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class Neo4jConfig {

//    @Bean
    public Driver neo4jDriver() {
        String uri = System.getenv("NEO4J_URI");
        String username = System.getenv("NEO4J_USERNAME");
        String password = System.getenv("NEO4J_PASSWORD");

        if (uri == null || username == null || password == null) {
            throw new RuntimeException("Neo4j connection details not found in env");
        }

        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }
}

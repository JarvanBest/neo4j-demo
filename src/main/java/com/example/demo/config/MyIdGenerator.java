package com.example.demo.config;
 
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.neo4j.core.schema.IdGenerator;
import org.springframework.stereotype.Component;
 
/**
 * Neo4jClient based ID generator
 */
@Component
class MyIdGenerator implements IdGenerator<String> {
 
    private final Neo4jClient neo4jClient;
 
    public MyIdGenerator(Neo4jClient neo4jClient) {
        this.neo4jClient = neo4jClient;
    }
 
    @Override
    public String generateId(String primaryLabel, Object entity) {
        return neo4jClient.query("YOUR CYPHER QUERY FOR THE NEXT ID")
                .fetchAs(String.class).one().get();
    }
}
package com.example.demo.domain;
 
import lombok.Data;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
 
import java.util.List;
@Data
@RelationshipProperties
public class Roles {
 
    @RelationshipId
    private Long id;
 
    private final List<String> roles;
 
    @TargetNode
    private  Person person;
 
    public Roles(Person person, List<String> roles) {
        this.person = person;
        this.roles = roles;
    }
 
    public List<String> getRoles() {
        return roles;
    }
}
package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Data
@Node("Person")
public class Person {

    @Id
    @GeneratedValue
    //    @GeneratedValue(value = MyIdGenerator2.class)
    //    @GeneratedValue(generatorRef = "myIdGenerator")
    private Long id;

    private  String name;

    private  Integer born;

    public Person(Integer born, String name) {
        this.born = born;
        this.name = name;
    }

    public Integer getBorn() {
        return born;
    }

    public String getName() {
        return name;
    }

}
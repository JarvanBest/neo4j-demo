package com.example.demo.repository;

import com.example.demo.domain.Movie;
import com.example.demo.domain.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

/**
 * 描述：
 * Created by zjw on 2023/8/22 19:33
 */
@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
    Person findPersonEntityByName(String name);

}

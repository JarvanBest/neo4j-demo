package com.example.demo.repository;

import com.example.demo.domain.SystemEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemRepository extends Neo4jRepository<SystemEntity, Long> {

    @Query("MATCH (a),(b) WHERE id(a)=$from and id(b)=$to MERGE (a)-[:invoke]->(b)")
    void addInvokeRelation(@Param("from") Long from, @Param("to") Long to);

    @Query("MATCH (a),(b) WHERE id(a)=$from and id(b)=$to MERGE (a)-[:consume]->(b)")
    void addConsumeRelation(@Param("from") Long from, @Param("to") Long to);

    @Query("MATCH (a),(b) WHERE id(a)=$from and id(b)=$to MERGE (a)-[:produce]->(b)")
    void addProduceRelation(@Param("from") Long from, @Param("to") Long to);

    @Query("MATCH (n:SystemEntity) where id(n)=$id RETURN n")
    SystemEntity findSystemById(@Param("id") Long id);

    //等价写法@Query("MATCH (n:SystemEntity {name: $name}) RETURN n")
    @Query("MATCH (n:SystemEntity) where n.name=$name RETURN n")
    SystemEntity findSystemByName(@Param("name") String name);

    @Query("MATCH (a:SystemEntity{id:$from})-[r:invoke]-(b:SystemEntity{id:$to}) DELETE r")
    void deleteConsumeRelation(@Param("from") Long from, @Param("to") Long to);
}
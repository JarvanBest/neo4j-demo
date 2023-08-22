package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Person;
import com.example.demo.domain.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;
import org.springframework.data.neo4j.repository.query.QueryFragmentsAndParameters;

import java.util.*;

@SpringBootTest
class Neo4jDemoApplicationTests {
    @Autowired
    private Neo4jTemplate neo4jTemplate;


    @Test
    void findAllPersonTest() {
        List<Person> findAll = neo4jTemplate.findAll(Person.class);
        System.out.println(JSONUtil.toJsonStr(findAll));
    }

    @Test
    void findAllMovieTest() {
        List<Movie> findAll = neo4jTemplate.findAll(Movie.class);
        System.out.println(JSONUtil.toJsonStr(findAll));
    }

    @Test
    void findByIdTest() {
        Optional<Person> byId = neo4jTemplate.findById(6L, Person.class);
        System.out.println(JSONUtil.toJsonStr(byId.get()));
    }

    @Test
    public void deleteTest() {
        neo4jTemplate.deleteAll(Person.class);
    }
    
    @Test
    void createNode() {
        neo4jTemplate.deleteAll(Person.class);
        // 创建节点
        Movie movie = new Movie("流浪地球", "是由中国电影股份有限公司、北京京西文化旅游股份有限公司、郭帆文化传媒（北京）有限公司、北京登峰国际文化传播有限公司联合出品，由郭帆执导，吴京特别出演、屈楚萧、赵今麦、李光洁、吴孟达等领衔主演的科幻冒险电影");
        // 添加关系
        movie.getActorsAndRoles().add(new Roles(new Person(1994, "刘启"), Collections.singletonList("初级驾驶员")));
        movie.getActorsAndRoles().add(new Roles(new Person(2002, "刘培强"), Collections.singletonList("中国航天员")));
        movie.getActorsAndRoles().add(new Roles(new Person(1952, "韩子昂"), Collections.singletonList("高级驾驶员")));
        movie.getActorsAndRoles().add(new Roles(new Person(2002, "韩朵朵"), Collections.singletonList("初中生")));
        movie.getActorsAndRoles().add(new Roles(new Person(1981, "王磊"), Collections.singletonList("救援队队长")));
        movie.getActorsAndRoles().add(new Roles(new Person(1991, "李一一"), Collections.singletonList("技术观察员")));
        movie.getActorsAndRoles().add(new Roles(new Person(1974, "何连科"), Collections.singletonList("救援队队员")));
        movie.getActorsAndRoles().add(new Roles(new Person(1991, "Tim"), Collections.singletonList("中美混血儿")));
        movie.getDirectors().add(new Person(1974, "吴京"));
        // 存入图数据库持久化
        neo4jTemplate.save(movie);
    }
    @Test
    void TestNoRepository() {
        // 删除所有节点和关系（删除节点会响应删除关联关系），避免后续创建节点重复影响
        // neo4jTemplate.deleteAll(Movie.class);
        //
        // neo4jTemplate.deleteAll(Person.class);

        // 查询 操作 两种方式

        // 1.手写cypherQuery  toExecutableQuery

        // 2.调用neo4jTemplate提供的方法.

        List<Person> personList = neo4jTemplate.findAll(Person.class);
        System.out.println(personList);
        Map<String, Object> map = new HashMap<>();
        map.put("usedName", "王磊");
        QueryFragmentsAndParameters parameters = new QueryFragmentsAndParameters("MATCH (n:Person) where n.name = $usedName return n",map);
        Person person = neo4jTemplate.toExecutableQuery(Person.class, parameters).getSingleResult().get();
        System.out.println(person);

        // 3. 通过属性关系查询节点
        map = new HashMap<>();
        map.put("roles", Collections.singletonList("救援队队员"));
        // 方法1.使用toExecutableQuery查询
        parameters = new QueryFragmentsAndParameters("MATCH (n:Person) -[relation:ACTED_IN]-> (m:Movie) WHERE relation.roles = $roles RETURN n",map);
        Optional<Person> role = neo4jTemplate.toExecutableQuery(Person.class, parameters).getSingleResult();
        System.out.println(role);

        // 方法2.使用findOne查询
        role = neo4jTemplate.findOne("MATCH (person:Person) -[relation:ACTED_IN]-> (movie:Movie) WHERE relation.roles = $roles RETURN person",map,Person.class);
        System.out.println(role);

        Long userId = person.getId();
        // 更新
        person.setName("王磊2");
        neo4jTemplate.save(person);

        Optional<Person> person2 = neo4jTemplate.findById(userId, Person.class);
        System.out.println(person2);

    }


}

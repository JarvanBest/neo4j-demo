package com.example.demo;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.example.demo.domain.Movie;
import com.example.demo.domain.Person;
import com.example.demo.domain.Roles;
import com.example.demo.domain.SystemEntity;
import com.example.demo.repository.MovieRepository;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.SystemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.neo4j.core.Neo4jTemplate;

import java.util.Collections;

/**
 * 描述：
 * Created by zjw on 2023/8/22 19:32
 */
@SpringBootTest
public class Neo4jTemplateTest {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private SystemRepository systemRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    Neo4jTemplate neo4jTemplate;

    /**
     * 使用repository操作图数据
     */
    @Test
    void testByRepository() {
        // 删除所有节点和关系（删除节点会响应删除关联关系），避免后续创建节点重复影响
        movieRepository.deleteAll();
        personRepository.deleteAll();
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

        // 查询
        Person person = personRepository.findPersonEntityByName("刘启");
        System.out.println(JSONUtil.toJsonStr(person));
        Movie movie1 = movieRepository.findMovieByTitle("流浪地球");
        System.out.println(JSONUtil.toJsonStr(movie1));

        Movie movie2 = movieRepository.findMovieById(movie.getId());
        System.out.println(JSONUtil.toJsonStr(movie2));

        // 注意：repository的save方法【对应的实体若id一致】则为修改，否则为新建。
        person.setBorn(1997);
        personRepository.save(person);
        person = personRepository.findPersonEntityByName("刘启");
        System.out.println(person);
    }
    @Test
    public void findByNameTest() {
        Person person = personRepository.findPersonEntityByName("刘启");
        System.out.println(JSONUtil.toJsonStr(person));
    }

}

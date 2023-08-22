package com.example.demo.domain;
 
import lombok.Data;
import org.springframework.data.annotation.Version;
import org.springframework.data.neo4j.core.schema.*;
import org.springframework.data.neo4j.core.support.UUIDStringGenerator;
 
import java.util.ArrayList;
import java.util.List;
 
@Data
@Node("Movie")
public class Movie {
 
    @Id
    @GeneratedValue
//    @GeneratedValue(UUIDStringGenerator.class)
    private Long id;
 
    //支持乐观锁定
    //判断这个实体是新的还是之前已经持久化过。
    //@Version
    private  String title;
 
    //映射属性
    @Property("tagline")
    private  String description;
 
    //INCOMING  指向自己
    //OUTGOING  指向别人
 
    //动态关系 指向自己
    @Relationship(type = "ACTED_IN", direction = Relationship.Direction.INCOMING)
    private List<Roles> actorsAndRoles = new ArrayList<>();
 
    //关系 指向别人
    @Relationship(type = "DIRECTED", direction = Relationship.Direction.INCOMING)
    private List<Person> directors = new ArrayList<>();
 
    public Movie(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
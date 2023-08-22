package com.example.demo;

import cn.hutool.json.JSONUtil;
import com.example.demo.domain.SystemEntity;
import com.example.demo.repository.SystemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

/**
 * 描述：
 * Created by zjw on 2023/8/22 19:48
 */
@SpringBootTest
public class Neo4jSystemTest {

    @Autowired
    SystemRepository systemRepository;

    // @Test
    public void addSystemNode() {

        systemRepository.deleteAll();

        SystemEntity systemEntity = new SystemEntity();
        systemEntity.setName("系统A"); // 45
        systemRepository.save(systemEntity);
        System.out.println("系统A" + "----------" + systemEntity.getId());

        SystemEntity systemEntity1 = new SystemEntity();
        systemEntity1.setName("系统B");//  46
        systemRepository.save(systemEntity1);
        System.out.println("系统B" + "----------" + systemEntity1.getId());

        SystemEntity systemEntity2 = new SystemEntity();
        systemEntity2.setName("系统C");//  47
        systemRepository.save(systemEntity2);
        System.out.println("系统C" + "----------" + systemEntity2.getId());

        SystemEntity systemEntity3 = new SystemEntity();
        systemEntity3.setName("系统D");//  48
        systemRepository.save(systemEntity3);
        System.out.println("系统D" + "----------" + systemEntity3.getId());

        SystemEntity systemEntity4 = new SystemEntity();
        systemEntity4.setName("系统E");// 49
        systemRepository.save(systemEntity4);
        System.out.println("系统E" + "----------" + systemEntity4.getId());

        SystemEntity systemEntity5 = new SystemEntity();
        systemEntity5.setName("系统F");//  50
        systemRepository.save(systemEntity5);
        System.out.println("系统F" + "----------" + systemEntity5.getId());
    }

    @Test
    public void addInvokeRelation() {
        systemRepository.addInvokeRelation(16L, 17L);
        systemRepository.addInvokeRelation(16L, 18L);
        systemRepository.addInvokeRelation(19L, 16L);
        systemRepository.addInvokeRelation(19L, 47L);
        systemRepository.addInvokeRelation(19L, 47L);
    }

    @Test
    public void addConsumeRelation() {
        systemRepository.addConsumeRelation(20L, 21L);
        systemRepository.addConsumeRelation(19L, 20L);
    }

    /**
     * 删除指定节点直接的关系 DELETE <node1-name>,<node2-name>,<relationship-name>
     */
    @Test
    public void deleteConsumeRelation2() {
        Long from = 19L, to = 16L;
        systemRepository.deleteConsumeRelation(from, to);
    }

    @Test
    public void addProduceRelation() {
        Long from = 16L, to = 21L;
        systemRepository.addProduceRelation(from, to);
    }

    @Test
    public void findSystemById() {
        Long id = 21L;
        Optional<SystemEntity> systemEntityOptional = systemRepository.findById(id);
        System.out.println(JSONUtil.toJsonStr(systemEntityOptional.get()));
    }

    @Test
    public void findSystemById1() {
        Long id = 21L;
        SystemEntity systemEntity = systemRepository.findSystemById(id);
        System.out.println(JSONUtil.toJsonStr(systemEntity));
    }

    @Test
    public void findSystemByName() {
        SystemEntity systemEntity = systemRepository.findSystemByName("系统F");
        System.out.println(JSONUtil.toJsonStr(systemEntity));
    }

    @Test
    public void getAllSystemNode() {
        Iterable<SystemEntity> systemEntities = systemRepository.findAll();
        for (SystemEntity systemEntity : systemEntities) {
            System.out.println("查询所有的节点为：" + JSONUtil.toJsonStr(systemEntity));
            System.out.println(JSONUtil.toJsonStr(systemEntity));
        }
    }
}

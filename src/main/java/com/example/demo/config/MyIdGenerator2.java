package com.example.demo.config;
 
import org.springframework.data.neo4j.core.schema.IdGenerator;
import org.springframework.util.StringUtils;
 
import java.util.concurrent.atomic.AtomicInteger;
 
/**
 * 自定义自增主键
 */
public class MyIdGenerator2 implements IdGenerator<String> {
 
    private final AtomicInteger sequence = new AtomicInteger(0);
 
    @Override
    public String generateId(String primaryLabel, Object entity) {
        return StringUtils.uncapitalize(primaryLabel) +
                "-" + sequence.incrementAndGet();
    }
}
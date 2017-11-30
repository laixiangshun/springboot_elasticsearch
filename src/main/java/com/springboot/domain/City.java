package com.springboot.domain;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * Created by lailai on 2017/11/28.
 * 使用Document注解针对实体快速建立索引
 * indexName必须为全部小写
 */

@Document(indexName = "provider",type = "city")
public class City implements Serializable{
    private static final long serializable=-1l;

    private Long id;

    private String name;

    private String description;

    private Integer score;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}

package com.api.entity;

import lombok.Data;

/**
 * @program: provider
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-01 19:15
 **/

@Data
public class Person {


    private Integer id;

    private String name;

    private String note;

    public Person(Integer id, String name, String note) {
        this.id = id;
        this.name = name;
        this.note = note;
    }

    public Person(){

    }
}


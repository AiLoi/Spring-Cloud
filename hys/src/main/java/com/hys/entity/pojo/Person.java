package com.hys.entity.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: provider
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-01 19:15
 **/

@Data
public class Person implements Serializable {


    private Long id;

    private String name;

    private String note;

}


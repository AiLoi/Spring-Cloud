package com.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: config-client
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-25 20:51
 **/

@RefreshScope
@RestController
public class TestController {


    @Value("${from}")
    private String from;

    @Autowired
    private Environment environment;

    @RequestMapping("/from")
    public String from(){
        return this.from;
    }

    @RequestMapping("/from1")
    public String from1(){
        return environment.getProperty("from","undefined");
    }



}


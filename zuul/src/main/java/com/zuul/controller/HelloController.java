package com.zuul.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: zuul
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-23 15:14
 **/
@RestController
@RequestMapping("/local")
public class HelloController {



    @RequestMapping("/hello")
    public String hello(){
        return "hello world!";
    }


}


package com.hys.controller;

import com.hys.entity.pojo.Person;
import com.hys.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @program: hys
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-17 09:51
 **/


@RestController
public class CollapseController {


    private static List<Person> list = new ArrayList<>();

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private HelloService helloService;


    @RequestMapping("/collapse")
    public String colla() throws ExecutionException, InterruptedException {

        Future<Person> person1 = helloService.find(1L);

        System.out.println(person1.get());
//        Future<Person> person2 = helloService.find(2L);
//        Future<Person> person3 = helloService.find(3L);
//        Future<Person> person4 = helloService.find(4L);
//        Future<Person> person5 = helloService.find(5L);
//
//        list.add(person1.get());
//        list.add(person2.get());
//        list.add(person3.get());
//        list.add(person4.get());
//        list.add(person5.get());
//        System.out.println(list);


//        Long[] arrlong = {10L,11L,12L};
//        List<Long> longs = new ArrayList<>(Arrays.asList(arrlong));
//        List<Person> personList = helloService.findAll(longs);
//        System.out.println(personList);



        return "person";


    }

}


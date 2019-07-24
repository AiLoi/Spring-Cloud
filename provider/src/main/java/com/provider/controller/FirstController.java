package com.provider.controller;

import com.provider.entity.po.Person;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: provider
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-01 19:13
 **/

@RestController
@RequestMapping("/first")
public class FirstController {


    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/person")
    public Person findPerson(Integer personId, HttpServletRequest request){


        Person person = new Person();
        person.setId(personId);
        person.setName(request.getRequestURL().toString());
        person.setNote("牛逼");


        return person;
    }


    @RequestMapping(value = "/all_person",produces = "application/json;charset=UTF-8")
    public List<Person> findAllPerson(@RequestBody List<Long> ids ,HttpServletRequest request){

        System.out.println(request.getRequestURL().append(System.currentTimeMillis()).append(Thread.currentThread().getStackTrace()[1].getClassName()));
        List<Person> list = new ArrayList<>();
        for(Long id : ids){
            Person person = new Person();
            person.setId(Math.toIntExact(id));
            person.setName("请求路径："+request.getRequestURL().toString()+","+"当前线程："+Thread.currentThread().getName());
            person.setNote(String.valueOf(System.currentTimeMillis()));
            list.add(person);
        }
        return list;
    }



    @PostMapping("/post/person")
    public Person postPerson(@RequestBody Person person){
        return person;
    }
}


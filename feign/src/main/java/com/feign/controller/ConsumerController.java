package com.feign.controller;

import com.feign.entity.Person;
import com.feign.service.PersonService;
import com.feign.service.RefactorPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: feign
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-18 17:26
 **/
@RestController
public class ConsumerController {

    @Autowired
    private PersonService personService;


    @Autowired
    private RefactorPersonService refactorPersonService;


    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping(value = "/feign-consumer",method = RequestMethod.GET)
    public Person helloConsumer(){
        return personService.hello(1);
    }


    @GetMapping("/feign/get_all")
    public List<Person> showPersons(){

        List<Long> ids = new ArrayList<Long>(){
            {
                add(1L);
                add(2L);
                add(3L);
                add(4L);
                add(5L);
            }
        };

        return personService.getAllPerson(ids);

    }

    @RequestMapping("/feign-consumer3")
    public Map<String,Object> helloConsumer2() throws InterruptedException {

        Map<String,Object> map =new ConcurrentHashMap<>();

        com.api.entity.Person person = refactorPersonService.hello(20);

        map.put("person",person);

        System.out.println(person);

        List<com.api.entity.Person> personList = refactorPersonService.hello(new ArrayList<Long>(){
            {
                add(21L);
                add(22L);
                add(23L);
                add(24L);
                add(25L);
            }
        });

        map.put("list",personList);

        return map;

    }


}


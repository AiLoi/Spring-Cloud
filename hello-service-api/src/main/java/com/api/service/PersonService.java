package com.api.service;

import com.api.entity.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @program: feign
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-18 17:23
 **/

@RequestMapping("/refactor")
public interface PersonService {


    @RequestMapping("/first/person")
    Person hello(@RequestParam("personId") Integer personId) throws InterruptedException;


    @PostMapping("/first/all_person")
    List<Person> hello(@RequestBody List<Long> ids);

}

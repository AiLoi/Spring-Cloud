package com.feign.service.componet;

import com.feign.entity.Person;
import com.feign.service.PersonService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: feign
 * @description: 对于Feign客户端进行的Hystrix服务降级配置
 * @author: Ailuoli
 * @create: 2019-07-22 20:13
 **/

@Component
public class PersonServiceFallback implements PersonService {

    @Override
    public Person hello(Integer personId) {

        Person person = new Person();
        person.setId(1);
        person.setName("error");
        person.setNote("error");

        return person;
    }

    @Override
    public List<Person> getAllPerson(List<Long> ids) {

        Person person = new Person();
        person.setId(1);
        person.setName("error");
        person.setNote("error");


        List<Person> personList = new ArrayList<>();
        personList.add(person);

        return personList;
    }
}


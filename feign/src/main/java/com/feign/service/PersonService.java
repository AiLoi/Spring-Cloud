package com.feign.service;

import com.feign.config.ConfigFeignLogLevel;
import com.feign.entity.Person;
import com.feign.service.componet.PersonServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
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

@Primary
@FeignClient(value = "first-service-provider",fallback = PersonServiceFallback.class,configuration = ConfigFeignLogLevel.class)
public interface PersonService {


    @RequestMapping("/first/person")
    Person hello(@RequestParam ("personId") Integer personId);


    @PostMapping("/first/all_person")
    List<Person> getAllPerson(@RequestBody List<Long> ids);

}

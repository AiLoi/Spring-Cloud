package com.provider.controller;

import com.api.entity.Person;
import com.api.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @program: provider
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-19 09:27
 **/
@Slf4j
@RestController
public class RefactorHelloController implements PersonService {

    @Value("${server.port}")
    private String port;

    @Autowired
    private DiscoveryClient client;


    @Qualifier("eurekaRegistration")
    @Autowired
    private Registration registration;


    @Override
    public Person hello(Integer personId) throws InterruptedException {

        System.out.println(System.currentTimeMillis() + "," + port);
        System.out.println(1);
        ServiceInstance serviceInstance = serviceInstance();
        int sleepTime = new Random().nextInt(3000);
        log.info("sleepTime:"+sleepTime);
//        Thread.sleep(sleepTime);
        log.info("/hello,host:"+serviceInstance.getHost()+", service_id:"+serviceInstance.getServiceId());

        return new Person(personId, System.currentTimeMillis() + ":" + Thread.currentThread().getName(), port);
    }

    @Override
    public List<Person> hello(List<Long> ids) {

        System.out.println(2);
        List<Person> list = new ArrayList<>();
        for (Long id : ids) {
            Person person = new Person();
            person.setId(Math.toIntExact(id));
            person.setName("当前线程：" + Thread.currentThread().getName());
            person.setNote(String.valueOf(System.currentTimeMillis()));
            list.add(person);
        }
        return list;
    }

    public ServiceInstance serviceInstance() {

        List<ServiceInstance> serviceInstanceList = client.getInstances(registration.getServiceId());

        if(serviceInstanceList!=null && serviceInstanceList.size()>0){

            for(ServiceInstance serviceInstance : serviceInstanceList){
                if(serviceInstance.getPort() == Integer.parseInt(port)){
                    return serviceInstance;
                }
            }
        }
        return null;

    }

}


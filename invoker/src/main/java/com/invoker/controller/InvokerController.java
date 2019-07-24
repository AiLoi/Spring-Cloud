package com.invoker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @program: invoker
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-01 19:48
 **/

@RestController
@RequestMapping("/invoker")
public class InvokerController {



    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SpringClientFactory factory;


    @RequestMapping("/router")
    public String router(){

        int personId = 1;

//        //getForEntity()函数
//
//        //get请求，第一个参数为请求路径，第二个参数为接受数据类型，第三个为get请求参数占位符，可以为空
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://FIRST-SERVICE-PROVIDER/first/person?personId={personId}",String.class,personId);
//
//        String body = responseEntity.getBody();
//        System.out.println(body);
//
//
//        Person person = new Person();
//        person.setId(1);
//        person.setName("Ailuoli");
//        person.setNote("牛逼");
//
//
//        //getForObject()函数
//
//        //post请求
//        ResponseEntity<Person> responseEntity1 = restTemplate.postForEntity("http://FIRST-SERVICE-PROVIDER/first/post/person",person,Person.class);
//
//        Person rperson = responseEntity1.getBody();
//        System.out.println(rperson);
//
//        System.out.println(responseEntity1.getStatusCode());
//        System.out.println(responseEntity1.getStatusCodeValue());


        for(int i = 0;i<6 ;i++){

            String json = restTemplate.getForObject("http://FIRST-SERVICE-PROVIDER/first/person?personId={personId}",String.class,personId);
            System.out.println(json);
        }




//        //postForLocation(),该方法实现了POST请求提交资源，并返回新的资源URI
//
//        URI uri = restTemplate.postForLocation("http://FIRST-SERVICE-PROVIDER/first/post/person",person);
//        System.out.println(uri);



        for(int i=0;i<6;i++){
            String json1 = restTemplate.getForObject("http://SECOND-SERVICE-PROVIDER/first/person?personId={personId}",String.class,personId);
            System.out.println(json1);
        }


//        System.out.println("=====默认输出配置：");
//        ZoneAwareLoadBalancer awareLoadBalancer = (ZoneAwareLoadBalancer) factory.getLoadBalancer("default");
//        System.out.println("IClientConfig:"+factory.getLoadBalancer("default").getClass().getName());
//        System.out.println("IRule:"+awareLoadBalancer.getRule().getClass().getName());
//        System.out.println("IPing:"+awareLoadBalancer.getPing().getClass().getName());
//        System.out.println("ServerList:"+awareLoadBalancer.getServerListImpl().getClass().getName());
//        System.out.println("ServerListFilter:"+awareLoadBalancer.getFilter().getClass().getName());
//        System.out.println("ILoadBalancer:"+awareLoadBalancer.getClass().getName());
//        System.out.println("PingInterval:"+awareLoadBalancer.getPingInterval());
//        System.out.println("=====输出 cloud-provider配置：");
//        //获取cloud-provider的配置
//        ZoneAwareLoadBalancer awareLoadBalancer1 = (ZoneAwareLoadBalancer) factory.getLoadBalancer("cloud-provider");
//        System.out.println("IClientConfig:"+factory.getLoadBalancer("cloud-provider").getClass().getName());
//        System.out.println("IRule:"+awareLoadBalancer1.getRule().getClass().getName());
//        System.out.println("IPing:"+awareLoadBalancer1.getPing().getClass().getName());
//        System.out.println("ServerList:"+awareLoadBalancer1.getServerListImpl().getClass().getName());
//        System.out.println("ServerListFilter:"+awareLoadBalancer1.getFilter().getClass().getName());
//        System.out.println("ILoadBalancer:"+awareLoadBalancer1.getClass().getName());
//        System.out.println("PingInterval:"+awareLoadBalancer1.getPingInterval());


        return "牛逼";
    }


}


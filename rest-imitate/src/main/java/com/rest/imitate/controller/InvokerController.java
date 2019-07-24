package com.rest.imitate.controller;

import com.rest.imitate.config.MyLoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @program: imitate
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 19:36
 **/

@RestController
@Configuration
public class InvokerController {


    @Bean
    @MyLoadBalanced
    public RestTemplate getMyRestTemplate(){
        return new RestTemplate();
    }


    @RequestMapping("/router")
    public String router(){

        RestTemplate restTemplate = getMyRestTemplate();

        String json = restTemplate.getForObject("http://my-server/hello",String.class);

        return json;
    }

    /**
     * 最终的请求都会转到这个服务
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)public String hello() {
        return "Hello World";
    }


}


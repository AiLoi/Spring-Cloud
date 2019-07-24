package com.rest.imitate.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @program: imitate
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 19:30
 **/
@Configuration
public class MyAutoConfiguration {



    @Autowired(required = false)
    @MyLoadBalanced
    private List<RestTemplate> restTemplateList = Collections.emptyList();

    @Bean
    public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer(){
        System.out.println("====这个Bean将在容器初始化时创建");

        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (RestTemplate restTemplate : restTemplateList){

                    //创建一个自定义拦截器实例
                    MyInterceptor myInterceptor = new MyInterceptor();
                    //获取RestTemplate 原来得拦截器
                    List list = new ArrayList(restTemplate.getInterceptors());
                    //添加到拦截器集合
                    list.add(myInterceptor);

                    //将新的拦截器集合设置到RestTemplate实例
                    restTemplate.setInterceptors(list);
                }
            }
        };

    }

}


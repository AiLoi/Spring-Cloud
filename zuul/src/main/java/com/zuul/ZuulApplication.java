package com.zuul;

import com.netflix.zuul.FilterProcessor;

import com.zuul.filter.DidiErrorAttributes;
import com.zuul.filter.DidiFilterProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;

//开启Zuul的API网关服务
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {

    public static void main(String[] args) {

        //启用自定义的核心处理器来完成优化
        FilterProcessor.setProcessor(new DidiFilterProcessor());
        SpringApplication.run(ZuulApplication.class, args);
    }

    @Bean
    public DefaultErrorAttributes errorAttributes(){

        return new DidiErrorAttributes();

    }



    /*
    如果为微服务都遵循了类似 userservice-v1,userservice-v2的命名规则，可以使用自定义服务与路由映射的关系
    来实现为符合上述规则的微服务自动化地创建类似/v1/userservice/**的路由匹配规则

    1.  ？ 通配符    匹配任意单个字符

    2.  * 通配符    匹配任意数量的字符

    3.  ** 通配符    匹配任意数量字符，支持多级目类



     */




//    @Bean
//    public PatternServiceRouteMapper serviceRouteMapper(){
//
//        return new PatternServiceRouteMapper("(?<name>^.+)-(?<version>)v.+$","${version}/${name}");
//
//    }

}

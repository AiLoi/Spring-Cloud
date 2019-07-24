package com.feign.config;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @program: feign
 * @description: 负责对Hystrix进行配置
 * @author: Ailuoli
 * @create: 2019-07-22 19:48
 **/

//@Configuration
public class ConfigDisableHystrix {


    @Bean
    @Scope("prototype")
    public Feign.Builder feignBuilder(){

        return Feign.builder();

    }

}


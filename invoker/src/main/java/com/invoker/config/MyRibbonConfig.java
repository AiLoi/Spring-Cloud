package com.invoker.config;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: invoker
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 18:25
 **/

@Configuration
@ExcludeFromComponentScan
public class MyRibbonConfig {


    @Bean
    public IRule MyRule(){
        return new MyRibbonRule();
    }

    @Bean
    public IPing getPing(){
        return new MyPing();
    }

}


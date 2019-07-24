package com.dynamic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayDynamicRouteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayDynamicRouteApplication.class, args);
    }


    /*
    这里需要使用@RefreshScope来将Zuul的配置内容动态化
     */
    @RefreshScope
    @ConfigurationProperties("zuul")
    public ZuulProperties zuulProperties(){

        return new ZuulProperties();

    }
}

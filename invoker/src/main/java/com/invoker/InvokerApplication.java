package com.invoker;

import com.invoker.config.ExcludeFromComponentScan;
import com.invoker.config.MyRibbonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@EnableDiscoveryClient
@SpringBootApplication
//这样配置，只有first-service-provider遵循自定义的负载规则
//@RibbonClient(name = "FIRST-SERVICE-PROVIDER,SECOND-SERVICE-PROVIDER",configuration = MyRibbonConfig.class)
@RibbonClient(name = "FIRST-SERVICE-PROVIDER",configuration = MyRibbonConfig.class)
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = ExcludeFromComponentScan.class)})
public class InvokerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InvokerApplication.class, args);
    }

}

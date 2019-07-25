package com.groovy.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: groovy
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-24 20:12
 **/
@Data
@ConfigurationProperties("zuul.filter")
public class FilterConfiguration {


    private String root;

    private Integer interval;

}


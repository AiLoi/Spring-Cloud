package com.feign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



/**
 * @program: feign
 * @description: 对feign进行日志配置
 * @author: Ailuoli
 * @create: 2019-07-22 20:39
 **/
@Configuration
public class ConfigFeignLogLevel {


    /*
    由于Logger.Level对象默认配置为NONE级别，所以这里需要对其进行配置

    1.NONE: 不记录任何信息
    2.BASIC: 仅记录请求方法，URL以及响应状态码和执行时间
    3.HEADERS: 除了记录BASIC级别的信息外，还会记录请求和响应的头信息
    4.FULL: 记录所有请求与响应的明细，包括头信息，请求体，元数据等

     */
    @Bean
    Logger.Level feignLoggerLevel(){

        return Logger.Level.FULL;

    }




}


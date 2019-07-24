package com.feign.service;

import com.feign.config.ConfigDisableHystrix;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @program: feign
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-19 09:39
 **/

//实现部分的服务进行hystrix开启
@FeignClient(value = "FIRST-SERVICE-PROVIDER"/*,configuration = ConfigDisableHystrix.class*/)
public interface RefactorPersonService extends com.api.service.PersonService {
}

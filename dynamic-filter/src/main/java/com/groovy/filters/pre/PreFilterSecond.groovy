package com.groovy.filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.exception.ZuulException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

class PreFilterSecond extends ZuulFilter {

    Logger logger = LoggerFactory.getLogger(PreFilterSecond.class)

    @Override
    String filterType() {
        return FilterConstants.PRE_TYPE
    }

    @Override
    int filterOrder() {
        return 1001
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() throws ZuulException {

        logger.info("这是第二个过滤器---动态加载-1")

        return null
    }
}

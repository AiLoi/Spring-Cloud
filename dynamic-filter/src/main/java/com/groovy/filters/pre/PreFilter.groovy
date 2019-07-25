package com.groovy.filters.pre

import com.netflix.zuul.ZuulFilter
import com.netflix.zuul.context.RequestContext
import com.netflix.zuul.exception.ZuulException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants

import javax.servlet.http.HttpServletRequest

class PreFilter extends ZuulFilter {


    Logger logger = LoggerFactory.getLogger(PreFilter.class)

    @Override
    String filterType() {
        return FilterConstants.PRE_TYPE
    }

    @Override
    int filterOrder() {
        return 1000
    }

    @Override
    boolean shouldFilter() {
        return true
    }

    @Override
    Object run() throws ZuulException {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest()

        logger.info("this is a pre filter: Send {} request to {}",request.getMethod(),request.getRequestURL().toString()+"-update")

        return null

    }
}

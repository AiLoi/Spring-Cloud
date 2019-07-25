package com.groovy.filter.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: groovy
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-25 09:40
 **/

@Slf4j
public class PreFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {


        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();

        log.info("this is a pre filter: Send {} request to {}",request.getMethod(),request.getRequestURL().toString());

        return null;

    }
}


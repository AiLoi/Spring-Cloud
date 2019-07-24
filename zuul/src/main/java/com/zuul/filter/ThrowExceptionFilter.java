package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @program: zuul
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-23 20:53
 **/
@Slf4j
@Component
public class ThrowExceptionFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        log.info("this is a pre filter, it will throw a RuntimeException");

        RequestContext requestContext = RequestContext.getCurrentContext();
        requestContext.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        doSomething();


        return null;
    }

    private void doSomething(){

        throw new RuntimeException("exist some errors...");
    }
}


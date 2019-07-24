package com.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @program: zuul
 * @description: 由于在过滤器链的源码中，每次过滤顺序是每次经过error级别处理时会再次经过post级别的处理器进行过滤，这里是对其补充
 * @author: Ailuoli
 * @create: 2019-07-24 10:11
 **/

@Slf4j
@Component
public class ErrorExtFilter extends SendErrorFilter {


    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;    //大于ErrorFilter的值
    }

    @Override
    public boolean shouldFilter() {

        //判断：仅处理来自post过滤器引起的异常

        RequestContext requestContext = RequestContext.getCurrentContext();

        ZuulFilter failedFilter = (ZuulFilter) requestContext.get("failed.filter");

        return failedFilter != null && failedFilter.filterType().equals("post");

    }
}


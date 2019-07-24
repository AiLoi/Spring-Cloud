package com.zuul.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @program: zuul
 * @description: 当有异常抛出，记录抛出异常的来源，FilterProcessor zuul核心处理器
 * @author: Ailuoli
 * @create: 2019-07-24 10:09
 **/


public class DidiFilterProcessor extends FilterProcessor {


    /*
    该方法定义了用来执行filter的具体逻辑，包括对请求上下文的设置，判断是否应该执行，执行时一些异常的处理
     */
    @Override
    public Object processZuulFilter(ZuulFilter filter) throws ZuulException {

        try {
            return super.processZuulFilter(filter);
        }catch (Exception e){

            RequestContext requestContext = RequestContext.getCurrentContext();
            requestContext.set("failed.filter",filter);
            throw e;
        }

    }
}


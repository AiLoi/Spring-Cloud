package com.zuul.filter;



import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

/**
 * @program: zuul
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-24 11:23
 **/
public class DidiErrorAttributes extends DefaultErrorAttributes {


    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {

        Map<String,Object> result = super.getErrorAttributes(webRequest, includeStackTrace);

        result.remove("exception");

        return result;

    }
}


package com.rest.imitate.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * @program: imitate
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 19:18
 **/
public class MyInterceptor implements ClientHttpRequestInterceptor {

    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

        System.out.println("========  这是定义的拦截器实现");

        System.out.println("              原来的URI:"+request.getURI());

        //换成新的请求对象（更换URI）

        MyHttpRequest newRequest = new MyHttpRequest(request);
        System.out.println("拦截后新的URI:"+request.getURI());
        return execution.execute(newRequest,body);

    }


}


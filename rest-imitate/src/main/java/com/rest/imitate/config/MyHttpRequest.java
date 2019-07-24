package com.rest.imitate.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;


import java.net.URI;
import java.net.URISyntaxException;

/**
 * @program: imitate
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 19:22
 **/
public class MyHttpRequest implements HttpRequest {


    private HttpRequest sourceRequest;


    public MyHttpRequest(HttpRequest sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    @Override
    public HttpMethod getMethod() {
        return sourceRequest.getMethod();
    }

    @Override
    public String getMethodValue() {
        return sourceRequest.getMethodValue();
    }

    @Override
    public URI getURI() {

        try {
            String oldUri = sourceRequest.getURI().toString();

            return new URI("http://localhost:8080/hello");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return sourceRequest.getURI();
    }

    @Override
    public HttpHeaders getHeaders() {
        return sourceRequest.getHeaders();
    }
}


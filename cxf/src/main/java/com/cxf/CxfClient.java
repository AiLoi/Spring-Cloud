package com.cxf;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.jaxrs.client.WebClient;


import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;


/**
 * @program: cxf
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-05 08:55
 **/
public class CxfClient {


    public static void main(String[] args) throws IOException {

        //创建WebClient
        WebClient client = WebClient.create("http://localhost:6001/first/person?personId=1");

        //获取响应
        Response response = client.get();

        //获取响应内容
        InputStream ent = (InputStream) response.getEntity();
        String content = IOUtils.readStringFromStream(ent);

        System.out.println(content);
    }


}


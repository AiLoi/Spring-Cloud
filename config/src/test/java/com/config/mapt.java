package com.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: config
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-24 16:37
 **/
public class mapt {


    public static void main(String[] args) {

        Map<String, Object> map = new HashMap<>();

        map.put("abc", "abc");

        map.put("123", "ABC");

        System.out.println(map.keySet());

        for (Map.Entry<String,Object> temp : map.entrySet()) {

            System.out.println(temp.getValue());

        }


    }


}


package com.invoker.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @program: invoker
 * @description: 自定义负载规则
 * @author: Ailuoli
 * @create: 2019-07-02 17:02
 **/

public class MyRibbonRule implements IRule {

    private ILoadBalancer loadBalancer;

    public MyRibbonRule(){

    }

    @Override
    public Server choose(Object key) {

        //获取全部服务器
        List<Server> serverList = loadBalancer.getAllServers();
        //只返回第一个Server对象
        return serverList.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {
        this.loadBalancer = lb;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return this.loadBalancer;
    }
}


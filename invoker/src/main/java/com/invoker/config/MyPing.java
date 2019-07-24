package com.invoker.config;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * @program: invoker
 * @description:
 * @author: Ailuoli
 * @create: 2019-07-04 18:47
 **/
public class MyPing implements IPing {

    @Override
    public boolean isAlive(Server server) {

        System.out.println("自定义ping的规则");
        DiscoveryEnabledServer dServer = (DiscoveryEnabledServer)server;

        InstanceInfo.InstanceStatus status = dServer.getInstanceInfo().getStatus();
        return status == InstanceInfo.InstanceStatus.UP;
    }
}


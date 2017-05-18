package com.tlyong1992.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务提供控制器
 * @author yesh
 *         (M.M)!
 *         Created by 2017/5/2.
 */
@RestController
public class ClientController {

    private final Logger log = Logger.getLogger(ClientController.class);

    private final DiscoveryClient discoveryClient;

    @Autowired
    public ClientController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @RequestMapping("/hi")
    public String hello1(@RequestParam String name){
        System.err.println("-----------------------------");
        ServiceInstance instance = discoveryClient.getInstances("chat-eureka-client").get(0);

        log.info("Method:---->"+this.getClass().getSimpleName()+"---->Hello");
        log.info("Url:---->"+instance.getUri());
        log.info("Host:---->"+instance.getHost());
        log.info("Port:---->"+instance.getPort());

        return "Hello !!   "+name + " ,I here in port  "+instance.getPort();
    }
}

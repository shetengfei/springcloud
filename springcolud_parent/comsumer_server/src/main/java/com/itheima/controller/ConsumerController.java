package com.itheima.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping("/findUser")
    public String findUser(Integer id) {


        //为了解决url地址的硬编码问题,且不方便后期维护1. 在服务消费者中，我们把url地址硬编码到代码中，不方便后期维护
        //2. 在服务消费者中，不清楚服务提供者的状态
        //3. 服务提供者只有一个服务，即便服务提供者形成集群，服务消费者还需要自己实现负载均衡
        //4. 服务提供者的如果出现故障，是否能够及时发现：
        //
        //其实上面说的问题，概括一下就是微服务架构必然要面临的问题
    //
    //- 服务管理：自动注册与发现、状态监管
    //- 服务负载均衡
    //- 熔断器
       /* Eureka解决了第一个问题：服务的管理，注册和发现、状态监管、动态路由。

        Eureka负责管理记录服务提供者的信息。服务调用者无需自己寻找服务，Eureka自动匹配服务给调用者。

        Eureka与服务之间通过心跳机制进行监控；*/

    //url 注册者请求路径
    //String url = "http://localhost:9091/findById?id=" + id;
    String url = "http://producer-server/user/findById?id=" +id;
        return restTemplate.getForObject(url, String.class);


}

}

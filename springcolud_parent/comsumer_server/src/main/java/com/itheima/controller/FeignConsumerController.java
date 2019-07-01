package com.itheima.controller;

import com.itheima.domain.User;
import com.itheima.feign.UserClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/30
 * @description ：
 * @version: 1.0
 */


/**
 * 用于调用UserClient客户端,实现远程调用
 * 好处:1. 不需要拼接URL,参数
 *      2.集成Ribbo的负载均衡功能
 *      3.集成了Hystrix的熔断器功能
 *      4.支持请求压缩
 */
@RestController
public class FeignConsumerController {
    //注入FeignClient客户端

    @Resource
    UserClient userClient;


    @RequestMapping("/findByIdFeign")
    public User findById( Integer id){

        return userClient.findById(id);
    }
}

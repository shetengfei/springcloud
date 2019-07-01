package com.itheima;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * spring-boot启动类  程序的入口
 */
@SpringBootApplication
@EnableDiscoveryClient   //开启服务发现
@EnableFeignClients   //开启Feign功能
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);


    }
    //注册restTemplate

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}


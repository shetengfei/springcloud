package com.itheima.controller;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */
@RestController
@RequestMapping("/user")
@RefreshScope  //刷新配置
public class UserConterller {

    @Value("${test.hello}")
    private String name;
    @Resource
    UserService userService;
    //将端口注入到当前类中
    @Value("${server.port}")
    Integer port;

    @RequestMapping("/findAll")
    public List<User> findAll() {
        List<User> userList = userService.findALL();
        return userList;
    }

    @RequestMapping("/findById")
    public User findById(Integer id) {
        System.out.printf("配置文件中的test.hello===%s%n", name);
        System.out.println("消费者访问的当前端口 port:" +port)
        ;return userService.findById(id);
    }


}

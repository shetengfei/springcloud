package com.itheima.feign.impl;

import com.itheima.domain.User;
import com.itheima.feign.UserClient;
import org.springframework.stereotype.Component;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/30
 * @description ：
 * @version: 1.0
 */

@Component   //当前UserClient实现类,注入到spring容器中
public class UserClientFallback implements UserClient {
    @Override
    public User findById(Integer id) {
        User user=new User();
        user.setUsername("您访问的用户信息不存在!!!");
        return user;
    }
}

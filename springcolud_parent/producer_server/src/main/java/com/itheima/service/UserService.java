package com.itheima.service;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */

import com.itheima.pojo.User;

import java.util.List;

/**
 * 使用spring-boot-starter-data-jpa
 */
public interface UserService {
    List<User> findALL();

    User findById(Integer id);
}

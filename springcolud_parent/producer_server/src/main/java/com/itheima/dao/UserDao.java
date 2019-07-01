package com.itheima.dao;

import com.itheima.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */

/**
 * 在使用 spring-boot-starter-data-jpa 时
 * dao层需要继承  JpaRepository 封装了简单的查询的增删改操作
 */
@Repository
public interface UserDao extends JpaRepository<User, Integer> {
}

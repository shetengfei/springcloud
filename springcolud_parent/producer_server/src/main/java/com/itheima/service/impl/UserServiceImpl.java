package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/28
 * @description ：
 * @version: 1.0
 */
@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserDao userDao;

    @Override
    public List<User> findALL() {
        return userDao.findAll();
    }

    @Override
    public User findById(Integer id) {

        Optional<User> byId = userDao.findById(id);

        return byId.get();
    }
}

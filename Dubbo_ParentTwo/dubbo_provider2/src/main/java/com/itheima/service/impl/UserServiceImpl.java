package com.itheima.service.impl;/*
@Author:李正铠
@Date:2020年03月28日22时31分
*/

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.UserDao;
import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findById(Integer id) {

        System.out.println("provider2的findById方法");
        return userDao.findById(id);
    }
}

package com.itheima.service.impl;/*
@Author:李正铠
@Date:2020年03月28日00时34分
*/

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.service.HelloService;
import com.itheima.pojo.User;

@Service(protocol = "rmi")
public class HelloServiceImpl implements HelloService {
    @Override
    public User HelloTest() {

        User user = new User();
        user.setId(2);
        user.setUsername("李正铠");
        user.setAddresss("珠海");
        user.setSex("男");
        return user;

    }
}

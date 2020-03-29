package com.itheima.service.impl;/*
@Author:李正铠
@Date:2020年03月28日22时32分
*/

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.pojo.User;
import com.itheima.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

@Service(protocol = "rmi")
public class HelloServiceImpl implements HelloService {
    @Override
    public User HelloTest() {

        System.out.println("provider2的helloTest方法");
        User user = new User();
        user.setId(2);
        user.setUsername("李正铠");
        user.setAddresss("珠海");
        user.setSex("男");
        return user;
    }
}

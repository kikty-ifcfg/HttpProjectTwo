package com.itheima.controller;/*
@Author:李正铠
@Date:2020年03月27日21时09分
*/

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.User;
import com.itheima.service.HelloService;
import com.itheima.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping(value = "/user")
public class UserController {

//    @Autowired
    @Reference
    private UserService userService;

    @RequestMapping(value = "/findById")
    public User findById(Integer id){
        User byId = userService.findById(id);
            return byId;
    }

    @Reference
    private HelloService helloService;

    @RequestMapping(value = "/hello")
    public User Hello(){
        User user = helloService.HelloTest();
        return user;
    }


}

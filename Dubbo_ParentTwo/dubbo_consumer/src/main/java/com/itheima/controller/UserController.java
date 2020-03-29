package com.itheima.controller;/*
@Author:李正铠
@Date:2020年03月28日22时46分
*/

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.pojo.User;
import com.itheima.service.HelloService;
import com.itheima.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping(value = "/user")
public class UserController {

    @Reference(loadbalance = "roundrobin")
    private UserService userService;

    @RequestMapping(value = "/findById")
    public User fingById(Integer id){
        System.out.println("Controller的findByid方运行");
        User byId = userService.findById(id);
        return byId;
    }

    @Reference(loadbalance = "roundrobin")
    private HelloService helloService;

    @RequestMapping(value = "/hello")
    public User hello(){
        return helloService.HelloTest();
    }


}

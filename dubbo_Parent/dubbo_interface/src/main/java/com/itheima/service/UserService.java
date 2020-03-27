package com.itheima.service;/*
@Author:李正铠
@Date:2020年03月27日19时34分
*/

import com.itheima.pojo.User;

public interface UserService {

    public User findById(Integer id);  //这里的参数id也是被Integer序列化的
}

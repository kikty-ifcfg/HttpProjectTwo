package com.itheima.dao;/*
@Author:李正铠
@Date:2020年03月27日19时42分
*/

import com.itheima.pojo.User;

public interface UserDao {

    User findById(Integer id);
}

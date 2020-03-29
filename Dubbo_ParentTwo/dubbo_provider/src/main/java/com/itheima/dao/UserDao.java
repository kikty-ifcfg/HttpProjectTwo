package com.itheima.dao;/*
@Author:李正铠
@Date:2020年03月28日22时30分
*/

import com.itheima.pojo.User;

public interface UserDao {
    User findById(Integer id);
}

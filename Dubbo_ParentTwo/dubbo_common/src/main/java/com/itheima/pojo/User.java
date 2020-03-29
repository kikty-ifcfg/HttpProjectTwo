package com.itheima.pojo;/*
@Author:李正铠
@Date:2020年03月28日22时26分
*/

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {


    private Integer id;
    private String username;
    private Date birthday;
    private String sex;
    private String addresss;

    public User() {
    }

    public User(Integer id, String username, Date birthday, String sex, String addresss) {
        this.id = id;
        this.username = username;
        this.birthday = birthday;
        this.sex = sex;
        this.addresss = addresss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddresss() {
        return addresss;
    }

    public void setAddresss(String addresss) {
        this.addresss = addresss;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                ", sex='" + sex + '\'' +
                ", addresss='" + addresss + '\'' +
                '}';
    }

}

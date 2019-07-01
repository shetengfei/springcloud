package com.itheima.domain;

import java.util.Date;

/**
 * @author ：折腾飞
 * @date ：Created in 2019/6/30
 * @description ：
 * @version: 1.0
 */
public class User {
    private Integer id;
    private String username;
    private String passsword;
    private String sex;
    private Date brithday;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", passsword='" + passsword + '\'' +
                ", sex='" + sex + '\'' +
                ", brithday=" + brithday +
                '}';
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

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBrithday() {
        return brithday;
    }

    public void setBrithday(Date brithday) {
        this.brithday = brithday;
    }
}

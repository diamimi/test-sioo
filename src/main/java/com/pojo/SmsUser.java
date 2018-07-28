package com.pojo;

import org.apache.ibatis.type.Alias;

/**
 * @Author: HeQi
 * @Date:Create in 15:00 2018/7/25
 */
@Alias(value="user")
public class SmsUser {

    private Integer id;

    private String username;

    private String address;

    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

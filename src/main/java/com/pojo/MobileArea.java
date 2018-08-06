package com.pojo;

import java.io.Serializable;

/**
 * @Author: HeQi
 * @Date:Create in 12:04 2018/8/3
 */
public class MobileArea implements Serializable{

    private String number;

    private String province;

    private Integer mtype;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getMtype() {
        return mtype;
    }

    public void setMtype(Integer mtype) {
        this.mtype = mtype;
    }
}

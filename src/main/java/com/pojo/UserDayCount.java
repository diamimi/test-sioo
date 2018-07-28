package com.pojo;

/**
 * @Author: HeQi
 * @Date:Create in 15:17 2018/7/17
 */
public class UserDayCount  {
    private Integer uid;

    private String username;

    private String company;

    private Integer total;


    private Integer fail;

    private Integer asucc;

    private Integer af;

    private Integer wz;

    private Integer time;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getFail() {
        return fail==null?0:fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Integer getAsucc() {
        return asucc;
    }

    public void setAsucc(Integer asucc) {
        this.asucc = asucc;
    }

    public Integer getAf() {
        return af;
    }

    public void setAf(Integer af) {
        this.af = af;
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "UserDayCount{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", company='" + company + '\'' +
                ", total=" + total +
                ", fail=" + fail +
                ", asucc=" + asucc +
                ", af=" + af +
                ", wz=" + wz +
                ", time=" + time +
                '}';
    }

  /*  @Override
    public int compareTo(Object o) {
        return this.getTotal().compareTo(((UserDayCount) o).getTotal());
    }*/
}

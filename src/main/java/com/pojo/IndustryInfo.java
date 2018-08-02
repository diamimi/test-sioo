package com.pojo;

import org.apache.commons.lang.StringUtils;

/**
 * @Author: HeQi
 * @Date:Create in 9:26 2018/8/2
 */
public class IndustryInfo {

    private Integer id;

    private Integer parentId;


    private Integer level;

    private String name;

    private String fullname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        name= StringUtils.replace(name,"/","_");
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }


    @Override
    public String toString() {
        return "IndustryInfo{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}

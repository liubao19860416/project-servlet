package com.saick.base.service;

import java.io.Serializable;

/**
 * 权限验证的实体bean
 * 
 * @author Liubao
 * @2014年12月18日
 * 
 */
public class Privilege implements Serializable {
    private static final long serialVersionUID = 6061388122867616200L;
    private int id;
    private String name;

    @Override
    public String toString() {
        return "Privilege [id=" + id + ", name=" + name + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

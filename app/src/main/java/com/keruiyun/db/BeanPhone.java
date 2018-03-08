package com.keruiyun.db;

/**
 * Created by Administrator on 2018/1/12.
 */

public class BeanPhone {
    int id;
    String name;
    String ip;

    public BeanPhone(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public BeanPhone() {

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}

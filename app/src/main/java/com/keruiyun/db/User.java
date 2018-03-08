package com.keruiyun.db;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2018/1/11.
 * @author 蓝美 王
 *
 *  默认账户为操作员
 *  type 0---操作员, 1---管理员
 *
 */

public class User {
    private int id;
    private String user;
    private String psw;
    private int type;
    private Bitmap userPortrait;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bitmap getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(Bitmap userPortrait) {
        this.userPortrait = userPortrait;
    }
}

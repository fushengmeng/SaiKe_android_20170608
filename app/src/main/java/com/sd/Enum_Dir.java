package com.sd;



/*
 * Created by Administrator on 2015/12/17.
 */
public enum Enum_Dir {
    // 利用构造函数传参
    DIR_root("saike"),
    DIR_img("img"),
    DIR_file("file");

    // 定义私有变量
    private String value;

    // 构造函数，枚举类型只能为私有
    Enum_Dir(String _value) {
        this.value = _value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);// 转换到字符串
    }

    public String value() {
        return this.value;
    }
}

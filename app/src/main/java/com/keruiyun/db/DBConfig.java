package com.keruiyun.db;

/**
 * Created by Administrator on 2018/1/11.
 */

public class DBConfig {

    public static class T_user{
        public String T_name="user";
        public String id="_id";
        public String user="user";
        public String psw="psw";
        public String type="type";
        public String img="img";

        public String createTable(){
           return  "create table " +T_name+
                   " (" +
                   id +" integer primary key autoincrement, " +
                   user+" text unique, " +
                   psw +" text," +
                   type +" integer, " +
                   img +" BLOB " +
                   ");";
        }
    }

    public static class T_Phone{
        public String T_name="phone";
        public String id="_id";
        public String name="name";
        public String ip="ip";


        public String createTable(){
            return  "create table " +T_name+
                    " (" +
                    id +" integer primary key autoincrement, " +
                    name+" text , " +
                    ip +" text " +
                    ");";
        }
    }

    public static class T_Phone_call{
        public String T_name="phoneCall";
        public String id="_id";
        public String name="name";
        public String ip="ip";


        public String createTable(){
            return  "create table " +T_name+
                    " (" +
                    id +" integer primary key autoincrement, " +
                    name+" text , " +
                    ip +" text " +
                    ");";
        }
    }
}

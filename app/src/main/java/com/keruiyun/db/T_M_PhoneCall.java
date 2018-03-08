package com.keruiyun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/11.
 */

public class T_M_PhoneCall {
    private Context mContext;
    private DBConfig.T_Phone_call userKeyInfo;
    private DBUser dbUser;
    private SQLiteDatabase db;

    public T_M_PhoneCall(Context mContext) {
        this.mContext = mContext;
        userKeyInfo=new DBConfig.T_Phone_call();
        dbUser=new DBUser(mContext);
        db = dbUser.getWritableDatabase();
    }

    public void close(){
        if (db!=null)
            db.close();
    }

    private Cursor query(String select){
        return db.query(userKeyInfo.T_name,null,select,null,null,null,null);
    }

    public BeanPhone insertUser(String name,String ip){
        ContentValues values=new ContentValues();
        values.put(userKeyInfo.name,name);
        values.put(userKeyInfo.ip,ip);
        try {
            long result = db.insert(userKeyInfo.T_name, userKeyInfo.ip, values);
            BeanPhone beanPhone=new BeanPhone(name,ip);
            beanPhone.setId((int) result);
            return beanPhone;
        }catch (SQLiteException e){
            e.printStackTrace();
            return null;
        }

    }



    public boolean update(int id,BeanPhone beanPhone){
        String select=userKeyInfo.id+" = "+id;
        ContentValues values=new ContentValues();
        values.put(userKeyInfo.name,beanPhone.name);
        values.put(userKeyInfo.ip,beanPhone.ip);
        int result = db.update(userKeyInfo.T_name, values, select, null);
        return result>0;
    }

    public boolean delete(int id){
        String select=userKeyInfo.id+" = "+id;
        int result = db.delete(userKeyInfo.T_name,  select, null);
        return result>0;
    }


    public List<BeanPhone> queryAll(){
        Cursor c=query(null);
        List<BeanPhone> listDatas=new ArrayList<>();
        while (c.moveToNext()){
            listDatas.add(createBean(c));
        }
        return listDatas;
    }

    private BeanPhone createBean(Cursor c){
        BeanPhone bean=new BeanPhone();
        bean.setId(c.getInt(c.getColumnIndex(userKeyInfo.id)));
        bean.setName(c.getString(c.getColumnIndex(userKeyInfo.name)));
        bean.setIp(c.getString(c.getColumnIndex(userKeyInfo.ip)));
        return bean;
    }
}

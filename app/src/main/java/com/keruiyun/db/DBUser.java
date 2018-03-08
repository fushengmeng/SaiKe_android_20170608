package com.keruiyun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.keruiyun.saike.R;

/**
 * Created by Administrator on 2018/1/11.
 */

public class DBUser extends SQLiteOpenHelper {
    public static String DB_User_Name="user.db";
    private static int DB_User_version=1;
    private Context context;

    public DBUser(Context context) {
        super(context, DB_User_Name, null, DB_User_version);
        this.context=context;
    }

    public DBUser(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(new DBConfig.T_user().createTable());
        db.execSQL(new DBConfig.T_Phone().createTable());
        db.execSQL(new DBConfig.T_Phone_call().createTable());
        inserDefaultData(db);
        inserDefaultCallData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void inserDefaultData(SQLiteDatabase db){
        DBConfig.T_Phone userKeyInfo =new DBConfig.T_Phone();
        String operating_room = context.getResources().getString(R.string.operating_room);
        for (int i=1;i<=18;i++){
            ContentValues values=new ContentValues();
            values.put(userKeyInfo.name,operating_room+""+i);
            values.put(userKeyInfo.ip,"192.168.1."+(100+i));
            db.insert(userKeyInfo.T_name, userKeyInfo.ip, values);
        }
    }

    public void inserDefaultCallData(SQLiteDatabase db){
        DBConfig.T_Phone_call userKeyInfo =new DBConfig.T_Phone_call();
        String operating_room = context.getResources().getString(R.string.operating_room);
        for (int i=1;i<=18;i++){
            ContentValues values=new ContentValues();
            values.put(userKeyInfo.name,operating_room+""+i);
            values.put(userKeyInfo.ip,"80"+i);
            db.insert(userKeyInfo.T_name, userKeyInfo.ip, values);
        }
    }



}
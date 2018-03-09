package com.keruiyun.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.keruiyun.saike.R;
import com.keruiyun.saike.util.LogCus;
import com.util.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2018/1/11.
 */

public class T_M_User {
    private Context mContext;
    private DBConfig.T_user userKeyInfo;
    private DBUser dbUser;
    private SQLiteDatabase db;

    public T_M_User(Context mContext) {
        this.mContext = mContext;
        userKeyInfo=new DBConfig.T_user();
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

    public boolean insertUser(User user){
        ContentValues values=new ContentValues();
        values.put(userKeyInfo.user,user.getUser());
        values.put(userKeyInfo.psw,user.getPsw());
        values.put(userKeyInfo.type,user.getType());
        values.put(userKeyInfo.img,bmpToByteArray(user.getUserPortrait()));
        try {
            long result = db.insert(userKeyInfo.T_name, userKeyInfo.user, values);
            LogCus.msg("注册："+result);
            String msg=mContext.getResources().getString(R.string.msg_user_register_success);
            if (result==-1){
                msg=mContext.getResources().getString(R.string.msg_user_register_exit);
            }
                ToastUtil.showToast(msg);
            return result>0;
        }catch (SQLiteException e){
            e.printStackTrace();
            String msg=mContext.getResources().getString(R.string.msg_user_register_fail);
            ToastUtil.showToast(msg);
            return false;
        }

    }

    public boolean isSurePsw(String user,String psw){
        String select=userKeyInfo.user+" = \""+user+"\"";
        Cursor c=query(select);
        if (c.moveToFirst()&&c.getCount()==1){
            String userPsw=c.getString(c.getColumnIndex(userKeyInfo.psw));
            if (userPsw.equals(psw)){
                return true;
            }else {
                String msg = mContext.getResources().getString(R.string.user_psw_wrong);
                ToastUtil.showToast(msg);
                return false;
            }

        }else {
            String format = mContext.getResources().getString(R.string.user_non);
            ToastUtil.showToast(String.format(format,user));
            return false;
        }
    }

    public String getUserPsw(String user) {
        String select=userKeyInfo.user+" = \""+user+"\"";
        Cursor c=query(select);
        String userPsw="";
        if (c.moveToFirst()) {
            userPsw=c.getString(c.getColumnIndex(userKeyInfo.psw));

        }
        c.close();
        return userPsw;
    }

    public int getUserType(String user) {
        String select=userKeyInfo.user+" = \""+user+"\"";
        Cursor c=query(select);
        int type=0;
        if (c.moveToFirst()) {
            type=c.getInt(c.getColumnIndex(userKeyInfo.type));

        }
        c.close();
        return type;
    }

    public boolean modifyUserPsw(String user,String psw) {
        String select=userKeyInfo.user+" = \""+user+"\"";
        ContentValues values=new ContentValues();
        values.put(userKeyInfo.psw,psw);
        int rowNum = db.update(userKeyInfo.T_name, values, select, null);
        return rowNum>0;
    }



    // Bitmap to byte[]
    public byte[] bmpToByteArray(Bitmap bmp) {
        if (bmp==null)
            return null;
        // Default size is 32 bytes
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    // Cursor to bitmap
    public Bitmap getPortrait(String user) {
        String select=userKeyInfo.user+" = \""+user+"\"";
        Cursor c=query(select);
        byte[] data;
        if (c.moveToFirst()) {
            data = c.getBlob(c.getColumnIndex(userKeyInfo.img));
            if (data!=null){
                try {
                    return BitmapFactory.decodeByteArray(data, 0, data.length);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        c.close();
        return null;
    }

}

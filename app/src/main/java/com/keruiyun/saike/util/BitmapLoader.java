package com.keruiyun.saike.util;

import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapLoader 
{
	public static Bitmap readBitMap(Context context, int resId)
	{  
	    BitmapFactory.Options opt = new BitmapFactory.Options();  
	    opt.inPreferredConfig = Bitmap.Config.RGB_565;   
	    opt.inPurgeable = true;  
	    opt.inInputShareable = true;  
	       
	    //获取资源图片  
	    InputStream is = context.getResources().openRawResource(resId);  
	        return BitmapFactory.decodeStream(is,null,opt);  
	}
	
	public static Bitmap readBitMap4444(Context context, int resId)
	{  
	    BitmapFactory.Options opt = new BitmapFactory.Options();  
	    opt.inPreferredConfig = Bitmap.Config.ARGB_4444;   
	    opt.inPurgeable = true;  
	    opt.inInputShareable = true;  
	       
	    //获取资源图片  
	    InputStream is = context.getResources().openRawResource(resId);  
	        return BitmapFactory.decodeStream(is,null,opt);  
	}
	
	public static Bitmap readBitMap8888(Context context, int resId)
	{  
	    BitmapFactory.Options opt = new BitmapFactory.Options();  
	    opt.inPreferredConfig = Bitmap.Config.ARGB_8888;   
	    opt.inPurgeable = true;  
	    opt.inInputShareable = true;  
	       
	    //获取资源图片  
	    InputStream is = context.getResources().openRawResource(resId);  
	        return BitmapFactory.decodeStream(is,null,opt);  
	}
}

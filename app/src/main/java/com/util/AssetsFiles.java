package com.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import com.keruiyun.saike.main.MainApplication;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2018/3/15.
 */

public class AssetsFiles {

    /**
     * 获取assets目录下的图片
     *
     * @param context
     * @param fileName
     * @return
     */
    public static Bitmap getImageFromAssetsBitmap(Context context, String fileName) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            String bgPath= MainApplication.getTheme(context)+"/"+fileName;
            InputStream is = am.open(bgPath);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }

    public static BitmapDrawable getImageFromAssetsDrawable(Context context, String fileName){
        Bitmap bitmap = getImageFromAssetsBitmap(context, fileName);
        if (bitmap!=null)
            return new BitmapDrawable(context.getResources(),bitmap);
        return null;
    }

}

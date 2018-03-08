package com.keruiyun.upload;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;

import android.view.View;
import android.widget.Button;


import com.keruiyun.saike.BaseActivity;
import com.keruiyun.saike.setting.dialog.DialogFragment_PicSelect;
import com.keruiyun.saike.util.LogCus;
import com.sd.CustomExce;
import com.sd.Enum_Dir;
import com.sd.SDCardUtils;
import com.util.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;



public class SelectPicTools {
    public final static String TAG = "SelectPicTools";
    public final static String DATA = "SelectPicTools";

    public static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int NONE = 10;
    public static final int PHOTO_RESOULT = 11;// 结果
    public static final int PHOTO_GRAPH = 12;// 拍照
    public static final int PHOTO_PICK = 13; // 缩放



    File uploadImgFile;
    private String uploadPic;
    boolean isCrop=true;//图片是否裁剪

    int resultCode=0;

    private Context mContext;

    public SelectPicTools(Context mContext) {
        this.mContext = mContext;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        photoSelectActivityResult(requestCode, resultCode, data);
    }

    public void loadImg(View view) {

        DialogFragment_PicSelect picSelect=new DialogFragment_PicSelect();
        picSelect.setPicPopupListene(new DialogFragment_PicSelect.PicPopupListener() {
            @Override
            public void takePhone(Button v) {
                selectTakePhone();
            }

            @Override
            public void pickPhone(Button v) {
                selectPickPhone();
            }
        });
        picSelect.show(((BaseActivity)mContext).getSupportFragmentManager(),DialogFragment_PicSelect.class.getName());
        /*new SelectPicPopupWindow(mContext, new SelectPicPopupWindow.PicPopupListener() {
            @Override
            public void takePhone(Button v) {
                selectTakePhone();
                new RxPermissions(SelectPicTools.this)
                        .request(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    selectTakePhone();
                                }
                            }
                        });

            }

            @Override
            public void pickPhone(Button v) {
                selectPickPhone();
                new RxPermissions(SelectPicTools.this)
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean) {
                                    selectPickPhone();
                                }
                            }
                        });

            }
        }).showPopupWindow(view);*/
    }



    /**
     * 相册
     */
    private void selectPickPhone() {
        String uploadImgName = getImgName();
        File dir;
        try {
            dir = SDCardUtils.getDirFile(Enum_Dir.DIR_img);
        } catch (CustomExce customExce) {
            customExce.printStackTrace();
            ToastUtil.showToast( customExce.getMessage());
            return;
        }
        uploadImgFile = new File(dir, uploadImgName);
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,IMAGE_UNSPECIFIED);
        ((BaseActivity)mContext).startActivityForResult(intent, PHOTO_PICK);
    }

    /**
     * 拍照
     */
    private void selectTakePhone() {
        String uploadImgName = getImgName();
        File dir;
        try {
            dir = SDCardUtils.getDirFile(Enum_Dir.DIR_img);
        } catch (CustomExce customExce) {
            customExce.printStackTrace();
            ToastUtil.showToast( customExce.getMessage());
            return;
        }
        uploadImgFile = new File(dir, uploadImgName);
        /** getUriForFile(Context context, String authority, File file):此处的authority需要和manifest里面保持一致。
         *   photoURI打印结果：content://cn.lovexiaoai.myapp.fileprovider/camera_photos/Pictures/Screenshots/testImg.png 。
         *   这里的camera_photos:对应filepaths.xml中的name
         */
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri photoURI = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", uploadImgFile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(uploadImgFile));
        }*/
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(uploadImgFile));
        ((BaseActivity)mContext).startActivityForResult(intent, PHOTO_GRAPH);
    }

    private String getImgName() {
//        return  "test.JPEG";
        return System.currentTimeMillis() + ".JPEG";
    }


    public void photoSelectActivityResult(int requestCode, int resultCode, Intent data){
        LogCus.msg(TAG+"图片选择：isCrop:"+isCrop+":requestCode:"+requestCode+"\tresultCode:"+resultCode);

        if (requestCode == NONE)
            return;
        switch (requestCode){
            case PHOTO_PICK://从相册返回的数据

                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    if (isCrop){
                        crop(uri);
                    }else {
                        String[] proj = {MediaStore.Images.Media.DATA};
                        Cursor cursor = mContext.getContentResolver().query(uri, proj, null, null, null);
                        if(cursor.moveToFirst()) {
                            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                            String path = cursor.getString(column_index);
                            LogCus.msg(TAG+"相册图片返回：:"+path+"");
                            uploadImgFile=new File(path);
                            selectImgResult(path);
                        }
                        cursor.close();
                    }


                }
                break;
            case PHOTO_GRAPH:// 从相机返回的数据

                try {
                    if (SDCardUtils.getDirFile(Enum_Dir.DIR_img)!=null) {
                        if (isCrop){
                            Uri photoURI;
                            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                photoURI = FileProvider.getUriForFile(this,
                                        getPackageName()+".fileprovider", uploadImgFile);

                            } else {
                                photoURI= Uri.fromFile(uploadImgFile);
                            }*/
                            photoURI= Uri.fromFile(uploadImgFile);
                            crop(photoURI);
                        }else {
                            LogCus.msg(TAG+"相机图片返回：:"+uploadImgFile.getAbsolutePath()+"");
                            selectImgResult(uploadImgFile.getAbsolutePath());

                        }
                    } else {
                        ToastUtil.showToast("sd无效");
                    }
                } catch (CustomExce customExce) {
                    customExce.printStackTrace();
                    ToastUtil.showToast( "sd无效");
                }
                break;
            case PHOTO_RESOULT://从剪切图片返回的数据

                LogCus.msg(TAG+"剪切图片返回：imgFile:exists:"+this.uploadImgFile.exists());
                LogCus.msg(TAG+"剪切图片返回：imgFile:exists:"+this.uploadImgFile.getAbsolutePath());
                selectImgResult(uploadImgFile.getAbsolutePath());


                break;
        }
    }

    public void selectImgResult(String path) {

    }


    /*
    * 剪切图片
    */
    private void crop(Uri uri) {
        // 裁剪图片意图
//        L.d(TAG,"裁剪图片:"+uri.getAuthority());
        Intent intent = new Intent("com.android.camera.action.CROP");

        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
//        // 裁剪框的比例，1：1
        LogCus.msg("手机："+ Build.BRAND+"");
        if (Build.BRAND.equals("HUAWEI")){
            intent.putExtra("aspectX", 9998);
            intent.putExtra("aspectY", 9999);
        }else {
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
        }
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX",400);
        intent.putExtra("outputY",400);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,  Uri.fromFile(uploadImgFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG);// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", false);
        // 开启一个带有返回值的Activity，PHOTO_RESOULT
        ((BaseActivity)mContext).startActivityForResult(intent, PHOTO_RESOULT);
    }

    public static void saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return ;
        }
        return ;
    }



}

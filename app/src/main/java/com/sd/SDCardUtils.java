package com.sd;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.storage.StorageManager;

import com.keruiyun.saike.util.LogCus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


/**
 * Operation about SD card
 *
 * @Create 2013-6-17
 */
public class SDCardUtils {

    /**
     * Check the SD card
     *
     * @return
     */
    private static boolean checkSDCardAvailable() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }

    public static boolean checkSDCardAvailable(String sdState) {
        return sdState.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = FileUtils.readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    public static List<StorageInfo> listAvaliableStorage(Context context) {
        ArrayList storagges = new ArrayList();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);
                    File file = new File(info.path);
                    if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
                        /**
                        *StorageVolume这个类中提供了一个isRemovable()接口，
                         * 通过反射调用它就可以知道存储器是否可以移除。
                         * 把可以移除的存储器认定为外置sdcard，不可移除的存储器认定为内置存储器。
                        * */
                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                        String state = null;
                        try {
                            /**
                             *存储器的挂载状态
                             */
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager, info.path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (info.isMounted()) {
                            info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                            storagges.add(info);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        storagges.trimToSize();

        return storagges;
    }

    /**
     *@param dir  遍历 dir目录中音频文件
     * @return 音频文件列表
     */
    public static ArrayList<File> getAudioList(String dir) {
        File rootDir=new File(dir);
        ArrayList<File> files = new ArrayList<File>();
        if (!rootDir.exists())
            return files;
        if (rootDir.isDirectory()){
            File[] listFiles = rootDir.listFiles();
            if (listFiles!=null){
                for (int i = 0; i < listFiles.length; i++) {
                    if (listFiles[i].isDirectory()){
                        files.addAll(getAudioList(listFiles[i].getAbsolutePath()));
                    }else if (listFiles[i].isFile()){
                        boolean isAudio=MediaFile.isAudioFileType(listFiles[i].getAbsolutePath());
//                        LogCus.msg("目录扫描:isAudio:"+isAudio+":"+listFiles[i].getAbsolutePath());
                        if (isAudio){
                            files.add(listFiles[i]);
                        }
                    }
                }
            }

        }else {
           if (rootDir.isFile()){
               if (MediaFile.isAudioFileType(rootDir.getAbsolutePath())){
                   files.add(rootDir);
               }
           }
        }



        return files;
    }


    /**
     * 获取扩展SD卡存储目录
     *
     * 如果有外接的SD卡，并且已挂载，则返回这个外置SD卡目录
     * 否则：返回内置SD卡目录
     *
     * @return
     */
    private static File getExternalSdCardPath() {
        if (checkSDCardAvailable()) {
            File sdCardFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
            sdCardFile=new File(sdCardFile, Enum_Dir.DIR_root.toString());
            if (!sdCardFile.exists()){
                sdCardFile.mkdirs();
            }
            return sdCardFile;
        }

        String path = null;

        File sdCardFile = null;

        ArrayList<String> devMountList = getDevMountList();
        for (String devMount : devMountList) {
            File file = new File(devMount);
            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();
                File testWritable = new File(path, Enum_Dir.DIR_root.toString());
                if (testWritable.exists()){
                    path=testWritable.getAbsolutePath();
                }else{
                    if (testWritable.mkdirs()) {
                        path=testWritable.getAbsolutePath();
                    } else {
                        path = null;
                    }
                }

            }
        }

        if (path != null) {
            sdCardFile = new File(path);
            return sdCardFile;
        }

        return null;
    }

    /**获取目录*/
    public static File getDirFile(Enum_Dir enum_dir) throws CustomExce{
        File imgFile=getExternalSdCardPath();
        if (imgFile==null){

            throw new CustomExce("获取SD卡失败："+FileUtils.readFile("/etc/vold.fstab"));
        }else{
            if (Enum_Dir.DIR_root==enum_dir){
                return imgFile;
            }else{
                imgFile=new File(imgFile, enum_dir.toString());
                if (!imgFile.exists())
                    imgFile.mkdirs();
            }

        }

        return imgFile;
    }

    /**
     * 递归删除文件和文件夹
     * 要删除的根目录
     */
    public static void deleteDirFile(File file){

        if(file.isFile()){
//            if (file.getName().endsWith(".apk"))
            file.delete();
            return;
        }
        if(file.isDirectory()){
            File[] childFile = file.listFiles();
            if(childFile == null || childFile.length == 0){
                file.delete();
                return;
            }
            for(File f : childFile){
                deleteDirFile(f);
            }
            file.delete();
        }
    }


    /**
     * Check if the file is exists
     * @param filePath
     * @param fileName
     * @return
     */
    public static boolean isFileExistsInSDCard(String filePath, String fileName){
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File file = new File(filePath, fileName);
            if (file.exists()) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Write file to SD card
     * @param filePath
     * @param filename
     * @param content
     * @return
     * @throws Exception
     */
    public static boolean saveFileToSDCard(String filePath, String filename, String content)
            throws Exception {
        boolean flag = false;
        if (checkSDCardAvailable()) {
            File dir = new File(filePath);
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(filePath, filename);
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(content.getBytes());
            outStream.close();
            flag = true;
        }
        return flag;
    }

    /**
     * Read file as stream from SD card
     *
     * @param fileName
     *            String PATH =
     *            Environment.getExternalStorageDirectory().getAbsolutePath() +
     *            "/dirName";
     * @return
     */
    public static byte[] readFileFromSDCard(String filePath, String fileName) {
        byte[] buffer = null;
        try {
            if (checkSDCardAvailable()) {
                String filePaht = filePath + "/" + fileName;
                FileInputStream fin = new FileInputStream(filePaht);
                int length = fin.available();
                buffer = new byte[length];
                fin.read(buffer);
                fin.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * Delete file
     *
     * @param filePath
     * @param fileName
     *            filePath =
     *            android.os.Environment.getExternalStorageDirectory().getPath()
     * @return
     */
    public static boolean deleteSDFile(String filePath, String fileName) {
        File file = new File(filePath + "/" + fileName);
        if (file == null || !file.exists() || file.isDirectory())
            return false;
        return file.delete();
    }

    /**获取img
     *
     * */
    public static Drawable getImageDrawable(File file){
        if(!file.exists()) return null;
        try{
            InputStream inp = new FileInputStream(file);
            BitmapDrawable bd = new BitmapDrawable(BitmapFactory.decodeStream(inp));
            Drawable d = (Drawable) bd;
            return d;

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
}

package com.keruiyun.saike.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class LoadLocalImageUtil {

	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
			.bitmapConfig(Bitmap.Config.ARGB_8888).build();

	private LoadLocalImageUtil() {

	}

	private static LoadLocalImageUtil instance = null;

	public static synchronized LoadLocalImageUtil getInstance() {
		if (instance == null) {
			instance = new LoadLocalImageUtil();
		}
		return instance;
	}

	/**
	 * 从内存卡中异步加载本地图片
	 * 
	 * @param uri
	 * @param imageView
	 */
	public void displayFromSDCard(String uri, ImageView imageView) {
		// String imageUri = "file:///mnt/sdcard/image.png"; // from SD card
		ImageLoader.getInstance().displayImage("file://" + uri, imageView);
	}

	/**
	 * 从assets文件夹中异步加载图片
	 * 
	 * @param imageName
	 *            图片名称，带后缀的，例如：1.png
	 * @param imageView
	 */
	public void dispalyFromAssets(String imageName, ImageView imageView) {
		// String imageUri = "assets://image.png"; // from assets
		ImageLoader.getInstance().displayImage("assets://" + imageName,
				imageView);
	}

	/**
	 * 从drawable中异步加载本地图片
	 * 
	 * @param imageId
	 * @param imageView
	 */
	public void displayFromDrawable(int imageId, ImageView imageView) {
		// String imageUri = "drawable://" + R.drawable.image; // from drawables
		// (only images, non-9patch)
		ImageLoader.getInstance().displayImage("drawable://" + imageId,
				imageView, options);
	}

	/**
	 * 从内容提提供者中抓取图片
	 */
	public void displayFromContent(String uri, ImageView imageView) {
		// String imageUri = "content://media/external/audio/albumart/13"; //
		// from content provider
		ImageLoader.getInstance().displayImage("content://" + uri, imageView);
	}
}

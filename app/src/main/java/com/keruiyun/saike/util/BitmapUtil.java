package com.keruiyun.saike.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapUtil {

	public synchronized static Bitmap getBitmap(String path) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opts);

		opts.inJustDecodeBounds = false;
		try {
			opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
			opts.inPurgeable = true;
			opts.inInputShareable = true;
			Bitmap bmp = BitmapFactory.decodeFile(path, opts);

			return bmp;
		} catch (OutOfMemoryError err) {

		}
		return null;
	}

}

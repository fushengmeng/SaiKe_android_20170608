package com.keruiyun.saike.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CommonDef {
	public static final int REQUEST_ALBUM = 1; // ?????
	public static final int REQUEST_CAMERA = 2; // ?????
	public static final int REQUEST_VILLAGE = 3; // ???§³??
	public static final int REQUEST_MOIFYINFO = 4; // ??????
	public static final int REQUEST_REALEASETOPIC = 5; // ????????
	public static final int REQUEST_REALEASEVENT = 6; // ??????
	public static final int REQUEST_REALEASRECOMMEND = 7; // ??????????
	public static final int REQUEST_CAMERA_DATA = 8; // ?????¨¹?????

	public static final int RESULT_MOIFYNICKNAME = 1; // ??????
	public static final int RESULT_MOIFYSEX = 2; // ??????
	public static final int RESULT_MOIFYSIGNATUE = 3; // ?????????
	public static final int RESULT_MOIFYMOBILE = 4; // ??????
	public static final int RESULT_MOIFYEMAIL = 5; // ???????
	public static final int RESULT_MOIFYREMARK = 6; // ?????

	public static final int ImageShowSize = 120;
	public static final int ImageShowSize1 = 140;

	public static byte[] readStream(InputStream inStream) throws Exception {
		byte[] buffer = new byte[1024];
		int len = -1;
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		byte[] data = outStream.toByteArray();
		outStream.close();
		inStream.close();
		return data;
	}

	public static Bitmap getPicFromBytes(byte[] bytes,
			BitmapFactory.Options opts) {
		if (bytes != null)
			if (opts != null)
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
						opts);
			else
				return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		return null;
	}

	// ?§Ø????????????
	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern
				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,1,3,5-9])|(17[8,7,6]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	// ?§Ø?email?????????
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	// ?§Ø???????????
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	// ?§Ø????????????
	public static boolean isChineseChara(String str) {
		Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.find();// true????????????????false
	}

	// ??????????????????...
	public static String getSubString(String str, int index) {
		String strResult = str;
		if (str.length() - 1 > index) {
			strResult = str.substring(0, index);
			strResult += "...";
		}
		return strResult;
	}

	public static int StringToSecond(String fomat, String dateTime) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new SimpleDateFormat(fomat).parse(dateTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return (int) (c.getTimeInMillis() / 1000);
	}

	public static void setListViewHeight(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	@SuppressLint("SimpleDateFormat")
	public static File getPhotoFileName() {
		String state = Environment.getExternalStorageState(); // ?§Ø???????sd??
		if (state.equals(Environment.MEDIA_MOUNTED)) { // ???????????????
			File photoDir = new File(Environment.getExternalStorageDirectory()
					+ "/DCIM/Camera");
			photoDir.mkdirs();// ?????????¥??
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"'IMG'yyyyMMddHHmmss");
			return new File(photoDir, dateFormat.format(date) + ".png");
		}
		return null;
	}

	public static Intent getCropImageIntent(Bitmap data) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setType("image/*");
		intent.putExtra("data", data);
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 128);
		intent.putExtra("outputY", 128);
		intent.putExtra("return-data", true);
		return intent;
	}

	public static Intent getCropImageIntent(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 128);
		intent.putExtra("outputY", 128);
		intent.putExtra("return-data", true);
		return intent;
	}

	public static String[] resourceList = { "[§¸??]", "[????]", "[??§¸]",
			"[????]", "[???]", "[?]", "[????]", "[????]", "[????]", "[????]",
			"[???§¸]", "[??]", "[????]", "[????]", "[????]", "[??]", "[???]",
			"[????]", "[??]", "[????]", "0", "[????]", "[??]", "[???]", "[??]",
			"[??]" };
}

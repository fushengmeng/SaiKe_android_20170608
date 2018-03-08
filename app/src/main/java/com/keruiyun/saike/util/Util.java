package com.keruiyun.saike.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Base64;

/**
 * The util class
 */
public class Util {

	public static boolean checkIsRealPhone(Context context) {
		boolean ret = true;

		TelephonyManager telmgr = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceID = telmgr.getDeviceId();
		boolean isEmulator = "000000000000000".equalsIgnoreCase(deviceID);
		if (isEmulator) {
			ret = false;
		}

		return ret;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static int getScreenHeight(Activity act) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	public static int getScreenWidth(Activity act) {
		DisplayMetrics outMetrics = new DisplayMetrics();
		act.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	public static boolean isContainsNumber(String mobiles) {
		Pattern p = Pattern.compile("[0-9]");
		Matcher m = p.matcher(mobiles);
		return m.find();
	}

	public static StateListDrawable getStateListDrawable(Context ctx, float v,
			String color, String press) {
		int radius = dip2px(ctx, v);
		float[] outerR = new float[] { radius, radius, radius, radius, radius,
				radius, radius, radius };
		RoundRectShape roundRectShape = new RoundRectShape(outerR, null, null);
		StateListDrawable sd = new StateListDrawable();
		ShapeDrawable myShapeDrawable = new ShapeDrawable(roundRectShape);
		myShapeDrawable.getPaint().setColor(Color.parseColor(color));
		if (null != press) {
			RoundRectShape pressRectShape = new RoundRectShape(outerR, null,
					null);
			ShapeDrawable pressShapeDrawable = new ShapeDrawable(pressRectShape);
			pressShapeDrawable.getPaint().setColor(Color.parseColor(press));
			sd.addState(new int[] { android.R.attr.state_pressed,
					android.R.attr.state_enabled }, pressShapeDrawable);
		}
		sd.addState(new int[] {}, myShapeDrawable);
		return sd;
	}

	public static String UUID() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	public static String EncryptAsDoNet(String message, String key)
			throws Exception {
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
		byte[] encryptbyte = cipher.doFinal(message.getBytes());
		return new String(Base64.encode(encryptbyte, Base64.DEFAULT));
	}

	public static String replaceHtml(String html) {
		return html.replace("&lt;", "<").replace("&gt;", ">")
				.replace("&amp;ldquo;", "“").replace("&amp;rdquo;", "”")
				.replace("&amp;nbsp;", " ").replace("&amp;", "&")
				.replace("<p>", "").replace("</p>", "");
	}

	public static String getMyUUID(Activity ctx) {
		final TelephonyManager tm = (TelephonyManager) ctx.getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		final String tmDevice, tmSerial, tmPhone, androidId;
		tmDevice = "" + tm.getDeviceId();
		tmSerial = "" + tm.getSimSerialNumber();
		androidId = ""
				+ android.provider.Settings.Secure.getString(
						ctx.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
		UUID deviceUuid = new UUID(androidId.hashCode(),
				((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
		String uniqueId = deviceUuid.toString();
		return uniqueId;
	}

	public static String getLocalMacAddress(Context ctx) {
		WifiManager wifiMgr = (WifiManager) ctx
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = (null == wifiMgr ? null : wifiMgr.getConnectionInfo());
		if (null != info) {
			return info.getMacAddress();
			// ip = int2ip(info.getIpAddress());
		}
		return "";
	}

	public static String int2ip(long ipInt) {
		StringBuilder sb = new StringBuilder();
		sb.append(ipInt & 0xFF).append(".");
		sb.append((ipInt >> 8) & 0xFF).append(".");
		sb.append((ipInt >> 16) & 0xFF).append(".");
		sb.append((ipInt >> 24) & 0xFF);
		return sb.toString();
	}

	public static float sp2px(Resources resources, float sp) {
		final float scale = resources.getDisplayMetrics().scaledDensity;
		return sp * scale;
	}

	public static float dp2px(Resources resources, float dp) {
		final float scale = resources.getDisplayMetrics().density;
		return dp * scale + 0.5f;
	}

	public static String getFileType(String fileName) {
		int i = fileName.lastIndexOf(".");
		if (i > -1 && i < fileName.length()) {
			return fileName.substring(i + 1);
		}
		return "";
	}
}

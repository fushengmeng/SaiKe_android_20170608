package com.keruiyun.saike.util;

import android.content.Context;

public class UserUtil {
	public static void saveUserName(Context ctx, String userName) {
		PreferencesUtil.getInstance(ctx).setStringValue("User", "userName",
				userName);
	}

	public static String getUserName(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				"userName", "");
	}

	public static void savePassword(Context ctx, String password) {
		PreferencesUtil.getInstance(ctx).setStringValue("User", "password",
				password);
	}

	public static String getPassword(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				"password", "");
	}

	public static String getShipAddr(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "shipAddr", "");
	}

	public static void saveShipAddr(Context ctx, String addr) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "shipAddr", addr);
	}

	public static String getUserData(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "userData", "");
	}

	public static void saveUserData(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "userData", data);
	}

	public static String getUserAddress(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "userAddress", "");
	}

	public static void saveUserAddress(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "userAddress", data);
	}

	public static int getUserID(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getIntValue("User",
				getUserName(ctx) + "userID", 0);
	}

	public static void saveUserID(Context ctx, int ID) {
		PreferencesUtil.getInstance(ctx).setIntValue("User",
				getUserName(ctx) + "userID", ID);
	}

	public static String getLoginType(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "loginType", "");
	}

	public static void saveLoginType(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "loginType", data);
	}

	public static int getUserStar(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getIntValue("User",
				getUserName(ctx) + "userStar", 1);
	}

	public static void saveUserStar(Context ctx, int ID) {
		PreferencesUtil.getInstance(ctx).setIntValue("User",
				getUserName(ctx) + "userStar", ID);
	}

	public static String getAvatar(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "avatar", "");
	}

	public static void saveAvatar(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "avatar", data);
	}

	public static String getPoster(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "Poster", "");
	}

	public static void savePoster(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "Poster", data);
	}

	public static String getProfuct(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				getUserName(ctx) + "Product", "");
	}

	public static void saveProduct(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User",
				getUserName(ctx) + "Product", data);
	}

	public static String getBanner(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				"Banner", "");
	}

	public static void saveBanner(Context ctx, String data) {
		PreferencesUtil.getInstance(ctx).setStringValue("User", "Banner", data);
	}

	public static boolean isFirstLoad(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getBooleanValue("User",
				"FirstLoad", true);
	}

	public static void setFirstLoad(Context ctx, boolean val) {
		PreferencesUtil.getInstance(ctx).setBooleanValue("User", "FirstLoad",
				val);
	}

	public static boolean isLogin(Context ctx) {
		if (UserUtil.getUserName(ctx).trim().length() > 0
				&& UserUtil.getPassword(ctx).trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static void saveUser(Context ctx, String user) {
		PreferencesUtil.getInstance(ctx).setStringValue("User", "user", user);
	}

	public static String getUser(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User", "user",
				"");
	}

	public static void saveUserLst(Context ctx, String userName) {
		PreferencesUtil.getInstance(ctx).setStringValue("User", "userLst",
				userName);
	}

	public static String getUserLst(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getStringValue("User",
				"userLst", "");
	}

	public static boolean isRemember(Context ctx) {
		return PreferencesUtil.getInstance(ctx).getBooleanValue("User",
				"remember", true);
	}

	public static void saveRemember(Context ctx, boolean val) {
		PreferencesUtil.getInstance(ctx).setBooleanValue("User", "remember",
				val);
	}
}

package com.keruiyun.saike.util;

import android.os.Environment;

public class Consts {
	public final static int SUCCESS = 100;
	public final static int FAILTURE = -1;
	public final static boolean DEBUG = false;

	public static String USB_PATH = "";

	public final static String SDCARD_PATH = Environment
			.getExternalStorageDirectory() + "/";
	public final static String SAUNA_SYSTEMPATH = SDCARD_PATH + "sauna/";
	public final static String SAUNA_VIDEO_PATH = SAUNA_SYSTEMPATH + "video/";
	public final static String VOLTAGE_UNIT = "V";
	public final static String ELEC_UNIT = "A";
	public final static String POWER_UNIT = "W";
	public final static String TEMP_UNIT = "W";

	public final static int VIEW_W = 1560;
	public final static int VIEW_H = 2833;

	public static String USERNAME = "";
	public final static boolean ISSHOWBIG = true;
	public final static boolean LOGINVALID = false;
	public static boolean IS_MANAGER = false;
	public static boolean IS_STATUS_BAR = true;
	
	public static boolean IS_READ_BY_TWO_STEP = true; // 分2次读完整协议
	public static boolean IS_SHOW_VIDEO_CALL = false; // 显示视频童话
}

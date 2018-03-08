package com.keruiyun.saike.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCompletedReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		String pkName = arg0.getPackageName();
		Intent intent = arg0.getPackageManager().getLaunchIntentForPackage(
				pkName);
		arg0.startActivity(intent);
	}

}

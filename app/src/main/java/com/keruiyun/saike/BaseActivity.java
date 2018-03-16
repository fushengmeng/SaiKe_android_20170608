package com.keruiyun.saike;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bilibili.magicasakura.utils.ThemeHelper;
import com.bilibili.magicasakura.utils.ThemeUtils;

import com.keruiyun.saike.fragment.OnDialogFragmentListener;
import com.keruiyun.saike.main.MainApplication;
import com.keruiyun.saike.setting.ViewHolderAdvancedSetup;
import com.keruiyun.saike.util.Consts;


import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.ButterKnife;

import static com.keruiyun.saike.main.MainApplication.context;
import static com.keruiyun.saike.main.MainApplication.getInstance;


public abstract class BaseActivity extends FragmentActivity implements ViewHolderAdvancedSetup.ClickListener {
	public BaseActivity instance;


	private ArrayList<OnDialogFragmentListener> mThemeListener = new ArrayList<>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setColorThemeTxt(ThemeHelper.getTheme(this));
		setContentView(loadContentView());
		instance=this;
		// TODO: add setContentView(...) invocation
		ButterKnife.bind(this);

		initView();
		initMusic();

	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		if (intent!=null)
			setIntent(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		for (OnDialogFragmentListener listener:mThemeListener){
			listener.onActivityResultListener(requestCode, resultCode, data);
		}
	}


	private void initMusic(){

	}

	private void setColorThemeTxt(int currentTheme){
		switch (currentTheme){
			case ThemeHelper.THEME_YELLOW:
				MainApplication.getInstance().colorThemeTxtRes=R.color.black;
				break;
			default:
				MainApplication.getInstance().colorThemeTxtRes=R.color.white;


		}
	}

	@Override
	public void onConfirm(int currentTheme) {
		setColorThemeTxt(currentTheme);

		if (ThemeHelper.getTheme(this) != currentTheme) {
			ThemeHelper.setTheme(this, currentTheme);
			ThemeUtils.refreshUI(this, new ThemeUtils.ExtraRefreshable() {
						@Override
						public void refreshGlobal(Activity activity) {
							//for global setting, just do once
//							if (Build.VERSION.SDK_INT >= 21) {
////								BaseActivity content = BaseActivity.this;
//								ActivityManager.TaskDescription taskDescription = new ActivityManager.TaskDescription(null, null, ThemeUtils.getThemeAttrColor(context, android.R.attr.colorPrimary));
//								setTaskDescription(taskDescription);
////								getWindow().setStatusBarColor(ThemeUtils.getColorById(content, R.color.theme_color_primary));
//							}

						}

						@Override
						public void refreshSpecificView(View view) {
						}
					}
			);
		}
		changeTheme();
	}



	public void setThemeListener( OnDialogFragmentListener listener) {

		if (listener != null) {
			mThemeListener.add(listener);
		}
	}

	public void removeThemeListener( OnDialogFragmentListener listener) {
		if (listener != null) {
			mThemeListener.remove(listener);
		}
	}


	public void updateTime() {

	}



	public void changeTheme() {
		for (OnDialogFragmentListener listener:mThemeListener){
			listener.onDialogFragmentRefreshThemeListener();
		}

	}




	/**
	 * @param outState 取消保存状态
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		//super.onSaveInstanceState(outState);
	}

	/**
	 * @param savedInstanceState 取消保存状态
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		//super.onRestoreInstanceState(savedInstanceState);
	}

	public abstract int loadContentView();

	public void initView(){};

	public static void hideSoftInput(View view){
		InputMethodManager imm = (InputMethodManager)getInstance().getSystemService(Context.INPUT_METHOD_SERVICE);

//		// 获取软键盘的显示状态
//		boolean isOpen=imm.isActive();
//
//		// 如果软键盘已经显示，则隐藏，反之则显示
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//
//		// 隐藏软键盘
//		imm.hideSoftInputFromWindow(view, InputMethodManager.HIDE_NOT_ALWAYS);
//
//		// 强制显示软键盘
//		imm.showSoftInput(view,InputMethodManager.SHOW_FORCED);

		// 强制隐藏软键盘
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}




	protected void displayStatusBar() {
		if (!Consts.IS_STATUS_BAR) {
			Intent intent = new Intent("com.android.action.display_statusbar");
			sendBroadcast(intent);
			Intent intent2 = new Intent(
					"com.android.action.display_navigationbar");
			sendBroadcast(intent2);
			Consts.IS_STATUS_BAR = true;
		}
	}

	protected void hideStatusBar() {
		if (Consts.IS_STATUS_BAR) {
			Intent intent = new Intent("com.android.action.hide_statusbar");
			sendBroadcast(intent);
			Intent intent2 = new Intent("com.android.action.hide_navigationbar");
			sendBroadcast(intent2);
			Consts.IS_STATUS_BAR = false;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		hideStatusBar();
	}

	public void backFinish(){
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		mThemeListener.clear();

	}

}

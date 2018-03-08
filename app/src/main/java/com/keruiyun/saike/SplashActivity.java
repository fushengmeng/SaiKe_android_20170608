/*
 ============================================================================
 Name        : SplashActivity.java
 Version     : 1.0.0
 Copyright   : www.keruiyun.com
 Description : 启动页
 ============================================================================
 */

package com.keruiyun.saike;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class SplashActivity extends BaseActivity {
	private ImageView iv, iv1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_splash);
		iv = (ImageView) findViewById(R.id.imageView1);
		iv1 = (ImageView) findViewById(R.id.imageView2);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(SplashActivity.this,
						HomeActivity.class);
				startActivity(intent);
				finish();
			}
		}, 2000);
	}

	@Override
	public int loadContentView() {
		return 0;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		iv = null;
		iv1 = null;
	}
}

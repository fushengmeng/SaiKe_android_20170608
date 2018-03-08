/*
 * Copyright (C) 2013 47 Degrees, LLC
 *  http://47deg.com
 *  hello@47deg.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.keruiyun.saike.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {

	public static final String SHARED_PREFERENCES_NAME = "RENRENCAI_PREFERENCES";

	/**
	 * Instance
	 */
	private static PreferencesUtil preferencesManager = null;

	/**
	 * Shared Preferences
	 */
	private SharedPreferences sharedPreferences;

	/**
	 * Constructor
	 * 
	 * @param context
	 */
	private PreferencesUtil(Context context) {
		sharedPreferences = context.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

	public static PreferencesUtil getInstance(Context context) {
		if (preferencesManager == null) {
			preferencesManager = new PreferencesUtil(context);
		}
		return preferencesManager;
	}

	public void setBooleanValue(String category, String title, boolean value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putBoolean(category + "_" + title, value);
		editor.commit();
	}

	public boolean getBooleanValue(String category, String title, boolean def) {
		return sharedPreferences.getBoolean(category + "_" + title, def);
	}

	public void setStringValue(String category, String title, String value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString(category + "_" + title, value);
		editor.commit();
	}

	public String getStringValue(String category, String title, String def) {
		return sharedPreferences.getString(category + "_" + title, def);
	}

	public void setIntValue(String category, String title, int value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putInt(category + "_" + title, value);
		editor.commit();
	}

	public int getIntValue(String category, String title, int defaultValue) {
		return sharedPreferences.getInt(category + "_" + title, defaultValue);
	}
	
	public void setLongValue(String category, String title, long value) {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putLong(category + "_" + title, value);
		editor.commit();
	}

	public long getLongValue(String category, String title, long defaultValue) {
		return sharedPreferences.getLong(category + "_" + title, defaultValue);
	}
}

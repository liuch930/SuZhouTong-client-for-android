package com.hua.goddess.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * 配置文件内容
 */
public class PreferencesUtils {

	private Context ctx;
	private Editor editor;
	private SharedPreferences sp;
	
	// 根据文件名，取配置文件
	public PreferencesUtils(Context context, String preferenceName) {
		ctx = context;
		sp = ctx.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
		editor = sp.edit();
	}
	
	//不包含文件名，取默认配置文件
	public PreferencesUtils(Context context) {
		ctx = context;
		sp = PreferenceManager.getDefaultSharedPreferences(ctx);
		editor = sp.edit();
	}
	
	public boolean getBoolean(String key, boolean defValue) {
		return sp.getBoolean(key, defValue);
	}
	
	public void putBoolean(String key, boolean state) {
		editor.putBoolean(key, state);
		editor.commit();
	}
	
	public String getString(String key, String defValue) {
		return sp.getString(key, defValue);
	}
	
	public void putString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
	}
	
	public int getInt(String key, int defValue) {
		return sp.getInt(key, defValue);
	}
	
	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}
	
	public void putLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}
	
	public long getLong(String key, Long defValue) {
		return sp.getLong(key, defValue);
	}
	
	public boolean contains(String key){
		return sp.contains(key);
	}
	
	public void remove(String key){
		editor.remove(key);
		editor.commit();
	}
}



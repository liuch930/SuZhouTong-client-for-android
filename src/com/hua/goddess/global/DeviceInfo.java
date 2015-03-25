package com.hua.goddess.global;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
/**
 * 获取设备信息
 * @author Evil
 *
 */
public class DeviceInfo {

	/**
	 * 获取当前网络状态
	 * @return NetworkInfo
	 */
	public static NetworkInfo getCurrentNetStatus(Context ctx) {
		ConnectivityManager manager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getActiveNetworkInfo();
	}
	
	/**
	 * 获取网络连接状态
	 * @param ctx
	 * @return true:有网 false：没网
	 */
	public static boolean isNetworkAvailable(Context ctx){
		NetworkInfo nki = getCurrentNetStatus(ctx);
		if(nki!=null)
			return nki.isAvailable();
		else
			return false;
	}
	
	/**
	 * 获取可用存储空间大小
	 * 若存在SD卡则返回SD卡剩余控件大小
	 * 否则返回手机内存剩余空间大小
	 * @return
	 */
	public static long getAvailableStorageSpace(){
		long externalSpace = getExternalStorageSpace();
		if(externalSpace==-1L){
			return getInternalStorageSpace();
		}
		
		return externalSpace;
	}
	
	/**
	 * 获取SD卡可用空间
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getExternalStorageSpace(){
		long availableSDCardSpace = -1L;
		//存在SD卡
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());   
			long blockSize = sf.getBlockSize();//块大小,单位byte
			long availCount = sf.getAvailableBlocks();//可用块数量
//			long blockCount = sf.getBlockCount();//块总数量
//			Log.d("", "block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");   
//			Log.d("", "可用的block数目：:"+ availCount+",剩余空间:"+ availCount*blockSize/1024+"KB");   
			
			availableSDCardSpace = availCount*blockSize/1024/1024;//可用SD卡空间，单位MB
		}
		
		return availableSDCardSpace;
	}
	
	/**
	 * 获取机器内部可用空间
	 * @return availableSDCardSpace 可用空间(MB)。-1L:没有SD卡
	 */
	public static long getInternalStorageSpace(){
		long availableInternalSpace = -1L;

		StatFs sf = new StatFs(Environment.getDataDirectory().getPath());   
		long blockSize = sf.getBlockSize();//块大小,单位byte
		long availCount = sf.getAvailableBlocks();//可用块数量
//			long blockCount = sf.getBlockCount();//块总数量
//			Log.d("", "block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");   
//			Log.d("", "可用的block数目：:"+ availCount+",剩余空间:"+ availCount*blockSize/1024+"KB");   
		
		availableInternalSpace = availCount*blockSize/1024/1024;//可用SD卡空间，单位MB
		
		return availableInternalSpace;
	}
	
	/**
	 * 获取SD卡总空间
	 * @return availableSDCardSpace 总空间(MB)。-1L:没有SD卡
	 */
	public static long getExternalStorageTotalSpace(){
		long availableSDCardSpace = -1L;
		//存在SD卡
		if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());   
			long blockSize = sf.getBlockSize();//块大小,单位byte
//			long availCount = sf.getAvailableBlocks();//可用块数量
			long blockCount = sf.getBlockCount();//块总数量
//			Log.d("", "block大小:"+ blockSize+",block数目:"+ blockCount+",总大小:"+blockSize*blockCount/1024+"KB");   
//			Log.d("", "可用的block数目：:"+ availCount+",剩余空间:"+ availCount*blockSize/1024+"KB");   
			
			availableSDCardSpace = blockCount*blockSize/1024/1024;//总SD卡空间，单位MB
		}
		
		return availableSDCardSpace;
	}
	
	/**
	 * 下载次数文字
	 * @param downloadTime
	 * @return
	 */
	public static String getDownloadTimesText(String downloadTime){
		int dlt = 1000;
		try {
			dlt = Integer.parseInt(downloadTime);
		} catch (NumberFormatException e) {
		}
		
		int t = 1;
		for(;dlt>=10;t=t*10){
			dlt = dlt/10;
		}
		
		int ldlt = dlt/5;
		
		switch(ldlt){
		case 0:
			return (1*t)+"-"+(5*t);
		case 1:
			return (5*t)+"-"+(10*t);
		default:
			return (1*t)+"-"+(5*t);
		}
	}
	
	/**
	 * 获取mac 地址
	 * @return
	 */
    public static String getLocalMacAddress(Context ctx) {  
        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);  
        WifiInfo info = wifi.getConnectionInfo();  
        return info.getMacAddress();  
    }  


	/**
	 * 获得设备型号
	 * 
	 * @return
	 */
	public static String getDeviceModel() {
		return Build.MODEL;
	}

	/**
	 * 获得国际移动设备身份码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 获得国际移动用户识别码
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}

	/**
	 * 获得设备屏幕矩形区域范围
	 * 
	 * @param context
	 * @return
	 */
	public static Rect getScreenRect(Activity activity) {
		// 取出平屏幕的宽和高
		DisplayMetrics metric = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
		return new Rect(0, 0, metric.widthPixels, metric.heightPixels);
	}

	/**
	 * 获得设备屏幕密度
	 */
	public static float getScreenDensity(Context context) {
		DisplayMetrics metrics = context.getApplicationContext().getResources()
				.getDisplayMetrics();
		return metrics.density;
	}

	/**
	 * 获得系统版本
	 */
	public static String getSDKVersion() {
		return android.os.Build.VERSION.SDK;
	}

	public static int getSDKVersionInt() {
		return android.os.Build.VERSION.SDK_INT;
	}
	
	public static String getSystemVersion(){
		return 	android.os.Build.VERSION.RELEASE;
	}

	//Get the Location by GPS or WIFI  
    public static Location getLocation(Context context) {  
        try {
			LocationManager locMan = (LocationManager) context  
			        .getSystemService(Context.LOCATION_SERVICE);  
			Location location = locMan  
			        .getLastKnownLocation(LocationManager.GPS_PROVIDER);  
			if (location == null) {  
			    location = locMan  
			            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);  
			}  
			return location;
		} catch (Exception e) {
		}  
        return null;
    } 
}

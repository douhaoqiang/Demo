package com.dhq.selectpic.pickimg.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;


/**
 * 应用程序的环境
 * 
 * @author 792793182@qq.com 2015-06-11
 * 
 */
public final class Environment {
	
	private static final String TAG = Environment.class.getSimpleName();
   
	private Context applicationContext;
	
	private int screenWidth;
	private int screenHeight;
	
	private static class InstanceHolder {
		private static Environment instance = new Environment();
	}

	private Environment() { }
	
    public static Environment getInstance() {
        return InstanceHolder.instance;
    }
    
    /**
     * 注意：此方法只能在Application的onCreate方法中调用一次
     */
    public void init(Context applicationContext) {
    	if (this.applicationContext == null) {
		}
	   if(applicationContext==null){
		  this.applicationContext = applicationContext.getApplicationContext();
	   }else{
		  this.applicationContext = applicationContext;
	   }
    }

	
    /**
     * 获取屏幕宽度
     * @return        屏幕宽度（单位：px）
     */
    public int getScreenWidth() {
    	if (0 == screenWidth) {
    		DisplayMetrics metric = new DisplayMetrics();
        	WindowManager windowManager = (WindowManager) applicationContext.getSystemService(Context.WINDOW_SERVICE);
        	windowManager.getDefaultDisplay().getMetrics(metric);
        	screenWidth = metric.widthPixels;
		}
    	return screenWidth;
	}
    /**
	 * 判断外部存储器是否可用
	 */
    public boolean isExternalStorageAvailable() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }
    
    /**
	 * 获取外部存储器的路径
	 */
    public String getExternalStorageDirectory() {
    	return android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
	}

    /**
     * 获取SD卡的路径
     */
    public String getSDPath() {
		if (isExternalStorageAvailable()) {
			return getExternalStorageDirectory() + "/" + applicationContext.getPackageName();
		}
		return "";
	}
    
    /**
     * 获取ROM上的私有目录
     */
    public String getInternalDir() {
		return applicationContext.getFilesDir().getAbsolutePath();
	}
    
    /**
     * 获取我们的私有的目录，优先在SD卡上
     */
    public String getMyDir() {
		String dir = getSDPath();
		if (TextUtils.isEmpty(dir)) {
			dir = getInternalDir();
		}
		
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		
		return dir;
	}
    

}

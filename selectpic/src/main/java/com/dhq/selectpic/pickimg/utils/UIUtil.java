package com.dhq.selectpic.pickimg.utils;

import android.content.Context;

/**
 * 与UI相关的帮助类
 * 
 * @author 792793182@qq.com 2015-06-12
 *
 */
public final class UIUtil {

	private static final String TAG = UIUtil.class.getSimpleName();
	
	private UIUtil() { }
	
	/**  
	* 根据手机的分辨率从 dip 的单位 转成为 px(像素)
	*/  
	public static int dip2px(Context context, double dpValue) {   
		final float scale = context.getResources().getDisplayMetrics().density;   
		return (int) (dpValue * scale + 0.5);   
	}   

}

package com.dhq.selectpic.pickimg.ui.dialog;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.LocalServerSocket;
import android.net.LocalSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;


import com.dhq.selectpic.R;
import com.dhq.selectpic.pickimg.utils.BitmapUtil;
import com.dhq.selectpic.pickimg.utils.Environment;
import com.dhq.selectpic.pickimg.utils.ThreadPoolManager;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 选择图片来源的弹出框
 * 
 * @author 792793182@qq.com 2015-06-25
 *
 */
public final class ImagePickerDialog {

	private static final String TAG = ImagePickerDialog.class.getSimpleName();
	
	private FragmentActivity fragmentActivity;

	
	private Fragment fragment;
	
	/** 保存到目的路径 */
	private File desFile;
	
	/** 是否需要裁剪 */
	private boolean needCrop;

	private int cropWidth = 200;

	private int cropHeight = 200;

	/** 是否支持多选 */
	private boolean multi;
	
	/** 最多选择多少张，仅在multi为true时有效 */
	private int maxCount;
	
	/** 最大宽度（单位：px） */
	private int maxWidth = 200;
	
	/** 文件大小（单位：KB） */
	private long maxKB = 20;
	
	private Callback callback;
	
	/**
	 * {@link ImagePickerFragment#TYPE_BOTH}、{@link ImagePickerFragment#TYPE_CAMERA_ONLY}、{@link ImagePickerFragment#TYPE_PICK_ONLY}
	 */
	private String type = ImagePickerFragment.TYPE_BOTH;
	
	private AtomicBoolean isShowing = new AtomicBoolean(false);
	
	/**
	 * 不传入文件路径，文件路径自有决定，通过回调返回保存路径
	 * @param fragmentActivity  宿主
	 * @param callback          结果回调
	 */
	public ImagePickerDialog(FragmentActivity fragmentActivity, Callback callback) {
		this(fragmentActivity,null, false, callback);
	}

	/**
	 * 传入文件路径
	 * @param fragmentActivity  宿主
	 * @param desFile           保存文件到此路径
	 * @param callback          结果回调
	 */
	public ImagePickerDialog(FragmentActivity fragmentActivity, File desFile, Callback callback) {
		this(fragmentActivity, desFile, false, callback);
	}
	
	/**
	 * 不传入文件路径，文件路径自有决定，通过回调返回保存路径
	 * @param fragmentActivity  宿主
	 * @param needCrop          是否需要裁剪
	 * @param callback          结果回调
	 */
	public ImagePickerDialog(FragmentActivity fragmentActivity,  boolean needCrop, Callback callback) {
		this(fragmentActivity,  null, needCrop, callback);
	}
	
	/**
	 * 传入文件路径，可裁剪
	 * @param fragmentActivity  宿主
	 * @param desFile           保存文件到此路径
	 * @param needCrop          是否需要裁剪
	 * @param callback          结果回调
	 */
	public ImagePickerDialog(FragmentActivity fragmentActivity, File desFile, boolean needCrop, Callback callback) {
		this.fragmentActivity = fragmentActivity;
		this.needCrop = needCrop;
		this.desFile = desFile;
		this.callback = callback;
		Environment.getInstance().init(fragmentActivity.getApplicationContext());
	}
	
	/**
	 * 需要裁剪，裁剪大小为200x200px
	 */
	public ImagePickerDialog needCrop() {
		needCrop = true;
		return this;
	}

	/**
	 * 需要裁剪，裁剪大小为200x200px
	 */
	public ImagePickerDialog needCrop(int width, int height) {
		needCrop = true;
		cropWidth = width;
		cropHeight = height;
		return this;
	}

	/**
	 * 如果选择图片的话，是多选模式
	 */
	public final ImagePickerDialog multi(int maxCount) {
		multi = true;
		this.maxCount = maxCount;
		return this;
	}
	
	/**
	 * 设置最大宽度，高度等比例缩放
	 * @param maxWidth 单位：px
	 */
	public final ImagePickerDialog maxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		return this;
	}
	
	/**
	 * 设置文件最大大小
	 * @param maxKB 单位：KB
	 */
	public final ImagePickerDialog maxKB(int maxKB) {
		this.maxKB = maxKB;
		return this;
	}
	
	/**
	 * 只打开照相机
	 */
	public final ImagePickerDialog cameraOnly() {
		type = ImagePickerFragment.TYPE_CAMERA_ONLY;
		return this;
	}
	
	/**
	 * 只选择图片
	 */
	public final ImagePickerDialog pickOnly() {
		type = ImagePickerFragment.TYPE_PICK_ONLY;
		return this;
	}
	
	/**
	 * 显示此Dialog
	 * @param anchorView  参照系，是一个View
	 * @param xOff        相对于anchorView在X方向上的偏移量
	 * @param yOff        相对于anchorView在Y方向上的偏移量
	 */
	public void show(View anchorView, int xOff, int yOff) {
		show(anchorView, xOff, yOff, 0);
	}
	
	/**
	 * 显示此Dialog
	 * @param anchorView  参照系，是一个View
	 * @param duration    显示的时间（单位：ms）
	 */
	public void show(View anchorView, int duration) {
		show(anchorView, 0, 0, duration);
    }
    
	/**
	 * 显示此Dialog
	 * @param anchorView  参照系，是一个View
	 * @param xOff        相对于anchorView在X方向上的偏移量
	 * @param yOff        相对于anchorView在Y方向上的偏移量
	 * @param duration    显示的时间（单位：ms）
	 */
    public void show(View anchorView, int xOff, int yOff, int duration) {
    	int[] locationOfViewOnScreen = new int[2];
		// 获取此view在屏幕上的位置
		anchorView.getLocationOnScreen(locationOfViewOnScreen);
		
		
		//以屏幕左上角为参照点
		show(Gravity.LEFT | Gravity.TOP, locationOfViewOnScreen[0] + xOff, -locationOfViewOnScreen[1] + yOff, duration);
    }
    
	/**
	 * 显示此Dialog
	 * @param gravity     参照系，相对于屏幕的位置，参看{@link Gravity}
	 * @param xOff        相对于gravity在X方向上的偏移量
	 * @param yOff        相对于gravity在Y方向上的偏移量
	 */
	public void show(int gravity, int xOff, int yOff) {
		show(gravity, xOff, yOff, 0);
	}
	
	/**
	 * 显示此Dialog
	 * @param gravity     参照系，相对于屏幕的位置，参看{@link Gravity}
	 * @param xOff        相对于gravity在X方向上的偏移量
	 * @param yOff        相对于gravity在Y方向上的偏移量
	 * @param duration    显示的时间（单位：ms）
	 */
	public void show(int gravity, int xOff, int yOff, long duration) {
		if (isShowing.get()) {
			return;
		}
		isShowing.set(true);
		
		//启动Socket进行监听
		ThreadPoolManager.EXECUTOR.execute(new Server(callback));
		
		Bundle args = new Bundle();
		args.putSerializable(ImagePickerFragment.KEY_DES_FILE, desFile);
		args.putBoolean(ImagePickerFragment.KEY_NEED_CROP, needCrop);
		args.putInt(ImagePickerFragment.KEY_CROP_WIDTH, cropWidth);
		args.putInt(ImagePickerFragment.KEY_CROP_HEIGHT, cropHeight);
		args.putBoolean(ImagePickerFragment.KEY_MULTI, multi);
		args.putInt(ImagePickerFragment.KEY_MAX_COUNT, maxCount);
		args.putInt(ImagePickerFragment.KEY_GRAVITY, gravity);
		args.putInt(ImagePickerFragment.KEY_X_OFF, xOff);
		args.putInt(ImagePickerFragment.KEY_Y_OFF, yOff);
		args.putLong(ImagePickerFragment.KEY_DURATION, duration);
		args.putString(ImagePickerFragment.KEY_TYPE, type);
		
		fragment = new ImagePickerFragment();
		fragment.setArguments(args);
		
    	FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
		View rootView = ((ViewGroup) fragmentActivity.findViewById(android.R.id.content)).getChildAt(0);
//		fragmentTransaction.add()
    	fragmentTransaction.add(rootView.getId(), fragment);
    	fragmentTransaction.commit();
	}
	
	protected void show(long duration) {
    	show(Gravity.CENTER, 0, 0, duration);
    }
	
	public void show() {
		show(0);
	}
    
	public void dismiss(boolean hasAnimation) {
		if (isShowing.get()) {
			FragmentTransaction fragmentTransaction = fragmentActivity.getSupportFragmentManager().beginTransaction();
			fragmentTransaction.remove(fragment);
			fragmentTransaction.commitAllowingStateLoss();
		}
	}
	
	public void dismiss() {
    	dismiss(true);
	}
	
	public boolean isShowing() {
		return isShowing.get();
	}
	
	/**
	 * 
	 * 结果回调，所有回调方法都在主线程中执行
	 *
	 */
	public interface Callback {
		
		/** 得到结果，在主线程中执行 */
		void onResult(File file);
		
		/** 多选的结果， 得到结果，在主线程中执行 */
		void onMultiResult(File[] files);
		
		/** 取消，在主线程中执行 */
		void onCancel();
	}
	
	/**
	 * 用UNIX Socket比较快速，用于接收消息
	 */
	private final class Server implements Runnable {
		
		private Callback callback;
		
		public Server(Callback callback) {
			this.callback = callback;
		}
		
		@Override
		public void run() {
			String result = "";
			
			LocalServerSocket localServerSocket = null;
			try {
				localServerSocket = new LocalServerSocket(ImagePickerFragment.SOCKET_NAME);
				LocalSocket localSocket = localServerSocket.accept();
				InputStream is = localSocket.getInputStream();
				byte[] buffer = new byte[1024];
				int byteCount = is.read(buffer);
				
				if (byteCount > 0) {
					result = new String(buffer, 0, byteCount);
				}
				
				Log.d(TAG, "result = " + result);
			} catch (IOException e) {
				Log.e(TAG, "run()", e);
			} finally {
				if (localServerSocket != null) {
					try {
						localServerSocket.close();
					} catch (IOException e) {
						Log.e(TAG, "run()", e);
					}
				}
			}
			
			//取消操作
			if ("onCancel".equals(result)) {
				new Handler(Looper.getMainLooper()).post(new Runnable() {
					
					@Override
					public void run() {
						dismiss(true);
						callback.onCancel();
					}
				});
			} else {
				//多选的操作
				if (multi) {
					Log.d(TAG, "multi");
					try {
						JSONArray jsonArray = new JSONArray(result);
						int length = jsonArray.length();
						final File[] files = new File[length];
						for (int i = 0; i < length; i++) {
							files[i] = new File(jsonArray.getString(i));
							handle(files[i]);
						}
						
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							
							@Override
							public void run() {
								dismiss(true);
								callback.onMultiResult(files);
							}
						});
					} catch (JSONException e) {
						Log.e(TAG, "", e);
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							
							@Override
							public void run() {
								dismiss(true);
								callback.onCancel();
							}
						});
					}
				} else {
					final File file = new File(result);
					//进行采样率变小和压缩处理
					handle(file);

					new Handler(Looper.getMainLooper()).post(new Runnable() {
						
						@Override
						public void run() {
							dismiss(true);
							callback.onResult(file);
						}
					});
				}
			}
		}

		private void handle(File file) {
			Log.d(TAG, "handle()");

			if (file.exists() && file.isFile()) {
				String filePath = file.getAbsolutePath();
				Point point = BitmapUtil.getWidthAndHeight(filePath);
				Log.d(TAG, "src.width = " + point.x + ",src.height = " + point.y);
				int max = Math.max(point.x, point.y);
				//如果图片比需要的大，就进行采样率的降低处理和缩放
				if (max > maxWidth && maxWidth > 0) {
					int radio = (int) Math.floor((float) max / maxWidth);
					if (radio % 2 != 0) {
						//变成偶数
						radio += 1;
					}

					BitmapFactory.Options options = new BitmapFactory.Options();
					// 降低采用率
					options.inSampleSize = radio;

					Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);
					Log.d(TAG, "width = " + bitmap.getWidth() + ",height = " + bitmap.getHeight());

					//重新保存文件
					BitmapUtil.saveBitmapToFile(bitmap, filePath, CompressFormat.JPEG);
				}

				if (maxKB > 0) {
					long length = 0L;
					int quality = 100;
					//如果通过降低采样率，还是比预期的大，进行压缩
					while ((length = file.length()) > maxKB * 1024) {
						Log.d(TAG, "in while length = " + length / 1024 + "KB");
						Bitmap bitmap = BitmapFactory.decodeFile(filePath);
						BitmapUtil.saveBitmapToFile(bitmap, filePath, quality -= 10, CompressFormat.JPEG);
					}
					Log.d(TAG, "length = " + length / 1024 + "KB");
				}
			}
		}
	}
}

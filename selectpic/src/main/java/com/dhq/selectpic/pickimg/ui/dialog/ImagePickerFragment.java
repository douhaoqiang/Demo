package com.dhq.selectpic.pickimg.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.dhq.selectpic.pickimg.PickimgSelectPicActivity;
import com.dhq.selectpic.pickimg.ui.adapter.ItemAdapter;
import com.dhq.selectpic.pickimg.utils.BitmapUtil;
import com.dhq.selectpic.pickimg.utils.Environment;
import com.dhq.selectpic.pickimg.utils.IOUtil;
import com.dhq.selectpic.pickimg.utils.UIUtil;

import org.json.JSONArray;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 这是一个透明层，用于接收从拍照和选择相片返回的数据
 * 
 * @author 792793182@qq.com 2015-06-25
 *
 */
public final class ImagePickerFragment extends Fragment {
	
	static final String SOCKET_NAME = "image_picker";
	
	static final String KEY_DES_FILE = "desFile";
	
	static final String KEY_NEED_CROP = "needCrop";
	static final String KEY_CROP_WIDTH = "cropWidth";
	static final String KEY_CROP_HEIGHT = "cropHeight";
	
	static final String KEY_MULTI = "multi";
	
	static final String KEY_MAX_COUNT = "maxCount";
	
	static final String KEY_GRAVITY = "gravity";
	
	static final String KEY_X_OFF = "xOff";
	
	static final String KEY_Y_OFF = "yOff";
	
	static final String KEY_DURATION = "duration";
	
	static final String KEY_TYPE = "type";
	
	static final String TYPE_BOTH = "type_both";
	
	static final String TYPE_CAMERA_ONLY = "type_camera_only";
	
	static final String TYPE_PICK_ONLY = "type_pick_only";
	
	private static final String TAG = ImagePickerFragment.class.getSimpleName();
	
	private static final int REQUEST_CODE_CAMERA = ImageChooseDialog.REQUESTCODE_CAMERA;
	private static final int REQUEST_CODE_PICK_PIC = ImageChooseDialog.REQUESTCODE_PICK_PIC;
	private static final int REQUEST_CODE_CROP_PIC = 202;
	
	private Uri imageUri;
	
	/** 保存到目的路径 */
	private File desFile;
	
	/** 是否需要裁剪 */
	private boolean needCrop;

	private int cropWidth = 200;

	private int cropHeight = 200;
	
	/** 是否支持多选 */
	private boolean multi;
	
	/** 最多选择多少张 */
	private int maxCount;
	
	private int gravity = Gravity.BOTTOM;
	
	private int xOff;
	
	private int yOff;
	
	private long duration;
	
	/**
	 * {@link #TYPE_BOTH}、{@link #TYPE_CAMERA_ONLY}、{@link #TYPE_PICK_ONLY}
	 */
	private String type;
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		
		outState.putSerializable(KEY_DES_FILE, desFile);
		outState.putBoolean(KEY_NEED_CROP, needCrop);
		outState.putInt(KEY_CROP_WIDTH, cropWidth);
		outState.putInt(KEY_CROP_HEIGHT, cropHeight);
		outState.putBoolean(KEY_MULTI, multi);
		outState.putInt(KEY_MAX_COUNT, maxCount);
		outState.putInt(KEY_GRAVITY, gravity);
		outState.putInt(KEY_X_OFF, xOff);
		outState.putInt(KEY_Y_OFF, yOff);
		outState.putLong(KEY_DURATION, duration);
		outState.putString(KEY_TYPE, type);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			desFile = (File) bundle.getSerializable(KEY_DES_FILE);
			needCrop = bundle.getBoolean(KEY_NEED_CROP);
			cropWidth = bundle.getInt(KEY_CROP_WIDTH, 200);
			cropHeight = bundle.getInt(KEY_CROP_HEIGHT, 200);
			multi = bundle.getBoolean(KEY_MULTI);
			maxCount = bundle.getInt(KEY_MAX_COUNT);
			gravity = bundle.getInt(KEY_GRAVITY);
			xOff = bundle.getInt(KEY_X_OFF);
			yOff = bundle.getInt(KEY_Y_OFF);
			duration = bundle.getLong(KEY_DURATION);
			type = bundle.getString(KEY_TYPE);
		} else {
			desFile = (File) savedInstanceState.getSerializable(KEY_DES_FILE);
			needCrop = savedInstanceState.getBoolean(KEY_NEED_CROP);
			cropWidth = savedInstanceState.getInt(KEY_CROP_WIDTH, 200);
			cropHeight = savedInstanceState.getInt(KEY_CROP_HEIGHT, 200);
			multi = savedInstanceState.getBoolean(KEY_MULTI);
			maxCount = savedInstanceState.getInt(KEY_MAX_COUNT);
			gravity = savedInstanceState.getInt(KEY_GRAVITY);
			xOff = savedInstanceState.getInt(KEY_X_OFF);
			yOff = savedInstanceState.getInt(KEY_Y_OFF);
			duration = savedInstanceState.getLong(KEY_DURATION);
			type = savedInstanceState.getString(KEY_TYPE);
		}
		
		if (maxCount == 0) {
			maxCount = 10;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		Log.d(TAG, "onCreateView()");
		
		LinearLayout layout = new LinearLayout(getActivity());
		layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		layout.setBackgroundColor(Color.TRANSPARENT);
		return layout;
	}
	
	@Override
	public void onActivityCreated(Bundle bundle) {
		super.onActivityCreated(bundle);
		
		Log.d(TAG, "onActivityCreated()");
		
		if (TYPE_CAMERA_ONLY.equals(type)) {
			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(ImageChooseDialog.TEMP_FILE));
			
			try {
				startActivityForResult(openCameraIntent, REQUEST_CODE_CAMERA);
			} catch (Exception e) {
				Log.d(TAG, "onClick()", e);
				sendMessage("onCancel");
			}
		} else if (TYPE_PICK_ONLY.equals(type)) {
			//如果多选
			if (multi) {
				Intent intent = new Intent(getActivity(), PickimgSelectPicActivity.class);
				intent.putExtra("max", maxCount);
				try {
					startActivityForResult(intent, REQUEST_CODE_PICK_PIC);
				} catch (Exception e) {
					Log.e(TAG, "onClick()", e);
					sendMessage("onCancel");
				}
			} else {
				Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
				openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				try {
					startActivityForResult(openAlbumIntent, REQUEST_CODE_PICK_PIC);
				} catch (Exception e) {
					Log.e(TAG, "onClick()", e);
					sendMessage("onCancel");
				}
			}
		} else {
			ImageChooseDialog dialog = new ImageChooseDialog(getActivity(), ImagePickerFragment.this, multi, maxCount);
			dialog.show(gravity, xOff, yOff, duration);
			dialog.setOnCancelListener(new Dialog.OnCancelListener() {
				
				@Override
				public void onCancel(DialogInterface dialog) {
					Log.d(TAG, "onCancel()");
					sendMessage("onCancel");
				}
			});
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		Log.d(TAG, "onActivityResult() requestCode = " + requestCode + ", resultCode = " + resultCode + ", body = " + data);
		
		Activity activity = getActivity();
		
		//有返回数据
		if (resultCode == Activity.RESULT_OK || resultCode == 10086) {
			switch (requestCode) {
			//如果是拍照返回的结果
			case REQUEST_CODE_CAMERA:
				/**
				 * 拍照的图片保存在这个地方，参看{@link ImageChooseDialog#TEMP_FILE}
				 */
				imageUri = Uri.fromFile(ImageChooseDialog.TEMP_FILE);
				Log.d(TAG, "camera success, imageUri = " + imageUri);
				
				//如果需要裁剪，就启动裁剪程序进行裁剪
				if (needCrop) {
					startCropApp(imageUri);
				} else {
					handleCameraSuccess(ImageChooseDialog.TEMP_FILE, desFile);
				}
				break;
			//如果是选择图片返回的结果
			case REQUEST_CODE_PICK_PIC:
				if (multi) {
					ArrayList<String> selectedUris = data.getStringArrayListExtra("body");
					Log.d(TAG, "multi select uris = " + selectedUris);
					
					ArrayList<File> files = handlePickSuccess(selectedUris);
					JSONArray jsonArray = new JSONArray();
					for (File file : files) {
						jsonArray.put(file.getAbsolutePath());
						Log.d(TAG, "file =" + file);
					}
					sendMessage(jsonArray.toString());
				} else {
					// 照片的原始资源地址
					imageUri = data.getData();
					Log.d(TAG, "pick pic success, imageUri = " + imageUri);
					
					//如果需要裁剪，就启动裁剪程序进行裁剪
					if (needCrop) {
						startCropApp(imageUri);
					} else {
						handlePickSuccess(imageUri, desFile);
					}
				}
				break;
			//剪切返回的数据
			case REQUEST_CODE_CROP_PIC:
				if (null == data) {
					Toast.makeText(activity, "裁剪失败", Toast.LENGTH_LONG).show();
					sendMessage("onCancel");
				} else {
					Bundle bundle = data.getExtras();
					if (null == bundle) {
						Toast.makeText(activity, "裁剪失败", Toast.LENGTH_LONG).show();
						sendMessage("onCancel");
					} else {
						Bitmap cropedBitmap = bundle.getParcelable("body");
						if (cropedBitmap == null) {
							Toast.makeText(activity, "裁剪失败", Toast.LENGTH_LONG).show();
							sendMessage("onCancel");
						} else {
							Log.d(TAG, "crop pic success");
							handleCropSuccess(cropedBitmap, desFile);
						}
					}
				}
				break;
			default:
				break;
			}
		} else {
			//错误信息处理
			switch (requestCode) {
			//如果是拍照返回的结果
			case REQUEST_CODE_CAMERA:
				Toast.makeText(activity, "您取消了拍照", Toast.LENGTH_LONG).show();
				sendMessage("onCancel");
				break;
			//如果是选择图片返回的结果
			case REQUEST_CODE_PICK_PIC:
				Toast.makeText(activity, "您取消了选择照片", Toast.LENGTH_LONG).show();
				sendMessage("onCancel");
				break;
			case REQUEST_CODE_CROP_PIC:
				Toast.makeText(activity, "您取消了裁剪", Toast.LENGTH_LONG).show();
				sendMessage("onCancel");
				break;
			default:
				break;
			}
			
			try {
				//失败了或是取消了，删除之
				if (imageUri != null) {
					getActivity().getContentResolver().delete(imageUri, null, null);
					imageUri = null;
				}
			} catch (Exception e) {
				Log.e(TAG, "onActivityResult()", e);
			}
		}
	}
	
	/**
	 * 发送消息给Server
	 * @param message 消息
	 */
	private void sendMessage(String message) {
		LocalSocket localSocket = new LocalSocket();
		OutputStream os = null;
		try {
			localSocket.connect(new LocalSocketAddress(SOCKET_NAME));
			os = localSocket.getOutputStream();
			os.write(message.getBytes());
			Log.d(TAG, "sendMessage() " + message);
		} catch (IOException e) {
			Log.e(TAG, "sendMessage()", e);
		} finally {
			try {
				localSocket.close();
			} catch (IOException e) {
				Log.e(TAG, "sendMessage()", e);
			}
		}
	}
	
	private void handleCameraSuccess(File srcFile, File desFile) {
		//如果没有给定目标地址，在SD卡的应用目录下面，以时间戳为前缀的文件名作为目标路径
		if (desFile == null) {
			desFile = new File(Environment.getInstance().getMyDir(), System.currentTimeMillis() + ".jpg");
		}
		
		//如果文件路径没有，就创建父文件夹
		File parentFile = desFile.getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		//重新移动到指定的路径
		if (srcFile.renameTo(desFile)) {
			Log.d(TAG, "saveBitmapToFile success desFile = " + srcFile);
			sendMessageJudgeMulti(desFile.getAbsolutePath());
		} else {
			Log.d(TAG, "saveBitmapToFile fail desFile = " + srcFile);
			sendMessageJudgeMulti(srcFile.getAbsolutePath());
		}
	}

	private void sendMessageJudgeMulti(String path){
		if(multi) {
			JSONArray jsonArray = new JSONArray();
			jsonArray.put(path);
			sendMessage(jsonArray.toString());
		}else
			sendMessage(path);
	}

	private ArrayList<File> handlePickSuccess(ArrayList<String> uris) {
		if (uris == null) {
			return new ArrayList<File>();
		}
		
		InputStream is = null;
		String parentDir = Environment.getInstance().getMyDir();
		ArrayList<File> files = new ArrayList<File>();
		for (String uriStr : uris) {
			try {
				Uri uri = Uri.parse(uriStr);
				File desFile = new File(parentDir, System.currentTimeMillis() + ".jpg");
				is = getActivity().getContentResolver().openInputStream(uri);
				IOUtil.writeFile(is, desFile);
				
				files.add(desFile);
			} catch (Exception e) {
				Log.e(TAG, "handlePickSuccess()", e);
			} finally {
				if (is != null) {
					try {
						is.close();
					} catch (IOException e) {
						Log.e(TAG, "handle()", e);
					}
				}
			}
		}
		
		return files;
	}
	
	private void handlePickSuccess(Uri srcUri, File desFile) {
		//如果没有给定目标地址，在SD卡的应用目录下面，以时间戳为前缀的文件名作为目标路径
		if (desFile == null) {
			desFile = new File(Environment.getInstance().getMyDir(), System.currentTimeMillis() + ".jpg");
		}
		
		//如果文件路径没有，就创建父文件夹
		File parentFile = desFile.getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		InputStream is = null;
		try {
			is = getActivity().getContentResolver().openInputStream(srcUri);
			IOUtil.writeFile(is, desFile);
			
			Log.d(TAG, "saveBitmapToFile success desFile = " + desFile);
			sendMessage(desFile.getAbsolutePath());
		} catch (IOException e) {
			Log.e(TAG, "handle()", e);

			Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_LONG).show();
			
			sendMessage("onCancel");
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					Log.e(TAG, "handle()", e);
				}
			}
		}
	}
	
	private void handleCropSuccess(Bitmap cropedBitmap, File desFile) {
		//如果没有给定目标地址，在SD卡的应用目录下面，以时间戳为前缀的文件名作为目标路径
		if (desFile == null) {
			desFile = new File(Environment.getInstance().getMyDir(), System.currentTimeMillis() + ".jpg");
		}
		
		//如果文件路径没有，就创建父文件夹
		File parentFile = desFile.getParentFile();
		if (parentFile != null && !parentFile.exists()) {
			parentFile.mkdirs();
		}
		
		if (BitmapUtil.saveBitmapToFile(cropedBitmap, desFile.getAbsolutePath(), 100, CompressFormat.JPEG)) {
			Log.d(TAG, "saveBitmapToFile success desFile = " + desFile);
			sendMessageJudgeMulti(desFile.getAbsolutePath());
		} else {
			Log.d(TAG, "saveBitmapToFile desFile = " + imageUri);
			sendMessageJudgeMulti(imageUri.toString());
		}
	}
	
	/**
	 * 打开支持裁剪的应用
	 */
	private boolean startCropApp(Uri imageUri) {
		Log.d(TAG, "startCropApp()");
		
		final Context context = getActivity();
		PackageManager pm = context.getPackageManager();
		
		Intent intent = new Intent("com.android.camera.action.CROP").setType("image/*");
		List<ResolveInfo> resolveInfos = pm.queryIntentActivities(intent, 0);
		if (resolveInfos.isEmpty()) {
			Toast.makeText(context, "没有找到支持裁剪的应用", Toast.LENGTH_LONG).show();
			return false;
		} else {
			intent.setData(imageUri);
			intent.putExtra("outputX", cropWidth);
			intent.putExtra("outputY", cropHeight);
			intent.putExtra("aspectX", cropWidth);
			intent.putExtra("aspectY", cropHeight);
			intent.putExtra("scale", true);
			intent.putExtra("return-body", true);
			
			//如果只有一个应用支持裁剪，就直接打开这个应用
			if (resolveInfos.size() == 1) {
				try {
					Intent intent2 = new Intent(intent);
					ResolveInfo resolveInfo = resolveInfos.get(0);
					intent2.setComponent(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name));
					startActivityForResult(intent2, REQUEST_CODE_CROP_PIC);
					return true;
				} catch (Exception e) {
					Log.e(TAG, "startCropApp()", e);
					Toast.makeText(context, "没有找到支持裁剪的应用", Toast.LENGTH_LONG).show();
					return false;
				}
			} else {
				ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
				
				//如果有多个应用支持裁剪，显示一个弹出框，让用户选择使用哪个
				//之所以显示一个自定义的弹出框，是因为系统的弹框很难看
				for (ResolveInfo resolveInfo : resolveInfos) {
					AppInfo appInfo = new AppInfo();

					appInfo.title = pm.getApplicationLabel(resolveInfo.activityInfo.applicationInfo);
					appInfo.icon = pm.getApplicationIcon(resolveInfo.activityInfo.applicationInfo);
					appInfo.appIntent = new Intent(intent);
					appInfo.appIntent.setComponent(new ComponentName(
							resolveInfo.activityInfo.packageName,
							resolveInfo.activityInfo.name));

					appInfos.add(appInfo);
				}
				
				AppInfoListDialog dialog = new AppInfoListDialog(getActivity(), appInfos, new AppInfoListDialog.OnResult() {
					
					@Override
					public void onResult(Dialog dialog, AppInfo appInfo) {
						try {
							startActivityForResult(appInfo.appIntent, REQUEST_CODE_CROP_PIC);
							dialog.dismiss();
						} catch (Exception e) {
							Toast.makeText(context, "无法启动您选择的应用", Toast.LENGTH_LONG).show();
						}
					}
				});
				dialog.setCancelable(false);
				dialog.show(Gravity.CENTER, 0, 0, 0);
			}
		}
		
		return true;
	}
	
	private static class AppInfo {
		public CharSequence title;
		public Drawable icon;
		public Intent appIntent;
	}
	
	public static final class AppInfoListDialog extends CustomDialog {

		public AppInfoListDialog(Activity activity, List<AppInfo> items, final OnResult onResult) {
			super(activity);
			
			if (items == null) {
				return;
			}
			
			LinearLayout layout = new LinearLayout(activity);
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setGravity(Gravity.CENTER);
			
			int a = UIUtil.dip2px(activity, 10);
			LinearLayout.LayoutParams lpmw = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			
			TextView titleTv = new TextView(activity);
			titleTv.setText("请选择裁剪应用，请重新选择");
			titleTv.setTextSize(16);
			titleTv.setTextColor(Color.parseColor("#888888"));
			titleTv.setBackgroundColor(Color.parseColor("#dbdbdb"));
			titleTv.setGravity(Gravity.CENTER);
			titleTv.setPadding(0, a, 0, a);
			layout.addView(titleTv, lpmw);
			
			ListView listView = new ListView(activity);
			listView.setBackgroundColor(Color.WHITE);
			listView.setCacheColorHint(Color.TRANSPARENT);
			//注意：下面两行代码的顺序不能变
			listView.setDivider(new ColorDrawable(Color.parseColor("#d1d1d1")));
			listView.setDividerHeight(1);
			
			final AppInfoAdapter adapter = new AppInfoAdapter(items);
			listView.setAdapter(adapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//回调
					if (onResult != null) {
						onResult.onResult(AppInfoListDialog.this, adapter.getItem(position));
					}
				}
			});
			layout.addView(listView, lpmw);
			
			setContentView(layout);
			
			setWindowWidth(Environment.getInstance().getScreenWidth() * 2 / 3);
		}
		
		private static class AppInfoAdapter extends ItemAdapter<AppInfo> {
			
			protected AppInfoAdapter(List<AppInfo> items) {
				super(items);
			}

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				AppInfo appInfo = getItem(position);
				
				Context context = parent.getContext();
				int padding = UIUtil.dip2px(context, 15);
				
				LinearLayout layout = new LinearLayout(context);
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setGravity(Gravity.CENTER_VERTICAL);
				layout.setPadding(padding, padding, padding, padding);
				
				ImageView imageView = new ImageView(context);
				imageView.setScaleType(ScaleType.CENTER_INSIDE);
				imageView.setBackgroundColor(Color.TRANSPARENT);
				imageView.setImageDrawable(appInfo.icon);
				layout.addView(imageView);
				
				TextView textView = new TextView(parent.getContext());
				textView.setText(appInfo.title);
				textView.setTextSize(16);
				textView.setTextColor(Color.BLACK);
				textView.setPadding(padding, 0, 0, 0);
				layout.addView(textView);
				
				return layout;
			}
		}
		
		public interface OnResult {
			
			void onResult(Dialog dialog, AppInfo appInfo);
		}
	}
}
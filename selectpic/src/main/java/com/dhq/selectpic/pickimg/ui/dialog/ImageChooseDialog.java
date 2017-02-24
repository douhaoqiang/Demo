package com.dhq.selectpic.pickimg.ui.dialog;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;


import com.dhq.selectpic.R;
import com.dhq.selectpic.pickimg.PickimgSelectPicActivity;
import com.dhq.selectpic.pickimg.utils.Environment;
import com.dhq.selectpic.pickimg.utils.IOUtil;
import com.dhq.selectpic.pickimg.utils.UIUtil;

import java.io.File;
import java.io.InputStream;

/**
 * 选择图片来源的弹出框
 *
 */
public final class ImageChooseDialog extends CustomDialog {
	
	private static final String TAG = ImageChooseDialog.class.getSimpleName();
	
	/** 打开照相机 */
	public static final int REQUESTCODE_CAMERA = 200;
	
	/** 选择照片 */
	public static final int REQUESTCODE_PICK_PIC = 201;
	
	// 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
	public static final File TEMP_FILE = new File(android.os.Environment.getExternalStorageDirectory(), "image.jpg");
	
	/**
	 * 
	 * @param activity 所在的Activity
	 * @param fragment 如果在Fragment中，必须出入此值
	 */
	public ImageChooseDialog(final Activity activity, final Fragment fragment, final boolean multi, final int maxCount) {
		super(activity);
		setContentView(R.layout.pickimg_type_lay);
		setWindowWidth(Environment.getInstance().getScreenWidth());

		TextView cameraTv= (TextView) findViewById(R.id.pickimg_type_camera_tv);
		TextView albumTv= (TextView) findViewById(R.id.pickimg_type_album_tv);
		TextView cancleTv= (TextView) findViewById(R.id.pickimg_type_cancle_tv);
		cameraTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(TEMP_FILE));

				try {
					if (fragment == null) {
						activity.startActivityForResult(openCameraIntent, REQUESTCODE_CAMERA);
					} else {
						fragment.startActivityForResult(openCameraIntent, REQUESTCODE_CAMERA);
					}

					dismiss();
				} catch (Exception e) {
					Log.d(TAG, "onClick()", e);
					cancel();
				}
			}
		});

		albumTv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (multi) {
					Intent intent = new Intent(activity, PickimgSelectPicActivity.class);
					intent.putExtra("max", maxCount);
					try {
						if (fragment == null) {
							activity.startActivityForResult(intent, REQUESTCODE_PICK_PIC);
						} else {
							fragment.startActivityForResult(intent, REQUESTCODE_PICK_PIC);
						}

						dismiss();
					} catch (Exception e) {
						Log.e(TAG, "onClick()", e);
						cancel();
					}
				} else {
					Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
					openAlbumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					try {
						if (fragment == null) {
							activity.startActivityForResult(openAlbumIntent, REQUESTCODE_PICK_PIC);
						} else {
							fragment.startActivityForResult(openAlbumIntent, REQUESTCODE_PICK_PIC);
						}

						dismiss();
					} catch (Exception e) {
						Log.e(TAG, "onClick()", e);
						cancel();
					}
				}
			}
		});

		cancleTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cancel();
			}
		});

	}

//	public boolean onActivityResult(Activity activity, File newFile, int requestCode, int resultCode, Intent data) {
//		if (resultCode == Activity.RESULT_OK) {
//			if (requestCode == REQUESTCODE_CAMERA) {
//				//移动图片的位置
//				if (TEMP_FILE.renameTo(newFile)) {
//					return true;
//				} else {
//					Toast.makeText(activity, "打开照相机失败", Toast.LENGTH_LONG).show();
//					return false;
//				}
//			} else if (requestCode == REQUESTCODE_PICK_PIC) {
//				try {
//					InputStream is = activity.getContentResolver().openInputStream(data.getData());
//					//写入文件
//					IOUtil.writeFile(is, newFile);
//					return true;
//				} catch (Exception e) {
//					Toast.makeText(activity, "选择图片失败", Toast.LENGTH_LONG).show();
//					Log.e(TAG, "onActivityResult()", e);
//					return false;
//				}
//			}
//		}
//		return false;
//	}
}

package com.dhq.selectpic.pickimg;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dhq.selectpic.R;
import com.dhq.selectpic.pickimg.ui.dialog.ImagePickerDialog;

import java.io.File;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class PickimgTestActivity extends FragmentActivity {
	private static final String TAG = ImagePickerDialog.class.getSimpleName();

	private ImageView mImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pickimg_test_activity);

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(PickimgTestActivity.this));

		mImage = (ImageView)findViewById(R.id.main_image);
		Button button = (Button)findViewById(R.id.main_select);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openImagePickerDialog();
			}
		});
	}

	private void openImagePickerDialog() {
		ImagePickerDialog pickDialog = new ImagePickerDialog(PickimgTestActivity.this,
                new ImagePickerDialog.Callback() {
                    @Override
                    public void onResult(File file) {
                        if (file.exists()) {
							Log.i(TAG, "图片" + " " + file.getAbsolutePath());
                            String path = file.getAbsolutePath();
							ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), mImage);
                        }
                    }

                    @Override
                    public void onMultiResult(File[] files) {
						if (files != null && files.length > 0 && files[0].exists()) {
							Log.i(TAG, "已选择： " + files.length + " 张图片");
							for(int i=0; i<files.length; i++){
								Log.i(TAG, "图片" + i + " " + files[i].getAbsolutePath());
							}
							String path = files[0].getAbsolutePath();

							ImageLoader.getInstance().displayImage(Uri.fromFile(new File(path)).toString(), mImage);
						}
                    }

                    @Override
                    public void onCancel() {

                    }

                });
		pickDialog.needCrop()
				.needCrop(400, 300)
				.multi(10)
				.maxKB(1024)
				.maxWidth(720);
//				.cameraOnly()
//				.pickOnly();
		pickDialog.show(Gravity.BOTTOM, 0, 0, 0);
	}

}

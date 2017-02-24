package com.dhq.selectpic.pickimg.ui.dialog;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


import com.dhq.selectpic.R;
import com.dhq.selectpic.pickimg.ui.ImageFloderBean;
import com.dhq.selectpic.pickimg.ui.adapter.PicAdapter;
import com.dhq.selectpic.pickimg.utils.BasePopupWindowForListView;
import com.dhq.selectpic.pickimg.utils.ViewHolder;

import java.util.List;

public class ListImageDirPopupWindow extends
		BasePopupWindowForListView<ImageFloderBean> {
	private ListView mListDir;

	public ListImageDirPopupWindow(int width, int height,
			List<ImageFloderBean> datas, View convertView) {
		super(convertView, width, height, true, datas);
	}

	@Override
	public void initViews() {
		mListDir = (ListView) findViewById(R.id.id_list_dir);
		mListDir.setAdapter(new PicAdapter<ImageFloderBean>(context, mDatas,
				R.layout.pickimg_list_dir_item) {
			@Override
			public void convert(ViewHolder helper, ImageFloderBean item) {
				helper.setText(R.id.id_dir_item_name, item.getName());
				helper.setImageByUrl(R.id.id_dir_item_image,
						item.getFirstImagePath());
				helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
			}
		});
	}

	public interface OnImageDirSelected {
		void selected(ImageFloderBean floder);
	}

	private OnImageDirSelected mImageDirSelected;

	public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
		this.mImageDirSelected = mImageDirSelected;
	}

	@Override
	public void initEvents() {
		mListDir.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				if (mImageDirSelected != null) {
					mImageDirSelected.selected(mDatas.get(position));
				}
			}
		});
	}

	@Override
	public void init() {


	}

	@Override
	protected void beforeInitWeNeedSomeParams(Object... params) {

	}

}

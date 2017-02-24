package com.dhq.selectpic.pickimg.ui.adapter;

import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 基本的现实项的适配器，这个类不能实例化
 * 
 * @author 792793182@qq.com 2015-06-11
 * 
 */
public abstract class ItemAdapter<T > extends BaseAdapter implements List<T> {
	
	//数据项集合
	private List<T> mItems;
	
	/**
	 * 构造方法
	 * @param items    要显示的项的数据列表
	 */
	protected ItemAdapter(List<T> items) {
		mItems = items;
	}

	public void setItems(List<T> items) {
		mItems = items;
		notifyDataSetChanged();
	}

	public List<T> getItems() {
		return mItems;
	}
	
	protected View getConvertView(int layoutId, ViewGroup parent) {
		return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
	}
	
/**-----------------------------------------------------------------*/

	@Override
	public int getCount() {
		return mItems == null ? 0 : mItems.size();
	}

	@Override
	public T getItem(int position) {
		return mItems == null ? null : mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mItems == null ? -1 : position;
	}
	
/**-----------------------------------------------------------------*/
	
	@Override
	public void add(int location, T object) {
		if (object == null) {
			return;
		}
		
		if (mItems == null) {
			mItems = new ArrayList<T>();
		}
		
		mItems.add(location, object);
		notifyDataSetChanged();
	}

	@Override
	public boolean add(T object) {
		if (object == null) {
			return false;
		}
		
		if (mItems == null) {
			mItems = new ArrayList<T>();
		}
		
		boolean flag = mItems.add(object);
		notifyDataSetChanged();
		return flag;
	}	

	@Override
	public boolean addAll(int location, Collection<? extends T> collection) {
		if (collection == null) {
			return false;
		}
		
		if (mItems == null) {
			mItems = new ArrayList<T>();
		}
		
		boolean flag = mItems.addAll(location, collection);
		notifyDataSetChanged();
		return flag;
	}

	@Override
	public boolean addAll(Collection<? extends T> collection) {
		if (collection == null) {
			return false;
		}
		
		if (mItems == null) {
			mItems = new ArrayList<T>();
		}
		
		boolean flag = mItems.addAll(collection);
		notifyDataSetChanged();
		return flag;
	}

	@Override
	public void clear() {
		if (mItems != null) {
			mItems.clear();
			notifyDataSetChanged();
		}
	}

	@Override
	public boolean contains(Object object) {
		return mItems == null ? false : mItems.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> collection) {
		return mItems == null ? false : mItems.containsAll(collection);
	}

	@Override
	public T get(int location) {
		return mItems == null ? null : mItems.get(location);
	}

	@Override
	public int indexOf(Object object) {
		return mItems == null ? -1 : mItems.indexOf(object);
	}

	@Override
	public Iterator<T> iterator() {
		return mItems == null ? null : mItems.iterator();
	}

	@Override
	public int lastIndexOf(Object object) {
		return mItems == null ? -1 : mItems.lastIndexOf(object);
	}

	@Override
	public ListIterator<T> listIterator() {
		return mItems == null ? null : mItems.listIterator();
	}

	@Override
	public ListIterator<T> listIterator(int location) {
		return mItems == null ? null : mItems.listIterator(location);
	}

	@Override
	public T remove(int location) {
		if (mItems == null) {
			return null;
		}
		
		T t = mItems.remove(location);
		notifyDataSetChanged();
		return t;
	}

	@Override
	public boolean remove(Object object) {
		if (mItems == null) {
			return false;
		}
		
		boolean flag = mItems.remove(object);
		notifyDataSetChanged();
		return flag;
	}

	@Override
	public boolean removeAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		
		if (mItems == null) {
			return false;
		}
		
		boolean flag = mItems.removeAll(collection);
		notifyDataSetChanged();
		return flag;
	}

	@Override
	public boolean retainAll(Collection<?> collection) {
		if (collection == null) {
			return false;
		}
		
		if (mItems == null) {
			return false;
		}
		
		boolean flag = mItems.retainAll(collection);
		notifyDataSetChanged();
		return flag;
	}

	@Override
	public T set(int location, T object) {
		if (mItems == null) {
			return null;
		}
		
		T t = mItems.set(location, object);
		notifyDataSetChanged();
		return t;
	}

	@Override
	public int size() {
		return mItems == null ? 0 : mItems.size();
	}

	@Override
	public List<T> subList(int start, int end) {
		return mItems == null ? null : mItems.subList(start, end);
	}

	@Override
	public Object[] toArray() {
		return mItems == null ? null : mItems.toArray();
	}

	@Override
	public <E> E[] toArray(E[] array) {
		return mItems == null ? null : mItems.toArray(array);
	}
	
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		//父类的方法会抛出2个异常
		try {
			super.unregisterDataSetObserver(observer);
		} catch (Exception e) {
			Log.e(getClass().getName(), "unregisterDataSetObserver()", e);
		}
	}
}

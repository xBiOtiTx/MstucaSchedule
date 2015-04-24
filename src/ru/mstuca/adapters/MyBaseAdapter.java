package ru.mstuca.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
	Context mContext;
	LayoutInflater mInflater;
	ArrayList<T> mItems;

	MyBaseAdapter(Context context, ArrayList<T> items) {
		super();
		mContext = context;
		mItems = items;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return (mItems != null) ? mItems.size() : 0;
	}

	@Override
	public Object getItem(int position) {
		return (mItems != null) ? mItems.get(position) : null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public Context getContext() {
		return mContext;
	}

}

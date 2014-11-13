package ru.mstuca.navigationdrawer;

import java.util.ArrayList;
import java.util.TreeSet;

import ru.mstuca.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NavigationDrawerAdapter extends BaseAdapter {
	private ArrayList<NavigationDrawerItem> mItems;
	private TreeSet<Integer> mSectionHeader = new TreeSet<Integer>();

	private LayoutInflater mInflater;

	public NavigationDrawerAdapter(Context context) {
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	// =====================================================================

	public void addItem(final NavigationDrawerItem item) {
		mItems.add(item);
		notifyDataSetChanged();
	}

	public void addSectionHeaderItem(final NavigationDrawerItem item) {
		mItems.add(item);
		mSectionHeader.add(mItems.size() - 1);
		notifyDataSetChanged();
	}

	// =====================================================================

	@Override
	public int getItemViewType(int position) {
		return mItems.get(position).getType();
	}

	@Override
	public int getViewTypeCount() {
		return NavigationDrawerItem.TYPE_COUNT;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public NavigationDrawerItem getItem(int position) {
		return mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		int rowType = getItemViewType(position);

		if (convertView == null) {
			holder = new ViewHolder();
			switch (rowType) {
			case NavigationDrawerItem.TYPE_ITEM:
				convertView = mInflater.inflate(R.layout.navigation_drawer_item, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tvItem);
				break;

			case NavigationDrawerItem.TYPE_SELECTION_HEADER:
				convertView = mInflater.inflate(R.layout.navigation_drawer_header, parent, false);
				holder.tvTitle = (TextView) convertView.findViewById(R.id.tvSelectionHeader);
				break;
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		NavigationDrawerItem item = mItems.get(position);

		holder.tvTitle.setText(item.getTitle());
		holder.tvTitle.setCompoundDrawablesWithIntrinsicBounds(item.getIcon(), 0, 0, 0);

		return convertView;
	}

	public static class ViewHolder {
		public TextView tvTitle;
	}
}

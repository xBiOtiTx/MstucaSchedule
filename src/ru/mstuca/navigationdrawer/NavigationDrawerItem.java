package ru.mstuca.navigationdrawer;

import android.app.Fragment;

public class NavigationDrawerItem {
	// TODO -> to enum
	public static final int TYPE_COUNT = 2;

	public static final int TYPE_ITEM = 0;
	public static final int TYPE_SELECTION_HEADER = 1;

	// ========================================

	private int mType;

	private String mTitle;
	private int mIcon;

	private Class<? extends Fragment> mFragmentClass;

	private NavigationDrawerItem(int type, String title, int icon, Class<? extends Fragment> fragment) {
		mType = type;
		mTitle = title;
		mIcon = icon;
		switch (mType) {
		case TYPE_ITEM:
			mFragmentClass = fragment;
			break;

		case TYPE_SELECTION_HEADER:
			// nothing
			break;

		default:
			throw new IllegalArgumentException("Incorrect type");
		}
	}

	// ========================================

	public static NavigationDrawerItem newItem(String title, int icon, Class<? extends Fragment> fragment) {
		return new NavigationDrawerItem(TYPE_ITEM, title, icon, fragment);
	}

	public static NavigationDrawerItem newItem(String title, Class<? extends Fragment> fragment) {
		return new NavigationDrawerItem(TYPE_ITEM, title, 0, fragment);
	}

	public static NavigationDrawerItem newSelectionHeader(String title) {
		return new NavigationDrawerItem(TYPE_SELECTION_HEADER, title, 0, null);
	}

	public static NavigationDrawerItem newSelectionHeader(String title, int icon) {
		return new NavigationDrawerItem(TYPE_SELECTION_HEADER, title, icon, null);
	}

	public int getType() {
		return mType;
	}

	public String getTitle() {
		return mTitle;
	}

	public int getIcon() {
		return mIcon;
	}
}

package ru.mstuca.adapters;

import java.util.ArrayList;

import ru.mstuca.R;
import ru.mstuca.views.NonScrollListView;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class WeekCursorAdapter extends CursorAdapter {

	private LayoutInflater mInflater;

	public WeekCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.layout_day, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub

		TextView tvDayOfWeek = (TextView) view.findViewById(R.id.tvDayOfWeek);
		TextView tvDayOfMonth = (TextView) view.findViewById(R.id.tvDayOfMonth);
		NonScrollListView nslvDay = (NonScrollListView) view.findViewById(R.id.nslvDay);

		// tvDayOfWeek.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvDayOfMonth.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));

		// nslvDay.setAdapter...
	}

}

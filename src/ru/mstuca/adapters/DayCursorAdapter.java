package ru.mstuca.adapters;

import ru.mstuca.R;
import ru.mstuca.database.GroupScheduleDB;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

// TODO ViewHolder
public class DayCursorAdapter extends CursorAdapter {
	private Context mContext;
	private LayoutInflater mInflater;

	public DayCursorAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext=context;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return mInflater.inflate(R.layout.layout_day_item, parent, false);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		LinearLayout llColor = (LinearLayout) view.findViewById(R.id.llColor);
		TextView tvNumber = (TextView) view.findViewById(R.id.tvNumber);
		TextView tvTitle = (TextView) view.findViewById(R.id.tvTitle);
		TextView tvTeacher = (TextView) view.findViewById(R.id.tvTeacher);
		TextView tvLessonType = (TextView) view.findViewById(R.id.tvLessonType);
		TextView tvSubGroup = (TextView) view.findViewById(R.id.tvSubGroup);
		TextView tvRoom = (TextView) view.findViewById(R.id.tvRoom);

		int columnIndexAuditory = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_AUDITORY);
		int columnIndexDate = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_DATE);
		int columnIndexId = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_ID);
		int columnIndexNumber = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_NUMBER);
		int columnIndexSubgroup = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_SUBGROUP);
		int columnIndexTeacher = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TEACHER);
		int columnIndexTitle = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TITLE);
		int columnIndexType = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TYPE);
		int columnIndexWeekDay = cursor.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_WEEK_DAY);
		
		//llColor.setBackgroundColor(mContext.getResources().getColor(R.color.))

		// llColor.setBackgroundColor(Color.CYAN);
		// tvNumber.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvTitle.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvName.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvType.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvSubGroup.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
		// tvRoom.setText(cursor.getString(cursor.getColumnIndex(Table.CONTENT)));
	}

}

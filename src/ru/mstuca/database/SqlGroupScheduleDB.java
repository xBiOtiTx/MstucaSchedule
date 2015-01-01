package ru.mstuca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ru.mstuca.model.Group;
import ru.mstuca.model.ScheduleItem;

/**
 * Created by Евгений on 30.12.2014.
 */
public class SqlGroupScheduleDB {

    private Context mContext;
    private Group mGroup;
    private SQLiteOpenHelper mHelper;

    public static SqlGroupScheduleDB getInstance(Context context, Group group) {
        return new SqlGroupScheduleDB(context, group);
    }

    private SqlGroupScheduleDB(Context context, Group group) {
        mContext = context;
        mGroup = group;
        mHelper = new GroupScheduleDB.GroupScheduleDBHelper(mContext, mGroup.getId());
    }

    // input: date
    private static final String sql_day = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " WHERE " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + "=" + "?";

    public List<ScheduleItem> getDayByDate(String day_date) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_day, new String[]{day_date});
            List<ScheduleItem> result = new ArrayList<ScheduleItem>(c.getCount());

            final int index_number = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_NUMBER);
            final int index_week_day = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_WEEK_DAY);
            final int index_title = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TITLE);
            final int index_date = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_DATE);
            final int index_auditory = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_AUDITORY);
            final int index_type = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TYPE);
            final int index_teacher = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_TEACHER);
            final int index_sub_group = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_SUBGROUP);

            while (c.moveToNext()) {
                final Integer number = c.getInt(index_number);
                final Integer weekDay = c.getInt(index_week_day);
                final String title = c.getString(index_title);
                final String date = c.getString(index_date);
                final String auditory = c.getString(index_auditory);
                final Integer type = c.getInt(index_type);
                final String teacher = c.getString(index_teacher);
                final Integer subGroup = c.getInt(index_sub_group);

                ScheduleItem item = new ScheduleItem(number, weekDay, title, date, auditory, type, teacher, subGroup);
                result.add(item);
            }

            return result;
        } finally {
            if (c != null) {
                c.close();
            }

            if (db != null) {
                db.close();
            }
        }
    }

    public int bulkInsert(ContentValues[] values) {
        SQLiteDatabase db = null;
        try {
            db = mHelper.getWritableDatabase();
            int count = 0;
            db.beginTransaction();
            for (ContentValues v : values) {
                db.insert(GroupScheduleDB.TABLE_GROUP, null, v);
                count++;
            }
            db.setTransactionSuccessful();
            return count;
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }
}

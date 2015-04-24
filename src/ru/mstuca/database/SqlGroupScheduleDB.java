package ru.mstuca.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ru.mstuca.model.Group;
import ru.mstuca.model.ScheduleItem;

/**
 * Created by ������� on 30.12.2014.
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

    // input: begin date, end date.(inclusive)
    private static final String sql_days = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " WHERE " + "date(" + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + ")" + " BETWEEN " + "?" + " AND " + "?";

    public ArrayList<ScheduleItem> getDays(String begin_date, String end_date) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_days, new String[]{begin_date, end_date});
            ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>(c.getCount());

            Log.d("TEST", sql_days);
            Log.d("TEST", "params: " + begin_date + ", " + end_date);
            Log.d("TEST", "result count = " + c.getCount());

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


    //public Date getMinDate() {
    //    return null;
    //}

    //public Date getMaxDate() {
    //    return null;
    //}

    private static final String sql_first_date = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " GROUP BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " ASC" + " LIMIT 1";
    //private static final String sql_first_date = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP;
    public Calendar getFirstMonday() {
        //Log.d("SQL", "getFirstMonday()...");
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_first_date, null);
            //Log.d("SQL", sql_first_date);
            //Log.d("SQL", "c.getCount() = " + c.getCount());
            if (c.moveToFirst()) {
                final int index_date = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_DATE);
                final String date = c.getString(index_date);

                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar result = Calendar.getInstance();
                result.setTime(sdf.parse(date));
                while(result.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
                    result.add(Calendar.DATE, -1);
                }
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    private static final String sql_last_date = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " GROUP BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " DESC" + " LIMIT 1";

    public Calendar getLastMonday() {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_last_date, null);
            if (c.moveToFirst()) {
                final int index_date = c.getColumnIndex(GroupScheduleDB.TABLE_GROUP_FIELD_DATE);
                final String date = c.getString(index_date);

                final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar result = Calendar.getInstance();
                result.setTime(sdf.parse(date));
                while(result.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
                    result.add(Calendar.DATE, -1);
                }
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }

            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    // TODO возможна ошибка +-1 неделя
    public int getWeeksCount2() {
        Calendar first = getFirstMonday();
        Calendar last = getLastMonday();
        //Log.d("SQL", "last = " + last);
        //Log.d("SQL", "first = " + first);
        long millis = Math.abs(last.getTime().getTime() - first.getTime().getTime());
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        return (int) (weeks);
    }

    public ArrayList<ScheduleItem> getWeekByNumber2(int weekNumber) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = getFirstMonday();
        c.add(Calendar.DATE, (weekNumber-1)*7);
        String begin = sdf.format(c.getTime());
        c.add(Calendar.DATE, 7);
        String end = sdf.format(c.getTime());
        Log.d("SQL","getWeekByNumber2...");
        Log.d("SQL","weekNumber = " + weekNumber);
        Log.d("SQL",begin + " - " + end);
        return getDays(begin, end);
    }

    // TODO fix this, in GroupScheduleDB.TABLE_GROUP new field TABLE_GROUP_FIELD_WEEK_NUMBER
    //private static final String sql_weeks_count = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " WHERE " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + ">=" + "date(?)" + " AND " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + "<=" + "date(?)" + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE;
    //private static final String sql_weeks_count = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " WHERE " + "date(" + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + ")" + " BETWEEN " + "?" + " AND " + "?" + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE;
    private static final String sql_weeks_count = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " GROUP BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE;

    public int getWeeksCount() {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_weeks_count, null);
            List<ScheduleItem> result = new ArrayList<ScheduleItem>(c.getCount());

            Log.d("TEST", sql_weeks_count);
            Log.d("TEST", "result count = " + c.getCount());

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

            //for (int i = 0; i < 10; i++) {
            //    Log.d("TEST", result.get(i).getDate());
            //}

            int count = 0;
            int lastDayOfWeek = 10;
            for (ScheduleItem i : result) {
                if (i.getWeekDay() < lastDayOfWeek) {
                    count++;
                }
                lastDayOfWeek = i.getWeekDay();
            }
            return count;
        } finally {
            if (c != null) {
                c.close();
            }

            if (db != null) {
                db.close();
            }
        }
    }

    // TODO getWeekNumberByDate
    public int getWeekNumberByDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar dateMonday = Calendar.getInstance();
        try {
            dateMonday.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        while(dateMonday.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY) {
            dateMonday.add(Calendar.DATE, -1);
        }

        Calendar firstMonday = getFirstMonday();

        long millis = Math.abs(dateMonday.getTime().getTime() - firstMonday.getTime().getTime());
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        return (int) (weeks);
    }

    // TODO getWeekByNumber
    private static final String sql_week_by_number = "SELECT * FROM " + GroupScheduleDB.TABLE_GROUP + " GROUP BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE + " ORDER BY " + GroupScheduleDB.TABLE_GROUP_FIELD_DATE;
    public ArrayList<ScheduleItem> getWeekByNumber(int weekNumber) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = mHelper.getReadableDatabase();
            c = db.rawQuery(sql_week_by_number, null);
            ArrayList<ScheduleItem> result = new ArrayList<ScheduleItem>(c.getCount());

            Log.d("TEST", sql_days);
            Log.d("TEST", "result count = " + c.getCount());

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

            int count = 0;
            int lastDayOfWeek = 10;
            ArrayList<ScheduleItem> week = new ArrayList<ScheduleItem>();
            for (ScheduleItem i : result) {
                if (i.getWeekDay() < lastDayOfWeek) {
                    count++;
                }
                if (count == weekNumber) {
                    week.add(i);
                }
                if (count > weekNumber) {
                    break;
                }
            }

            return week;
        } finally {
            if (c != null) {
                c.close();
            }

            if (db != null) {
                db.close();
            }
        }
    }

    public int clear() {
        Log.d("TEST", "clear");
        SQLiteDatabase db = null;
        try {
            int count = 0;
            db = mHelper.getWritableDatabase();
            db.delete(GroupScheduleDB.TABLE_GROUP, null, null);
            return count;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    // TODO optimize
    public int bulkInsert(ContentValues[] values) {
        Log.d("TEST", "bulkInsert " + values.length);
        SQLiteDatabase db = null;
        try {
            db = mHelper.getWritableDatabase();
            db.beginTransaction();
            int count = 0;
            for (ContentValues v : values) {
                db.insert(GroupScheduleDB.TABLE_GROUP, null, v);
                count++;
            }
            db.setTransactionSuccessful();
            return count;
        } finally {
            if (db != null) {
                if (db.inTransaction()) { // TODO It is necessary?
                    db.endTransaction();
                }
                db.close();
            }
        }
    }
}

package ru.mstuca.database;

import ru.mstuca.model.Group;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GroupScheduleDB {
	public static final String DATABASE_NAME = "group.db"; // {id}group.db
	public static final int DATABASE_VERSION = 3;

    // TODO public static final String TABLE_GROUP_META = "tbgroupmeta"; // metadata for TABLE_GROUP
    // public static final String TABLE_GROUP_META_FIELD_WEEKS_COUNT = "weekscount";
    // public static final String TABLE_GROUP_META_FIELD_LAST_UPDATE = "lastupdate";
    // public static final String TABLE_GROUP_META_FIELD_FIRST_DATE = "firstdate";
    // public static final String TABLE_GROUP_META_FIELD_LAST_DATE = "lastdate";

	public static final String TABLE_GROUP = "tbgroup";
	public static final String TABLE_GROUP_FIELD_NUMBER = "number"; // int
	public static final String TABLE_GROUP_FIELD_WEEK_DAY = "weekday"; // int // TODO deprecated
    // TODO public static final String TABLE_GROUP_FIELD_WEEK_NUMBER = "weeknumber";
	public static final String TABLE_GROUP_FIELD_TITLE = "title"; // text
	public static final String TABLE_GROUP_FIELD_DATE = "date"; // text
	public static final String TABLE_GROUP_FIELD_AUDITORY = "auditory"; // text
	public static final String TABLE_GROUP_FIELD_TYPE = "type"; // int
	public static final String TABLE_GROUP_FIELD_TEACHER = "teacher"; // text
	public static final String TABLE_GROUP_FIELD_SUBGROUP = "subgroup"; // int
	public static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP + " (" + TABLE_GROUP_FIELD_NUMBER + " INTEGER," + TABLE_GROUP_FIELD_WEEK_DAY + " INTEGER," + TABLE_GROUP_FIELD_TITLE + " TEXT,"
			+ TABLE_GROUP_FIELD_DATE + " TEXT," + TABLE_GROUP_FIELD_AUDITORY + " TEXT," + TABLE_GROUP_FIELD_TYPE + " INTEGER,"
			+ TABLE_GROUP_FIELD_TEACHER + " TEXT," + TABLE_GROUP_FIELD_SUBGROUP + " INTEGER" + ");";

	public static class GroupScheduleDBHelper extends SQLiteOpenHelper {
		public GroupScheduleDBHelper(Context context, int id) {
			this(context, id + "");
		}

		public GroupScheduleDBHelper(Context context, String id) {
			super(context, id + DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
            Log.w("TEST", "onCreate " + this.getClass().getName());
			db.execSQL(CREATE_TABLE_GROUP);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("TEST", "onUpgrade " + oldVersion + " -> " + newVersion);
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUP);
			onCreate(db);
		}
	}
}

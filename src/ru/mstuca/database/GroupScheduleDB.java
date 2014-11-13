package ru.mstuca.database;

import ru.mstuca.model.Group;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GroupScheduleDB {
	public static final String DATABASE_NAME = "group.db"; // {id}group.db
	public static final int DATABASE_VERSION = 1;

	public static final String TABLE_GROUP = "tbgroup";
	public static final String TABLE_GROUP_FIELD_ID = "_id"; // pk int
	public static final String TABLE_GROUP_FIELD_NUMBER = "number"; // int
	public static final String TABLE_GROUP_FIELD_WEEK_DAY = "weekday"; // int
	public static final String TABLE_GROUP_FIELD_TITLE = "title"; // text
	public static final String TABLE_GROUP_FIELD_DATE = "date"; // text
	public static final String TABLE_GROUP_FIELD_AUDITORY = "auditory"; // text
	public static final String TABLE_GROUP_FIELD_TYPE = "type"; // int
	public static final String TABLE_GROUP_FIELD_TEACHER = "teacher"; // text
	public static final String TABLE_GROUP_FIELD_SUBGROUP = "subgroup"; // int
	public static final String CREATE_TABLE_GROUP = "CREATE TABLE " + TABLE_GROUP + " (" + TABLE_GROUP_FIELD_ID + " INTEGER PRIMARY KEY,"
			+ TABLE_GROUP_FIELD_NUMBER + " INTEGER," + TABLE_GROUP_FIELD_WEEK_DAY + " INTEGER," + TABLE_GROUP_FIELD_TITLE + " TEXT,"
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
			db.execSQL(CREATE_TABLE_GROUP);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Log.w("SQLite", "onUpgrade " + oldVersion + " -> " + newVersion);
			db.execSQL("DROP TABLE IF IT EXIST " + TABLE_GROUP);
			onCreate(db);
		}
	}
}

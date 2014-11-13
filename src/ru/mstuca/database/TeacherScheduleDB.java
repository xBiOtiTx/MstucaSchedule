package ru.mstuca.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//Ѕаза данных расписани€ по преподавателю
public class TeacherScheduleDB {
	public static final String DATABASE_NAME = "teacher.db"; // {id}teacher.db
	public static final int DATABASE_VERSION = 1;

	// TODO копипаст
	public static final String TABLE_GROUPS = "tbgroups";
	public static final String TABLE_GROUPS_FIELD_ID = "id";
	public static final String TABLE_GROUPS_FIELD_TITLE = "title";
	public static final String CREATE_TABLE_GROUPS = "CREATE TABLE " + TABLE_GROUPS + " (" + TABLE_GROUPS_FIELD_ID + " INTEGER PRIMARY KEY,"
			+ TABLE_GROUPS_FIELD_TITLE + " TEXT" + ");";

	public static final String TABLE_TEACHERS = "tbteachers";
	public static final String TABLE_TEACHERS_FIELD_ID = "id";
	public static final String TABLE_TEACHERS_FIELD_TITLE = "title";
	public static final String CREATE_TABLE_TEACHERS = "CREATE TABLE " + TABLE_TEACHERS + " (" + TABLE_TEACHERS_FIELD_ID + " INTEGER PRIMARY KEY,"
			+ TABLE_TEACHERS_FIELD_TITLE + " TEXT" + ");";

	public class TeacherScheduleDBHelper extends SQLiteOpenHelper {
		public TeacherScheduleDBHelper(Context context, int teacherId) {
			super(context, teacherId + DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_GROUPS);
			db.execSQL(CREATE_TABLE_TEACHERS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// Log.w("SQLite", "onUpgrade " + oldVersion + " -> " + newVersion);
			db.execSQL("DROP TABLE IF IT EXIST " + TABLE_GROUPS);
			db.execSQL("DROP TABLE IF IT EXIST " + TABLE_TEACHERS);
			onCreate(db);
		}
	}
}

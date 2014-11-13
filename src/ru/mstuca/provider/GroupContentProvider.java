package ru.mstuca.provider;

import java.util.concurrent.TimeUnit;

import ru.mstuca.database.GroupScheduleDB;
import ru.mstuca.database.GroupScheduleDB.GroupScheduleDBHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class GroupContentProvider extends ContentProvider {
	// // Uri
	// authority // аналогия - база // TODO -> manifest
	public static final String AUTHORITY = "ru.mstuca.providers.group";

	// path // аналогия - таблица
	public static final String GROUP_PATH = "group";

	// Общий Uri
	public static final Uri GROUP_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GROUP_PATH);

	// Типы данных
	// === groups ===
	static final String GROUP_CONTENT_DIR_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + GROUP_PATH;
	// static final String GROUP_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + GROUP_PATH;

	// // UriMatcher
	// общий Uri
	static final int URI_GROUP = 1;
	static final int URI_GROUP_ID = 11;

	// описание и создание UriMatcher
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTHORITY, GROUP_PATH, URI_GROUP);
		uriMatcher.addURI(AUTHORITY, GROUP_PATH + "/#", URI_GROUP_ID);
	}

	GroupScheduleDBHelper dbHelper;
	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		Log.d("TEST", "GroupContentProvider onCreate");
		// dbHelper = new GroupScheduleDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d("TEST", "query - " + uri.toString());

		Cursor cursor = null;

		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case URI_GROUP_ID: {
			Log.d("TEST", "URI_GROUP");
			String id = uri.getLastPathSegment();
			dbHelper = new GroupScheduleDBHelper(getContext(), id);
			if (TextUtils.isEmpty(sortOrder)) {
				sortOrder = GroupScheduleDB.TABLE_GROUP_FIELD_NUMBER + " ASC";
			}
			cursor = db.query(GroupScheduleDB.TABLE_GROUP, projection, selection, selectionArgs, null, null, sortOrder);
			Log.d("TEST", "real = " + cursor.getCount());

			cursor.setNotificationUri(getContext().getContentResolver(), GROUP_CONTENT_URI);
			break;
		}

		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}

		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case URI_GROUP:
			return GROUP_CONTENT_DIR_TYPE;
			// case URI_GROUPS_ID:
			// return GROUP_CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d("TEST", "insert, " + uri.toString());
		if (uriMatcher.match(uri) != URI_GROUP)
			throw new IllegalArgumentException("Wrong URI: " + uri);

		db = dbHelper.getWritableDatabase();
		long rowID = db.insert(GroupScheduleDB.TABLE_GROUP, null, values);
		Uri resultUri = ContentUris.withAppendedId(GROUP_CONTENT_URI, rowID);
		getContext().getContentResolver().notifyChange(resultUri, null);
		return resultUri;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		Log.d("TEST", "bulkInsert");
		int count = 0;
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case URI_GROUP: {
			Log.d("TEST", "bulkInsert URI_GROUPS");
			db.beginTransaction();
			for (ContentValues contentValue : values) {
				db.insert(GroupScheduleDB.TABLE_GROUP, null, contentValue);
				count++;
			}

			db.setTransactionSuccessful();
			db.endTransaction();

			break;
		}

		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d("TEST", "delete");
		int count = 0;
		// switch (uriMatcher.match(uri)) {
		// case URI_GROUPS: {
		// db = dbHelper.getWritableDatabase();
		// count = db.delete(MstucaDB.TABLE_GROUPS, selection, selectionArgs);
		// getContext().getContentResolver().notifyChange(uri, null);
		// break;
		// }
		//
		// case URI_TEACHERS: {
		// db = dbHelper.getWritableDatabase();
		// count = db.delete(MstucaDB.TABLE_TEACHERS, selection, selectionArgs);
		// getContext().getContentResolver().notifyChange(uri, null);
		// break;
		// }
		//
		// default:
		// throw new IllegalArgumentException("Wrong URI: " + uri);
		// }

		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}

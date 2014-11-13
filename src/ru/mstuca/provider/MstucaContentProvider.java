package ru.mstuca.provider;

import java.util.concurrent.TimeUnit;

import ru.mstuca.database.MstucaDB;
import ru.mstuca.database.MstucaDB.MstucaDBHelper;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MstucaContentProvider extends ContentProvider {
	// // Uri
	// authority // аналоги€ - база
	public static final String AUTHORITY = "ru.mstuca.providers.mstuca";

	// path // аналоги€ - таблица
	public static final String GROUPS_PATH = "groups";
	public static final String TEACHERS_PATH = "teachers";

	// ќбщий Uri
	public static final Uri GROUPS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + GROUPS_PATH);
	public static final Uri TEACHERS_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TEACHERS_PATH);

	// “ипы данных
	// === groups ===
	static final String GROUPS_CONTENT_DIR_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + GROUPS_PATH;
	static final String GROUPS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + GROUPS_PATH;
	// === teachers ===
	static final String TEACHERS_CONTENT_DIR_TYPE = "vnd.android.cursor.dir/vnd." + AUTHORITY + "." + TEACHERS_PATH;
	static final String TEACHERS_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd." + AUTHORITY + "." + TEACHERS_PATH;

	// // UriMatcher
	// общий Uri
	static final int URI_GROUPS = 1;
	static final int URI_TEACHERS = 2;

	// Uri с указанным ID
	static final int URI_GROUPS_ID = 11;
	static final int URI_TEACHERS_ID = 12;

	// описание и создание UriMatcher
	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

		uriMatcher.addURI(AUTHORITY, GROUPS_PATH, URI_GROUPS);
		uriMatcher.addURI(AUTHORITY, GROUPS_PATH + "/#", URI_GROUPS_ID);

		uriMatcher.addURI(AUTHORITY, TEACHERS_PATH, URI_TEACHERS);
		uriMatcher.addURI(AUTHORITY, TEACHERS_PATH + "/#", URI_TEACHERS_ID);
	}

	MstucaDBHelper dbHelper;
	SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		Log.d("TEST", "MstucaContentProvider onCreate");
		dbHelper = new MstucaDBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		Log.d("TEST", "query - " + uri.toString());

		Cursor cursor = null;
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case URI_GROUPS: {
			Log.d("TEST", "URI_GROUPS");
			if (TextUtils.isEmpty(sortOrder)) {
				sortOrder = MstucaDB.TABLE_GROUPS_FIELD_TITLE + " ASC";
			}
			cursor = db.query(MstucaDB.TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
			Log.d("TEST", "real = " + cursor.getCount());
			
			Log.d("TEST", "setNotificationUri for" + GROUPS_CONTENT_URI);
			cursor.setNotificationUri(getContext().getContentResolver(), GROUPS_CONTENT_URI);
			break;
		}

		case URI_GROUPS_ID: {
			Log.d("TEST", "URI_GROUPS_ID");
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				selection = MstucaDB.TABLE_GROUPS_FIELD_ID + " = " + id;
			} else {
				selection = selection + " AND " + MstucaDB.TABLE_GROUPS_FIELD_ID + " = " + id;
			}
			cursor = db.query(MstucaDB.TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
			Log.d("TEST", "real = " + cursor.getCount());
			
			Log.d("TEST", "setNotificationUri for" + GROUPS_CONTENT_URI);
			cursor.setNotificationUri(getContext().getContentResolver(), GROUPS_CONTENT_URI);
			break;
		}

		case URI_TEACHERS: {
			Log.d("TEST", "URI_TEACHERS");
			if (TextUtils.isEmpty(sortOrder)) {
				sortOrder = MstucaDB.TABLE_TEACHERS_FIELD_TITLE + " ASC";
			}
			cursor = db.query(MstucaDB.TABLE_TEACHERS, projection, selection, selectionArgs, null, null, sortOrder);
			Log.d("TEST", "real = " + cursor.getCount());
			
			Log.d("TEST", "setNotificationUri for" + TEACHERS_CONTENT_URI);
			cursor.setNotificationUri(getContext().getContentResolver(), TEACHERS_CONTENT_URI);
			break;
		}

		case URI_TEACHERS_ID: {
			Log.d("TEST", "URI_TEACHERS_ID");
			String id = uri.getLastPathSegment();
			if (TextUtils.isEmpty(selection)) {
				selection = MstucaDB.TABLE_TEACHERS_FIELD_ID + " = " + id;
			} else {
				selection = selection + " AND " + MstucaDB.TABLE_TEACHERS_FIELD_ID + " = " + id;
			}
			cursor = db.query(MstucaDB.TABLE_TEACHERS, projection, selection, selectionArgs, null, null, sortOrder);
			Log.d("TEST", "real = " + cursor.getCount());
			
			Log.d("TEST", "setNotificationUri for" + TEACHERS_CONTENT_URI);
			cursor.setNotificationUri(getContext().getContentResolver(), TEACHERS_CONTENT_URI);
			break;
		}

		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}

		// TODO —делать правильно. ѕока работает только выборка по группам.
		// db = dbHelper.getWritableDatabase();
		// Cursor cursor = db.query(MstucaDB.TABLE_GROUPS, projection, selection, selectionArgs, null, null, sortOrder);
		// cursor.setNotificationUri(getContext().getContentResolver(), GROUPS_CONTENT_URI);
		
		Log.d("-> TEST", "setNotificationUri for" + uri);
		//cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case URI_GROUPS:
			return GROUPS_CONTENT_DIR_TYPE;
		case URI_GROUPS_ID:
			return GROUPS_CONTENT_ITEM_TYPE;
		case URI_TEACHERS:
			return TEACHERS_CONTENT_DIR_TYPE;
		case URI_TEACHERS_ID:
			return TEACHERS_CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		Log.d("TEST", "insert, " + uri.toString());
		if (uriMatcher.match(uri) != URI_GROUPS)
			throw new IllegalArgumentException("Wrong URI: " + uri);

		db = dbHelper.getWritableDatabase();
		long rowID = db.insert(MstucaDB.TABLE_GROUPS, null, values);
		Uri resultUri = ContentUris.withAppendedId(GROUPS_CONTENT_URI, rowID);
		// уведомл€ем ContentResolver, что данные по адресу resultUri изменились
		getContext().getContentResolver().notifyChange(resultUri, null);
		return resultUri;
	}

	@Override
	public int bulkInsert(Uri uri, ContentValues[] values) {
		Log.d("TEST", "bulkInsert");
		int count = 0;
		db = dbHelper.getWritableDatabase();
		switch (uriMatcher.match(uri)) {
		case URI_GROUPS: {
			Log.d("TEST", "bulkInsert URI_GROUPS");
			db.beginTransaction();
			for (ContentValues contentValue : values) {
				db.insert(MstucaDB.TABLE_GROUPS, null, contentValue);
				count++;
			}

			db.setTransactionSuccessful();
			db.endTransaction();

			break;
		}

		case URI_TEACHERS: {
			Log.d("TEST", "bulkInsert URI_TEACHERS");
			db.beginTransaction();

			for (ContentValues contentValue : values) {
				db.insert(MstucaDB.TABLE_TEACHERS, null, contentValue);
				count++;
			}

			db.setTransactionSuccessful();
			db.endTransaction();

			break;
		}

		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}

		Log.d("TEST", "notifyChange for" + uri);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		Log.d("TEST", "delete");
		int count = 0;
		switch (uriMatcher.match(uri)) {
		case URI_GROUPS: {
			db = dbHelper.getWritableDatabase();
			count = db.delete(MstucaDB.TABLE_GROUPS, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			break;
		}

		case URI_TEACHERS: {
			db = dbHelper.getWritableDatabase();
			count = db.delete(MstucaDB.TABLE_TEACHERS, selection, selectionArgs);
			getContext().getContentResolver().notifyChange(uri, null);
			break;
		}

		default:
			throw new IllegalArgumentException("Wrong URI: " + uri);
		}

		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
}

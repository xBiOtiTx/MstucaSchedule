package ru.mstuca.persistance;

import java.util.ArrayList;
import java.util.List;

import ru.mstuca.database.MstucaDB;
import ru.mstuca.model.Group;
import ru.mstuca.model.ListGroups;
import ru.mstuca.provider.MstucaContentProvider;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.octo.android.robospice.persistence.ObjectPersister;
import com.octo.android.robospice.persistence.exception.CacheLoadingException;
import com.octo.android.robospice.persistence.exception.CacheSavingException;

public class ListGroupsPersister extends ObjectPersister<ListGroups> {
	public ListGroupsPersister(Application application, Class<ListGroups> modelObjectType) {
		super(application, modelObjectType);
	}

	// TODO ~ CacheEntry
	// https://github.com/stephanenicolas/robospice/blob/b3ede624b4ec2191d8eef10dede089fc091bcc95/extensions/robospice-ormlite-parent/robospice-ormlite/src/main/java/com/octo/android/robospice/persistence/ormlite/CacheEntry.java
	@Override
	public ListGroups loadDataFromCache(Object cacheKey, long maxTimeInCache) throws CacheLoadingException {
		Log.d("TEST", "loadDataFromCache(key=" + cacheKey + ", time=" + maxTimeInCache + ") " + getHandledClass().getSimpleName());

		ListGroups data = null;

		final String[] projection = new String[] { MstucaDB.TABLE_GROUPS_FIELD_ID, MstucaDB.TABLE_GROUPS_FIELD_TITLE };
		Cursor c = getApplication().getContentResolver().query(MstucaContentProvider.GROUPS_CONTENT_URI, projection, null, null, null);
		if (c != null && c.getCount() > 0) {
			List<Group> array = new ArrayList<Group>();

			int indexId = c.getColumnIndex(MstucaDB.TABLE_GROUPS_FIELD_ID);
			int indexTitle = c.getColumnIndex(MstucaDB.TABLE_GROUPS_FIELD_TITLE);

			while (c.moveToNext()) {
				int id = c.getInt(indexId);
				String title = c.getString(indexTitle);

				Group item = new Group(id, title);
				array.add(item);
			}

			data = new ListGroups();
			data.setData(array);

			c.close();
		}

		// data = null;
		return data;
	}

	@Override
	public ListGroups saveDataToCacheAndReturnData(final ListGroups data, final Object cacheKey) throws CacheSavingException {
		Log.d("TEST", "saveDataToCacheAndReturnData(key=" + cacheKey + ") = " + data);

		ArrayList<ContentValues> values = new ArrayList<ContentValues>();

		for (Group g : data.getData()) {
			ContentValues cv = new ContentValues();
			cv.put(MstucaDB.TABLE_GROUPS_FIELD_ID, g.getId());
			cv.put(MstucaDB.TABLE_GROUPS_FIELD_TITLE, g.getTitle());
			values.add(cv);
		}

		getApplication().getContentResolver().delete(MstucaContentProvider.GROUPS_CONTENT_URI, null, null);
		getApplication().getContentResolver().bulkInsert(MstucaContentProvider.GROUPS_CONTENT_URI, values.toArray(new ContentValues[values.size()]));

		return data;
	}

	@Override
	public boolean isDataInCache(Object cacheKey, long maxTimeInCache) {
		Log.d("TEST", "isDataInCache");

		// try {
		// CacheEntry cacheEntry = databaseHelper.queryCacheKeyForIdFromDatabase(String.valueOf(cacheKey));
		// if (cacheEntry == null) {
		// return false;
		// }
		// long timeInCache = System.currentTimeMillis() - cacheEntry.getTimestamp();
		// return maxTimeInCache == DurationInMillis.ALWAYS_RETURNED || timeInCache <= maxTimeInCache;
		// } catch (SQLException e) {
		// Ln.e(e, "SQL error");
		// return false;
		// }

		// TODO kostil
		return false;
	}

	@Override
	public long getCreationDateInCache(Object cacheKey) throws CacheLoadingException {
		Log.d("TEST", "getCreationDateInCache");

		// CacheEntry cacheEntry = null;
		// try {
		// cacheEntry = databaseHelper.queryCacheKeyForIdFromDatabase(String.valueOf(cacheKey));
		// } catch (SQLException e) {
		// throw new CacheLoadingException("Data could not be found in cache for cacheKey=" + cacheKey, e);
		// }
		// if (cacheEntry == null) {
		// throw new CacheLoadingException("Data could not be found in cache for cacheKey=" + cacheKey);
		// }
		// return cacheEntry.getTimestamp();

		// TODO kostil
		return 0;
	}

	@Override
	public boolean removeDataFromCache(Object cacheKey) {
		Log.d("TEST", "removeDataFromCache");

		// try {
		// String id = cacheKey.toString();
		// CacheEntry cacheEntry = databaseHelper.queryCacheKeyForIdFromDatabase(id);
		// if (cacheEntry == null) {
		// return false;
		// }
		// Class<?> clazz = Class.forName(cacheEntry.getResultClassName());
		// databaseHelper.deleteByIdFromDataBase(cacheEntry.getResultId(), clazz);
		// databaseHelper.deleteFromDataBase(cacheEntry, CacheEntry.class);
		// return true;
		// } catch (SQLException e) {
		// Ln.d(e);
		// return false;
		// } catch (ClassNotFoundException e) {
		// Ln.d(e);
		// return false;
		// }

		// TODO kostil
		return false;
	}

	@Override
	public void removeAllDataFromCache() {
		Log.d("TEST", "removeAllDataFromCache");

		// try {
		// databaseHelper.clearTableFromDataBase(getHandledClass());
		// databaseHelper.clearTableFromDataBase(CacheEntry.class);
		// } catch (SQLException e) {
		// Ln.d(e, "SQL Error");
		// }
	}

	@Override
	public List<ListGroups> loadAllDataFromCache() throws CacheLoadingException {
		Log.d("TEST", "loadAllDataFromCache");

		// try {
		// List<CacheEntry> listCacheEntry = databaseHelper.queryForAllFromDatabase(CacheEntry.class);
		// List<T> listObjectInCache = new ArrayList<T>();
		// for (CacheEntry cacheEntry : listCacheEntry) {
		// listObjectInCache.add(loadDataFromCache(cacheEntry.getCacheKey(), DurationInMillis.ALWAYS_RETURNED));
		// }
		// return listObjectInCache;
		// } catch (SQLException e) {
		// Ln.d(e);
		// return null;
		// }

		// TODO kostil
		return null;
	}

	@Override
	public List<Object> getAllCacheKeys() {
		Log.d("TEST", "getAllCacheKeys");

		// try {
		// List<CacheEntry> listCacheEntry = databaseHelper.queryForAllFromDatabase(CacheEntry.class);
		// List<Object> listCacheKey = new ArrayList<Object>();
		// for (CacheEntry cacheEntry : listCacheEntry) {
		// listCacheKey.add(cacheEntry.getCacheKey());
		// }
		// return listCacheKey;
		// } catch (SQLException e) {
		// return null;
		// }

		// TODO kostil
		return null;
	}
}

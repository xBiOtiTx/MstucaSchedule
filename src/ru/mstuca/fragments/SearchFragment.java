package ru.mstuca.fragments;

import ru.mstuca.R;
import ru.mstuca.database.MstucaDB;
import ru.mstuca.provider.MstucaContentProvider;
import ru.mstuca.request.ListGroupsRequest;
import ru.mstuca.request.ListTeachersRequest;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TabHost;

import com.octo.android.robospice.persistence.DurationInMillis;

public class SearchFragment extends BaseFragment {
	public SearchFragment() {
	}

	public static SearchFragment newInstance() {
		SearchFragment fragment = new SearchFragment();
		return fragment;
	}

	private SimpleCursorAdapter mGroupsAdapter;
	private SimpleCursorAdapter mTeachersAdapter;

	// ==================================================================================================================================================================
	// Fragment life cycle
	// ==================================================================================================================================================================

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.search_fragment_tabs, container, false);

		TabHost tabHost = (TabHost) rootView.findViewById(android.R.id.tabhost);
		tabHost.setup();

		TabHost.TabSpec tabSpec;

		tabSpec = tabHost.newTabSpec("tag1");
		// tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_group));
		tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_group_white));
		tabSpec.setContent(R.id.tab1);
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag2");
		// tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_teacher));
		tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_person));
		tabSpec.setContent(R.id.tab2);
		tabHost.addTab(tabSpec);

		{
			ListView list = (ListView) rootView.findViewById(R.id.tab1);
			String[] fromColumns = { MstucaDB.TABLE_GROUPS_FIELD_TITLE };
			int[] toViews = { android.R.id.text1 };
			mGroupsAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
			list.setAdapter(mGroupsAdapter);
		}
		{
			ListView list2 = (ListView) rootView.findViewById(R.id.tab2);
			String[] fromColumns = { MstucaDB.TABLE_TEACHERS_FIELD_TITLE };
			int[] toViews = { android.R.id.text1 };
			mTeachersAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
			list2.setAdapter(mTeachersAdapter);
		}

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		ListGroupsRequest groupsRequest = new ListGroupsRequest();
		getSpiceManager().execute(groupsRequest, new Integer(0), DurationInMillis.ALWAYS_RETURNED, null);
		getLoaderManager().initLoader(0, null, mGroupsLoaderCallbacks);

		ListTeachersRequest teachersRequest = new ListTeachersRequest();
		getSpiceManager().execute(teachersRequest, new Integer(1), DurationInMillis.ALWAYS_RETURNED, null);
		getLoaderManager().initLoader(1, null, mTeachersLoaderCallbacks);
	}

	// ==================================================================================================================================================================
	// LoaderCallbacks
	// ==================================================================================================================================================================

	LoaderCallbacks<Cursor> mTeachersLoaderCallbacks = new LoaderCallbacks<Cursor>() {

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			mTeachersAdapter.swapCursor(null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			Log.d("TEST", "mTeachersLoaderCallbacks: " + data.getCount());
			mTeachersAdapter.swapCursor(data);
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			final String[] projection = new String[] { MstucaDB.TABLE_TEACHERS_FIELD_ID, MstucaDB.TABLE_TEACHERS_FIELD_TITLE };
			return new CursorLoader(getActivity(), // Parent activity context
					MstucaContentProvider.TEACHERS_CONTENT_URI, // Table to query
					projection, // Projection to return
					null, // null, // No selection clause
					null, // No selection arguments
					null // AsipCitiesDB.TABLE_CITIES_COLUMN_TITLE // Default sort order
			);
		}
	};

	LoaderCallbacks<Cursor> mGroupsLoaderCallbacks = new LoaderCallbacks<Cursor>() {

		@Override
		public void onLoaderReset(Loader<Cursor> loader) {
			mGroupsAdapter.swapCursor(null);
		}

		@Override
		public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
			Log.d("TEST", "mGroupsLoaderCallbacks: " + data.getCount());
			mGroupsAdapter.swapCursor(data);
		}

		@Override
		public Loader<Cursor> onCreateLoader(int id, Bundle args) {
			final String[] projection = new String[] { MstucaDB.TABLE_GROUPS_FIELD_ID, MstucaDB.TABLE_GROUPS_FIELD_TITLE };
			return new CursorLoader(getActivity(), // Parent activity context
					MstucaContentProvider.GROUPS_CONTENT_URI, // Table to query
					projection, // Projection to return
					null, // null, // No selection clause
					null, // No selection arguments
					null // AsipCitiesDB.TABLE_CITIES_COLUMN_TITLE // Default sort order
			);
		}
	};

}

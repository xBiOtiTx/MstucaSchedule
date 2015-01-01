package ru.mstuca.fragments;

import ru.mstuca.R;
import ru.mstuca.database.MstucaDB;
import ru.mstuca.model.Group;
import ru.mstuca.model.Teacher;
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
import android.widget.AdapterView;
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

    private static final int GROUPS_LOADER = 0;
    private static final int TEACHERS_LOADER = 1;

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
        //tabSpec.setIndicator("Группы");
		tabSpec.setContent(R.id.tab1);
		tabHost.addTab(tabSpec);

		tabSpec = tabHost.newTabSpec("tag2");
		// tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_teacher));
		tabSpec.setIndicator(null, getResources().getDrawable(R.drawable.ic_action_person));
        //tabSpec.setIndicator("Преподаватели");
		tabSpec.setContent(R.id.tab2);
		tabHost.addTab(tabSpec);

		{
			ListView list1 = (ListView) rootView.findViewById(R.id.tab1);
			String[] fromColumns = { MstucaDB.TABLE_GROUPS_FIELD_TITLE };
			int[] toViews = { android.R.id.text1 };
			mGroupsAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
			list1.setAdapter(mGroupsAdapter);
            list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor c = (Cursor) parent.getItemAtPosition(position);

                    int index_group_id = c.getColumnIndex(MstucaDB.TABLE_GROUPS_FIELD_ID);
                    int index_group_title = c.getColumnIndex(MstucaDB.TABLE_GROUPS_FIELD_TITLE);

                    int group_id = c.getInt(index_group_id);
                    String group_title = c.getString(index_group_title);

                    Group item = new Group(group_id,group_title);

                    getFragmentManager().beginTransaction().replace(R.id.container, GroupScheduleFragment.newInstance(item)).commit();
                }
            });
		}
		{
			ListView list2 = (ListView) rootView.findViewById(R.id.tab2);
			String[] fromColumns = { MstucaDB.TABLE_TEACHERS_FIELD_TITLE };
			int[] toViews = { android.R.id.text1 };
			mTeachersAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
			list2.setAdapter(mTeachersAdapter);
            list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Cursor c = (Cursor) parent.getItemAtPosition(position);

                    int index_teacher_id = c.getColumnIndex(MstucaDB.TABLE_TEACHERS_FIELD_ID);
                    int index_teacher_title = c.getColumnIndex(MstucaDB.TABLE_TEACHERS_FIELD_TITLE);

                    int teacher_id = c.getInt(index_teacher_id);
                    String teacher_title = c.getString(index_teacher_title);

                    Teacher item = new Teacher(teacher_id, teacher_title);

                    getFragmentManager().beginTransaction().replace(R.id.container, TeacherScheduleFragment.newInstance(item)).commit();
                }
            });
		}

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		ListGroupsRequest groupsRequest = new ListGroupsRequest();
		getSpiceManager().execute(groupsRequest, Integer.valueOf(0), DurationInMillis.ALWAYS_RETURNED, null);
		getLoaderManager().initLoader(GROUPS_LOADER, null, mGroupsLoaderCallbacks);

		ListTeachersRequest teachersRequest = new ListTeachersRequest();
		getSpiceManager().execute(teachersRequest, Integer.valueOf(1), DurationInMillis.ALWAYS_RETURNED, null);
		getLoaderManager().initLoader(TEACHERS_LOADER, null, mTeachersLoaderCallbacks);
	}

    // ==================================================================================================================================================================
    // OnItemListeners
    // ==================================================================================================================================================================


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

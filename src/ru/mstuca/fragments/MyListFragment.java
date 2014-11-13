package ru.mstuca.fragments;

import ru.mstuca.R;
import ru.mstuca.database.MstucaDB;
import ru.mstuca.model.ListGroups;
import ru.mstuca.provider.MstucaContentProvider;
import ru.mstuca.request.ListGroupsRequest;
import ru.mstuca.service.MstucaSpiceService;
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

import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.octo.android.robospice.request.listener.RequestProgress;
import com.octo.android.robospice.request.listener.RequestProgressListener;

public class MyListFragment extends BaseFragment implements LoaderCallbacks<Cursor> {
	private SpiceManager spiceManager = new SpiceManager(MstucaSpiceService.class);

	public MyListFragment() {
	}

	public static MyListFragment newInstance() {
		MyListFragment fragment = new MyListFragment();
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// getActivity().deleteDatabase(AsipPreferencesNew.getInstance(getActivity()).getCityId() + "city.db");
		getActivity().deleteDatabase("mstuca.db");
		spiceManager.start(getActivity().getApplicationContext());
	}

	@Override
	public void onDestroy() {
		if (spiceManager.isStarted()) { // TODO or -> onStart()/onStop()? // may be the bug https://github.com/stephanenicolas/robospice/issues/96
			spiceManager.shouldStop();
		}
		super.onDestroy();
	}

	private SimpleCursorAdapter mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_list, container, false);

		final ListView list = (ListView) rootView.findViewById(R.id.listView1);
		String[] fromColumns = { MstucaDB.TABLE_GROUPS_FIELD_TITLE };
		int[] toViews = { android.R.id.text1 };
		mAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, null, fromColumns, toViews, 0);
		list.setAdapter(mAdapter);

		return rootView;
	}

	@Override
	public void onResume() {
		super.onResume();

		ListGroupsRequest request = new ListGroupsRequest();
		spiceManager.execute(request, new Integer(0), DurationInMillis.ALWAYS_RETURNED, myRequestListener);
		// spiceManager.addListenerIfPending(InitialData.class, new Integer(0), myRequestListener2);
		getLoaderManager().initLoader(0, null, this);
	}

	MyRequestListener myRequestListener = new MyRequestListener();

	public class MyRequestListener implements RequestListener<ListGroups>, RequestProgressListener {

		@Override
		public void onRequestProgressUpdate(RequestProgress progress) {
			switch (progress.getStatus()) {
			case COMPLETE:
				Log.d("TEST", "onRequestProgressUpdate COMPLETE");
				// getActivity().setProgressBarIndeterminate(false);
				// getActivity().setProgressBarIndeterminateVisibility(false);
				break;

			case LOADING_FROM_NETWORK:
				Log.d("TEST", "onRequestProgressUpdate LOADING_FROM_NETWORK = " + progress.getProgress());
				// getActivity().setProgressBarIndeterminateVisibility(true);
				// getActivity().setProgressBarIndeterminate(true);
				break;

			case PENDING:
				Log.d("TEST", "onRequestProgressUpdate PENDING");
				if (progress.getProgress() > 0) {
					// getActivity().setProgressBarIndeterminateVisibility(true);
					// getActivity().setProgressBarIndeterminate(true);
				} else {
					// getActivity().setProgressBarIndeterminate(false);
					// getActivity().setProgressBarIndeterminateVisibility(false);
				}
				break;

			case READING_FROM_CACHE:
				Log.d("TEST", "onRequestProgressUpdate READING_FROM_CACHE");
				break;

			case WRITING_TO_CACHE:
				Log.d("TEST", "onRequestProgressUpdate WRITING_TO_CACHE");
				break;

			default:
				break;
			}
		}

		@Override
		public void onRequestFailure(SpiceException e) {
			Log.d("TEST", "onRequestFailure");
		}

		@Override
		public void onRequestSuccess(ListGroups data) {
			Log.d("TEST", "onRequestSuccess");
			// mAdapter.swapCursor(newCursor).close();
		}
	}

	// ===========================================================================================================================
	// Loader

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle bundle) {
		final String[] projection = new String[] { MstucaDB.TABLE_GROUPS_FIELD_ID, MstucaDB.TABLE_GROUPS_FIELD_TITLE };

		return new CursorLoader(getActivity(), // Parent activity context
				MstucaContentProvider.GROUPS_CONTENT_URI, // Table to query
				projection, // Projection to return
				null, // null, // No selection clause
				null, // No selection arguments
				null // AsipCitiesDB.TABLE_CITIES_COLUMN_TITLE // Default sort order
		);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		Log.d("TEST", "onLoadFinished");
		mAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		Log.d("TEST", "onLoaderReset");
		mAdapter.swapCursor(null);
	}

}
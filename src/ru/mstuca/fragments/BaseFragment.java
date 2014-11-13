package ru.mstuca.fragments;

import ru.mstuca.service.MstucaSpiceService;

import com.octo.android.robospice.SpiceManager;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;

public class BaseFragment extends Fragment implements OnSharedPreferenceChangeListener {
	protected static final String APP_TITLE = "title";
	protected static final String APP_SUBTITLE = "subtitle";

	private SpiceManager spiceManager = new SpiceManager(MstucaSpiceService.class);

	protected Context mContext;

	// protected AsipApplication mApp;
	// protected FragmentActivity mActivity;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.mContext = activity.getApplicationContext();
		// this.mApp = (AsipApplication) activity.getApplication();
		// this.mActivity = (FragmentActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		spiceManager.start(getActivity().getApplicationContext());

		// TODO or mb an mActivity?
		// mContext.getSharedPreferences(AsipPreferences.APP_SHARED_PREFERENCES, 0).registerOnSharedPreferenceChangeListener(this);
	}

	public SpiceManager getSpiceManager() {
		return spiceManager;
	}

	@Override
	public void onDestroy() {
		if (spiceManager.isStarted()) { // TODO or -> onStart()/onStop()? // may be the bug https://github.com/stephanenicolas/robospice/issues/96
			spiceManager.shouldStop();
		}
		// mContext.getSharedPreferences(AsipPreferences.APP_SHARED_PREFERENCES, 0).unregisterOnSharedPreferenceChangeListener(this);
		super.onDestroy();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
		// Empty. For override method;
	}
}

package ru.mstuca.fragments;

import ru.mstuca.MstucaConstants;
import ru.mstuca.service.MstucaSpiceService;

import com.octo.android.robospice.SpiceManager;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;


public class BaseFragment extends Fragment implements OnSharedPreferenceChangeListener,MstucaConstants {
	private SpiceManager mSpiceManager = new SpiceManager(MstucaSpiceService.class);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSpiceManager.start(getActivity().getApplicationContext());
		getActivity().getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE).registerOnSharedPreferenceChangeListener(this);
	}

	public SpiceManager getSpiceManager() {
		return mSpiceManager;
	}

	@Override
	public void onDestroy() {
        super.onDestroy();
		if (mSpiceManager.isStarted()) { // TODO or -> onStart()/onStop()? // may be the bug https://github.com/stephanenicolas/robospice/issues/96
			mSpiceManager.shouldStop();
		}
		getActivity().getSharedPreferences(APP_SHARED_PREFERENCES, 0).unregisterOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	}
}

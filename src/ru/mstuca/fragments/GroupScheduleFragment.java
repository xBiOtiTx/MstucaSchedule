package ru.mstuca.fragments;

import com.google.gson.Gson;

import ru.mstuca.R;
import ru.mstuca.model.Group;
import ru.mstuca.views.NonScrollListView;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class GroupScheduleFragment extends BaseFragment {
	private static final String ARG_GROUP = Group.class.getName();

	public GroupScheduleFragment() {
	}

	public static GroupScheduleFragment newInstance(Group group) { // newInstance(Group group) -> GSON?
		GroupScheduleFragment fragment = new GroupScheduleFragment();
		Bundle args = new Bundle();
		args.putString(ARG_GROUP, new Gson().toJson(group));
		args.putString(APP_TITLE, group.getTitle());
		// args.putString(APP_SUBTITLE, new Gson().toJson(group));
		fragment.setArguments(args);

		return fragment;
	}

	private Group mGroup;
	private WeekPagerAdapter mDemoCollectionPagerAdapter;
	private ViewPager mViewPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGroup = new Gson().fromJson(getArguments().getString(ARG_GROUP), Group.class);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_group_schedule, container, false);

		mDemoCollectionPagerAdapter = new WeekPagerAdapter(getFragmentManager());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDemoCollectionPagerAdapter);

		return rootView;
	}

	public static class WeekPagerAdapter extends FragmentStatePagerAdapter {
		public WeekPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new WeekFragment();
			Bundle args = new Bundle();
			args.putInt(WeekFragment.ARG_WEEK_NUMBER, i + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return 3; // TODO
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Ќедел€ " + (position + 1);
			// return "Ќедел€ " + position + "\n" + (position) + " окт - " + (position + 7) + " окт";
			// return (position) + " окт - " + (position + 7) + " окт";
		}
	}

	public static class WeekFragment extends Fragment {
		public static final String ARG_WEEK_NUMBER = "week";

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			// View rootView = inflater.inflate(R.layout.fragment_week, container, false);

			// ListView lvWeek = (ListView) rootView.findViewById(R.id.lvWeek);

			// cursorloader -> выбрать ¬—≈ пары попадающий в заданную неделю.
			// --- или выбрать имена иа даты дней недели с количествами(а мб и нет) зан€тий. -> выборки по зан€ти€м в
			// таком случает будут во внуренних адаптерах по дн€м, каждый из которых будет делать запрос на день?

			View rootView = inflater.inflate(R.layout.fragment_week_hardcode, container, false);
			// may be use a viewholder?

			NonScrollListView nslvMonday = (NonScrollListView) rootView.findViewById(R.id.nslvMonday);
			NonScrollListView nslvTuesday = (NonScrollListView) rootView.findViewById(R.id.nslvTuesday);
			NonScrollListView nslvWednesday = (NonScrollListView) rootView.findViewById(R.id.nslvWednesday);
			NonScrollListView nslvThursday = (NonScrollListView) rootView.findViewById(R.id.nslvThursday);
			NonScrollListView nslvFriday = (NonScrollListView) rootView.findViewById(R.id.nslvFriday);
			NonScrollListView nslvSaturday = (NonScrollListView) rootView.findViewById(R.id.nslvSaturday);
			NonScrollListView nslvSunday = (NonScrollListView) rootView.findViewById(R.id.nslvSunday);

			TextView tvMondayDate = (TextView) rootView.findViewById(R.id.tvMondayDate);
			TextView tvTuesdayDate = (TextView) rootView.findViewById(R.id.tvTuesdayDate);
			TextView tvWednesdayDate = (TextView) rootView.findViewById(R.id.tvWednesdayDate);
			TextView tvThursdayDate = (TextView) rootView.findViewById(R.id.tvThursdayDate);
			TextView tvFridayDate = (TextView) rootView.findViewById(R.id.tvFridayDate);
			TextView tvSaturdayDate = (TextView) rootView.findViewById(R.id.tvSaturdayDate);
			TextView tvSundayDate = (TextView) rootView.findViewById(R.id.tvSundayDate);

			// tvMondayDate.setText("");

			// »ли захардкодить дни недели и захардкодить по адаптеру на каждый день недели

			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			// initloader
			// spicerequest - ?
		}
	}
}

package ru.mstuca.fragments;

import com.google.gson.Gson;

import ru.mstuca.R;
import ru.mstuca.adapters.WeekAdapter2;
import ru.mstuca.database.SqlGroupScheduleDB;
import ru.mstuca.model.Group;
import ru.mstuca.model.ScheduleItem;
import ru.mstuca.model.Week;
import ru.mstuca.adapters.ListFragmentPagerAdapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

public class GroupScheduleFragment extends BaseFragment {
    private static final String ARG_GROUP = Group.class.getName();

    public static GroupScheduleFragment newInstance(Group group) {
        GroupScheduleFragment fragment = new GroupScheduleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_GROUP, new Gson().toJson(group));
        args.putString(APP_TITLE, group.getTitle());
        fragment.setArguments(args);
        return fragment;
    }

    private Group mGroup;
    private WeekPagerAdapter mWeekPagerAdapter;
    private ViewPager mViewPager;

    private ListView mListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mGroup = new Gson().fromJson(getArguments().getString(ARG_GROUP), Group.class);

        //>>>
        List<Week> weeks = new ArrayList<>();
        List<Fragment> weekFragments = new ArrayList<>();
        for (Week w: weeks) {
            Fragment fragment = new WeekFragment();

            Bundle args = new Bundle();
            args.putString(WeekFragment.ARG_WEEK, new Gson().toJson(w));
            fragment.setArguments(args);

            weekFragments.add(fragment);
        }
        ListFragmentPagerAdapter weekAdapter = new ListFragmentPagerAdapter(getFragmentManager(), weekFragments);
        //<<<
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_group_schedule, container, false);
        mWeekPagerAdapter = new WeekPagerAdapter(getActivity(), getFragmentManager(), mGroup);
        mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        mViewPager.setAdapter(mWeekPagerAdapter);
        //vs

        //mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
        //mWeekPagerAdapter = new WeekPagerAdapter(getActivity(), getFragmentManager(), mViewPager, mGroup);


        //mViewPager.setPageMargin(1);
        //mViewPager.setCurrentItem(15);
        //mViewPager.scr
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //mViewPager.setCurrentItem(2,true); // with smooth scroll
        //GroupScheduleRequest request = new GroupScheduleRequest(mGroup);
        //getSpiceManager().execute(request,mGroup, DurationInMillis.ALWAYS_EXPIRED,null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.schedule, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_today:
                Log.d("TEST", "menu_action_today"); // 4 неделя вторник 2014-09-23

                //Date now = new Date();
                //int weekNumber = mWeekPagerAdapter.getWeekNumberByDate(now);
                //mViewPager.setCurrentItem(pageNumber,true);


                int n = SqlGroupScheduleDB.getInstance(getActivity(), mGroup).getWeekNumberByDate("2014-09-23");
                mViewPager.setCurrentItem(n,true);

                // Fragment f = getChildFragmentManager().findFragmentById(mViewPager.getId());
                Fragment f = mWeekPagerAdapter.getRegisteredFragment(n);
                // Fragment f =  mWeekPagerAdapter.getItem(n);
                Log.d("TEST", "f = " + f + " and " + (f instanceof  WeekFragment));
                if(f instanceof  WeekFragment) {
                    final WeekFragment wf = (WeekFragment)f;
                    wf.scrollToDate("2014-09-25");
                    //wf.getListView().smoothScrollToPosition(wf.getAdapter().getPositionByDate("2014-09-23"));
                    //wf.getListView().smoothScrollToPosition(8);
                   // Log.d("TEST","getPositionByDate = " + wf.getAdapter().getPositionByDate("2014-09-23"));
                    //wf.getListView().setSelection(wf.getAdapter().getPositionByDate("2014-09-23"));
                    //wf.getListView().post(new Runnable() {
                   //     @Override
                   //     public void run() {
                   //         wf.getListView().smoothScrollToPosition(10);
                    //    }
                   // });
                }


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // ==================================================================================================================================================================
    // Week
    // ==================================================================================================================================================================

    public static class WeekPagerAdapter extends FragmentStatePagerAdapter {
        private SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();
        private Context mContext;
        private Group mGroup;
        private int mWeeksCount;

        public WeekPagerAdapter(Context context, FragmentManager fm, Group group) {
            super(fm);
            mGroup = group;
            mContext = context;
            mWeeksCount = SqlGroupScheduleDB.getInstance(mContext, mGroup).getWeeksCount2();
            //ViewPager vp = vp;
            //vp.setAdapter(this);
            Log.d("TEST", "mWeeksCount = " + mWeeksCount);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new WeekFragment();
            Bundle args = new Bundle();
            args.putInt(WeekFragment.ARG_WEEK_NUMBER, i + 1);
            args.putString(WeekFragment.ARG_GROUP, new Gson().toJson(mGroup));
            //args.putString(WeekFragment.ARG_SCROLL_TO_DATE, ???);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position,fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            //if(registeredFragments.get())
            return registeredFragments.get(position);
        }

        @Override
        public int getCount() {
            return mWeeksCount;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "Неделя " + (position + 1);
        }
    }

    public static class WeekFragment extends ListFragment implements LoaderManager.LoaderCallbacks<ArrayList<ScheduleItem>> {
        public static final String ARG_WEEK = "ARG_WEEK";
        public static final String ARG_WEEK_NUMBER = "ARG_WEEK_NUMBER";
        public static final String ARG_GROUP = "ARG_GROUP";
        private static final int WEEK_LOADER_ID = 0;

        private Group mGroup;
        private int mWeekNumber;

        private WeekAdapter2 mAdapter;

        private boolean needScroll = false;
        private int scrollPosition = 0;
        private boolean loadFinished = false;

        public WeekAdapter2 getAdapter() {
            return mAdapter;
        }

        public void scrollToDate(String date) {
            scrollPosition = getAdapter().getPositionByDate(date);
            Log.d("TEST", "scrollPosition = " + scrollPosition);
            if(loadFinished) {
                needScroll = false;
                getListView().setSelection(scrollPosition);
                //getListView().post(new Runnable() {
                //    @Override
                //    public void run() {
                //        getListView().smoothScrollToPosition(scrollPosition);
                //   }
                //});
            } else {
                needScroll = true;
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mGroup = new Gson().fromJson(getArguments().getString(ARG_GROUP), Group.class);
            mWeekNumber = getArguments().getInt(ARG_WEEK_NUMBER);
            //mAdapter = new WeekAdapter(getActivity(), null);
            //setListAdapter(mAdapter);
            mAdapter = new WeekAdapter2(getActivity(), null);
            setListAdapter(mAdapter);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            //getListView().setFastScrollEnabled(true);
        }

        @Override
        public void onResume() {
            super.onResume();
            getLoaderManager().initLoader(WEEK_LOADER_ID, null, this);
            //setEmptyText("Занятий нет");
            //setSelection(2);
            //getListView().smoothScrollToPosition(5);
        }

        @Override
        public Loader<ArrayList<ScheduleItem>> onCreateLoader(int id, Bundle bundle) {
            switch (id) {
                case WEEK_LOADER_ID:
                    return new WeekLoader(getActivity(), mGroup, mWeekNumber);

                default:
                    Log.d("TEST", "Invalid loader id");
                    return null;
            }
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<ScheduleItem>> loader, ArrayList<ScheduleItem> items) {
            mAdapter.swapData(items);
            //getListView().smoothScrollToPosition(5);
            //getListView().smoothScrollByOffset(1000);
            //getListView().setSelection(10);

            //getListView().post(new Runnable() {
            ///    @Override
            //    public void run() {
            //        getListView().smoothScrollToPosition(10);
             //   }
            //});
            loadFinished = true;
            if(needScroll) {
                needScroll = false;
                getListView().post(new Runnable() {
                    @Override
                    public void run() {
                        getListView().smoothScrollToPosition(scrollPosition);
                    }
                });
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<ScheduleItem>> loader) {
            mAdapter.swapData(null);
        }

        private static class WeekLoader extends AsyncTaskLoader<ArrayList<ScheduleItem>> {
            private Group mGroup;
            private int mWeekNumber;
            private ArrayList<ScheduleItem> mData;

            public WeekLoader(Context context, Group group, int weekNumber) {
                super(context);
                mGroup = group;
                mWeekNumber = weekNumber;
            }

            @Override
            public void deliverResult(ArrayList<ScheduleItem> data) {
                mData = data;
                super.deliverResult(data);
            }

            @Override
            protected void onStartLoading() {
                if (mData != null) {
                    deliverResult(mData);
                }
                if (takeContentChanged() || mData == null) {
                    forceLoad();
                }
            }

            @Override
            public ArrayList<ScheduleItem> loadInBackground() {
                Log.d("TEST", "loadInBackground " + mWeekNumber);
                return SqlGroupScheduleDB.getInstance(getContext(), mGroup).getWeekByNumber2(mWeekNumber);
                /*
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                int k = Math.abs(mWeekNumber - 15);

                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                //Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday
                switch (c.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:
                        c.add(Calendar.DATE, 0 * k);
                        break;
                    case Calendar.TUESDAY:
                        c.add(Calendar.DATE, -1 * k);
                        break;
                    case Calendar.WEDNESDAY:
                        c.add(Calendar.DATE, -2 * k);
                        break;
                    case Calendar.THURSDAY:
                        c.add(Calendar.DATE, -3 * k);
                        break;
                    case Calendar.FRIDAY:
                        c.add(Calendar.DATE, -4 * k);
                        break;
                    case Calendar.SATURDAY:
                        c.add(Calendar.DATE, -5 * k);
                        break;
                    case Calendar.SUNDAY:
                        c.add(Calendar.DATE, -6 * k);
                        break;
                    default:
                        Log.d("TEST", "Invalid day of week");
                }
                String begin = sdf.format(c.getTime());
                c.add(Calendar.DATE, 7);
                String end = sdf.format(c.getTime());

                int weeksCount = SqlGroupScheduleDB.getInstance(getContext(), mGroup).getWeeksCount();
                Log.d("TEST", "weeks count = " + weeksCount);

                return SqlGroupScheduleDB.getInstance(getContext(), mGroup).getDays(begin, end);
                //return SqlGroupScheduleDB.getInstance(getContext(), mGroup).getDays("2014-09-01", "2014-09-07");
                */
            }
        }
    }
}

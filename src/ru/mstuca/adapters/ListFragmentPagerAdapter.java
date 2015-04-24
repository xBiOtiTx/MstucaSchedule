package ru.mstuca.adapters;


import android.app.Fragment;
import android.app.FragmentManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragmentPagerAdapter extends android.support.v13.app.FragmentStatePagerAdapter {
    private List<Fragment> mFragments;

    public ListFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        mFragments = fragments;
//        Map m = new HashMap();
//        m.isEmpty()
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}

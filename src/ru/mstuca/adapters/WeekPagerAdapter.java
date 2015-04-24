package ru.mstuca.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import java.util.List;

import ru.mstuca.fragments.GroupScheduleFragment;

import static ru.mstuca.fragments.GroupScheduleFragment.*;

// TODO
public class WeekPagerAdapter extends ListFragmentPagerAdapter {
    public WeekPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm, fragments);
    }
}

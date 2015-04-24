package ru.mstuca.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Евгений on 06.04.2015.
 */
public class ScrollableViewPager extends ViewPager {
    public static class ScrollablePagerAdapter extends FragmentStatePagerAdapter {
        public ScrollablePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return null;
        }

        @Override
        public int getCount() {
            return 0;
        }

        public void scroll() {
        }
    }

    public ScrollableViewPager(Context context) {
        super(context);
    }

    public ScrollableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


}

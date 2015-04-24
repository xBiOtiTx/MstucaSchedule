package ru.mstuca.adapters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ru.mstuca.R;
import ru.mstuca.model.ScheduleItem;

// TODO null pointer exception
// TODO add text for empty days
public class WeekAdapter extends BaseAdapter {
    private List<WeekItem> mWeekItems;
    private LayoutInflater mInflater;

    private static final int TYPES_COUNT = 2;

    private static final int TYPE_DAY_ITEM = 0;
    private static final int TYPE_DAY_HEADER = 1;
    // TYPE_DAY_EMPTY_ITEM
    // TYPE_EMPTY_DAY

    private static class WeekItem {
        int itemType;
        ScheduleItem item;
    }

    private static class ItemHolder {
        TextView tvNumber;
        TextView tvLessonTitle;
        TextView tvTeacherName;
        TextView tvLessonType;
        TextView tvSubGroup;
        TextView tvAuditory;
    }

    private static class HeaderHolder {
        TextView tvDayOfWeek;
        TextView tvDayOfMonth;
    }

    public WeekAdapter(Context context, ArrayList<ScheduleItem> items) {
        mWeekItems = new ArrayList<WeekItem>();
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(items);
    }

    public void setData(ArrayList<ScheduleItem> items) {
        mWeekItems.clear();
        if (items != null) {
            Collections.sort(items, new ScheduleItem.ScheduleItemComparator());
            for (ScheduleItem i : items) {
                if ((mWeekItems.size() == 0) || (i.getWeekDay() != mWeekItems.get(mWeekItems.size() - 1).item.getWeekDay())) {
                    WeekItem weekItem = new WeekItem();
                    weekItem.item = i;
                    weekItem.itemType = TYPE_DAY_HEADER;
                    mWeekItems.add(weekItem);
                    Log.d("TEST", "+header");
                }

                WeekItem weekItem = new WeekItem();
                weekItem.item = i;
                weekItem.itemType = TYPE_DAY_ITEM;
                mWeekItems.add(weekItem);
            }
        }
    }

    public void swapData(ArrayList<ScheduleItem> items) {
        setData(items);
        notifyDataSetChanged();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return mWeekItems.get(position).itemType == TYPE_DAY_ITEM;
    }

    public void fillDay(ItemHolder holder, ScheduleItem item) {
        holder.tvSubGroup.setText(Integer.toString(item.getSubGroup()));
        holder.tvTeacherName.setText(item.getTeacher());
        holder.tvNumber.setText(Integer.toString(item.getNumber()));
        holder.tvAuditory.setText(item.getAuditory());
        holder.tvLessonTitle.setText(item.getTitle());
        holder.tvLessonType.setText(Integer.toString(item.getType()));
    }

    public void fillHeader(HeaderHolder holder, ScheduleItem item) {
        holder.tvDayOfWeek.setText(Integer.toString(item.getWeekDay()));
        holder.tvDayOfMonth.setText(item.getDate());
    }

    @Override
    public int getCount() {
        return mWeekItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mWeekItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return mWeekItems.get(position).itemType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (convertView == null) {
            switch (viewType) {
                case TYPE_DAY_ITEM: {
                    ItemHolder holder = new ItemHolder();
                    convertView = mInflater.inflate(R.layout.layout_item_day0, null);
                    holder.tvAuditory = (TextView) convertView.findViewById(R.id.tvAuditory);
                    holder.tvLessonTitle = (TextView) convertView.findViewById(R.id.tvLessonTitle);
                    holder.tvLessonType = (TextView) convertView.findViewById(R.id.tvLessonType);
                    holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
                    holder.tvTeacherName = (TextView) convertView.findViewById(R.id.tvTeacherName);
                    holder.tvSubGroup = (TextView) convertView.findViewById(R.id.tvSubGroup);
                    convertView.setTag(holder);
                    fillDay(holder, ((WeekItem) getItem(position)).item);
                    break;
                }

                case TYPE_DAY_HEADER: {
                    HeaderHolder holder = new HeaderHolder();
                    convertView = mInflater.inflate(R.layout.layout_item_header0, null);
                    holder.tvDayOfMonth = (TextView) convertView.findViewById(R.id.tvDayOfMonth);
                    holder.tvDayOfWeek = (TextView) convertView.findViewById(R.id.tvDayOfWeek);
                    convertView.setTag(holder);
                    fillHeader(holder, ((WeekItem) getItem(position)).item);
                    break;
                }
                default:
                    Log.d("TEST", "Invalid view type");
            }
        } else {
            switch (viewType) {
                case TYPE_DAY_ITEM: {
                    ItemHolder holder = (ItemHolder) convertView.getTag();
                    fillDay(holder, ((WeekItem) getItem(position)).item);
                    break;
                }

                case TYPE_DAY_HEADER: {
                    HeaderHolder holder = (HeaderHolder) convertView.getTag();
                    fillHeader(holder, ((WeekItem) getItem(position)).item);
                    break;
                }
                default:
                    Log.d("TEST", "Invalid view type");
            }
        }
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return TYPES_COUNT;
    }
}

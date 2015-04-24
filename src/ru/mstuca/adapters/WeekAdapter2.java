package ru.mstuca.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import ru.mstuca.R;
import ru.mstuca.model.ELessonType;
import ru.mstuca.model.ScheduleItem;

public class WeekAdapter2 extends BaseAdapter {
    //========================================================================================================================================================================
    // Static fields
    //========================================================================================================================================================================
    private static final int TYPES_COUNT = 4;

    private static final int TYPE_DAY_ITEM = 0;
    private static final int TYPE_DAY_HEADER = 1;
    private static final int TYPE_DAY_EMPTY_ITEM = 2;
    private static final int TYPE_EMPTY_DAY = 3;

    private static final int MAX_ITEMS = 10;

    //========================================================================================================================================================================
    // Private fields
    //========================================================================================================================================================================

    private LayoutInflater mInflater;
    private List<WeekItem> mWeekItems = new ArrayList<WeekItem>();

    //========================================================================================================================================================================
    // Private classes
    //========================================================================================================================================================================

    private static class WeekItem {
        String mDate;
        int mType;
        int mNumber;
        ScheduleItem mItem;

        public static WeekItem createHeader(String date) {
            WeekItem result = new WeekItem();
            result.mDate = date;
            result.mType = TYPE_DAY_HEADER;
            return result;
        }

        public static WeekItem createItem(String date, ScheduleItem item) {
            WeekItem result = new WeekItem();
            result.mDate = date;
            result.mType = TYPE_DAY_ITEM;
            result.mItem = item;
            return result;
        }

        public static WeekItem createEmptyItem(String date, int number) {
            WeekItem result = new WeekItem();
            result.mDate = date;
            result.mType = TYPE_DAY_EMPTY_ITEM;
            result.mNumber = number;
            return result;
        }

        public static WeekItem createEmptyDay(String date) {
            WeekItem result = new WeekItem();
            result.mDate = date;
            result.mType = TYPE_EMPTY_DAY;
            return result;
        }
    }

    private static class HeaderHolder {
        TextView tvDayOfWeek;
        TextView tvDayOfMonth;
    }

    private static class ItemHolder {
        TextView tvNumber;
        TextView tvLessonTitle;
        TextView tvTeacherName;
        TextView tvLessonType;
        TextView tvSubGroup;
        TextView tvAuditory;
    }

    private static class EmptyItemHolder {
        TextView tvNumberEmptyItem;
    }

    //========================================================================================================================================================================
    // Constructor
    //========================================================================================================================================================================

    public WeekAdapter2(Context context, ArrayList<ScheduleItem> items) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        setData(items);
    }

    //========================================================================================================================================================================
    // Override methods
    //========================================================================================================================================================================

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        //return mWeekItems.get(position).mType == TYPE_DAY_ITEM;
        return mWeekItems.get(position).mType == TYPE_DAY_ITEM || mWeekItems.get(position).mType == TYPE_DAY_EMPTY_ITEM;
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
        return mWeekItems.get(position).mType;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (convertView == null) {
            switch (viewType) {
                case TYPE_DAY_HEADER: {
                    HeaderHolder holder = new HeaderHolder();
                    convertView = mInflater.inflate(R.layout.layout_item_header0, parent, false);
                    holder.tvDayOfMonth = (TextView) convertView.findViewById(R.id.tvDayOfMonth);
                    holder.tvDayOfWeek = (TextView) convertView.findViewById(R.id.tvDayOfWeek);
                    convertView.setTag(holder);
                    fillHeader(holder, ((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_DAY_ITEM: {
                    ItemHolder holder = new ItemHolder();
                    convertView = mInflater.inflate(R.layout.layout_item_day0, parent, false);
                    holder.tvAuditory = (TextView) convertView.findViewById(R.id.tvAuditory);
                    holder.tvLessonTitle = (TextView) convertView.findViewById(R.id.tvLessonTitle);
                    holder.tvLessonType = (TextView) convertView.findViewById(R.id.tvLessonType);
                    holder.tvNumber = (TextView) convertView.findViewById(R.id.tvNumber);
                    holder.tvTeacherName = (TextView) convertView.findViewById(R.id.tvTeacherName);
                    holder.tvSubGroup = (TextView) convertView.findViewById(R.id.tvSubGroup);
                    convertView.setTag(holder);
                    fillItem(holder, ((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_DAY_EMPTY_ITEM: {
                    EmptyItemHolder holder = new EmptyItemHolder();
                    convertView = mInflater.inflate(R.layout.layout_empty_item, parent, false);
                    holder.tvNumberEmptyItem = (TextView) convertView.findViewById(R.id.tvNumber);
                    convertView.setTag(holder);
                    fillEmptyItem(holder,((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_EMPTY_DAY: {
                    convertView = mInflater.inflate(R.layout.layout_empty_day, parent, false);
                    break;
                }

                default:
                    Log.d("TEST", "Invalid view type");
            }
        } else {
            switch (viewType) {
                case TYPE_DAY_HEADER: {
                    HeaderHolder holder = (HeaderHolder) convertView.getTag();
                    fillHeader(holder, ((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_DAY_ITEM: {
                    ItemHolder holder = (ItemHolder) convertView.getTag();
                    fillItem(holder, ((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_DAY_EMPTY_ITEM: {
                    EmptyItemHolder holder = (EmptyItemHolder) convertView.getTag();
                    fillEmptyItem(holder, ((WeekItem) getItem(position)));
                    break;
                }

                case TYPE_EMPTY_DAY: {
                    // do nothing.
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

    //========================================================================================================================================================================
    // Private methods
    //========================================================================================================================================================================

    private void createDay(String date, ScheduleItem[] items) {
        mWeekItems.add(WeekItem.createHeader(date));

        int realItems = 0;
        for (ScheduleItem i : items) {
            if (i != null) {
                realItems++;
            }
        }
        if (realItems == 0) {
            mWeekItems.add(WeekItem.createEmptyDay(date));
        } else {
            int maxNumber = 0;
            for (ScheduleItem i : items) {
                if (i != null) {
                    maxNumber = Math.max(maxNumber, i.getNumber());
                }
            }

            for (int i = 0; i < maxNumber; i++) {
                if (items[i] != null) {
                    mWeekItems.add(WeekItem.createItem(date, items[i]));
                } else {
                    mWeekItems.add(WeekItem.createEmptyItem(date,i+1));
                }
            }
        }
    }

    private void fillHeader(HeaderHolder holder, WeekItem item) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = sdf.parse(item.mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf = new SimpleDateFormat("EEEE");

        holder.tvDayOfWeek.setText(sdf.format(d));
        holder.tvDayOfMonth.setText(item.mDate);
    }

    private void fillItem(ItemHolder holder, WeekItem item) {
        if(item.mItem.getSubGroup()!=0) {
            holder.tvSubGroup.setText("подгруппа " + Integer.toString(item.mItem.getSubGroup()));
        } else {
            holder.tvSubGroup.setText("");
        }
        holder.tvTeacherName.setText(item.mItem.getTeacher());
        holder.tvNumber.setText(Integer.toString(item.mItem.getNumber()));
        holder.tvAuditory.setText(item.mItem.getAuditory());
        holder.tvLessonTitle.setText(item.mItem.getTitle());
        holder.tvLessonType.setText(ELessonType.fromStatus(item.mItem.getType()).getTitle());

    }

    private void fillEmptyItem(EmptyItemHolder holder, WeekItem item) {
        holder.tvNumberEmptyItem.setText(Integer.toString(item.mNumber));
    }

    //========================================================================================================================================================================
    // Public methods
    //========================================================================================================================================================================

    public void setData(ArrayList<ScheduleItem> items) {
        mWeekItems.clear();
        if (items != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Collections.sort(items, new ScheduleItem.ScheduleItemComparator());

            //List<ScheduleItem> mMondayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mTuesdayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mWednesdayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mThursdayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mFridayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mSaturdayItems = new ArrayList<ScheduleItem>();
            //List<ScheduleItem> mSundayItems = new ArrayList<ScheduleItem>();

            ScheduleItem mMondayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mTuesdayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mWednesdayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mThursdayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mFridayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mSaturdayItems[] = new ScheduleItem[MAX_ITEMS];
            ScheduleItem mSundayItems[] = new ScheduleItem[MAX_ITEMS];

            for (ScheduleItem i : items) {
                Calendar c = Calendar.getInstance();
                try {
                    c.setTime(sdf.parse(i.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                switch (c.get(Calendar.DAY_OF_WEEK)) {
                    case Calendar.MONDAY:
                        //mMondayItems.add(i);
                        mMondayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.TUESDAY:
                        //mTuesdayItems.add(i);
                        mTuesdayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.WEDNESDAY:
                        //mWednesdayItems.add(i);
                        mWednesdayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.THURSDAY:
                        //mThursdayItems.add(i);
                        mThursdayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.FRIDAY:
                        //mFridayItems.add(i);
                        mFridayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.SATURDAY:
                        //mSaturdayItems.add(i);
                        mSaturdayItems[i.getNumber()-1] = i;
                        break;

                    case Calendar.SUNDAY:
                        //mSundayItems.add(i);
                        mSundayItems[i.getNumber()-1] = i;
                        break;

                    default:
                        Log.d("TEST", "Invalid day of week");
                }
            }

            Calendar c = Calendar.getInstance();
            try {
                c.setTime(sdf.parse(items.get(0).getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            while (c.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
                c.add(Calendar.DATE, -1);
            }

            c.add(Calendar.DATE, 0);
            createDay(sdf.format(c.getTime()), mMondayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mTuesdayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mWednesdayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mThursdayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mFridayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mSaturdayItems);

            c.add(Calendar.DATE, 1);
            createDay(sdf.format(c.getTime()), mSundayItems);
        }
    }

    public void swapData(ArrayList<ScheduleItem> items) {
        setData(items);
        notifyDataSetChanged();
    }

    public int getPositionByDate(String date) {
        for (int i = 0; i < mWeekItems.size(); i++) {
            Log.d("TEST",mWeekItems.get(i).mDate);
            if (mWeekItems.get(i).mDate.equals(date)) {
                return i;
            }
        }
        return -1;
    }
}

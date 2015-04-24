package ru.mstuca.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GroupSchedule {
	private List<ScheduleItem> mData;

	public List<ScheduleItem> getData() {
		return mData;
	}

	public void setData(List<ScheduleItem> data) {
		this.mData = data;
	}

    public static void prepare(GroupSchedule schedule) {
        ArrayList arrayList;

        Collections.sort(schedule.mData, new ScheduleItem.ScheduleItemComparator());
        //String currentDate = schedule.mData.get(0).getDate();
        //int currentNumber = schedule.mData.get(0).getNumber();
        for (ScheduleItem i: schedule.mData) {
            // если в этот день пары начинаются не с первой, то заполняем до этой пары пустышками
            for (int j = 0; j < i.getNumber(); j++) {

            }
        }
    }
}

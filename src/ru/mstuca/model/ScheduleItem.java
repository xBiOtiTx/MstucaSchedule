package ru.mstuca.model;

import java.util.Comparator;

public class ScheduleItem {
    private Integer mNumber; // номер пары
    private Integer mWeekDay; // день недели // remove in lesson
    private String mTitle; // Название предмета
    private String mDate; // Дата проведения занятия // remove in lesson
    private String mAuditory; // Аудитория
    private Integer mType; // тип // ELessonType?
    private String mTeacher; // Имя преподавателя
    private Integer mSubGroup; // Название подгруппы

    public ScheduleItem(Integer mNumber, Integer mWeekDay, String mTitle, String mDate, String mAuditory, Integer mType, String mTeacher,
                        Integer mSubGroup) {
        super();
        this.mNumber = mNumber;
        this.mWeekDay = mWeekDay;
        this.mTitle = mTitle;
        this.mDate = mDate;
        this.mAuditory = mAuditory;
        this.mType = mType;
        this.mTeacher = mTeacher;
        this.mSubGroup = mSubGroup;
    }

    public Integer getNumber() {
        return mNumber;
    }

    public Integer getWeekDay() {
        return mWeekDay;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        return mDate;
    }

    public String getAuditory() {
        return mAuditory;
    }

    public Integer getType() {
        return mType;
    }

    public String getTeacher() {
        return mTeacher;
    }

    public Integer getSubGroup() {
        return mSubGroup;
    }

    public static class ScheduleItemComparator implements Comparator<ScheduleItem> {
        @Override
        public int compare(ScheduleItem lhs, ScheduleItem rhs) {

            int weekCompareResult = lhs.getWeekDay() - rhs.getWeekDay();
            if (weekCompareResult != 0) {
                return weekCompareResult;
            }

            int numberCompareResult = lhs.getNumber() - rhs.getNumber();
            if (numberCompareResult != 0) {
                return numberCompareResult;
            }

            return 0;
        }
    }
}

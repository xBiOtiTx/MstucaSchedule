package ru.mstuca.model;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Week {
    private int mWeekNumber; // Номер недели. Так же определяей её тип(верхняя/нижняя)
    private Date mBegin; // Дата понедельника
    private Date mEnd; // Дата воскресенья

    private Day mMonday; // Понедельник
    private Day mTuesday; // Вторник
    private Day mWednesday; // Среда
    private Day mThursday; // Четверг
    private Day mFriday; // Пятница
    private Day mSaturday; // Суббота
    private Day mSunday; // Воскресенье

    public Week(int weekNumber, List<Day> days) {
        this.mWeekNumber = weekNumber;
        for (Day day : days) {
            switch (day.getDayOfWeek()) {
                case MONDAY:
                    mMonday = day;
                    break;

                case TUESDAY:
                    mTuesday = day;
                    break;

                case WEDNESDAY:
                    mWednesday = day;
                    break;

                case THURSDAY:
                    mThursday = day;
                    break;

                case FRIDAY:
                    mFriday = day;
                    break;

                case SATURDAY:
                    mSaturday = day;
                    break;

                case SUNDAY:
                    mSunday = day;
                    break;
            }
        }
    }

    public Week(int mWeekNumber, Day mMonday, Day mTuesday, Day mWednesday, Day mThursday, Day mFriday, Day mSaturday, Day mSunday) {
        this.mWeekNumber = mWeekNumber;
        this.mMonday = mMonday;
        this.mTuesday = mTuesday;
        this.mWednesday = mWednesday;
        this.mThursday = mThursday;
        this.mFriday = mFriday;
        this.mSaturday = mSaturday;
        this.mSunday = mSunday;
    }

    public int getWeekNumber() {
        return mWeekNumber;
    }

    public void setWeekNumber(int weekNumber) {
        this.mWeekNumber = weekNumber;
    }

    public Date getBegin() {
        return mBegin;
    }

    public void setBegin(Date begin) {
        this.mBegin = begin;
    }

    public Date getEnd() {
        return mEnd;
    }

    public void setEnd(Date end) {
        this.mEnd = end;
    }

    public Day getMonday() {
        return mMonday;
    }

    public void setMonday(Day monday) {
        this.mMonday = monday;
    }

    public Day getTuesday() {
        return mTuesday;
    }

    public void setTuesday(Day tuesday) {
        this.mTuesday = tuesday;
    }

    public Day getWednesday() {
        return mWednesday;
    }

    public void setWednesday(Day wednesday) {
        this.mWednesday = wednesday;
    }

    public Day getThursday() {
        return mThursday;
    }

    public void setThursday(Day thursday) {
        this.mThursday = thursday;
    }

    public Day getFriday() {
        return mFriday;
    }

    public void setFriday(Day friday) {
        this.mFriday = friday;
    }

    public Day getSaturday() {
        return mSaturday;
    }

    public void setSaturday(Day saturday) {
        this.mSaturday = saturday;
    }

    public Day getSunday() {
        return mSunday;
    }

    public void setSunday(Day sunday) {
        this.mSunday = sunday;
    }

    public static class WeekComparator implements Comparator<Week> {
        @Override
        public int compare(Week lhs, Week rhs) {
            return lhs.getWeekNumber() - rhs.getWeekNumber();
        }
    }
}

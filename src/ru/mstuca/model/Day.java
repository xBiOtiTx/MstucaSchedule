package ru.mstuca.model;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Day {
    private Date mDate; // Дата дня недели
    private EDayOfWeek mDayOfWeek; // День недели
    private int mFirstLesson; // Номер первой пары
    private int mLastLesson; // Номер последней пары
    private List<ScheduleItem> mLessons; // пары //List<Lesson>

    private void setDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.TUESDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.WEDNESDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.THURSDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.FRIDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.SATURDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            case Calendar.SUNDAY:
                mDayOfWeek = EDayOfWeek.MONDAY;
                break;

            default:
                mDayOfWeek = EDayOfWeek.UNKNOWN;
                break;
        }
    }

    public EDayOfWeek getDayOfWeek() {
        return mDayOfWeek;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public int getFirstLesson() {
        return mFirstLesson;
    }

    public void setFirstLesson(int mFirstLesson) {
        this.mFirstLesson = mFirstLesson;
    }

    public int getLastLesson() {
        return mLastLesson;
    }

    public void setLastLesson(int mLastLesson) {
        this.mLastLesson = mLastLesson;
    }

    public List<ScheduleItem> getLessons() {
        return mLessons;
    }

    public void setLessons(List<ScheduleItem> mLessons) {
        this.mLessons = mLessons;
    }

    public ScheduleItem getLessonByNumber(int number) {
        for (ScheduleItem i : mLessons) {
            if (i.getNumber() == number) {
                return i;
            }
        }
        return null;
    }
}

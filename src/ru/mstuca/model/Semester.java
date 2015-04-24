package ru.mstuca.model;

import java.util.Date;
import java.util.List;

public class Semester {
    private Date mDateBegin; //дата первого дня с занятиями
    private Date mDateEnd; // дата последнего дня с занятиями

    private List<Week> mWeeks; // недели

    public Semester(List<Week> weeks) {
        this.mWeeks = weeks;
        setDates(weeks);
    }

    private void setDates(List<Week> weeks) {
        if (weeks != null && !weeks.isEmpty()) {
            mDateBegin = weeks.get(0).getBegin();
            mDateEnd = weeks.get(0).getEnd();
            for (Week week : weeks) {
                if (week.getBegin().before(mDateBegin)) {
                    mDateBegin = week.getBegin();
                }
                if (week.getEnd().after(mDateEnd)) {
                    mDateEnd = week.getEnd();
                }
            }
        }
    }

    public Date getDateBegin() {
        return mDateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.mDateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return mDateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.mDateEnd = dateEnd;
    }

    public List<Week> getWeeks() {
        return mWeeks;
    }

    public void setWeeks(List<Week> weeks) {
        this.mWeeks = weeks;
        setDates(weeks);
    }
}

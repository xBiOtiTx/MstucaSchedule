package ru.mstuca.model;

public class ScheduleItem {
	private Integer mNumber; // номер пары
	private Integer mWeekDay; // день недели
	private String mTitle; // название предмета
	private String mDate; // дата проведения
	private String mAuditory; // аудитория
	private Integer mType; // тип занятия: практика, лекция, семинар, лаб.раб.
	private String mTeacher; // имя преподавателя кто ведёт
	private Integer mSubGroup; // номер подгруппы. 0 - для всех

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

	public Integer getmNumber() {
		return mNumber;
	}

	public Integer getmWeekDay() {
		return mWeekDay;
	}

	public String getmTitle() {
		return mTitle;
	}

	public String getmDate() {
		return mDate;
	}

	public String getmAuditory() {
		return mAuditory;
	}

	public Integer getmType() {
		return mType;
	}

	public String getmTeacher() {
		return mTeacher;
	}

	public Integer getmSubGroup() {
		return mSubGroup;
	}
}

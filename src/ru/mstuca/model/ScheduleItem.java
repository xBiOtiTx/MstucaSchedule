package ru.mstuca.model;

public class ScheduleItem {
	private Integer mNumber; // ����� ����
	private Integer mWeekDay; // ���� ������
	private String mTitle; // �������� ��������
	private String mDate; // ���� ����������
	private String mAuditory; // ���������
	private Integer mType; // ��� �������: ��������, ������, �������, ���.���.
	private String mTeacher; // ��� ������������� ��� ����
	private Integer mSubGroup; // ����� ���������. 0 - ��� ����

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

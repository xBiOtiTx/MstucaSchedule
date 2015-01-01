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


}

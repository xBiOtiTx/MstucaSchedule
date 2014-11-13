package ru.mstuca.model;

public class Lesson {
	private String mSubGroupTitle; // �������� ��������� // null - ��� ��� ""?
	private String mTitle; // �������� ��������
	private String mTeacher; // ��� ������������� ��� ����
	private ELessonType mType; // ��� �������: ��������, ������, �������, ���.���.
	private String mTypeTitle; // �������� ���� �������
	private String mRoom; // ����� ���������

	public Lesson(String mTitle, String mTeacher, ELessonType mType, String mRoom) {
		super();
		this.mTitle = mTitle;
		this.mTeacher = mTeacher;
		this.mType = mType;
		this.mRoom = mRoom;
		this.mTypeTitle = mType.getTitle();
	}

	public String getTitle() {
		return mTitle;
	}

	public String getTeacher() {
		return mTeacher;
	}

	public ELessonType getType() {
		return mType;
	}

	public String getTypeTitle() {
		return mTypeTitle;
	}

	public String getRoom() {
		return mRoom;
	}
}

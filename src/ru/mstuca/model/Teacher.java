package ru.mstuca.model;

public class Teacher {
	private int id; // id �������������
	private String title; // ���(+�������) �������������

	public Teacher(int mId, String mTitle) {
		super();
		this.id = mId;
		this.title = mTitle;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
}

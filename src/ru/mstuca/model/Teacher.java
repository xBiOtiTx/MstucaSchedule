package ru.mstuca.model;

public class Teacher {
	private int id; // id преподавателя
	private String title; // Имя(+степень) преподавателя

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

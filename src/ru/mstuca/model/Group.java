package ru.mstuca.model;

public class Group {
	private int id; // id ������ // TODO int -> Long
	private String title; // �������� ������

	public Group(int id, String title) {
		super();
		this.id = id;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}
}

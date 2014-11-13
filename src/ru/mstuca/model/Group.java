package ru.mstuca.model;

public class Group {
	private int id; // id группы // TODO int -> Long
	private String title; // название группы

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

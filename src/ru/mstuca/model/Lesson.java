package ru.mstuca.model;

public class Lesson {
	private String mSubGroupTitle; // Название подгруппы // null - все или ""?
	private String mTitle; // название предмета
	private String mTeacher; // имя преподавателя кто ведёт
	private ELessonType mType; // тип занятия: практика, лекция, семинар, лаб.раб.
	private String mTypeTitle; // Название типа занятия
	private String mRoom; // номер аудитории

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

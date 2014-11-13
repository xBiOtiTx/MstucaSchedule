package ru.mstuca.model;

public enum ELessonType {
	LECTURE, // лекция
	PRACTICAL, // пр зан
	LAB, // лаб раб
	SEMINAR; // семинар
	// TODO EMPTY

	public String getTitle() {
		// TODO брать строки откуда-то из вне
		switch (this) {
		case LAB:
			return "Лаб.раб.";

		case LECTURE:
			return "Лекция";

		case PRACTICAL:
			return "Пр.Зан.";

		case SEMINAR:
			return "Семинар";

		}

		return ""; // а хз
	}
}

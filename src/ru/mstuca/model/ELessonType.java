package ru.mstuca.model;

public enum ELessonType {
	LECTURE, // ������
	PRACTICAL, // �� ���
	LAB, // ��� ���
	SEMINAR; // �������
	// TODO EMPTY

	public String getTitle() {
		// TODO ����� ������ ������-�� �� ���
		switch (this) {
		case LAB:
			return "���.���.";

		case LECTURE:
			return "������";

		case PRACTICAL:
			return "��.���.";

		case SEMINAR:
			return "�������";

		}

		return ""; // � ��
	}
}

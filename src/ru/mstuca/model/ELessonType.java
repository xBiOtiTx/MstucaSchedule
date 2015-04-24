package ru.mstuca.model;

import android.util.Log;

public enum ELessonType {
    LECTURE, // лекция
    PRACTICAL, // пр зан
    LAB, // лаб раб
    SEMINAR, // семинар
    EMPTY; // пусто
    // ERROR; // ошибка

    public static final int STATUS_LECTURE = 1;
    public static final int STATUS_PRACTICAL = 2;
    public static final int STATUS_LAB = 3;
    public static final int STATUS_SEMINAR = 4;
    public static final int STATUS_EMPTY = 0;

    public static int toStatus(ELessonType lesson) {
        switch (lesson) {
            case LECTURE:
                return STATUS_LECTURE;

            case PRACTICAL:
                return STATUS_PRACTICAL;

            case LAB:
                return STATUS_LAB;

            case SEMINAR:
                return STATUS_SEMINAR;

            default:
                return STATUS_EMPTY;
        }
    }

    public static ELessonType fromStatus(int status) {
        switch (status) {
            case STATUS_LECTURE :
                return LECTURE;

            case STATUS_PRACTICAL :
                return PRACTICAL;

            case STATUS_LAB :
                return LAB;

            case STATUS_SEMINAR :
                return SEMINAR;

            default :
                return EMPTY;
        }
    }

    public String getTitle() {
        // TODO Take the strings from the resources
        switch (this) {
            case LAB:
                return "Лаб.раб.";

            case LECTURE:
                return "Лекция";

            case PRACTICAL:
                return "Пр.Зан.";

            case SEMINAR:
                return "Семинар";

            case EMPTY:
                return "Пусто";

            default:
                Log.d("TEST", "Invalid ELessonType");
                return "";
        }
    }
}

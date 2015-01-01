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

    @Override
    public boolean equals(Object o) {
        if(o instanceof Group) {
            Group tmp = (Group) o;
            return (tmp.id==this.id);
        }
        return false;
    }
}

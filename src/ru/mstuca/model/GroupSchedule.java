package ru.mstuca.model;

import java.util.List;

public class GroupSchedule {
	private List<ScheduleItem> groups;

	public List<ScheduleItem> getData() {
		return groups;
	}

	public void setData(List<ScheduleItem> groups) {
		this.groups = groups;
	}
}

package ru.mstuca.fragments;

import ru.mstuca.R;
import ru.mstuca.model.Teacher;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TeacherScheduleFragment extends BaseFragment {
	public TeacherScheduleFragment() {
	}

	public static TeacherScheduleFragment newInstance(Teacher teacher) {
		TeacherScheduleFragment fragment = new TeacherScheduleFragment();
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_schedule_teacher, container, false);

		ListView lvSchedule = (ListView) rootView.findViewById(R.id.lvSchedule);

		return rootView;
	}
}

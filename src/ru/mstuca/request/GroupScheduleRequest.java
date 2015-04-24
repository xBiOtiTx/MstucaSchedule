package ru.mstuca.request;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import ru.mstuca.model.Group;
import ru.mstuca.model.GroupSchedule;
import ru.mstuca.model.ListTeachers;
import ru.mstuca.model.ScheduleItem;
import ru.mstuca.model.Teacher;
import android.net.Uri;
import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;

public class GroupScheduleRequest extends SpiceRequest<GroupSchedule> {
    private Group mGroup;

	public GroupScheduleRequest(Group group) {
		super(GroupSchedule.class);
		this.mGroup = group;
	}

	@Override
	public GroupSchedule loadDataFromNetwork() throws Exception {
		Log.d("TEST", "loadDataFromNetwork");

        Log.d("TEST", "http://r.mstuca.me/get_semester_schedule/group" + mGroup.getId());
		Uri.Builder uriBuilder = Uri.parse("http://r.mstuca.me/get_semester_schedule/group" + mGroup.getId()).buildUpon();
		String url = uriBuilder.build().toString();

		HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
		String result = IOUtils.toString(urlConnection.getInputStream());
		urlConnection.disconnect();

		GroupSchedule data = new GroupSchedule();
		List<ScheduleItem> array = new ArrayList<ScheduleItem>();
		JSONArray json = new JSONArray(result);
		for (int i = 0; i < json.length(); i++) {
			JSONObject jsonTemp = json.getJSONObject(i);

			int number = jsonTemp.getInt("number");
			int weekDay = jsonTemp.getInt("weekDay");
			String title = jsonTemp.getString("title");
			String date = jsonTemp.getString("date");
			String auditory = jsonTemp.getString("auditory");
			int type = jsonTemp.getInt("type");
			String teacher = jsonTemp.getString("teacher");
			int subGroup = jsonTemp.getInt("subGroup");

			ScheduleItem item = new ScheduleItem(number, weekDay, title, date, auditory, type, teacher, subGroup);
			array.add(item);
		}
		data.setData(array);

		return data;
	}

}

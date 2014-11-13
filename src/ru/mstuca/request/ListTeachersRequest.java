package ru.mstuca.request;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import ru.mstuca.model.ListTeachers;
import ru.mstuca.model.Teacher;
import android.net.Uri;
import android.util.Log;

import com.octo.android.robospice.request.SpiceRequest;

public class ListTeachersRequest extends SpiceRequest<ListTeachers> {

	public ListTeachersRequest() {
		super(ListTeachers.class);
	}

	@Override
	public ListTeachers loadDataFromNetwork() throws Exception {
		Log.d("TEST", "loadDataFromNetwork");

		Uri.Builder uriBuilder = Uri.parse("http://r.mstuca.me/get_teachers_list/").buildUpon();
		String url = uriBuilder.build().toString();

		HttpURLConnection urlConnection = (HttpURLConnection) new URL(url).openConnection();
		String result = IOUtils.toString(urlConnection.getInputStream());
		urlConnection.disconnect();

		ListTeachers data = new ListTeachers();
		List<Teacher> array = new ArrayList<Teacher>();
		JSONArray json = new JSONArray(result);
		for (int i = 0; i < json.length(); i++) {
			JSONObject jsonTemp = json.getJSONObject(i);

			int id = jsonTemp.getInt("id");
			String title = jsonTemp.getString("name");

			Teacher item = new Teacher(id, title);
			array.add(item);
		}
		data.setData(array);

		return data;
	}

}

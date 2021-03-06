package ru.mstuca.service;

import ru.mstuca.model.GroupSchedule;
import ru.mstuca.model.ListGroups;
import ru.mstuca.model.ListTeachers;
import ru.mstuca.persistance.GroupSchedulePersister;
import ru.mstuca.persistance.ListGroupsPersister;
import ru.mstuca.persistance.ListTeachersPersister;

import android.app.Application;

import com.octo.android.robospice.SpiceService;
import com.octo.android.robospice.persistence.CacheManager;

public class MstucaSpiceService extends SpiceService {

	@Override
	public CacheManager createCacheManager(Application application) {
		CacheManager cacheManager = new CacheManager();

		ListGroupsPersister myListGroupsPersister = new ListGroupsPersister(application, ListGroups.class);
		ListTeachersPersister myListTeachersPersister = new ListTeachersPersister(application, ListTeachers.class);
        GroupSchedulePersister groupSchedulePersister = new GroupSchedulePersister(application, GroupSchedule.class);

		cacheManager.addPersister(myListGroupsPersister);
		cacheManager.addPersister(myListTeachersPersister);
        cacheManager.addPersister(groupSchedulePersister);
		return cacheManager;
	}
}

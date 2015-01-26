package com.maina.formdatav2.repository.impl;

import java.util.List;

import android.database.Cursor;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.DformRespondentTypeE;
import com.maina.formdatav2.repository.IDFormRespondentTypeRepository;
import com.maina.formdatav2.repository.RepositoryBase;


public class DFormRespondentTypeRepository extends RepositoryBase implements IDFormRespondentTypeRepository {
	
	public DFormRespondentTypeRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public void setClass() {
		DataClass = DformRespondentTypeE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

	@Override
	public Cursor getByFormId(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Name as name FROM DformRespondentTypeE frt";
		return executeQuery(sql, params, "frt", true);
	}

}

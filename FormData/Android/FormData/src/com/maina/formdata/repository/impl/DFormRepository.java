package com.maina.formdata.repository.impl;

import java.util.List;

import android.database.Cursor;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformE;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.RepositoryBase;

public class DFormRepository extends RepositoryBase implements IDFormRepository {

	public DFormRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public void setClass() {
		DataClass = DformE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public Cursor getForms(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Name as name from DformE df";
		return executeQuery(sql, params, "df", true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getFormCount() throws Exception {
		return dataManager.publicDao(DataClass).queryForAll().size();
	}

}

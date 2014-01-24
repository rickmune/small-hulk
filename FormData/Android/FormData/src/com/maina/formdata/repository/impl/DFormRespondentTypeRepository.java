package com.maina.formdata.repository.impl;

import java.util.List;

import android.database.Cursor;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformRespondentTypeE;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.RepositoryBase;

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
		this.dataManager = dataManager;
	}

	@Override
	public Cursor getByFormId(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Name as name FROM DformRespondentTypeE frt";
		return executeQuery(sql, params, "frt", true);
	}

}

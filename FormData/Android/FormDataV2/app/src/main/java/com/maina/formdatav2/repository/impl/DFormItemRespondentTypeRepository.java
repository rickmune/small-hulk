package com.maina.formdatav2.repository.impl;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.DformItemRespondentTypeE;
import com.maina.formdatav2.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdatav2.repository.RepositoryBase;

import java.util.List;
import java.util.UUID;


public class DFormItemRespondentTypeRepository extends RepositoryBase implements IDFormItemRespondentTypeRepository {

	
	public DFormItemRespondentTypeRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public void setClass() {
		DataClass =  DformItemRespondentTypeE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DformItemRespondentTypeE> getByFormItemId(UUID formItemId) throws Exception {
		return getDataManager().publicDao(DataClass).queryForEq("FormItemId", formItemId);
	}

}

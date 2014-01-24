package com.maina.formdata.repository.impl;

import java.util.List;
import java.util.UUID;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformItemRespondentTypeE;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.RepositoryBase;

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
		this.dataManager = dataManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DformItemRespondentTypeE> getByFormItemId(UUID formItemId) throws Exception {
		return dataManager.publicDao(DataClass).queryForEq("FormItemId", formItemId);
	}

}

package com.maina.formdata.repository.impl;

import java.util.List;
import java.util.UUID;

import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformItemE;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.RepositoryBase;
import com.maina.formdata.utils.SortedArrayList;

public class DFormItemRepository extends RepositoryBase implements IDFormItemRepository {

	IDFormItemRespondentTypeRepository formItemRespondentTypes;
	
	public DFormItemRepository(IDataManager dataManager, IDFormItemRespondentTypeRepository _formItemRespondentTypes) {
		setClass();
		setDataManager(dataManager);
		formItemRespondentTypes = _formItemRespondentTypes;
	}

	@Override
	public void setClass() {
		DataClass = DformItemE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DformItemE> getByFormId(UUID formId) throws Exception {
		Log.d("DFormItemRepository", "dataManager == null): "+(dataManager == null));
		List<DformItemE> dformItemEs = dataManager.publicDao(DataClass).queryForEq(DformItemE.DformEID, formId);
		SortedArrayList arrayList = new SortedArrayList();
		for(DformItemE itemE : dformItemEs){
			itemE.setFormItemRespondentTypes(formItemRespondentTypes.getByFormItemId(itemE.getId()));
			arrayList.insertSorted(itemE);
		}
		return arrayList;
	}

}

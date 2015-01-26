package com.maina.formdatav2.repository.impl;

import java.util.List;
import java.util.UUID;

import android.util.Log;

import com.maina.formdatav2.Utils.SortedArrayList;
import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.DformItemE;
import com.maina.formdatav2.repository.IDFormItemRepository;
import com.maina.formdatav2.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdatav2.repository.RepositoryBase;

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
		setData(dataManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DformItemE> getByFormId(UUID formId) throws Exception {
		Log.d("DFormItemRepository", "dataManager == null): "+(getDataManager() == null));
		List<DformItemE> dformItemEs = getDataManager().publicDao(DataClass).queryForEq(DformItemE.DformEID, formId);
		SortedArrayList arrayList = new SortedArrayList();
		for(DformItemE itemE : dformItemEs){
			itemE.setFormItemRespondentTypes(formItemRespondentTypes.getByFormItemId(itemE.getId()));
            Log.d("DFormItemRepository", "itemE: " + itemE.toString());
			arrayList.insertSorted(itemE);
		}
		return arrayList;
	}

}

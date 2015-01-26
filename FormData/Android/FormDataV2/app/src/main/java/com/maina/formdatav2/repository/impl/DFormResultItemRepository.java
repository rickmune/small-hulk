package com.maina.formdatav2.repository.impl;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.DformResultItemE;
import com.maina.formdatav2.repository.IDFormResultItemRepository;
import com.maina.formdatav2.repository.RepositoryBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class DFormResultItemRepository extends RepositoryBase implements IDFormResultItemRepository {

	
	public DFormResultItemRepository(IDataManager dataManager) {
		setDataManager(dataManager);
		setClass();
	}

	@Override
	public void setClass() {
		DataClass = DformResultItemE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

	@Override
	public List<DformResultItemE> getByResultId(UUID resultId) throws Exception {
		List<DformResultItemE> list = getDataManager().publicDao(DformResultItemE.class)
				.queryForEq("DformResultE_id", resultId);
		List<DformResultItemE> listFinal = new ArrayList<DformResultItemE>(); 
		for(DformResultItemE itemE : list){
			String ans = itemE.getItemAnswer();
			List<String> myList = new ArrayList<String>();
			Collections.addAll(myList, ans.split(","));
			itemE.setFormItemAnswer(myList);
			listFinal.add(itemE);
		}
		return listFinal;
	}

}

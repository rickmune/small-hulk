package com.maina.formdata.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformResultItemE;
import com.maina.formdata.repository.IDFormResultItemRepository;
import com.maina.formdata.repository.RepositoryBase;

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

package com.maina.formdata.repository.impl;

import java.util.List;

import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.repository.IDFormResultItemRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.RepositoryBase;

public class DFormResultRepository extends RepositoryBase implements IDFormResultRepository {

	IDFormResultItemRepository resultItemRepository;
	
	public DFormResultRepository(IDataManager dataManager, IDFormResultItemRepository _resultItemRepository) {
		setClass();
		setDataManager(dataManager);
		resultItemRepository = _resultItemRepository;
	}

	@Override
	public void setClass() {
		DataClass = DformResultE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public int Saveresult(DformResultE dformResultE) throws Exception {
		DformResultE resultE = dataManager.save(dformResultE, DataClass);
		Log.d("DFormResultRepository", "Saveresult: "+resultE.getId());
		int x = resultItemRepository.saveBatch(dformResultE.getFormResultItem());
		Log.d("DFormResultRepository", "Saveresult items: "+x);
		return 0;
	}

	@Override
	public DformResultE getReadyToSend() throws Exception {
		List<DformResultE> list = dataManager.publicDao(DataClass).queryForEq("Sent", false);
		for(DformResultE resultE : list){
			if(resultE.isDone()){
				resultE.setFormResultItem(resultItemRepository.getByResultId(resultE.getId()));
				return resultE;
			}
		}
		return null;
	}

}

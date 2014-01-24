package com.maina.formdata.repository.impl;

import java.util.List;

import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.repository.IDFormResultItemRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.RepositoryBase;

/**
 * @author Patrick
 *
 */
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

	@SuppressWarnings("unchecked")
	@Override
	public int Saveresult(DformResultE dformResultE) throws Exception {
		DformResultE resultE = dataManager.save(dformResultE, DataClass);
		Log.d("DFormResultRepository", "Saveresult: "+resultE.getId());
		int x = resultItemRepository.saveBatch(dformResultE.getFormResultItem());
		Log.d("DFormResultRepository", "Saveresult items: "+x);
		return 0;
	}

	@SuppressWarnings("unchecked")
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
	
	/* (non-Javadoc)
	 * @see com.maina.formdata.repository.IDFormResultRepository#getStatusNumbers()
	 * @return array of int with 0=total, 1=sent, 2=unsent
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int[] getStatusNumbers() throws Exception {
		int[] status = new int[3];
		List<DformResultE> list = dataManager.publicDao(DataClass).queryForAll();
		for (DformResultE form : list) {
			if (form.isDone()){
				status[0] += 1;
				if(form.isSent()){
					status[1] += 1;
				}else{
					status[2] += 1;
				}
			}
		}
		return status;
	}

}

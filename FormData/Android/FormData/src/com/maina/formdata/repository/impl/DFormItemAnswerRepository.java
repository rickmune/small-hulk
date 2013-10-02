package com.maina.formdata.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Pair;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformItemAnswerE;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.RepositoryBase;

public class DFormItemAnswerRepository extends RepositoryBase implements IDFormItemAnswerRepository {

	public DFormItemAnswerRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public void setClass() {
		DataClass = DformItemAnswerE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public Cursor getAnswerItem(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Text as name FROM DformItemAnswerE dfi";
		return executeQuery(sql, params, "dfi");
	}

	public List<Pair<String, String>> getAnswerItems(UUID formItemId) throws Exception {
		List<Pair<String, String>> pairs = new ArrayList<Pair<String,String>>();
		List<DformItemAnswerE> list = dataManager.publicDao(DformItemAnswerE.class).
				queryForEq("dformItemE_id", formItemId);
		for(DformItemAnswerE answerE : list){
			pairs.add( new Pair<String, String>(answerE.getText(), answerE.getValue()) );
		}
		return pairs;
	}
}

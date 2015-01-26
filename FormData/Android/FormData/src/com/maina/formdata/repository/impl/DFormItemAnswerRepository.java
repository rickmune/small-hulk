package com.maina.formdata.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
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
		setData(dataManager);
	}

	@Override
	public Cursor getAnswerItem(List<String> params) throws Exception {
		String sql = "SELECT Value as _id, Text as name FROM DformItemAnswerE dfi";
		return executeQuery(sql, params, "dfi", true);
	}

	public List<Pair<String, String>> getAnswerItems(UUID formItemId) throws Exception {
		List<Pair<String, String>> pairs = new ArrayList<Pair<String,String>>();
		List<DformItemAnswerE> list = getDataManager().publicDao(DformItemAnswerE.class).
				queryForEq("dformItemE_id", formItemId);
		SortedList sortedList = new SortedList();
		for(DformItemAnswerE answerE : list){
			sortedList.insertSorted(answerE);
		}
		for(DformItemAnswerE answerE : sortedList){
			pairs.add( new Pair<String, String>(answerE.getText(), answerE.getValue()) );
		}
		return pairs;
	}

    public void deleteByFormId(UUID formItemId) throws Exception{
        List<DformItemAnswerE> list = (getDataManager().publicDao(DformItemAnswerE.class).
                queryForEq("dformItemE_id", formItemId));
        for(DformItemAnswerE answerE : list){
            getDataManager().publicDao(DformItemAnswerE.class).deleteById(answerE.getId());
        }
    }
	
	class SortedList extends ArrayList<DformItemAnswerE>{
		
		private static final long serialVersionUID = -9101376233508678082L;

		public void insertSorted(DformItemAnswerE value) {
	        add(value);
	        Comparable<String> cmp = (Comparable<String>) value.getText();
	        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1).getText()) < 0; i--)
	            Collections.swap(this, i, i-1);
	    }
	}
}

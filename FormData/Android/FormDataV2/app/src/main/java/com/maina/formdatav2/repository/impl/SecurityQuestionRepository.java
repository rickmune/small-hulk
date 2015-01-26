package com.maina.formdatav2.repository.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.dto.QuestionL;
import com.maina.formdatav2.entity.QuestionList;
import com.maina.formdatav2.repository.ISecurityQuestionRepository;
import com.maina.formdatav2.repository.RepositoryBase;

public class SecurityQuestionRepository extends RepositoryBase implements ISecurityQuestionRepository {

	public SecurityQuestionRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}
	
	@Override
	public Cursor getSecurityQuestion(List<String> params) throws Exception {
		String sql = "SELECT qId as _id, Name as name FROM QuestionList ql";
		return executeQuery(sql, params, "ql", true);
	}

	@Override
	public <T> int saveBatch(List<T> data) throws Exception{
		Log.d("SecurityQuestionRepository", "saveBatch size: " +data.size());
		int x = 0;
		List<QuestionList> avail = getDataManager().getAll(QuestionList.class);
		if(avail == null) avail = new ArrayList<QuestionList>();
		for (Iterator<T> iterator = data.iterator(); iterator.hasNext();) {
			QuestionL t = (QuestionL) iterator.next();
			QuestionList list = null;
			for(QuestionList ql : avail){
				if(ql.getqId() == t.getId()){
					list = ql;
					break;
				}
			}
			if(list == null){
				list = new QuestionList();
				list.setId(UUID.randomUUID());
			}
			
			list.setName(t.getName());
			list.setqId(t.getId());
            getDataManager().save(list, QuestionList.class);
			x++;
		}
		Log.d("SecurityQuestionRepository", "saved List: " + x);
		return x;
	}
	
	@Override
	public void setClass() {
		DataClass = QuestionList.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

}

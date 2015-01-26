package com.maina.formdata.repository.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DTownE;
import com.maina.formdata.repository.IDTownRepository;
import com.maina.formdata.repository.RepositoryBase;

public class DTownRepository extends RepositoryBase implements IDTownRepository {

	public DTownRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}
	
	@Override
	public Cursor getTownByParent(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Name as name from DTownE tf";
		return executeQuery(sql, params, "tf", true);
	}

	@Override
	public void setClass() {
		DataClass = DTownE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}
	
	public int ChildrenAvailble(String ParentId){
		int y = 0;
		try {
			Log.d("DTownRepository","ChildrenAvailable ParentId: " + ParentId);
			y = getDataManager().publicDao(DTownE.class).queryForEq("ParentId", UUID.fromString(ParentId)).size();
			Log.d("DTownRepository","Children Available: " + y);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return y;
	}

}

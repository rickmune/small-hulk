package com.maina.formdatav2.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.database.Cursor;

import android.util.Pair;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.DformE;
import com.maina.formdatav2.repository.IDFormRepository;
import com.maina.formdatav2.repository.RepositoryBase;

public class DFormRepository extends RepositoryBase implements IDFormRepository {

	public DFormRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public void setClass() {
		DataClass = DformE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

	@Override
	public Cursor getForms(List<String> params) throws Exception {
		String sql = "SELECT id as _id, Name as name from DformE df";
		return executeQuery(sql, params, "df", true);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getFormCount() throws Exception {
		return getDataManager().publicDao(DataClass).queryForAll().size();
	}

    public List<Pair<UUID, String>> getForms() throws Exception{
        List<Pair<UUID, String>> pairs = new ArrayList<>();
        List<DformE> list = getDataManager().getAll(DformE.class);
        for(DformE dformE : list) {
            pairs.add(new Pair<>(dformE.getId(), dformE.getName()));
        }
        return pairs;
    }
}

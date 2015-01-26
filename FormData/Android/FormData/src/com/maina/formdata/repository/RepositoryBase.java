package com.maina.formdata.repository;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Log;

import com.maina.formdata.datamanager.Datamanager;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;

public abstract class RepositoryBase {

	private static final String Tag = "RepositoryBase";
	private IDataManager dataManager;
	@SuppressWarnings("rawtypes")
	protected Class DataClass;
	
	public abstract void setClass();
	
	public abstract void setDataManager(IDataManager dataManager);
	
	@SuppressWarnings("unchecked")
	public <T> T save(T data) throws Exception{
		Log.d(Tag, "saving: " + DataClass.getCanonicalName());
		return (T) dataManager.save(data, DataClass);
	}
	
	@SuppressWarnings("unchecked")
	public <T> int saveBatch(List<T> data) throws Exception{
		Log.d(Tag, "saveBatching: " + DataClass.getCanonicalName());
		return dataManager.saveBatch(data, DataClass);
	}
	
	public Cursor executeQuery(String sql, List<String> searchParams, String alias, boolean order) throws Exception{
		Log.d(Tag, "executeQuery: " + DataClass.getCanonicalName());
		Cursor cursor = null;
		String sqlfinal = sql;
		if(searchParams != null && !searchParams.isEmpty())sqlfinal = search(sqlfinal, searchParams, alias);
		if(order)sqlfinal = sqlfinal +" Order by name asc";
		Log.d(Tag, "saveBatching sql: " + sqlfinal);
		cursor = dataManager.executeQuery(sqlfinal);
		return cursor;
	}
	
	private String search(String sql, List<String> searchParams, String alias){
		Log.d(Tag, "search: " + DataClass.getCanonicalName());
		String Where = "";
		int size = searchParams.size();
		for(int t = 1; t <= size; t++){
			if(t == 1){
				Where += " Where "+alias+"." + searchParams.get(t - 1);
			}else if(t % 2 == 0){
				Where += "='" + searchParams.get(t - 1)+"'";
			}else{
				Where += " "+alias+"." + searchParams.get(t - 1);
			}
		}
		sql += Where;
		Log.d(Tag,"search sql: "+sql);
		return sql;
	}

	@SuppressWarnings("unchecked")
	public <T> T getById(UUID id)throws Exception{
		Log.d(Tag, "getById Id: " + id +" Data: " + DataClass.getCanonicalName());
		T t = (T) dataManager.getById(id, DataClass);
		Log.d(Tag, "getById " + " Data: " + t.toString());
		return t;
	}
	
	@SuppressWarnings("unchecked")
	public void DeleteAll() throws Exception{		
		List<?> list = dataManager.publicDao(DataClass).queryForAll();
		Log.d(Tag, "DeleteAll b4: " + list.size());
		int x = dataManager.publicDao(DataClass).delete(list);
		Log.d(Tag, "DeleteAll after: " + x);
	}

    public IDataManager getDataManager(){
        if(dataManager == null){
            dataManager = (IDataManager) CloudManager.getObject(CloudConstants.DATABASEMANAGER.value);
        }
        return dataManager;
    }

    public void setData(IDataManager dataManager){
        this.dataManager = dataManager;
    }
}

package com.maina.formdatav2.datamanager;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;

import com.j256.ormlite.dao.Dao;
import com.maina.formdatav2.Utils.SortedArray;

public interface IDataManager {

	public <T> T save(T data, Class<T> dataClass) throws Exception;
	
	public <T> int saveBatch(List<T> data, Class<T> dataClass) throws Exception;
	
	public Cursor executeQuery(String query) throws Exception;
	
	public <T> T getById(UUID id, Class<T> dataClass)throws Exception;
	
	public <T> Dao<T, UUID> publicDao(Class<T> dataClass) throws Exception;
	
	public <T> List<T> getAll(Class<T> dataClass) throws Exception;
	
	public SortedArray getListDataHolder(String query) throws Exception;
}
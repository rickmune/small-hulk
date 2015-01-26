package com.maina.formdatav2.repository;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;

public interface IRepositoryBase {

	public <T> T save(T data) throws Exception;
	
	public <T> int saveBatch(List<T> data) throws Exception;
	
	public Cursor executeQuery(String sql, List<String> searchParams, String alias, boolean order) throws Exception;
	
	public <T> T getById(UUID id)throws Exception;
	
	public void DeleteAll() throws Exception;
}

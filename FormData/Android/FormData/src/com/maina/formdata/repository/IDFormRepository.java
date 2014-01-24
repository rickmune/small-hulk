package com.maina.formdata.repository;

import java.util.List;

import android.database.Cursor;

public interface IDFormRepository extends IRepositoryBase {
	
	public Cursor getForms(List<String> params) throws Exception;
	
	public int getFormCount() throws Exception;
}

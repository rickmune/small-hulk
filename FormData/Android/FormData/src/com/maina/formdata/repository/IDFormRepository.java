package com.maina.formdata.repository;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Pair;

public interface IDFormRepository extends IRepositoryBase {
	
	public Cursor getForms(List<String> params) throws Exception;
	
	public int getFormCount() throws Exception;

    public List<Pair<UUID, String>> getForms() throws Exception;
}

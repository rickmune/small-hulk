package com.maina.formdata.repository;

import java.util.List;

import android.database.Cursor;

public interface IDTownRepository extends IRepositoryBase {

	public Cursor getTownByParent(List<String> params) throws Exception;
	
	public int ChildrenAvailble(String ParentId);
}

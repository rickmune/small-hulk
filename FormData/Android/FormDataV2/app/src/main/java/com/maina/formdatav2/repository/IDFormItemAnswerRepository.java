package com.maina.formdatav2.repository;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;
import android.util.Pair;

public interface IDFormItemAnswerRepository extends IRepositoryBase {

	public Cursor getAnswerItem(List<String> params) throws Exception;
	
	public List<Pair<String, String>> getAnswerItems(UUID formItemId) throws Exception;

    public void deleteByFormId(UUID formItemId) throws Exception;
}

package com.maina.formdatav2.repository;

import java.util.List;

import android.database.Cursor;

public interface ISecurityQuestionRepository extends IRepositoryBase {

	public Cursor getSecurityQuestion(List<String> params) throws Exception;
	
	public <T> int saveBatch(List<T> data) throws Exception;
}

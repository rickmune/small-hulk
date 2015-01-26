package com.maina.formdatav2.repository;

import java.util.List;

import android.database.Cursor;

public interface IDFormRespondentTypeRepository extends IRepositoryBase {

	public Cursor getByFormId(List<String> params) throws Exception;
}

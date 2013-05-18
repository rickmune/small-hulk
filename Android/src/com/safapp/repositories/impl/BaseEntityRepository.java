package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

import com.safapp.entities.BaseEntity;
import com.safapp.repositories.IBaseEntityRepository;
import com.safapp.utils.CloudDataHolder;
import com.safapp.utils.database.IDataBaseManager;
import com.safapp.utils.enums.CloudConstants;

public abstract class BaseEntityRepository<T extends BaseEntity> implements IBaseEntityRepository<T> {

	IDataBaseManager dataBaseManager = (IDataBaseManager)CloudDataHolder.getObject(CloudConstants.DATABASEMANAGER.value);
	Class<T> dataClass;
	
	@Override
	public int save(T t) throws Exception {
		return dataBaseManager.save(t, dataClass);
	}

	@Override
	public List<T> getAll() throws Exception {
		return dataBaseManager.getAll(dataClass);
	}

	@Override
	public T getById(UUID id) throws Exception {
		return dataBaseManager.getById(id, dataClass);
	}

	@Override
	public int delete() throws Exception {
		return dataBaseManager.deleteAll(dataClass);
	}

	@Override
	public int deleteById(UUID Id) throws Exception {
		return dataBaseManager.deleteById(Id, dataClass);
	}
	
}

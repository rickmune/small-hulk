package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

import com.safapp.entities.BaseEntity;
import com.safapp.repositories.IBaseEntityRepository;

public class BaseEntityRepository<T extends BaseEntity> implements IBaseEntityRepository<T> {

	@Override
	public UUID save(T t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> getAll() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T getById(UUID id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}

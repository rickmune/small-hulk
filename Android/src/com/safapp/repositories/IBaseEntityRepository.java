package com.safapp.repositories;

import java.util.List;
import java.util.UUID;

public interface IBaseEntityRepository<T> {

	public UUID save(T t) throws Exception;
	
	public List<T> getAll() throws Exception;
	
	public T getById(UUID id) throws Exception;
	
	public int delete() throws Exception;
	
	public int deleteById() throws Exception;
}

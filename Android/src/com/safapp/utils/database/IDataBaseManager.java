package com.safapp.utils.database;

import java.util.List;
import java.util.UUID;

public interface IDataBaseManager {

	public <T> int save(T t, Class<T> dataClass) throws Exception;

	public <T> T getById(UUID Id, Class<T> dataClass) throws Exception;

	public <T> List<T> getAll(Class<T> dataClass) throws Exception;

	public <T> int deleteById(UUID Id, Class<T> dataClass) throws Exception;

	public <T> int deleteAll(Class<T> dataClass) throws Exception;
}

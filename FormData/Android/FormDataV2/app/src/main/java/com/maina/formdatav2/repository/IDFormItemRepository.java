package com.maina.formdatav2.repository;

import com.maina.formdatav2.entity.DformItemE;

import java.util.List;
import java.util.UUID;

public interface IDFormItemRepository extends IRepositoryBase {

	public List<DformItemE> getByFormId(UUID formId) throws Exception;
}

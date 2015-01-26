package com.maina.formdatav2.repository;

import com.maina.formdatav2.entity.DformItemRespondentTypeE;

import java.util.List;
import java.util.UUID;

public interface IDFormItemRespondentTypeRepository extends IRepositoryBase {

	public List<DformItemRespondentTypeE> getByFormItemId(UUID formItemId) throws Exception;
}

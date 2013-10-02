package com.maina.formdata.repository;

import java.util.List;
import java.util.UUID;

import com.maina.formdata.entity.DformItemE;

public interface IDFormItemRepository extends IRepositoryBase {

	public List<DformItemE> getByFormId(UUID formId) throws Exception;
}

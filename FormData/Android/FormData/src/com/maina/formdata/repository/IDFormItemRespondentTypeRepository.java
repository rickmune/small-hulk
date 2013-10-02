package com.maina.formdata.repository;

import java.util.List;
import java.util.UUID;

import com.maina.formdata.entity.DformItemRespondentTypeE;

public interface IDFormItemRespondentTypeRepository extends IRepositoryBase {

	public List<DformItemRespondentTypeE> getByFormItemId(UUID formItemId) throws Exception;
}

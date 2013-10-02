package com.maina.formdata.repository;

import java.util.List;
import java.util.UUID;

import com.maina.formdata.entity.DformResultItemE;

public interface IDFormResultItemRepository extends IRepositoryBase {

	public List<DformResultItemE> getByResultId(UUID resultId) throws Exception;
}

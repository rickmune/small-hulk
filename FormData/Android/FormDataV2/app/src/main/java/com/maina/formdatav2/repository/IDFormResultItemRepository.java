package com.maina.formdatav2.repository;

import com.maina.formdatav2.entity.DformResultItemE;

import java.util.List;
import java.util.UUID;

public interface IDFormResultItemRepository extends IRepositoryBase {

	public List<DformResultItemE> getByResultId(UUID resultId) throws Exception;
}

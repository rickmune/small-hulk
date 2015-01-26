package com.maina.formdatav2.repository;

import com.maina.formdatav2.entity.DformResultE;

public interface IDFormResultRepository extends IRepositoryBase {

	public int Saveresult(DformResultE dformResultE) throws Exception;
	
	public DformResultE getReadyToSend() throws Exception;
	
	public int[] getStatusNumbers() throws Exception;
}

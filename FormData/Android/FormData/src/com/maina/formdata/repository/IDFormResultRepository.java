package com.maina.formdata.repository;

import com.maina.formdata.entity.DformResultE;

public interface IDFormResultRepository extends IRepositoryBase {

	public int Saveresult(DformResultE dformResultE) throws Exception;
	
	public DformResultE getReadyToSend() throws Exception;
	
	public int[] getStatusNumbers() throws Exception;
}

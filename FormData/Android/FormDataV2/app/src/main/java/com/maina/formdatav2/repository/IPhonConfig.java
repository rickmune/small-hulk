package com.maina.formdatav2.repository;

import com.maina.formdatav2.entity.PhonConfig;

public interface IPhonConfig extends IRepositoryBase {

	public PhonConfig getConfig() throws Exception;
	
	public PhonConfig saveConfig(PhonConfig config) throws Exception;
	
	public void allowEdit() throws Exception;
}

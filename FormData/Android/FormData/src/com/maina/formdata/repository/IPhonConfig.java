package com.maina.formdata.repository;

import com.maina.formdata.entity.PhonConfig;

public interface IPhonConfig extends IRepositoryBase {

	public PhonConfig getConfig() throws Exception;
	
	public PhonConfig saveConfig(PhonConfig config) throws Exception;
	
	public void allowEdit() throws Exception;
}

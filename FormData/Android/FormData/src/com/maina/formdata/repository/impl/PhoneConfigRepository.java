package com.maina.formdata.repository.impl;

import java.util.List;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.PhonConfig;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.RepositoryBase;

public class PhoneConfigRepository extends RepositoryBase implements IPhonConfig {

	public PhoneConfigRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public PhonConfig getConfig() throws Exception {
		List<PhonConfig> configs = dataManager.publicDao(PhonConfig.class).queryForAll();
		if(configs != null && !configs.isEmpty()) return configs.get(0);
		return null;
	}
	
	@Override
	public PhonConfig saveConfig(PhonConfig config) throws Exception {
		List<PhonConfig> configs = dataManager.publicDao(PhonConfig.class).queryForAll();
		if(configs != null){
			config.setId(configs.get(0).getId());
		}
		dataManager.save(config, PhonConfig.class);
		return config;
	}
	
	@Override
	public void setClass() {
		DataClass = PhonConfig.class;
	}
	
	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}
	
}
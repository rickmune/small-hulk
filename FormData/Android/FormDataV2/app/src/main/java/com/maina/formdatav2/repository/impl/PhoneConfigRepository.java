package com.maina.formdatav2.repository.impl;

import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.entity.PhonConfig;
import com.maina.formdatav2.repository.IPhonConfig;
import com.maina.formdatav2.repository.RepositoryBase;

import java.util.List;

public class PhoneConfigRepository extends RepositoryBase implements IPhonConfig {

	public PhoneConfigRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public PhonConfig getConfig() throws Exception {
		List<PhonConfig> configs = getDataManager().publicDao(PhonConfig.class).queryForAll();
		if(configs != null && !configs.isEmpty()) return configs.get(0);
		return null;
	}
	
	@Override
	public PhonConfig saveConfig(PhonConfig config) throws Exception {
		List<PhonConfig> configs = getDataManager().publicDao(PhonConfig.class).queryForAll();
		if(configs != null){
			config.setId(configs.get(0).getId());
		}
        getDataManager().save(config, PhonConfig.class);
		return config;
	}
	
	@Override
	public void setClass() {
		DataClass = PhonConfig.class;
	}
	
	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

	@Override
	public void allowEdit() throws Exception {
		List<PhonConfig> configs = getDataManager().publicDao(PhonConfig.class).queryForAll();
		if(configs != null && !configs.isEmpty()) {
			PhonConfig config = configs.get(0);
			config.setEdit(true);
            getDataManager().save(config, PhonConfig.class);
		}
	}
	
}

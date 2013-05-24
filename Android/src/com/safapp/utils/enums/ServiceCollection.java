package com.safapp.utils.enums;

import com.safapp.service.IMasterDataSync;
import com.safapp.service.MasterDataSync;
import com.safapp.utils.http.HttpUtils;
import com.safapp.utils.http.IHttpUtils;

public enum ServiceCollection {

	MASTERDATASYNC(IMasterDataSync.class, MasterDataSync.class),
	HTTPUTILS(IHttpUtils.class, HttpUtils.class);
	
	@SuppressWarnings("rawtypes")
	public Class Iservice;
	@SuppressWarnings("rawtypes")
	public Class Service;
	
	@SuppressWarnings("rawtypes")
	private ServiceCollection(Class Iservice, Class Service){
		this.Iservice = Iservice;
		this.Service = Service;
	}
}

package com.safapp.utils.http;

import java.util.Hashtable;

import com.safapp.service.IServiceBase;

public interface IHttpUtils extends IServiceBase{

	public String PostRequest(String url, Hashtable<String, String> params) throws Exception;
	
	public String GetRequest(String url, Hashtable<String, String> params) throws Exception;
}

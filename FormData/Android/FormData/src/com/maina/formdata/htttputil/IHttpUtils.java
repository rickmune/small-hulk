package com.maina.formdata.htttputil;

import java.util.Hashtable;

public interface IHttpUtils {

	public String PostRequest(String url, Hashtable<String, String> table) throws Exception;
	
	public String GetRequest(String url, Hashtable<String, String> table) throws Exception;
}
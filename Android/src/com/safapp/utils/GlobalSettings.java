package com.safapp.utils;

public class GlobalSettings {

	private static final String ControlFolder = "/api/phone/";
	private static String IPADDRESS = "http://10.0.2.2:49444";//null;//"http://10.0.0.246:54192";
	private static final String masterdataController = "MasterData/";
	private static final String user = "users";
	
	public static String userWebService(){
		return getMasterDataController() + user;
	}
	
	public static String getIP_ADDRESS() {
		return IPADDRESS;
	}
	
	public static void setIP_ADDRESS(String iPADRESS) {
		IPADDRESS = iPADRESS;
	}
	
	//private stuff
	
	private static String getMasterDataController(){
		return getBaseUrl() + masterdataController;
	}
	
	private static String getBaseUrl(){
		return getIP_ADDRESS() + ControlFolder;
	}
}

package com.safapp.utils;

public class GlobalSettings {

	private static final String ControlFolder = "/api/phone/";
	private static String IPADDRESS = "http://10.0.0.246:49445";//null;//"http://10.0.0.246:54192";
	private static final String masterdataController = "MasterData/";
	private static final String user = "users";
	private static final String countries = "countries";
	private static final String addaccount = "addaccount";
	private static final String adduser = "adduser";
	
	public static String getuserWebService(){
		return getMasterDataController() + user;
	}
	
	public static String getcountryWebService(){
		return getMasterDataController() + countries;
	}
	
	public static String putAccountWebService(){
		return getMasterDataController() + addaccount;
	}
	
	public static String putUserWebService(){
		return getMasterDataController() + adduser;
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

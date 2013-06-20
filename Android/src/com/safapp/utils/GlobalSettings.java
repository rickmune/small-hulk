package com.safapp.utils;

public class GlobalSettings {

	private static final String ControlFolder = "/api/phone/";
	private static String IPADDRESS = "http://10.0.0.246:49445";//null;//"http://10.0.0.246:54192";
	private static final String masterdataController = "MasterData/";
	private static final String user = "users";
	private static final String countries = "countries";
	private static final String categories = "categories";
	private static final String products = "products";
	private static final String routes = "routes";
	private static final String outlets = "outlets";
	private static final String login = "login";
	
	private static final String addaccount = "addaccount";
	private static final String adduser = "adduser";
	private static final String addproduct = "addproduct";
	private static final String addcategory = "addcategory";
	private static final String addroute = "addroute";
	private static final String addoutlet = "addoutlet";
	
	
	public static String getuserWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + user;
		return getMasterDataController() + user;
	}
	
	public static String getcountryWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + countries;
		return getMasterDataController() + countries;
	}
	
	public static String loginWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + login;
		return getMasterDataController() + login;
	}	
	
	public static String getCategoryWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + categories;
		return getMasterDataController() + categories;
	}
	
	public static String getProductWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + products;
		return getMasterDataController() + products;
	}
	
	public static String getRouteWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + routes;
		return getMasterDataController() + routes;
	}
	
	public static String getOutletWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + outlets;
		return getMasterDataController() + outlets;
	}
	
	public static String putAccountWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + addaccount;
		return getMasterDataController() + addaccount;
	}
	
	public static String putUserWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + adduser;
		return getMasterDataController() + adduser;
	}
	
	public static String putProductWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + addproduct;
		return getMasterDataController() + addproduct;
	}
	
	public static String putCategoryWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + addcategory;
		return getMasterDataController() + addcategory;
	}
	
	public static String putRouteWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + addroute;
		return getMasterDataController() + addroute;
	}
	
	public static String putOutletWebService(boolean full){
		if(full)return getIP_ADDRESS() + getMasterDataController() + addoutlet;
		return getMasterDataController() + addoutlet;
	}
	
	public static String getIP_ADDRESS() {
		return IPADDRESS;
	}
	
	public static void setIP_ADDRESS(String iPADRESS) {
		IPADDRESS = iPADRESS;
	}
		
	private static String getMasterDataController(){
		return getBaseUrl() + masterdataController;
	}
	
	private static String getBaseUrl(){
		return ControlFolder;
	}
}

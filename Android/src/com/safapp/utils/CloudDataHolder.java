package com.safapp.utils;

import java.util.Enumeration;
import java.util.Hashtable;

public class CloudDataHolder {

	private static Hashtable<String, Object> transactionDataCloud = new Hashtable<String, Object>();
    private static Hashtable<String, Object> sessionDataCloud = new Hashtable<String, Object>();
    
    public static Object getObject(String id){
        Object obj=null;
        Enumeration<String> inner = transactionDataCloud.keys();
        while (inner.hasMoreElements()) {
            String controlIdKey = inner.nextElement();
            if(controlIdKey.equalsIgnoreCase(id)){
                obj = transactionDataCloud.get(controlIdKey);
                return obj;
            } 
        }
        inner = sessionDataCloud.keys();
        while (inner.hasMoreElements()) {
            String controlIdKey = (String) inner.nextElement();
            if(controlIdKey.equalsIgnoreCase(id)){
                obj=sessionDataCloud.get(controlIdKey);
                return obj;
            } 
        }
        return obj;
    }
    
    public static void putObject(String id, Object object, boolean transactional){
        if(object==null){
            object=(Object)" ";
        }
        if(transactional){
        	transactionDataCloud.put(id, object);
        }else{
        	sessionDataCloud.put(id, object);
        }
    }
    
    public static void deleteObject(String id){
        sessionDataCloud.remove(id);
        transactionDataCloud.remove(id);
    }
    
    public static void cleanCloud(){
    	transactionDataCloud = new Hashtable<String, Object>();
    }
}

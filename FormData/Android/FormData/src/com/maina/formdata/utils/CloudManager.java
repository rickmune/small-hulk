package com.maina.formdata.utils;

import java.util.Enumeration;
import java.util.Hashtable;

import android.util.Log;

public class CloudManager {

	private static Hashtable<String, Object> flowDataCloud = new Hashtable<String, Object>();
	private static Hashtable<String, Object> sessionDataCloud = new Hashtable<String, Object>();

	public static Object getObject(String id) {
		Object obj = null;
		Enumeration<String> inner = flowDataCloud.keys();
		while (inner.hasMoreElements()) {
			String controlIdKey = inner.nextElement();
			if (controlIdKey.equalsIgnoreCase(id)) {
				obj = flowDataCloud.get(controlIdKey);
				return obj;
			}
		}

		inner = sessionDataCloud.keys();
		while (inner.hasMoreElements()) {
			String controlIdKey = (String) inner.nextElement();
			if (controlIdKey.equalsIgnoreCase(id)) {
				obj = sessionDataCloud.get(controlIdKey);
				return obj;
			}
		}
		return obj;
	}

	public static void putObject(String id, Object object, boolean LongLived) {
		Log.d("CloudManager", "putObject: "+id);
		if (object == null) {
			object = (Object) " ";
		}
		if (LongLived) {
			sessionDataCloud.put(id, object);
		} else {
			flowDataCloud.put(id, object);
		}
	}

	public static void deleteObject(String id) {
		Log.d("CloudManager", "deleteObject: "+id);
		sessionDataCloud.remove(id);
		flowDataCloud.remove(id);
	}

	public static void cleanCloud() {
		flowDataCloud = new Hashtable<String, Object>();
	}
}

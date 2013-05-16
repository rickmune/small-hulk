package com.safapp.utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonConverter {

	public  static <T> String MapObject(T base){
    	String json = "";
    	GsonBuilder b = new GsonBuilder();
    	b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Gson gson = b.create();
    	Type typeOfSrc = new TypeToken<T>(){}.getType();
    	json = gson.toJson(base, typeOfSrc);
    	return json;
    }
}

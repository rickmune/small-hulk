package com.safapp.utils;

import java.lang.reflect.Type;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class JsonConverter {

	private static final String Tag = "JsonConverter";
	
	public  static <T> String MapObject(T base){
		Log.d(Tag, "MapObject: "+base.getClass().getCanonicalName());
    	String json = "";
    	GsonBuilder b = new GsonBuilder();
    	b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    	Gson gson = b.create();
    	Type typeOfSrc = new TypeToken<T>(){}.getType();
    	json = gson.toJson(base, typeOfSrc);
    	return json;
    }
	
	public static <T> T deserialize(String json, Type t){
		Log.d(Tag, "deserialize b4: "+json);
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Log.d(Tag, "deserialize EntitySyncInfoserializer");
    	Gson gson = b.create();
    	JsonParser parser = new JsonParser();
    	try{
    		JsonObject object1 = parser.parse(json.trim()).getAsJsonObject();
    		return gson.fromJson(object1, t);
    	}catch(Exception e){
    		Log.d(Tag, e.getMessage());
    		JsonArray array = parser.parse(json.trim()).getAsJsonArray();
    		return gson.fromJson(array, t);
    	}
	}
}

package com.maina.formdata.utils;

import java.lang.reflect.Type;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.maina.formdata.enums.DformItemType;

public class DformItemTypeserializer implements JsonDeserializer<DformItemType> {

	@Override
	public DformItemType deserialize(JsonElement json, Type type, JsonDeserializationContext arg2) throws JsonParseException {
		Log.d("DformItemTypeserializer", "JsonElement: "+json);
		DformItemType[] types = DformItemType.values();
		for(DformItemType type1 : types){
			if(type1.value == json.getAsInt()){
				return type1;
			}
		}
		return null;
	}

}

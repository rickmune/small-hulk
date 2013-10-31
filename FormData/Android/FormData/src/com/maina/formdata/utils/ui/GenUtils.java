package com.maina.formdata.utils.ui;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.Repositoryregistry;

public class GenUtils {

	private static String url = "http://test.icoders-solution.com";
	
	public static float roundTo(float number, int places){
		int sentin = 10 * places;
		System.out.println("roundTo sentin: "+sentin);
		int rounded = (int)(number * sentin);
		System.out.println("roundTo rounded: "+rounded);
		float fin = rounded / sentin;
		System.out.println("roundTo fin: "+fin);
		return fin;
	}

	public static String getUrl(IDataManager dataManager) {
		try {
			url = Repositoryregistry.get(IPhonConfig.class, dataManager).getConfig().getURL();
		} catch (Exception e) { e.printStackTrace(); }
		return url;
	}
	
}

package com.maina.formdata.utils.ui;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.Repositoryregistry;

public class GenUtils {

    //private static String url = "http://test.icoders-solution.com";
	private static String url = "http://topimage.icoders-solution.com";
	//private static String url = "http://10.0.0.194:62093";
	//private static String url = "http://equity.icoders-solution.com";
    public static final int SECONDS_TO_SLEEP = 10;
	
	public static float roundTo(float number, int places){
		int sentin = 10 ^ places;
		System.out.println("roundTo sentin: "+sentin);
		int rounded = (int)(number * sentin);
		System.out.println("roundTo rounded: "+rounded);
		float fin = rounded / sentin;
		System.out.println("roundTo fin: "+fin);
		return fin;
	}

	public static String getUrl(IDataManager dataManager) {
		try {
            String tmp = Repositoryregistry.get(IPhonConfig.class, dataManager).getConfig().getURL();
			if(tmp != null)
                url = tmp;
		} catch (Exception e) { e.printStackTrace(); }
		return url;
	}
	
}

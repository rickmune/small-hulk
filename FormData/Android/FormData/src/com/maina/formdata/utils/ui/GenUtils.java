package com.maina.formdata.utils.ui;

public class GenUtils {

	public static float roundTo(float number, int places){
		int sentin = 10 * places;
		System.out.println("roundTo sentin: "+sentin);
		int rounded = (int)(number * sentin);
		System.out.println("roundTo rounded: "+rounded);
		float fin = rounded / sentin;
		System.out.println("roundTo fin: "+fin);
		return fin;
	}
}

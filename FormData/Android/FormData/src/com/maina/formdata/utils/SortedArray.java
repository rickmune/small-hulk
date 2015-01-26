package com.maina.formdata.utils;

import java.util.ArrayList;
import java.util.Collections;

import com.maina.formdata.utils.ui.ListDataHolder;

public class SortedArray extends ArrayList<ListDataHolder> {

	private static final long serialVersionUID = -7453871109314880567L;

	public void insertSorted(ListDataHolder dataHolder){
		add(dataHolder);
		Comparable<String> cmp = (Comparable<String>) dataHolder.Value;
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1).Value) < 0; i--)
            Collections.swap(this, i, i-1);
	}
}

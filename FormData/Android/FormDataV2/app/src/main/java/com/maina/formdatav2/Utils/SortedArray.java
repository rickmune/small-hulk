package com.maina.formdatav2.Utils;

import com.maina.formdatav2.Utils.ui.ListDataHolder;

import java.util.ArrayList;
import java.util.Collections;

public class SortedArray extends ArrayList<ListDataHolder> {

	private static final long serialVersionUID = -7453871109314880567L;

	public void insertSorted(ListDataHolder dataHolder){
		add(dataHolder);
		Comparable<String> cmp = dataHolder.Value;
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1).Value) < 0; i--)
            Collections.swap(this, i, i-1);
	}
}

package com.maina.formdatav2.Utils;

import com.maina.formdatav2.entity.DformItemE;

import java.util.ArrayList;
import java.util.Collections;

public class SortedArrayList extends ArrayList<DformItemE> {

	private static final long serialVersionUID = 250101760353408981L;

	public void insertSorted(DformItemE value) {
        add(value);
        Comparable<Integer> cmp = (Comparable<Integer>) value.getOrder();
        for (int i = size()-1; i > 0 && cmp.compareTo(get(i-1).getOrder()) < 0; i--)
            Collections.swap(this, i, i-1);
    }
}

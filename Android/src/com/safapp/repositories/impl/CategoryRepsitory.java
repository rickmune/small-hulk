package com.safapp.repositories.impl;

import com.safapp.entities.Category;
import com.safapp.repositories.ICategoryRepsitory;

public class CategoryRepsitory extends BaseEntityRepository<Category> implements ICategoryRepsitory {

	@Override
	public void setDataClass() {
		dataClass = Category.class;
	}

}

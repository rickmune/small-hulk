package com.safapp.repositories.impl;

import android.database.Cursor;

import com.safapp.entities.Category;
import com.safapp.repositories.ICategoryRepsitory;

public class CategoryRepsitory extends BaseEntityRepository<Category> implements ICategoryRepsitory {

	
	public CategoryRepsitory() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Category.class;
	}

	@Override
	public Cursor getAllCat() throws Exception {
		String sql = "SELECT c.Id as _id, c.Name as entityName" +
				" FROM Category c";
		
		return dataBaseManager.queryDB(sql);
	}

}

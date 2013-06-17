package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

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

	@Override
	public List<String[]> getAForSpinner(UUID fillter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

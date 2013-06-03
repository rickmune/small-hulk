package com.safapp.repositories;

import android.database.Cursor;

import com.safapp.entities.Category;

public interface ICategoryRepsitory extends IBaseEntityRepository<Category> {

	public Cursor getAllCat() throws Exception; 
}

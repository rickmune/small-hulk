package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

import android.database.Cursor;

import com.safapp.entities.Country;
import com.safapp.repositories.ICountryRepository;

public class CountryRepository extends BaseEntityRepository<Country> implements ICountryRepository {

	public CountryRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Country.class;
	}

	@Override
	public Cursor getAllCountries() throws Exception {
		String sql = "SELECT c.Id as _id, c.Name as entityName" +
				" FROM Country c";
		
		return dataBaseManager.queryDB(sql);
	}

	@Override
	public List<String[]> getAForSpinner(UUID fillter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

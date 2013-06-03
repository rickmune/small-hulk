package com.safapp.repositories;

import android.database.Cursor;

import com.safapp.entities.Country;

public interface ICountryRepository extends IBaseEntityRepository<Country> {

	public Cursor getAllCountries() throws Exception;
}

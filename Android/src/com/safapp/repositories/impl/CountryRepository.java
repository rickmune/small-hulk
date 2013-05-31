package com.safapp.repositories.impl;

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

}

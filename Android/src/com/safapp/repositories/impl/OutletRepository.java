package com.safapp.repositories.impl;

import com.safapp.entities.Outlet;
import com.safapp.repositories.IOutletRepository;

public class OutletRepository extends BaseEntityRepository<Outlet> implements IOutletRepository {

	
	public OutletRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Outlet.class;
	}

}

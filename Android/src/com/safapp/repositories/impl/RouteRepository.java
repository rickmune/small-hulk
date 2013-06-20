package com.safapp.repositories.impl;

import com.safapp.entities.Route;
import com.safapp.repositories.IRouteRepository;

public class RouteRepository extends BaseEntityRepository<Route> implements IRouteRepository {

	
	public RouteRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Route.class;
	}

}

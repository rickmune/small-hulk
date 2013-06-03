package com.safapp.repositories.impl;

import com.safapp.entities.OutGoing;
import com.safapp.repositories.IOutGoingRepository;

public class OutGoingRepository extends BaseEntityRepository<OutGoing> implements IOutGoingRepository {

	
	public OutGoingRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = OutGoing.class;
	}

	@Override
	public OutGoing getNext() throws Exception {
		return null;
	}

}

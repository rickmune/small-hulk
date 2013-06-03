package com.safapp.repositories;

import com.safapp.entities.OutGoing;

public interface IOutGoingRepository extends IBaseEntityRepository<OutGoing> {

	public OutGoing getNext() throws Exception;
}

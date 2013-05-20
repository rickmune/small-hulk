package com.safapp.repositories;

import com.safapp.entities.User;
import com.safapp.utils.enums.UserType;

public interface IUserRepository extends IBaseEntityRepository<User> {

	public User getByEmailorPhone(String userName, UserType type) throws Exception;
	
}

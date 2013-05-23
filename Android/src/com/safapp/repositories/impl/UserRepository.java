package com.safapp.repositories.impl;

import com.safapp.entities.User;
import com.safapp.repositories.IUserRepository;
import com.safapp.utils.enums.UserType;

public class UserRepository extends BaseEntityRepository<User> implements IUserRepository {

	public UserRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = User.class;
	}

	@Override
	public User getByEmailorPhone(String userName, UserType type) throws Exception {
		//int userType = type.value;
		return null;
	}
	
}

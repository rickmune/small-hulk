package com.safapp.repositories.impl;

import com.safapp.entities.User;
import com.safapp.repositories.IUserRepository;

public class UserRepository extends BaseEntityRepository<User> implements IUserRepository {

	public UserRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = User.class;
	}
}

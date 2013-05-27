package com.safapp.repositories.impl;

import java.sql.SQLException;
import java.util.List;

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

	@Override
	public boolean doLogin(String userName, String passWord, UserType type) {
		List<User> userList = null;
		String userLType = "";
		if(type.equals(UserType.Email)){
			userLType = User.EMAIL;
		}else if(type.equals(UserType.Phone)){
			userLType = User.PHONE;
		}
		try {
			userList = dataBaseManager.getDBDao(dataClass).queryForEq(userLType, userName);
			for(User user : userList){
				if(user.getPassword().equals(passWord)){
					return true;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
	
}

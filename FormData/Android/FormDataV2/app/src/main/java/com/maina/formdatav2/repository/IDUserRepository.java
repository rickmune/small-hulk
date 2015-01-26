package com.maina.formdatav2.repository;

import com.maina.formdatav2.Utils.SyncEntity;
import com.maina.formdatav2.dto.UserDto;

public interface IDUserRepository extends IRepositoryBase {
	
	public SyncEntity<UserDto> login(String userName, String password) throws Exception;
	
	public String getuserName() throws Exception;
	
	public UserDto getUser() throws Exception;
}

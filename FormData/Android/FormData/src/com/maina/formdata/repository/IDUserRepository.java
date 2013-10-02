package com.maina.formdata.repository;

import com.maina.formdata.dto.UserDto;
import com.maina.formdata.utils.SyncEntity;

public interface IDUserRepository extends IRepositoryBase {
	
	public SyncEntity<UserDto> login(String userName, String password) throws Exception;
	
	public String getuserName() throws Exception;
}

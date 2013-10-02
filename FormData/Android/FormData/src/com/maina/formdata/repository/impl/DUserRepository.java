package com.maina.formdata.repository.impl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.RepositoryBase;
import com.maina.formdata.utils.SyncEntity;

public class DUserRepository extends RepositoryBase implements IDUserRepository {
	
	public DUserRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@Override
	public <T> T save(T data) throws Exception{
		Log.d("DUserRepository", "saving: " + DataClass.getCanonicalName()+"\n"+((DUserE)data).toString());
		return (T) dataManager.save(data, DataClass);
	}
	
	@Override
	public SyncEntity<UserDto> login(String userName, String password) throws Exception {
		Log.d("DUserRepository", "userName: " + userName +" password: "+password);
		List<DUserE> userEs = dataManager.publicDao(DUserE.class).queryForAll();
		SyncEntity<UserDto> entity = new SyncEntity<UserDto>();
		Log.d("DUserRepository", "entity size: " + userEs.size());
		entity.Status = false;
		if(userEs != null && !userEs.isEmpty()){
			DUserE user= userEs.get(0);
			Log.d("DUserRepository", "userName: " + user.toString());
			if(user != null && (user.getUsername().equalsIgnoreCase(userName) || user.getEmail().equalsIgnoreCase(userName))){
				if(user.getPassword().equalsIgnoreCase(password)){
					List<UserDto> t = new ArrayList<UserDto>();
					t.add(new UserDto(user.getId(), true, user.getUsername(), user.getPassword(), 
							user.getFullname(), user.getUserTypeId().value, user.getEmail(), user.getPhoneNumber(), 
							user.getClientId()));
					entity.setInfo("success");
					entity.Status = true;
					entity.setData(t);
				}else{
					entity.setInfo("Invalid Credetials");
				}
				entity.setInfo("Invalid Authentication. Confirm Spelling");
			}
					 
		}
		return entity;
	}

	@Override
	public void setClass() {
		DataClass = DUserE.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		this.dataManager = dataManager;
	}

	@Override
	public String getuserName() throws Exception {
		System.out.println("getuserName");
		List<DUserE> userEs = dataManager.publicDao(DUserE.class).queryForAll();
		if(userEs != null && !userEs.isEmpty()){
			//String username = userEs.get(0).get
			return userEs.get(0).getUsername();
		}
		System.out.println("getuserName returning null");
		return null;
	}

}

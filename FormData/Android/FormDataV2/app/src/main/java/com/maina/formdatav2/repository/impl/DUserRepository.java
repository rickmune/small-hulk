package com.maina.formdatav2.repository.impl;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.maina.formdatav2.Utils.SyncEntity;
import com.maina.formdatav2.datamanager.IDataManager;
import com.maina.formdatav2.dto.UserDto;
import com.maina.formdatav2.entity.DUserE;
import com.maina.formdatav2.repository.IDUserRepository;
import com.maina.formdatav2.repository.RepositoryBase;

public class DUserRepository extends RepositoryBase implements IDUserRepository {
	
	public DUserRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T save(T data) throws Exception{
		Log.d("DUserRepository", "saving: " + DataClass.getCanonicalName()+"\n"+((DUserE)data).toString());
		return (T) getDataManager().save(data, DataClass);
	}
	
	@Override
	public SyncEntity<UserDto> login(String userName, String password) throws Exception {
		Log.d("DUserRepository", "userName: " + userName +" password: "+password);
		List<DUserE> userEs = getDataManager().publicDao(DUserE.class).queryForAll();
		SyncEntity<UserDto> entity = new SyncEntity<UserDto>();
		Log.d("DUserRepository", "entity size: " + userEs.size());
		entity.Status = false;
		if(userEs != null && !userEs.isEmpty()){
			DUserE user= userEs.get(0);
			Log.d("DUserRepository", "userName: " + user.toString());
			if(user != null && (user.getUsername().equalsIgnoreCase(userName) || (user.getEmail() != null && user.getEmail().equalsIgnoreCase(userName)))){
				if(user.getPassword().equalsIgnoreCase(password)){
					List<UserDto> t = new ArrayList<UserDto>();
					t.add(new UserDto(user.getId(), true, user.getUsername(), user.getPassword(), 
							user.getFullname(), user.getUserTypeId().value, user.getEmail(), user.getPhoneNumber(), 
							user.getClientId(), user.getLocationId(), user.getClientName(), user.isIsPasswordChanged(),
							user.isIsSecuritySet(), user.getSecurityQuestionId(), user.getSecurityAnswer()));
					entity.setInfo("success");
					entity.Status = true;
					entity.setData(t);
				}else{
					entity.setInfo("Invalid Credetials");
				}
			}else{
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
        setData(dataManager);
	}

	@Override
	public String getuserName() throws Exception {
		System.out.println("getuserName");
		List<DUserE> userEs = getDataManager().publicDao(DUserE.class).queryForAll();
		if(userEs != null && !userEs.isEmpty()){
			//String username = userEs.get(0).get
			return userEs.get(0).getUsername();
		}
		System.out.println("getuserName returning null");
		return null;
	}

	@Override
	public UserDto getUser() throws Exception {
		DUserE user = getDataManager().publicDao(DUserE.class).queryForAll().get(0);
		System.out.println("user: " + user.toString());
		return new UserDto(user.getId(), true, user.getUsername(), user.getPassword(), 
				user.getFullname(), user.getUserTypeId().value, user.getEmail(), user.getPhoneNumber(), 
				user.getClientId(), user.getLocationId(), user.getClientName(), user.isIsPasswordChanged(),
				user.isIsSecuritySet(), user.getSecurityQuestionId(), user.getSecurityAnswer());
	}

}

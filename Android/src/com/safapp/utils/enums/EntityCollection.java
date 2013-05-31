package com.safapp.utils.enums;

import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.safapp.entities.Account;
import com.safapp.entities.BaseEntity;
import com.safapp.entities.Country;
import com.safapp.entities.User;

public enum EntityCollection {

	ACCOUNT(Account.class, new TypeToken<Account>(){}.getType()),
	USER(User.class, new TypeToken<User>(){}.getType()),
	COUNTRY(Country.class,  new TypeToken<Country>(){}.getType());
	
	public final Class<BaseEntity> value;
	public final Type entityType;

	@SuppressWarnings("unchecked")
	private <T extends BaseEntity> EntityCollection(Class<T> value, Type masterDataType) {
		this.value = (Class<BaseEntity>) value;
		this.entityType = masterDataType;
	}
}

package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.repositories.IAccountRepository;

public class AccountRepository extends BaseEntityRepository<Account> implements IAccountRepository {
	
	public AccountRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Account.class;
	}

	@Override
	public List<String[]> getAForSpinner(UUID fillter) throws Exception {
		return null;
	}

}

package com.safapp.repositories.impl;

import com.safapp.entities.Account;
import com.safapp.repositories.IAccountRepository;

public class AccountRepository extends BaseEntityRepository<Account> implements IAccountRepository {
	
	@Override
	public void setDataClass() {
		dataClass = Account.class;
	}

}
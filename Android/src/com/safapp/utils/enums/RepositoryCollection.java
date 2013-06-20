package com.safapp.utils.enums;

import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.repositories.ICountryRepository;
import com.safapp.repositories.IOutGoingRepository;
import com.safapp.repositories.IOutletRepository;
import com.safapp.repositories.IProductRepository;
import com.safapp.repositories.IRouteRepository;
import com.safapp.repositories.IUserRepository;
import com.safapp.repositories.impl.AccountRepository;
import com.safapp.repositories.impl.CategoryRepsitory;
import com.safapp.repositories.impl.CountryRepository;
import com.safapp.repositories.impl.OutGoingRepository;
import com.safapp.repositories.impl.OutletRepository;
import com.safapp.repositories.impl.ProductRepository;
import com.safapp.repositories.impl.RouteRepository;
import com.safapp.repositories.impl.UserRepository;

public enum RepositoryCollection {

	USERREPO(IUserRepository.class, UserRepository.class),
	ACCOUNTREPO(IAccountRepository.class, AccountRepository.class),
	CATEGORYREPO(ICategoryRepsitory.class, CategoryRepsitory.class),
	PRODUCTREPO(IProductRepository.class, ProductRepository.class),
	COUNTRYREPO(ICountryRepository.class, CountryRepository.class),
	OUTGOINGREPO(IOutGoingRepository.class, OutGoingRepository.class),
	OUTLETREPO(IOutletRepository.class, OutletRepository.class),
	ROUTEREPO(IRouteRepository.class, RouteRepository.class);
	
	@SuppressWarnings("rawtypes")
	public Class Irepo;
	@SuppressWarnings("rawtypes")
	public Class Repo;
	
	@SuppressWarnings("rawtypes")
	private RepositoryCollection(Class Irepo, Class Repo){
		this.Irepo = Irepo;
		this.Repo = Repo;
	}
}

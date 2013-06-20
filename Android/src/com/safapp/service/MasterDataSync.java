package com.safapp.service;

import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.safapp.dto.CategoryDTO;
import com.safapp.dto.CountryDTO;
import com.safapp.dto.OutletDTO;
import com.safapp.dto.ProductDTO;
import com.safapp.dto.RouteDTO;
import com.safapp.dto.UserDto;
import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.entities.OutGoing;
import com.safapp.entities.Outlet;
import com.safapp.entities.Product;
import com.safapp.entities.Route;
import com.safapp.entities.User;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.repositories.ICountryRepository;
import com.safapp.repositories.IOutGoingRepository;
import com.safapp.repositories.IOutletRepository;
import com.safapp.repositories.IProductRepository;
import com.safapp.repositories.IRouteRepository;
import com.safapp.repositories.IUserRepository;
import com.safapp.utils.GlobalSettings;
import com.safapp.utils.JsonConverter;
import com.safapp.utils.SyncEntity;
import com.safapp.utils.http.IHttpUtils;

public class MasterDataSync extends ServiceBase implements IMasterDataSync {

	public MasterDataSync(ICountryRepository countryRepository,
			IAccountRepository accountRepository,
			IUserRepository userRepository,
			IProductRepository productRepository,
			ICategoryRepsitory categoryRepsitory,
			IRouteRepository routeRepository,
			IOutletRepository outletRepository,
			IOutGoingRepository outGoingRepository, IHttpUtils httpUtils) {
		super();
		this.countryRepository = countryRepository;
		this.accountRepository = accountRepository;
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.categoryRepsitory = categoryRepsitory;
		this.routeRepository = routeRepository;
		this.outletRepository = outletRepository;
		this.outGoingRepository = outGoingRepository;
		this.httpUtils = httpUtils;
	}

	ICountryRepository countryRepository;
	IAccountRepository accountRepository;
	IUserRepository userRepository;
	IProductRepository productRepository;
	ICategoryRepsitory categoryRepsitory;
	IRouteRepository routeRepository;
	IOutletRepository outletRepository;
	IOutGoingRepository outGoingRepository;
	
	private static final String Tag = "MasterDataSync";
	IHttpUtils httpUtils;
	
	@Override
	public boolean getCountry() {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getcountryWebService(true), params);
			SyncEntity<CountryDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<CountryDTO>>(){}.getType());
			Log.d(Tag, "CountryDTO Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<CountryDTO> dtos = syncEntity.getData();
			Log.d(Tag, "dtos.size(): "+ dtos.size());
			for(CountryDTO dto : dtos){
				int y = countryRepository.save(dto.Map());
				Log.d(Tag, "save country: "+ y);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logIn(String userName, String Password) {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("username", userName);
		params.put("password", Password);
		try {
			string = httpUtils.GetRequest(GlobalSettings.loginWebService(true), params);
			SyncEntity<UserDto> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<UserDto>>(){}.getType());
			Log.d(Tag, "UserDto Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<UserDto> dtos = syncEntity.getData();
			Log.d(Tag, "UserDto dtos.size(): "+ dtos.size());
			for(UserDto dto : dtos){
				int x = userRepository.save(dto.Map());
				Log.d(Tag, "UserDto saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getProduct(String accountId) {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getProductWebService(true), params);
			SyncEntity<ProductDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<ProductDTO>>(){}.getType());
			Log.d(Tag, "ProductDTO Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<ProductDTO> dtos = syncEntity.getData();
			Log.d(Tag, "ProductDTO dtos.size(): "+ dtos.size());
			for(ProductDTO dto : dtos){
				int x = productRepository.save(dto.Map());
				Log.d(Tag, "ProductDTO saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getCategory(String accountId) {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getCategoryWebService(true), params);
			SyncEntity<CategoryDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<CategoryDTO>>(){}.getType());
			Log.d(Tag, "CategoryDTO Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<CategoryDTO> dtos = syncEntity.getData();
			Log.d(Tag, "CategoryDTO dtos.size(): "+ dtos.size());
			for(CategoryDTO dto : dtos){
				int x = categoryRepsitory.save(dto.Map());
				Log.d(Tag, "CategoryDTO saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean getUser(){
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getuserWebService(true), params);
			SyncEntity<UserDto> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<UserDto>>(){}.getType());
			Log.d(Tag, "UserDto Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<UserDto> dtos = syncEntity.getData();
			Log.d(Tag, "UserDto dtos.size(): "+ dtos.size());
			for(UserDto dto : dtos){
				int x = userRepository.save(dto.Map());
				Log.d(Tag, "UserDto saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getRoute(String accountId) {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getRouteWebService(true), params);
			SyncEntity<RouteDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<RouteDTO>>(){}.getType());
			Log.d(Tag, "RouteDTO Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<RouteDTO> dtos = syncEntity.getData();
			Log.d(Tag, "RouteDTO dtos.size(): "+ dtos.size());
			for(RouteDTO dto : dtos){
				int x = routeRepository.save(dto.Map());
				Log.d(Tag, "RouteDTO saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean getOutlet(String accountid) {
		String string = "";
		Hashtable<String, String> params = new Hashtable<String, String>();
		try {
			string = httpUtils.GetRequest(GlobalSettings.getOutletWebService(true), params);
			SyncEntity<OutletDTO> syncEntity = JsonConverter.deserialize(string, new TypeToken<SyncEntity<OutletDTO>>(){}.getType());
			Log.d(Tag, "OutletDTO Info: "+ syncEntity.getInfo()+" Is Success: " + syncEntity.isStatus());
			if(!syncEntity.isStatus())return false;
			List<OutletDTO> dtos = syncEntity.getData();
			Log.d(Tag, "OutletDTO dtos.size(): "+ dtos.size());
			for(OutletDTO dto : dtos){
				int x = outletRepository.save(dto.Map());
				Log.d(Tag, "OutletDTO saved: "+ x);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//TODO: check on adding 
	@Override
	public boolean AddUser(User user) {
		int x=0;
		try {
			x = userRepository.save(user);
			Log.d(Tag, "userRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(user.makeDTO());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putUserWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}

	@Override
	public boolean AddAccount(Account account) {
		int x = 0;
		try {
			x = accountRepository.save(account);
			Log.d(Tag, "accountRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(account.Map());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putAccountWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}
	
	@Override
	public boolean AddRoute(Route route) {
		int x=0;
		try {
			x = routeRepository.save(route);
			Log.d(Tag, "routeRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(route.Map());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putRouteWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}

	@Override
	public boolean AddOutlet(Outlet outlet) {
		int x=0;
		try {
			x = outletRepository.save(outlet);
			Log.d(Tag, "outletRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(outlet.Map());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putOutletWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}

	@Override
	public boolean AddCategory(Category category) {
		int x=0;
		try {
			x = categoryRepsitory.save(category);
			Log.d(Tag, "categoryRepsitory save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(category.Map());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putOutletWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}

	@Override
	public boolean Addproduct(Product product) {
		int x=0;
		try {
			x = productRepository.save(product);
			Log.d(Tag, "productRepository save: "+ x);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		String json = JsonConverter.MapObject(product.Map());
		OutGoing going = new OutGoing(UUID.randomUUID(), json, GlobalSettings.putOutletWebService(false));
		int y;
		try {
			y = outGoingRepository.save(going);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		Log.d(Tag, "outGoingRepository save: "+ y);
		return true;
	}	
}

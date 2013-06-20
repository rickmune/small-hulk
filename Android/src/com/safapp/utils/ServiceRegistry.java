package com.safapp.utils;

import java.util.EnumSet;
import java.util.HashMap;

import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.repositories.ICountryRepository;
import com.safapp.repositories.IOutGoingRepository;
import com.safapp.repositories.IOutletRepository;
import com.safapp.repositories.IProductRepository;
import com.safapp.repositories.IRouteRepository;
import com.safapp.repositories.IUserRepository;
import com.safapp.service.ILoginService;
import com.safapp.service.IMasterDataSync;
import com.safapp.service.IServiceBase;
import com.safapp.service.LoginService;
import com.safapp.service.MasterDataSync;
import com.safapp.utils.enums.ServiceCollection;
import com.safapp.utils.http.IHttpUtils;

public class ServiceRegistry {

	@SuppressWarnings("rawtypes")
	private static HashMap<Class, IServiceBase> repohashMap = new HashMap<Class, IServiceBase>();
	
	@SuppressWarnings({"unchecked" })
	public static <T extends IServiceBase> T get(Class<T> iService){
		IServiceBase t =  repohashMap.get(iService);
		if(t == null){
			t = getT(iService);
			repohashMap.put(iService, t);
		}
		return (T) t;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getT(Class<T> class1){
		T object = null;
		if(class1 == IMasterDataSync.class){
			object = (T) new MasterDataSync(RepositoryRegistry.get(ICountryRepository.class), RepositoryRegistry.get(IAccountRepository.class),
					RepositoryRegistry.get(IUserRepository.class), RepositoryRegistry.get(IProductRepository.class),
					RepositoryRegistry.get(ICategoryRepsitory.class), RepositoryRegistry.get(IRouteRepository.class),
					RepositoryRegistry.get(IOutletRepository.class), RepositoryRegistry.get(IOutGoingRepository.class), 
					get(IHttpUtils.class));
		}else if(class1 == ILoginService.class){
			object = (T) new LoginService(get(IMasterDataSync.class));
		}else{
			for(ServiceCollection dir : EnumSet.allOf(ServiceCollection.class)){
				if(dir.Iservice.equals(class1)){
					try {
						object = (T) dir.Service.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
		return object;
	}
}

package com.safapp.utils;

import java.util.EnumSet;
import java.util.HashMap;

import com.safapp.repositories.ICountryRepository;
import com.safapp.service.IMasterDataSync;
import com.safapp.service.IServiceBase;
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
			object = (T) new MasterDataSync(get(IHttpUtils.class), RepositoryRegistry.get(ICountryRepository.class));
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

package com.safapp.utils;

import java.util.EnumSet;
import java.util.HashMap;

import com.safapp.repositories.IBaseEntityRepository;
import com.safapp.utils.enums.RepositoryCollection;

public class RepositoryRegistry {

	@SuppressWarnings("rawtypes")
	private static HashMap<Class, IBaseEntityRepository> repohashMap = new HashMap<Class, IBaseEntityRepository>();
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends IBaseEntityRepository> T get(Class<T> iRepository){
		IBaseEntityRepository t =  repohashMap.get(iRepository);
		if(t == null){
			t = getT(iRepository);
			repohashMap.put(iRepository, t);
		}
		return (T) t;
	}
	
	@SuppressWarnings("unchecked")
	private static <T> T getT(Class<T> class1){
		T object = null;
		if(class1 == null){
		}else{
			for(RepositoryCollection dir : EnumSet.allOf(RepositoryCollection.class)){
				if(dir.Irepo.equals(class1)){
					try {
						object = (T) dir.Repo.newInstance();
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

package com.maina.formdata.repository;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumSet;
import java.util.HashMap;

import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.enums.RepositoryEnum;
import com.maina.formdata.repository.impl.DFormItemRepository;
import com.maina.formdata.repository.impl.DFormResultRepository;

public class Repositoryregistry {

	@SuppressWarnings("rawtypes")
	private static HashMap<Class, IRepositoryBase> repohashMap = new HashMap<Class, IRepositoryBase>();
	
	@SuppressWarnings("unchecked")
	public static <T extends IRepositoryBase> T get(Class<T> iRepository, IDataManager dataManager){
		IRepositoryBase t =  repohashMap.get(iRepository);
		if(t == null){
			t = getT(iRepository, dataManager);
			repohashMap.put(iRepository, t);
		}
		return (T) t;
	}
	@SuppressWarnings("unchecked")
	private static <T> T getT(Class<T> class1, IDataManager dataManager){
		T object = null;
		if(class1.equals(IDFormResultRepository.class)){
			object = (T) new DFormResultRepository(dataManager, get(IDFormResultItemRepository.class, dataManager));
		}else if(class1.equals(IDFormItemRepository.class)){
			object = (T) new DFormItemRepository(dataManager, get(IDFormItemRespondentTypeRepository.class, dataManager));
		}else{
			for(RepositoryEnum dir : EnumSet.allOf(RepositoryEnum.class)){
				if(dir.IRepo.equals(class1)){
					try {
						object = (T) dir.RealRepo.getDeclaredConstructor(IDataManager.class).newInstance(dataManager);//.newInstance();
						break;
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					}
				}
			}
		}Log.d("Repositoryregistry", "getT (object == null): "+(object== null));
		return object;
	}
}

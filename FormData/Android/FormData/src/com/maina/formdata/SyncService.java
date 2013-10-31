package com.maina.formdata;

import java.lang.reflect.Type;
import java.util.Hashtable;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.j256.ormlite.android.apptools.OrmLiteBaseService;
import com.maina.formdata.datamanager.Datamanager;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.BasicResponse;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.enums.DformItemType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.utils.DformItemTypeserializer;
import com.maina.formdata.utils.JsonConverter;
import com.maina.formdata.utils.ui.GenUtils;

public class SyncService extends OrmLiteBaseService<Datamanager> {

	private static final String Tag = "SyncService";
	protected IDataManager dataManager = null;
	private Thread thread2;
	private HttpUtils httpUtils;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
		Log.d(Tag, "SyncService started run=> ");
		return START_STICKY;
	}
	
	@Override
	public void onCreate(){
		super.onCreate();
		dataManager = getHelper();
		httpUtils = new HttpUtils();
		runThread();
	}
	
	private void runThread(){
		thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true){
					DformResultE resultE = null;
					try {
						resultE = Repositoryregistry.get(IDFormResultRepository.class, dataManager).getReadyToSend();
					} catch (Exception e) {e.printStackTrace();}
					if(resultE == null){
						try {
							Thread.sleep(1000 * 10);
							continue;
						} catch (Exception e) {e.printStackTrace();}
					}
					
					String json = JsonConverter.MapObject(resultE);
					String url = GenUtils.getUrl(dataManager) + "/api/client/form/publish";
					Hashtable<String, String> params = new Hashtable<String, String>();
					params.put("result", json);
					String res = null;
					try {
						res = httpUtils.PostRequest(url, params);
					} catch (Exception e) {
						e.printStackTrace();
					}
					Log.d(Tag, "runThread res: "+res);
					try {
						BasicResponse response = deserialize(res, new TypeToken<BasicResponse>() {}.getType());
						if(response.Status){
							resultE.setSent(true);
							Repositoryregistry.get(IDFormResultRepository.class, dataManager).save(resultE);
						}
					} catch (Exception e) {e.printStackTrace();}
				}
				
			}
		});
		if(!thread2.isAlive())
			thread2.start();
	}
	
	private <T> T deserialize(String json, Type t) throws Exception{
		Log.d(Tag, "deserialize b4: "+json);
		
		GsonBuilder b = new GsonBuilder();
		b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Log.d(Tag, "deserialize EntitySyncInfoserializer");
		b.registerTypeAdapter(DformItemType.class, new DformItemTypeserializer());
    	Gson gson = b.create();
    	JsonParser parser = new JsonParser();
    	try{
    		JsonObject object1 = parser.parse(json.trim()).getAsJsonObject();
    		Log.d(Tag, "(object1 == null): "+(object1 == null));
    		T y = gson.fromJson(object1, t);
    		Log.d(Tag, (y != null ? y.getClass().getName() : "is NUll"));
    		return y;
    	}catch(Exception e){
    		e.printStackTrace();
    		Log.d(Tag, e.getMessage());
			JsonArray array = parser.parse(json.trim()).getAsJsonArray();
			Log.d(Tag, "(array == null): "+(array == null));
			T y = gson.fromJson(array, t);
    		Log.d(Tag, (y != null ? y.getClass().getName() : "is NUll"));
			return y;
    	}
	}

}

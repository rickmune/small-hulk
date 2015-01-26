package com.maina.formdata;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.IBinder;
import android.util.Base64;
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
import com.maina.formdata.entity.ImagePath;
import com.maina.formdata.enums.DformItemType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.IImagePathRepository;
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
                    if(!sendForm()){
                        try {
                            Log.d(Tag, "runThread sleep");
                            Thread.sleep(1000 * GenUtils.SECONDS_TO_SLEEP);
                        } catch (Exception e) {e.printStackTrace();}
                    }
				}
			}
		});
		if(!thread2.isAlive())
			thread2.start();
	}

    private boolean sendForm(){
        DformResultE resultE = null;
        try {
            resultE = Repositoryregistry.get(IDFormResultRepository.class, dataManager).getReadyToSend();
        } catch (Exception e) {e.printStackTrace();}
        if(resultE == null){
            sendImage(getUnsentImage());
            try {
                Thread.sleep(1000 * GenUtils.SECONDS_TO_SLEEP);
                return false;
            } catch (Exception e) {e.printStackTrace();}
        } else {
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
            Log.d(Tag, "sendForm sleep");
            try {
                if (getServerResponse(res).Status) {
                    resultE.setSent(true);
                    Repositoryregistry.get(IDFormResultRepository.class, dataManager).save(resultE);
                    if(!sendImage(getByImageResult(resultE.getId()))){
                        try {
                            Log.d(Tag, "sendImage");
                            Thread.sleep(1000 * GenUtils.SECONDS_TO_SLEEP / 2);
                        } catch (Exception e) {e.printStackTrace();}
                        return sendImage(getByImageResult(resultE.getId()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private List<ImagePath> getByImageResult(UUID resultId)   {
        Log.d(Tag, "getByImageResult resultId: " + resultId);
        IImagePathRepository pathRepository = Repositoryregistry.get(IImagePathRepository.class, dataManager);
        try {
            return pathRepository.getByResultId(resultId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ImagePath> getUnsentImage(){
        IImagePathRepository pathRepository = Repositoryregistry.get(IImagePathRepository.class, dataManager);
        try {
            return pathRepository.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean sendImage(List<ImagePath> pathList){
        Log.d(Tag, "sendImage");
        if(pathList == null) return false;
        Log.d(Tag, "sendImage.size: " + pathList.size());
        for (ImagePath imagePath : pathList){
            String path = Environment.getExternalStorageDirectory() + "/FormData/" + imagePath.getImagePath();
            String json = mapObject(new Image(decodeImage(path), imagePath.getResultItemId().toString() + ".jpeg"));
            String url = GenUtils.getUrl(dataManager) + "/api/client/form/publishimage";
            Hashtable<String, String> params = new Hashtable<String, String>();
            params.put("result", json);
            String res = null;
            try {
                res = httpUtils.PostRequest(url, params);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(Tag, "runThread res: " + res);
            if (getServerResponse(res).Status) {
                try {
                    Repositoryregistry.get(IImagePathRepository.class, dataManager).deleteById(imagePath.getId());
                    if(deletePicFile(path)){
                        Log.d(Tag, "deletePicFile : " + path);
                    }
                }catch(Exception e){}
            }
        }
        return true;
    }

    private boolean deletePicFile(String path){
        return new File(path).delete();
    }

    private BasicResponse getServerResponse(String res){
        try {
            return deserialize(res, new TypeToken<BasicResponse>() {
            }.getType());
        }  catch (Exception e) {
            e.printStackTrace();
        }
         return new BasicResponse("Response is Null", false);
    }

    private String decodeImage(String path){
        Bitmap bitmap;
        String encodedImage="";
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 4;
            Log.d(Tag, "decodeImage Path: " + path);
            bitmap = BitmapFactory.decodeFile(path, options);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)) {
                Log.d(Tag, "Image to ByteArrayOutPutStream Done");
                byte[] bs = stream.toByteArray();
                encodedImage = Base64.encodeToString(bs, Base64.DEFAULT);
                Log.d(Tag, "encodedImage: " + encodedImage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return encodedImage;
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

    class Image{
        Image(String imagesBytes, String fileName) {
            ImagesBytes = imagesBytes;
            FileName = fileName;
        }
        public String ImagesBytes;
        public String FileName;
    }

    public  static <T> String mapObject(T base){
        String json = "";
        GsonBuilder b = new GsonBuilder();
        b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = b.create();
        Type typeOfSrc = new TypeToken<T>(){}.getType();
        json = gson.toJson(base, typeOfSrc);
        return json;
    }
}

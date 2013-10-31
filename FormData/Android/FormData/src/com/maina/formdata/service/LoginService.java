package com.maina.formdata.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.DBase;
import com.maina.formdata.dto.Dform;
import com.maina.formdata.dto.DformItem;
import com.maina.formdata.dto.DformItemAnswer;
import com.maina.formdata.dto.DformItemRespondentType;
import com.maina.formdata.dto.DformRespondentType;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.entity.DformE;
import com.maina.formdata.entity.DformItemAnswerE;
import com.maina.formdata.entity.DformItemE;
import com.maina.formdata.entity.DformItemRespondentTypeE;
import com.maina.formdata.entity.DformRespondentTypeE;
import com.maina.formdata.enums.DformItemType;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.htttputil.IHttpUtils;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.utils.DformItemTypeserializer;
import com.maina.formdata.utils.SyncEntity;
import com.maina.formdata.utils.ui.GenUtils;

public class LoginService implements ILoginService {

	private static final String Tag = "LoginService";
	IDFormRepository formRepository;
	IHttpUtils httpUtils;
	IDFormRespondentTypeRepository respondentTypeRepository;
	IDFormItemRepository formItemRepository;
	IDFormItemAnswerRepository formItemAnswerRepository;
	IDFormItemRespondentTypeRepository dformItemRespondentTypeErepository;
	IDUserRepository userRepository;
	IDataManager dataManager;
	
	public LoginService(IDFormRepository formRepository, IHttpUtils httpUtils,
			IDFormRespondentTypeRepository respondentTypeRepository,
			IDFormItemRepository formItemRepository, IDFormItemAnswerRepository formItemAnswerRepository,
			IDFormItemRespondentTypeRepository dformItemRespondentTypeErepository,
			IDUserRepository userRepository, IDataManager dataManager) {
		this.formRepository = formRepository;
		this.httpUtils = httpUtils;
		this.respondentTypeRepository = respondentTypeRepository;
		this.formItemRepository = formItemRepository;
		this.formItemAnswerRepository = formItemAnswerRepository;
		this.dformItemRespondentTypeErepository = dformItemRespondentTypeErepository;
		this.userRepository = userRepository;
		this.dataManager = dataManager;
	}

	@Override
	public boolean SyncForm(String[] params) throws Exception {
		Log.d(Tag, "SyncForm() starteds params.length: "+params.length);
		try {
			if(params.length == 1){
				return getFormIds(params[0]);
			}else{
				return login(params[0], params[1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		//if(!syncForm())throw new Exception(Tag+" Error @ syncForm");
	}
	
	private boolean login(String userName, String Password) throws Exception{
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("username", userName);
		params.put("password", Password);
		String res = httpUtils.GetRequest(GenUtils.getUrl(dataManager)+"/api/client/user/login", params);
		SyncEntity<UserDto> syncUser = deserialize(res, new TypeToken<SyncEntity<UserDto>>() {}.getType());
		if(!syncUser.Status){
			//TODO: get the message
			return false;
		}
		UserDto user = syncUser.getData().get(0);
		System.out.println(" User: "+user.toString());
		UserType t = (user.getUserType() == 2 ? UserType.TDR : UserType.Admin);
		DUserE dUserE = new DUserE(user.getId(), user.getUsername(), user.getPassword(), 
				user.getFullname(), t, user.getEmail(), user.getPhoneNumber(), user.getClientId(),
				user.getLocationId());
		userRepository.save(dUserE);
		return getFormIds(user.getClientId().toString());
	}
	
	private boolean getFormIds(String ClientId) throws Exception{
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("clientid", ClientId);
		String res = httpUtils.GetRequest(GenUtils.getUrl(dataManager)+"/api/client/form/getformids", params);
		SyncEntity<DBase> ids = deserialize(res, new TypeToken<SyncEntity<DBase>>() {}.getType());
		if(!ids.getStatus()){
			return false;
		}
		List<DBase> formIds = ids.getData();
		boolean y = false;
		for(DBase id : formIds){
			y = getFormById(id.getId());
		}
		return y;
	}
	
	private boolean getFormById(UUID formId)throws Exception{
		Hashtable<String, String> params = new Hashtable<String, String>();
		params.put("formid", formId.toString());
		String res = httpUtils.GetRequest(GenUtils.getUrl(dataManager)+"/api/client/form/getform", params);
		SyncEntity<Dform> form = deserialize(res, new TypeToken<SyncEntity<Dform>>() {}.getType());
		if(!form.Status){
			//TODO: get the message
			return false;
		}
		Dform dform = form.getData().get(0);
		if(dform == null){
			//TODO: get the message
			return false;
		}
		DformE dformE = new DformE(dform.getId(), dform.getName());
		Log.d(Tag, "syncForm (formRepository == null): "+(formRepository == null));
		DformE x = formRepository.save(dformE);
		Log.d(Tag, "Saved forms: "+x.getName());
		List<DformRespondentType> respondentTypes =  dform.getRespondentTypes();
		Log.d(Tag, "respondentTypes: "+respondentTypes.size());
		List<DformRespondentTypeE> respondentTypeEs = new ArrayList<DformRespondentTypeE>();
		for(DformRespondentType type : respondentTypes){
			respondentTypeEs.add(new DformRespondentTypeE(type.getId(), type.getName(), type.getCode(), dformE));
		}
		int rtype = respondentTypeRepository.saveBatch(respondentTypeEs);
		Log.d(Tag, "Saved respondentType: "+rtype);
		List<DformItem> dformItems = dform.getFormItems();
		for(DformItem item : dformItems){
			DformItemE dformItemE = new DformItemE(item.getId(), item.getLabel(), item.getFormItemType(), 
					item.isIsRequired(), dformE, item.getOrder(), item.getValidationText(),
					item.getValidationRegex());
			formItemRepository.save(dformItemE);
			List<DformItemAnswer> answers = item.getFormItemAnswer();
			List<DformItemAnswerE> answerEs = new ArrayList<DformItemAnswerE>();
			for(DformItemAnswer answer : answers){
				answerEs.add(new DformItemAnswerE(answer.getId(), answer.getText(), answer.getValue(), dformItemE));
			}
			formItemAnswerRepository.saveBatch(answerEs);
			List<DformItemRespondentType> itemRespondentTypes = item.getFormItemRespondentTypes();
			List<DformItemRespondentTypeE> itemRespondentTypeEs = new ArrayList<DformItemRespondentTypeE>();
			for(DformItemRespondentType itemRespondentType : itemRespondentTypes){
				itemRespondentTypeEs.add(new DformItemRespondentTypeE(itemRespondentType.getId(), 
						itemRespondentType.getRespondentTypeId(), itemRespondentType.getFormItemId()));
			}
			dformItemRespondentTypeErepository.saveBatch(itemRespondentTypeEs);
		}
		return true;
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

		
	private String makreResponse(){
		return "{\"Name\":\"Safaricom\",\"RespondentTypes\":[{\"Name\":\"M-PESA\",\"Code\":\"M\",\"Id\":\"9a8253a1-0402-429e-9ac3-9094429fa6a5\"},{\"Name\":\"Dealer\",\"Code\":\"D\",\"Id\":\"213fae70-c546-42bd-bcd2-c4885f874de6\"}],\"FormItems\":[{\"Order\":2,\"Label\":\"What is you Gender\",\"FormItemType\":2,\"FormItemRespondentTypes\":[{\"RespondentTypeId\":\"213fae70-c546-42bd-bcd2-c4885f874de6\",\"FormItemId\":\"9f2b67d3-db9f-4f05-b8d9-3b22f66765d9\",\"Id\":\"059a6182-9186-4ba5-ad1e-c90434c58f8d\"}],\"FormItemAnswer\":[{\"Text\":\"Female\",\"Value\":\"F\",\"Id\":\"62365bde-680b-43e9-bc96-81c2861d5fb8\"},{\"Text\":\"Male\",\"Value\":\"M\",\"Id\":\"02a639a1-98f8-4262-b485-d401518458d0\"}],\"IsRequired\":true,\"Id\":\"9f2b67d3-db9f-4f05-b8d9-3b22f66765d9\"},{\"Order\":3,\"Label\":\"Which of the following Product do you use?\",\"FormItemType\":3,\"FormItemRespondentTypes\":[{\"RespondentTypeId\":\"9a8253a1-0402-429e-9ac3-9094429fa6a5\",\"FormItemId\":\"92624c90-08d5-4544-811a-e3505d3eb60f\",\"Id\":\"335c75f4-48e9-48bd-b941-5fa3ddb4ac37\"}],\"FormItemAnswer\":[{\"Text\":\"A\",\"Value\":\"Aple\",\"Id\":\"7f9a67dc-00e6-4400-bfe6-19f9e20ed5d1\"},{\"Text\":\"Banana\",\"Value\":\"B\",\"Id\":\"43405fc3-b9b7-493b-bd78-a81500d98162\"},{\"Text\":\"Mango\",\"Value\":\"M\",\"Id\":\"1f94ce19-0509-4663-92de-af83306ff85b\"}],\"IsRequired\":true,\"Id\":\"92624c90-08d5-4544-811a-e3505d3eb60f\"},{\"Order\":1,\"Label\":\"What is you name?\",\"FormItemType\":1,\"FormItemRespondentTypes\":[{\"RespondentTypeId\":\"9a8253a1-0402-429e-9ac3-9094429fa6a5\",\"FormItemId\":\"0396aba1-7b06-4a1e-bb93-fb725024c6c3\",\"Id\":\"2429a54b-bea3-44ae-872b-37a510bb35e2\"}],\"FormItemAnswer\":[],\"IsRequired\":true,\"Id\":\"0396aba1-7b06-4a1e-bb93-fb725024c6c3\"}],\"Id\":\"2bfd38a8-fe58-4dc2-b5f6-139962f5ecbb\"}";
	}
}

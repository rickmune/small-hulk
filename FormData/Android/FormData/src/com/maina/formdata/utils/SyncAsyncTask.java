package com.maina.formdata.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormItemRespondentTypeRepository;
import com.maina.formdata.repository.IDFormRepository;
import com.maina.formdata.repository.IDFormRespondentTypeRepository;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.service.ILoginService;
import com.maina.formdata.service.LoginService;

public class SyncAsyncTask extends AsyncTask<String, Void, String[]> {

	private Context context;
	private IDataManager dataManager;
	private ProgressDialog dialog;
	SyncEntity<UserDto> entity ;
	
	public SyncAsyncTask(Context context, IDataManager dataManager, ProgressDialog dialog) {
		super();
		this.context = context;
		this.dataManager = dataManager;
		this.dialog = dialog;
		entity = new SyncEntity<UserDto>();
	}

	protected void onPreExecute() {
		dialog = new ProgressDialog(context);
		dialog.setMessage("Fetching data...");
		dialog.setIndeterminate(true);
		dialog.setCancelable(true);
		dialog.show();
	}
	
	@Override
	protected String[] doInBackground(String... params){
		ILoginService loginService = new LoginService(Repositoryregistry.get(IDFormRepository.class, dataManager), 
				new HttpUtils(), 
				Repositoryregistry.get(IDFormRespondentTypeRepository.class, dataManager), 
				Repositoryregistry.get(IDFormItemRepository.class, dataManager), 
				Repositoryregistry.get(IDFormItemAnswerRepository.class, dataManager), 
				Repositoryregistry.get(IDFormItemRespondentTypeRepository.class, dataManager),
				Repositoryregistry.get(IDUserRepository.class, dataManager), dataManager);
		try {
			Log.d("SyncService", "SyncForm() called with params.length: "+ params.length);
			loginService.SyncForm(params);
			return params;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(String[] returns) {
		if(dialog == null)return;
		else if(dialog != null && dialog.isShowing())dialog.dismiss();
		if(returns != null && returns.length > 1){
			try {
				entity = Repositoryregistry.get(IDUserRepository.class, dataManager).login(
						returns[1], returns[2]);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		}
	}
	
}

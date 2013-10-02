package com.maina.formdata.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maina.formdata.FormListActivity;
import com.maina.formdata.R;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DformItemE;
import com.maina.formdata.entity.DformItemRespondentTypeE;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.entity.DformResultItemE;
import com.maina.formdata.enums.DformItemType;
import com.maina.formdata.repository.IDFormItemAnswerRepository;
import com.maina.formdata.repository.IDFormItemRepository;
import com.maina.formdata.repository.IDFormResultRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.service.DFormLocationListener;
import com.maina.formdata.utils.CloudConstants;
import com.maina.formdata.utils.CloudManager;

public class FormItemActivity extends FormItemBase {

	private static final String TAG = "FormItemActivity";
	List<DformItemE> dformItemE;
	private ProgressDialog dialog;
	private Button next;
	private Button cancel;
	private Button save;
	private int CurrentItem;
	DformItemE dformItem = null;
	DFormLocationListener listener;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("FormItemActivity","onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formitem_activity);
		Bundle bundle = getIntent().getExtras();
		String formId = bundle.getString(FormListActivity.FORMID);
		String formName = bundle.getString(FormListActivity.FORMNAME);
		RespondentId = UUID.fromString( bundle.getString(FormListActivity.RESPONDENTTYPE));
		dynamicView = (LinearLayout)findViewById(R.id.dynamicView);
		btnSpinner = (Button)findViewById(R.id.pop_up);
		itemlabel = (TextView)findViewById(R.id.itemlabel);
		lblFormT = (TextView)findViewById(R.id.surveylabel);
		next = (Button)findViewById(R.id.next);
		cancel = (Button)findViewById(R.id.cancel);
		save = (Button)findViewById(R.id.save);
		next.setOnClickListener(new BtnClicked());
		cancel.setOnClickListener(new BtnClicked());
		save.setOnClickListener(new BtnClicked());
		lblFormT.setText("Form: "+formName);
		new GetFormItems().execute(UUID.fromString(formId));
		
		dformResultE = new DformResultE(UUID.randomUUID(), RespondentId, UUID.fromString(formId), 
				resultItemEs, false, false);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		listener = new DFormLocationListener(FormItemActivity.this);
	}
	
	private class GetFormItems extends AsyncTask<UUID, Void, Void>{

		@Override
		protected void onPreExecute() {			
			dialog = new ProgressDialog(FormItemActivity.this);
			dialog.setMessage("Loading");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected Void doInBackground(UUID... formIds) {
			try {
				Log.d("FormItemActivity","doInBackground (dataManager == null): "+(dataManager == null));
				dformItemE = Repositoryregistry.get(IDFormItemRepository.class, 
						(IDataManager)CloudManager.getObject(CloudConstants.DATABASEMANAGER.value)).getByFormId(formIds[0]);
				Log.d("GetFormItems","dformItemE.size(): "+dformItemE.size());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(Void voids) {
			if(dialog != null && dialog.isShowing())dialog.dismiss();
			CurrentItem = 0;
			boolean show = false;
			do{
				show = showQuestion();
			}while(!show);
			if(show)
				makeItems();
		}
		
	}

	private boolean showQuestion(){
		boolean show = false;
		if(dformItemE != null){
			Log.d("FormItemActivity"," showQuestion(): "+dformItemE.size());
			if(CurrentItem < dformItemE.size()){
				DformItemE dformItem = dformItemE.get(CurrentItem);
				List<DformItemRespondentTypeE> itemEs = dformItem.getFormItemRespondentTypes();
				for(DformItemRespondentTypeE typeE : itemEs){
					if(typeE.getRespondentTypeId().equals(RespondentId)){
						show = true;
						break;
					}
				}
				if(!show)CurrentItem++;
			}else{
				show = true;
			}
		}		
		return show;
	}
	
	private void makeItems(){
		dynamicView.removeAllViews();
		if(dformItemE != null){
			Log.d("FormItemActivity"," makeItems(): "+dformItemE.size());
			if(CurrentItem < dformItemE.size()){
				dformItem = dformItemE.get(CurrentItem);
				formItemId = dformItem.getId();
				itemlabel.setText(dformItem.getLabel());
				if(dformItem.getFormItemType().equals(DformItemType.Text)){
					makeTextbox(dformItem.getLabel(), dformItem.getValidationRegex());
				}else if(dformItem.getFormItemType().equals(DformItemType.DropdownList)){
					dropDownType(dformItem.getId());
					//makeDropdown(dformItem.getId(), 0);
				}else if(dformItem.getFormItemType().equals(DformItemType.MultiChoice)){
					makeCheckboxList(dformItem.getId(), "");
				}
				CurrentItem++;
			}else{
				itemlabel.setText("Form Completed.. Click on Save");
				Log.d("FormItemActivity"," makeItems() Done with Questions ");
				save.setVisibility(View.VISIBLE);
				next.setVisibility(View.GONE);
			}
		}
	}
	
	void dropDownType(UUID dformItemId){
		System.out.println("dropDownType");
		List<Pair<String, String>> list = new ArrayList<Pair<String,String>>();
		try {
			list = Repositoryregistry.get(IDFormItemAnswerRepository.class, dataManager)
					.getAnswerItems(dformItemId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("dropDownType list: "+list.size());
		if(list.size() > 4){
			makeDropdown(dformItemId, 0);
		}else{
			makeRadioButton(list);
		}
	}

	private class BtnClicked implements Button.OnClickListener{

		@Override
		public void onClick(View v) {
			if(v.getId() == R.id.next){
				if(dformItem.isIsRequired()){
					if(AnswerText.equals("")){
						Toast.makeText(FormItemActivity.this, "This is a required Question", Toast.LENGTH_LONG)
						.show();
						return;
					}
				}
				if(isText){
					if(!IsValid(dformItem.getValidationRegex(), AnswerText)){
						Toast.makeText(FormItemActivity.this, "Error! "+dformItem.getValidationText(), Toast.LENGTH_LONG)
						.show();
						return;
					}
					List<String> results = new ArrayList<String>();
					results.add(AnswerText);
					itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
							AnswerText+",");
					isText = false;
				}else if(isRadio){
					List<String> results = new ArrayList<String>();
					results.add(AnswerText);
					itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
							AnswerText+",");
					isRadio = false;
				}
				resultItemEs.add(itemE);
				itemE = null;
				AnswerText = "";
				boolean show = false;
				do{
					show = showQuestion();
				}while(!show);
				if(show)
					makeItems();
				
			}else if(v.getId() == R.id.cancel){
				Toast.makeText(FormItemActivity.this, "Cancel Operation", Toast.LENGTH_SHORT).show();
				finish();
			}else if(v.getId() == R.id.save){
				Toast.makeText(FormItemActivity.this, "Saving Operation", Toast.LENGTH_SHORT).show();
				dformResultE.setFormResultItem(resultItemEs);
				dformResultE.setDone(true);
				double location [] = listener.getLocations();
				dformResultE.setLatitude(location[0]);
				dformResultE.setLongitude(location[1]);
				try {
					Repositoryregistry.get(IDFormResultRepository.class, 
							(IDataManager)CloudManager.getObject(CloudConstants.DATABASEMANAGER.value)).Saveresult(dformResultE);
				} catch (Exception e) {e.printStackTrace();}
				finish();
			}
		}
	}
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	}
	
	public boolean IsValid(String RegexExpression, String response)
    {
    	Log.d("IsValid RegexExpression: ", RegexExpression + " response: "+response);
        if (RegexExpression == null || RegexExpression.trim().equals(""))
            return true;
        return RegValidate(RegexExpression, response);
    }
	
	private static boolean RegValidate(String expression, String value){
 		if(expression == null || value == null) return true;
 		Pattern pattern;
 		Matcher matcher;
 		pattern = Pattern.compile(expression);
 		matcher = pattern.matcher(value);
        return matcher.matches();
 	}
}

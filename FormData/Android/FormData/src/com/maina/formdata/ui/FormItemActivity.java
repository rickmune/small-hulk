package com.maina.formdata.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;

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

public class FormItemActivity extends FormItemBase implements LocationListener {

	private static final String TAG = "FormItemActivity";
	List<DformItemE> dformItemE;
	private ProgressDialog dialog;
	private Button next;
	private Button cancel;
	private Button save;
	private int CurrentItem;
	DformItemE dformItem = null;
	DFormLocationListener listener;
	String formId, formName, userName, locationId;
    private LocationManager locationManager;
    private String provider;
    protected Location location;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("FormItemActivity","onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formitem_activity);
		setTitle("Questions");
		Bundle bundle = getIntent().getExtras();
		formId = bundle.getString(FormListActivity.FORMID);
		formName = bundle.getString(FormListActivity.FORMNAME);
		userName = bundle.getString(FormListActivity.USERNAME);
		locationId = bundle.getString(FormListActivity.LOCATIONID);
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
        if(savedInstanceState == null) {
            Log.d("FormItemActivity","onCreate savedInstanceState == null");
            new GetFormItems().execute(UUID.fromString(formId));
        }
        //turnGPSOn();
        //initializeLocation();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
        Log.d("FormItemActivity","onSaveInstanceState");
		outState.putString(FormListActivity.FORMID, formId);
		outState.putString(FormListActivity.FORMNAME, formName);
		outState.putString(FormListActivity.USERNAME, userName);
		outState.putString(FormListActivity.LOCATIONID, locationId);
		outState.putString(FormListActivity.RESPONDENTTYPE, RespondentId.toString());
        outState.putInt("CurrentItem", CurrentItem);
        outState.putString("formItemId", formItemId.toString());
        CloudManager.putObject(CloudConstants.DFORMITEM.value, dformItem, false);
        CloudManager.putObject(CloudConstants.RESULTITEME.value, resultItemEs, false);
        CloudManager.putObject(CloudConstants.DFORMRESULTE.value, dformResultE, false);
        CloudManager.putObject(CloudConstants.DFORMITEME.value, dformItemE, false);
        CloudManager.putObject(CloudConstants.SUMMARY.value, summary, false);
		super.onSaveInstanceState(outState);
	}

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("FormItemActivity","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
        formId = savedInstanceState.getString(FormListActivity.FORMID);
        formName = savedInstanceState.getString(FormListActivity.FORMNAME);
        userName = savedInstanceState.getString(FormListActivity.USERNAME);
        locationId = savedInstanceState.getString(FormListActivity.LOCATIONID);
        RespondentId = UUID.fromString(savedInstanceState.getString(FormListActivity.RESPONDENTTYPE));
        CurrentItem = savedInstanceState.getInt("CurrentItem");
        formItemId = UUID.fromString(savedInstanceState.getString("formItemId"));
        try {
            dformItem = (DformItemE)CloudManager.getObject(CloudConstants.DFORMITEM.value);
            dformResultE = (DformResultE)CloudManager.getObject(CloudConstants.DFORMRESULTE.value);
            resultItemEs = (List<DformResultItemE>)CloudManager.getObject(CloudConstants.RESULTITEME.value);
            dformItemE = (List<DformItemE>)CloudManager.getObject(CloudConstants.DFORMITEME.value);
            summary =  (List<Pair<String, String>>)CloudManager.getObject(CloudConstants.SUMMARY.value);
        }catch (Exception e){e.printStackTrace();}


    }
	
	@Override
	protected void onResume() {
        Log.d("FormItemActivity", "onResume provider: " + provider);
		super.onResume();
        initializeLocation();
        listener = new DFormLocationListener(this);
	}

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
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
            summary = new ArrayList<Pair<String, String>>();
			String Version = "And-" + getResources().getString(R.string.v_number);
			resultItemEs = new ArrayList<DformResultItemE>();
			dformResultE = new DformResultE(UUID.randomUUID(), RespondentId, UUID.fromString(formId), 
					resultItemEs, false, false, userName, UUID.fromString(locationId), new Date(), Version);
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
	
	private boolean showQuestion(){//} throws Exception{
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
		}else{
            Log.d("FormItemActivity"," showQuestion(): dformItemE == null");
            //throw new Exception("Done");
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
                summary.add(new Pair<String, String>(dformItem.getLabel(), ""));
                Log.d("FormItemActivity"," summary size: "+summary.size() + " String: " + dformItem.toString());
				if(dformItem.getFormItemType().equals(DformItemType.Text)){
					makeTextbox(dformItem.getLabel(), dformItem.getValidationRegex());
				}else if(dformItem.getFormItemType().equals(DformItemType.DropdownList)){
					dropDownType(dformItem.getId());
					//makeDropdown(dformItem.getId(), 0);
				}else if(dformItem.getFormItemType().equals(DformItemType.MultiChoice)){
					makeCheckboxList(dformItem.getId(), "");
				}else if(dformItem.getFormItemType().equals(DformItemType.Date)){
                    makeDate("");
                } else if(dformItem.getFormItemType().equals(DformItemType.Image)){
                    captureImage(dformItem.getId());
                }
				CurrentItem++;
			}else{
                itemlabel.setTextSize(24);
                itemlabel.setTextColor(Color.BLUE);
				itemlabel.setText("Form Completed.. Click on Save");
                surveySummary(summary);
               // itemlabel.setText(summary());
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
            Log.d(TAG, "BtnClicked onClick view Id: " + (v == null ? "Null" : v.getId())
                + " NextBtb: " + R.id.next);
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
					if(view != null){
						Log.d(TAG, "TextInput is not null");
						/*view.setInputType(InputType.TYPE_NULL);*/
						InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
						imm.toggleSoftInput(InputMethodManager.HIDE_NOT_ALWAYS, 0);
						//getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
						
					}
					List<String> results = new ArrayList<String>();
                    Log.d("FormItemActivity", "isText AnswerText: "+AnswerText);
					AnswerText = removeComma(AnswerText);
                    Log.d("FormItemActivity", "isText AnswerText: "+AnswerText);
					results.add(AnswerText);
					itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
							AnswerText+",", false);
					isText = false;
                    addAnswerToSum(AnswerText);
				}else if(isRadio){
					List<String> results = new ArrayList<String>();
                    String [] answs = AnswerText.split(SEP);
					results.add(answs[0]);
					itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
                            answs[0]+",", false);
					isRadio = false;
                    addAnswerToSum(answs[1]);
				}else if(isDate){
                    List<String> results = new ArrayList<String>();
                    Log.d("FormItemActivity", "isDate AnswerText: "+AnswerText);
                    AnswerText = removeDoubleQ(AnswerText);
                    Log.d("FormItemActivity", "isDate AnswerText: "+AnswerText);
                    results.add(AnswerText);
                    itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
                            AnswerText+",", false);
                    isDate = false;
                    addAnswerToSum(AnswerText);
                } else if(isImage){
                    List<String> results = new ArrayList<String>();
                    Log.d("FormItemActivity", "isImage AnswerText: "+AnswerText);
                    results.add(AnswerText);
                    itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
                            AnswerText+",", true);
                    isImage = false;
                }
				resultItemEs.add(itemE);
				itemE = null;
				AnswerText = "";
				boolean show ;
                do{
                    show = showQuestion();
                }while(!show);
                if(show) makeItems();

			}else if(v.getId() == R.id.cancel){
				Toast.makeText(FormItemActivity.this, "Cancel Operation", Toast.LENGTH_SHORT).show();
				cancelSurv();
			}else if(v.getId() == R.id.save){
				Toast.makeText(FormItemActivity.this, "Saving Operation", Toast.LENGTH_SHORT).show();
				dformResultE.setFormResultItem(resultItemEs);
				dformResultE.setDone(true);
				//double location [] = listener.getLocations();
                if(location == null){
                    location = listener.getLocation();
                }
				dformResultE.setLatitude((location == null ? 0 : location.getLatitude()));
				dformResultE.setLongitude((location == null ? 0 : location.getLongitude()));
				try {
					Repositoryregistry.get(IDFormResultRepository.class, 
							(IDataManager)CloudManager.getObject(CloudConstants.DATABASEMANAGER.value)).Saveresult(dformResultE);
				} catch (Exception e) {e.printStackTrace();}
				finish();
			}
		}
	}
	
	private String removeComma(String ans){
        CharSequence cs = "^^";
        CharSequence c = ",";
		return ans.replace(c, cs);
	}

    private String removeDoubleQ(String ans){
        CharSequence cs = "";
        CharSequence c = "\"";
        return ans.replace(c, cs);
    }
	
	@Override
	protected void onDestroy() {
		Log.d(TAG, "onDestroy()");
		super.onDestroy();
	}
	
	public boolean IsValid(String RegexExpression, String response){
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK){
			cancelSurv();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	private void cancelSurv(){
		new AlertDialog.Builder(this)
		.setTitle("Warning")
		.setMessage("Your progress will be lost")
		.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}
				})
		.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).create().show();
	}

    private void addAnswerToSum(String answer){
        int index = summary.size() - 1;
        String pair1 = summary.get(index).first;
        summary.remove(index);
        summary.add(index, new Pair<String, String> (pair1, answer));
    }

    @Override
    public void onLocationChanged(Location location) {
        this.location = location;
        try {
            Toast.makeText(this, "location: ["+location.getLatitude() + ", " + location.getLongitude() + "]", Toast.LENGTH_LONG).show();
        }catch (Exception e){e.printStackTrace();}

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void initializeLocation(){
        // Get the location manager
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        // getting GPS status
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // getting network status
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!isGPSEnabled && !isNetworkEnabled) {
            // no network provider is enabled
        }else {
            // this.canGetLocation = true;
            if (isNetworkEnabled) {
                provider =  LocationManager.NETWORK_PROVIDER;
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                Log.d("FormItemActivity Network", "Network Enabled");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        Log.d("FormItemActivity Network", "location != null");
                    } else {
                        Log.d("FormItemActivity Network", "location == null");
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled) {
                provider =  LocationManager.GPS_PROVIDER;
                if (location == null) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                    Log.d("FormItemActivity GPS", "GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Log.d("FormItemActivity GPS", "location != null");
                        } else {
                            Log.d("FormItemActivity GPS", "location == null");
                        }
                    }
                }
            }
        }


        // Define the criteria how to select the locatioin provider -> use
        // default
        //Criteria criteria = new Criteria();
        //provider = locationManager.getBestProvider(criteria, false);
        Log.d("FormItemActivity", "Provider " + provider + " has been selected.");
        //location = locationManager.getLastKnownLocation(provider);
        // Initialize the location fields
        if (location != null) {
            Log.d("FormItemActivity", "location != null ");
            onLocationChanged(location);
        } else {
            Log.d("FormItemActivity", "location == null ");
        }
    }

    private void turnGPSOn(){
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if(!provider.contains("gps")){ //if gps is disabled
            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }
}

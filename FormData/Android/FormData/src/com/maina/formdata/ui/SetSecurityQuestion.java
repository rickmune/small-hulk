package com.maina.formdata.ui;

import java.util.ArrayList;
import java.util.Hashtable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.maina.formdata.R;
import com.maina.formdata.dto.BasicResponse;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.htttputil.IHttpUtils;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.utils.ui.GenUtils;
import com.maina.formdata.utils.ui.ListDialog;

public class SetSecurityQuestion extends BaseActivity{

	private static final int QUESTION = 201;
	private EditText SecurityAnswer;
	private ProgressDialog dialog;
	private String Error;
	private int QuestionId;
	IHttpUtils httpUtils;
	UserDto dto;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_question);
        setTitle("Change Password");
        if(savedInstanceState == null) dto = FromBundle(getIntent().getExtras(), new UserDto());
        if(httpUtils == null) httpUtils = new HttpUtils();
        (findViewById(R.id.sq_question)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callPopUp(ListDialog.class, QUESTION, "Select Question");
			}
		});
        
        SecurityAnswer = (EditText)findViewById(R.id.sq_answer);
        (findViewById(R.id.cancel_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtras(ToBundle(new Bundle(), dto));
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
        
        (findViewById(R.id.ok_btn)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validate()){
					new SetSecQuestion().execute();
				}
			}
		});
	}
	
	private boolean validate(){
		if(SecurityAnswer.getText() == null || SecurityAnswer.getText().toString().equals("")){
			return false;
		}
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//super.onActivityResult(requestCode, resultCode, data);
		if(resultCode == RESULT_OK){
			if (requestCode == QUESTION){
				String result = data.getStringExtra(ListDialog.RESULTNAME);
				QuestionId = Integer.parseInt(data.getStringExtra(ListDialog.RESULTID));
				
				((TextView)findViewById(R.id.sq_question_text)).setText(result);
				((Button)findViewById(R.id.sq_question)).setText(result);
			}
		}
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState = ToBundle(outState, dto);
		super.onSaveInstanceState(outState);
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		dto = FromBundle(savedInstanceState, new UserDto());
		super.onRestoreInstanceState(savedInstanceState);
	}
	
	private void callPopUp(@SuppressWarnings("rawtypes") Class cName, int resultCode, String t){
		ArrayList<String> map = new ArrayList<String>();
		Intent intent = new Intent(this, cName);
		Bundle bundle = new Bundle();
		bundle.putString("ClassName", "com.maina.formdata.repository.ISecurityQuestionRepository");
		bundle.putString("MethodName", "getSecurityQuestion");
		bundle.putString("title", t);
		bundle.putStringArrayList("Params", map);
		intent.putExtras(bundle);
		startActivityForResult(intent, resultCode);
	}
	
	class SetSecQuestion extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(SetSecurityQuestion.this);
			dialog.setMessage("Contacting Server, Please Wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String url = GenUtils.getUrl(dataManager)+"/api/client/user/securityquestion";
			String resp = null;
			
			try {
				Hashtable<String, String> table = new Hashtable<String, String>();
				table.put("userId", dto.getId().toString());
				table.put("qId", QuestionId+"");
				table.put("answer", SecurityAnswer.getText().toString());
				
				resp = httpUtils.PostRequestWithKey(url, table);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return resp;
		}
		
		@Override
		protected void onPostExecute(String returns) {
			if(dialog != null && dialog.isShowing())dialog.dismiss();
			if(returns == null){
				Error = "Server did not respond. Ensure your network is Ok the try changing your password again";
				((TextView)findViewById(R.id.sq_header)).setText(Error);
				Toast.makeText(SetSecurityQuestion.this, Error, Toast.LENGTH_LONG).show();
			}else{
				try {
					BasicResponse basicResponse = deserialize(returns, new TypeToken<BasicResponse>() {}.getType());
					if(basicResponse.Status){
						System.out.println("basicResponse.Status: "+basicResponse.Status);
						Toast.makeText(SetSecurityQuestion.this, "Security Password Changed", 
								Toast.LENGTH_LONG).show();
						dto.setSecurityQuestionId(QuestionId);
						dto.setSecurityAnswer(SecurityAnswer.getText().toString());
						dto.setIsSecuritySet(true);
						UserType t = (dto.getUserType() == 2 ? UserType.TDR : UserType.Admin);
						Repositoryregistry.get(IDUserRepository.class, dataManager).save(new DUserE(dto.getId(), dto.getUsername(), dto.getPassword(), 
								dto.getFullname(), t, dto.getEmail(), dto.getPhoneNumber(), dto.getClientId(),
								dto.getLocationId(), dto.getClientName(), dto.isIsPasswordChanged(),
								dto.isIsSecuritySet(), dto.getSecurityQuestionId(), dto.getSecurityAnswer()));
						System.out.println("About to return");
						Intent intent = new Intent();
						intent.putExtras(ToBundle(new Bundle(), dto));
						setResult(RESULT_OK, intent);
						finish();
					}else{
						Error = "Security Question not set";
						((TextView)findViewById(R.id.sq_header)).setText(Error);
						Toast.makeText(SetSecurityQuestion.this, Error, Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) { 
					e.printStackTrace();
					Error = "Error Occured. Ensure your network is Ok the try changing your password again";
					((TextView)findViewById(R.id.sq_header)).setText(Error);
					Toast.makeText(SetSecurityQuestion.this, Error, Toast.LENGTH_LONG).show();
				}
			}
		}
		
	}
	
}

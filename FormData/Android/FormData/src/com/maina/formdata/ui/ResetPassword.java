package com.maina.formdata.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.UUID;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
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
import com.maina.formdata.entity.DFormMessages;
import com.maina.formdata.entity.DUserE;
import com.maina.formdata.enums.MessageTypes;
import com.maina.formdata.enums.UserType;
import com.maina.formdata.htttputil.HttpUtils;
import com.maina.formdata.htttputil.IHttpUtils;
import com.maina.formdata.repository.IDUserRepository;
import com.maina.formdata.repository.IMessageRepository;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.utils.MakePWD;
import com.maina.formdata.utils.ui.GenUtils;
import com.maina.formdata.utils.ui.ListDialog;

public class ResetPassword extends BaseActivity{

	private static final int QUESTION = 301;
	private EditText SecurityAnswer;
	private ProgressDialog dialog;
	IHttpUtils httpUtils;
	private String Error;
	private UserDto user;
	private int QuestionId;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);
        setTitle("Reset Password");
        if(httpUtils == null) httpUtils = new HttpUtils();
        (findViewById(R.id.rst_question)).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callPopUp(ListDialog.class, QUESTION, "Select Question");
			}
		});
        
        SecurityAnswer = (EditText)findViewById(R.id.rst_answer);
        
        ((Button)(findViewById(R.id.cancel_btn))).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finish();
			}
		});
        
        ((Button)(findViewById(R.id.ok_reset))).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(validate()){
					System.out.println("Reset Password");
					new ResetPwd().execute();
				}else{
					((TextView)findViewById(R.id.rst_header)).setText(Error);
					Toast.makeText(ResetPassword.this, Error, Toast.LENGTH_LONG).show();
				}
			}
		});
	
        try {
			user = Repositoryregistry.get(IDUserRepository.class, dataManager).getUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if (requestCode == QUESTION){
				QuestionId = Integer.parseInt(data.getStringExtra(ListDialog.RESULTID));
				String result = data.getStringExtra(ListDialog.RESULTNAME);
				((TextView)findViewById(R.id.rst_question_text)).setText(result);
				((Button)(findViewById(R.id.ok_reset))).setText(result);
			}
		}
	}
	
	private boolean validate(){
		Error = "";
		if(SecurityAnswer.getText() == null || 
				SecurityAnswer.getText().toString().equals("")){
			Error = "Answer is empty ";
			return false;
		}else{
			if(!SecurityAnswer.getText().toString().equalsIgnoreCase(user.getSecurityAnswer())
					&& QuestionId != user.getSecurityQuestionId()){
				System.out.println("Reset Password pwd: "+user.getSecurityAnswer());
				Error = "Incorrect Answer ";
				return false;
			}
		}
		return true;
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
	
	class ResetPwd extends AsyncTask<Void, Void, String>{

		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(ResetPassword.this);
			dialog.setMessage("Contacting Server, Please Wait");
			dialog.setIndeterminate(true);
			dialog.setCancelable(true);
			dialog.show();
		}
		
		@Override
		protected String doInBackground(Void... params) {
			String url = GenUtils.getUrl(dataManager)+"/api/client/user/resetpassword";
			Hashtable<String, String> table = new Hashtable<String, String>();
			table.put("userId", user.getId().toString());
			String resp = null;
			try {
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
				((TextView)findViewById(R.id.rst_header)).setText(Error);
				Toast.makeText(ResetPassword.this, Error, Toast.LENGTH_LONG).show();
			}else{
				try {
					BasicResponse basicResponse = deserialize(returns, new TypeToken<BasicResponse>() {}.getType());
					if(basicResponse.Status == true){
						((TextView)findViewById(R.id.rst_header))
						.setText("New Password is: "+basicResponse.Info);
						Toast.makeText(ResetPassword.this, "Password Reset. New Password is: " + basicResponse.Info,
								Toast.LENGTH_LONG).show();						
						updatePassword(basicResponse.Info);
					}else{
						Error = "Password not reset";
						((TextView)findViewById(R.id.rst_header)).setText(Error);
						Toast.makeText(ResetPassword.this, Error, Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) { 
					e.printStackTrace();
					Error = "Error Occured. Ensure your network is Ok the try changing your password again";
					((TextView)findViewById(R.id.rst_header)).setText(Error);
					Toast.makeText(ResetPassword.this, Error, Toast.LENGTH_LONG).show();
				}
			}
		}
	}
	
	private void updatePassword(String password) throws Exception{
		user.setPassword(MakePWD.getMD5(password));
		UserType t = (user.getUserType() == 2 ? UserType.TDR : UserType.Admin);
		Repositoryregistry.get(IDUserRepository.class, dataManager).save(new DUserE(user.getId(), user.getUsername(), user.getPassword(), 
				user.getFullname(), t, user.getEmail(), user.getPhoneNumber(), user.getClientId(),
				user.getLocationId(), user.getClientName(), user.isIsPasswordChanged(),
				user.isIsSecuritySet(), user.getSecurityQuestionId(), user.getSecurityAnswer()));
		Repositoryregistry.get(IMessageRepository.class, dataManager)
		.save(new DFormMessages(UUID.randomUUID(), false, "new Password: " + password, 
				new Date(), MessageTypes.Password.value) );
		raiseNotification(password);
		setResult(RESULT_OK);
		finish();
	}
	
	private void raiseNotification(String password){
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		mBuilder.setSmallIcon(R.drawable.message_icon);
		mBuilder.setContentTitle("Password reset: " + password);
		mBuilder.setContentText("New Password: " + password);
		mBuilder.setTicker("New Password: " + password);
		mBuilder.setAutoCancel(true);
		
		Intent resultIntent = new Intent(ResetPassword.this, MessageNotification.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, resultIntent, 0);
		
		/*TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(MessageNotification.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
		            PendingIntent.FLAG_UPDATE_CURRENT );*/
		
		mBuilder.setContentIntent(pIntent);
		
		NotificationManager mNotificationManager = 
				(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			// notificationID allows you to update the notification later on.
		mNotificationManager.notify(999, mBuilder.build());
		
	}
}

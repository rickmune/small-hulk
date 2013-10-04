package com.maina.formdata.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.maina.formdata.R;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.entity.DformResultItemE;
import com.maina.formdata.utils.ui.CheckableListDialog;
import com.maina.formdata.utils.ui.ListDialog;

public class FormItemBase extends BaseActivity {

	private final String Tag = "FormItemBase";
	private final int DROPDOWNRESULT = 111;
	private final int MULTICHOICERESULT = 112;
	protected LinearLayout dynamicView;
	protected TextView itemlabel, lblFormT;
	protected Button btnSpinner;
	protected UUID RespondentId;
	protected DformResultE dformResultE;
	protected List<DformResultItemE> resultItemEs;
	protected DformResultItemE itemE; 
	protected UUID formItemId;
	protected boolean isText = false, isRadio = false;
	protected String AnswerText = "";
	
	EditText view;
	LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            LayoutParams.FILL_PARENT,
            LayoutParams.WRAP_CONTENT
    );
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("FormItemActivity","onCreate");
		super.onCreate(savedInstanceState);
		resultItemEs = new ArrayList<DformResultItemE>();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK){
			if (requestCode == DROPDOWNRESULT){
				AnswerText = data.getStringExtra(ListDialog.RESULTID);
				String result = data.getStringExtra(ListDialog.RESULTNAME);
				Log.d("FormItemBase","from Dropdown formItemId: "+result+ " resultName AnswerText: "+AnswerText);
				List<String> results = new ArrayList<String>();
				results.add(AnswerText);
				btnSpinner.setText(result);
				itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
						AnswerText+",");
			}else if (requestCode == MULTICHOICERESULT){
				AnswerText = data.getStringExtra(ListDialog.RESULTID);
				String result = data.getStringExtra(ListDialog.RESULTNAME);
				Log.d("FormItemBase","from multichoice formItemId: "+result+ " resultName AnswerText: "+AnswerText);
				btnSpinner.setText(result);				
				String [] r = AnswerText.split(",");
				List<String> results = new ArrayList<String>();
				for(int t = 0; t < r.length; t++){
					results.add(r[t]);
				}
				itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE, 
						AnswerText);
			}
		}
	}
	
	protected void makeTextbox(String text, String regex){
		isText = true;
		Log.d(Tag, "makeTextbox: "+text);
		view = new EditText(this);
		view.setTextSize(24);
		view.setRawInputType(inputType(regex));
		view.setHint("Provide answer");
		view.addTextChangedListener(new TextWatcher() {	
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				try {
					AnswerText = s.toString();
					Log.d(Tag, "typing ... "+AnswerText);
				} catch (Exception e) {
				}
			}
		});
		dynamicView.setFocusableInTouchMode(true);
		dynamicView.addView(view, p);
	}

	protected void makeDropdown(final UUID formItemId, int selected){
		Log.d("FormItemBase","makeDropdown formItemId: "+formItemId);
		btnSpinner.setVisibility(View.VISIBLE);
		btnSpinner.setText("Select Answer");
		btnSpinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callPopUp(ListDialog.class, DROPDOWNRESULT, formItemId.toString(), "Select Answer");
			}
		});
		dynamicView.addView(btnSpinner);
	}
	
	protected void makeCheckboxList(final UUID formItemId, String checked){
		
		Log.d(Tag, "makeCheckboxList qno: "+formItemId+" checked: "+checked);
		btnSpinner.setVisibility(View.VISIBLE);
		btnSpinner.setText("");
		btnSpinner.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				callPopUp(CheckableListDialog.class, MULTICHOICERESULT, formItemId.toString(), "Select Answer");
			}
		});
		dynamicView.addView(btnSpinner);
	}
	
	protected void makeRadioButton(List<Pair<String, String>> pairs){
		isRadio = true;
		RadioGroup radioGroup = new RadioGroup(this);
		for(Pair<String, String> pair : pairs){
			RadioButton radioButtonView = new RadioButton(this);
            radioButtonView.setText(pair.first);
            radioButtonView.setTag(pair);
            radioButtonView.setTextColor(getResources().getColor(R.color.black));
            radioGroup.addView(radioButtonView, p);
		}
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Pair<String, String> pair = (Pair<String, String>) group.findViewById(checkedId).getTag();
				AnswerText = pair.second;
				System.out.println("onCheckedChanged ... "+AnswerText);
				Log.d(Tag, "onCheckedChanged ... "+AnswerText);
			}
		});
		dynamicView.addView(radioGroup, p);
	}

	private void callPopUp(Class cName, int resultCode, String formItemId, String t){
		ArrayList<String> map = new ArrayList<String>();
		map.add("dformItemE_id"); 
		map.add(formItemId);
		Intent intent = new Intent(this, cName);
		Bundle bundle = new Bundle();
		bundle.putString("ClassName", "com.maina.formdata.repository.IDFormItemAnswerRepository");
		bundle.putString("MethodName", "getAnswerItem");
		bundle.putString("title", t);
		bundle.putStringArrayList("Params", map);
		intent.putExtras(bundle);
		startActivityForResult(intent, resultCode);
	}
	
	private int inputType(String regex){
		if(regex == null)regex="";
		int inType = InputType.TYPE_CLASS_TEXT;
		if(regex.contains("\\d")){
			inType = InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED;
		}else if(regex.contains("\\.")){
			inType = InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_NUMBER_FLAG_SIGNED;
		}
		return inType;
	}
}

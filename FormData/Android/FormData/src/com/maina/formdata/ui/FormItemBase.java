package com.maina.formdata.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.*;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.widget.LinearLayout.LayoutParams;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.maina.formdata.R;
import com.maina.formdata.entity.DformResultE;
import com.maina.formdata.entity.DformResultItemE;
import com.maina.formdata.utils.ui.CheckableListDialog;
import com.maina.formdata.utils.ui.ListDialog;

public class FormItemBase extends BaseActivity {

	private final String Tag = "FormItemBase";
	private final int DROPDOWNRESULT = 111, MULTICHOICERESULT = 112, IMAGECAPTURE = 113;
	protected LinearLayout dynamicView;
	protected TextView itemlabel, lblFormT;
	protected Button btnSpinner, mPickDate;
	protected UUID RespondentId, formItemId;
	protected DformResultE dformResultE;
	protected List<DformResultItemE> resultItemEs;
	protected DformResultItemE itemE;
	protected boolean isText = false, isRadio = false, isDate = false, isImage = false;
	protected String AnswerText = "";
    protected int mYear, mMonth, mDay;
    protected DatePickerDialog.OnDateSetListener mDateSetListener;
    protected static final String SEP = "1SEP1";
    protected List<Pair<String, String>> summary;
    private LinearLayout layout;
    private String _path = Environment.getExternalStorageDirectory() + imageFolder, fileName;
    private final static String imageFolder = "/FormData";
    private ImageButton imageView;
	
	EditText view;
    LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
            LayoutParams.MATCH_PARENT,
            LayoutParams.WRAP_CONTENT
    );
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d("FormItemActivity","onCreate");
		super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	}

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        _path = savedInstanceState.getString("_path");
        isImage = savedInstanceState.getBoolean("isImage") ;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("_path", _path);
        outState.putBoolean("isImage", isImage);
        super.onSaveInstanceState(outState);
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
						AnswerText+",", false);
			}else if (requestCode == MULTICHOICERESULT){
				AnswerText = data.getStringExtra(ListDialog.RESULTID);
				String result = data.getStringExtra(ListDialog.RESULTNAME);
				Log.d("FormItemBase","from multichoice formItemId: "+result+ " resultName AnswerText: "+AnswerText);
				btnSpinner.setText(result);				
				String [] r = AnswerText.split(",");
				List<String> results = new ArrayList<String>();
                Collections.addAll(results, r);
				itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE, 
						AnswerText, false);
			}else if(requestCode == IMAGECAPTURE){
                imageView = new ImageButton(this);
                try {
                    Bitmap bitmap = null;
                    try{
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 8;
                        bitmap = BitmapFactory.decodeFile( _path, options );
                    }catch (Exception e) {
                        try{
                            bitmap = (Bitmap) data.getExtras().get("data");
                        }catch (Exception a) {
                            a.printStackTrace();
                        }
                        e.printStackTrace();
                    }
                    Log.d("FormItemBase", "Image _path =: " + _path);
                    if(bitmap != null){
                        try {
                            Bitmap mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
                            imageView.setImageBitmap(mutableBitmap);
                            dynamicView.addView(imageView, p);
                        }catch (Exception e){e.printStackTrace();}
                    }
                    AnswerText = fileName + ".jpeg";//_path;
                    /*List<String> results = new ArrayList<String>();
                    results.add(_path);
                    itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
                            _path);

                    Log.d("FormItemBase", "(Bitmap) getIntent().getExtras().get(data): Done");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    if(bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)){
                        Log.d("FormItemBase","Image to ByteArrayOutPutStream Done" );
                        imageView.setImageBitmap(bitmap);
                        byte[] bs = stream.toByteArray();
                        AnswerText = Base64.encodeToString(bs, Base64.DEFAULT);
                        Log.d("FormItemBase", "Image AnswerText =: " + AnswerText);
                        List<String> results = new ArrayList<String>();
                        results.add(AnswerText);
                        itemE = new DformResultItemE(UUID.randomUUID(), formItemId, results, dformResultE,
                                AnswerText);
                    }else{

                    }  */
                    _path = Environment.getExternalStorageDirectory() + imageFolder;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
		}
	}
	
	protected void makeTextbox(String text, String regex){
		isText = true;
		Log.d(Tag, "makeTextbox: "+text);
		view = new EditText(this);
		view.setTextSize(24);
		view.setRawInputType(inputType(regex));
		view.setImeOptions(EditorInfo.IME_ACTION_DONE);
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
		InputMethodManager imm = (InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE);
		imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
		view.requestFocus();
		//getWindow().setSoftInputMode(WindowManager.LayoutParams.so)
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
				@SuppressWarnings("unchecked")
				Pair<String, String> pair = (Pair<String, String>) group.findViewById(checkedId).getTag();
				AnswerText = pair.second + SEP + pair.first;
				System.out.println("onCheckedChanged ... "+AnswerText);
				Log.d(Tag, "onCheckedChanged ... "+AnswerText);
			}
		});
		dynamicView.addView(radioGroup, p);
		
	}

	private void callPopUp(@SuppressWarnings("rawtypes") Class cName, int resultCode, String formItemId, String t){
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

    protected void makeDate(String text){
        isDate = true;
        mPickDate = new Button(this);
        dynamicView.addView(mPickDate);
        final Calendar c = Calendar.getInstance();
        //if(date == null){
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        /*}else{
            mYear = date.getYear();
            mMonth = date.getMonth();
            mDay = date.getDate();
        }*/
        final Context context = this;
        updateDisplay();
        mPickDate.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d(Tag, "validate: "+mYear);
                DatePickerDialog dialog;
                dialog = new DatePickerDialog(context, mDateSetListener,  mYear, mMonth, mDay);
                dialog.show();
            }
        });
        mDateSetListener =  new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                updateDisplay();
            }
        };

    }

    protected void captureImage(UUID fileNames){
        isImage = true;
        this.fileName = fileNames.toString();
        _path += "/"+fileName+".jpeg";
        Button button = new Button(this);
        button.setText("Capture Image");
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(createDirIfNotExist()){
                    capture();
                }
            }
        });
        dynamicView.addView(button, p);
    }

    private void updateDisplay() {
        mPickDate.setText("Date: "+
                new StringBuilder()
                        .append(mMonth + 1).append("-")
                        .append(mDay).append("-")
                        .append(mYear).append(" "));
        GsonBuilder b = new GsonBuilder();
        b.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Gson gson = b.create();
        AnswerText = gson.toJson(new Date(mYear - 1900, mMonth, mDay),
                new TypeToken<Date>() {}.getType());
    }

	private int inputType(String regex){
		if(regex == null)regex="";
		int inType = InputType.TYPE_CLASS_TEXT;
		if(regex.equals("")){
			inType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES;
		}else if(regex.contains("\\.")){
			inType = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED;
		}/*else if(regex.contains("\\d")){
			
		}*/else if(regex.contains("\\d")){
			inType = InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_SIGNED;
		}
		return inType;
	}

    protected void surveySummary(List<Pair<String, String>> pairs){
        ScrollView scrollView = new ScrollView(this);
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for(Pair pair : pairs){
            putSummary("Question: " + pair.first, Color.BLUE, 22);
            putSummary("\tAns: " + pair.second, Color.parseColor("#0B610B"), 22);
        }
        scrollView.addView(layout);
        dynamicView.addView(scrollView, p);
    }

    private void putSummary(String text, int color, float size){
        TextView textView = new TextView(this);
        textView.setText(text);
        textView.setTextColor(color);
        textView.setTextSize(size);
        layout.addView(textView, p);
    }

    public boolean createDirIfNotExist(){
        boolean ret = true;
        if(Environment.getExternalStorageDirectory().exists()){
            File dir = new File(Environment.getExternalStorageDirectory() + imageFolder);
            if(!dir.exists()){
                if(!dir.mkdirs()){
                    Log.d(Tag, "Problem creating folder: " + imageFolder);
                    ret = false;
                }
            }
            //dir.mkdir();
        }
        return ret;
    }

    public void capture() {
        File file = new File(_path);
        Uri outputFileUri = Uri.fromFile(file);
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE );
        intent.putExtra( MediaStore.EXTRA_OUTPUT, outputFileUri );
        startActivityForResult(intent, IMAGECAPTURE);
    }

}

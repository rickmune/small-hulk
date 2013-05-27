package com.safapp.ui;

import com.safapp.androidclient.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddCategory extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcategory);
		
	}
	
	public void onAddCategoryClick(View view){
		startActivity(new Intent(AddCategory.this, AddProduct.class));
	}
}

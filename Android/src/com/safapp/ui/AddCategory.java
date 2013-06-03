package com.safapp.ui;

import java.util.Date;
import java.util.UUID;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.safapp.androidclient.R;
import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.entities.OutGoing;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.IOutGoingRepository;
import com.safapp.utils.JsonConverter;
import com.safapp.utils.RepositoryRegistry;

public class AddCategory extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addcategory);
		
	}
	
	public void onAddCategoryClick(View view){
		String category_name = ((EditText)findViewById(R.id.enter_category_name)).getText().toString().trim();
		String category_desc = ((EditText)findViewById(R.id.enter_category_desc)).getText().toString().trim();
		if(category_desc.length() <= 0 || category_name.length() <= 0)
			Toast.makeText(AddCategory.this, "Fields Can't be NULL", Toast.LENGTH_LONG).show();
		Date date = new Date();
		Account account = null;
		try {
			account = RepositoryRegistry.get(IAccountRepository.class).getAll().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Category category = new Category(UUID.randomUUID(), date, date, true, category_name, category_desc, account);
		int y = -100;
		try {
			y = RepositoryRegistry.get(IOutGoingRepository.class).save(new OutGoing(UUID.randomUUID(), JsonConverter.MapObject(category)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(y > 0){
			Toast.makeText(AddCategory.this, "Category Saved", Toast.LENGTH_LONG).show();
			finish();
		}
		Toast.makeText(AddCategory.this, "there was some error", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}

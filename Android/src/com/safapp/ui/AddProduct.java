package com.safapp.ui;

import java.util.Date;
import java.util.UUID;

import com.safapp.androidclient.R;
import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.entities.OutGoing;
import com.safapp.entities.Product;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.repositories.IOutGoingRepository;
import com.safapp.repositories.IProductRepository;
import com.safapp.utils.JsonConverter;
import com.safapp.utils.RepositoryRegistry;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProduct extends Activity{

	private UUID Id = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addproduct);
		Spinner spinner = (Spinner) findViewById(R.id.addproduct_category);
		Cursor cursor = null;
		try {
			cursor = RepositoryRegistry.get(ICategoryRepsitory.class).getAllCat();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cursor.getCount() > 0) {
			String[] from = new String[] { "entityName" };
			int[] to = new int[] { android.R.id.text1 };
			SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, 
					cursor, from, to);
			mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(mAdapter);
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int pos,
					long log) {
				Cursor c = (Cursor) parent.getItemAtPosition(pos);
				Id = UUID.fromString(c.getString(c.getColumnIndexOrThrow("_id")));
			}

			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}
	
	public void onAddCategoryClick(View view){
		String product_name = ((EditText)findViewById(R.id.enter_product_name)).getText().toString().trim();
		String product_desc = ((EditText)findViewById(R.id.enter_product_desc)).getText().toString().trim();
		String product_bp = ((EditText)findViewById(R.id.enter_product_bp)).getText().toString().trim();
		String product_sp = ((EditText)findViewById(R.id.enter_product_sp)).getText().toString().trim();
		Date date = new Date();
		Account account = null;
		try {
			account = RepositoryRegistry.get(IAccountRepository.class).getAll().get(0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Category category = null;
		try {
			category = RepositoryRegistry.get(ICategoryRepsitory.class).getById(Id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Product product = new Product(UUID.randomUUID(), date, date, true, product_name, product_desc, 
				category, Float.parseFloat(product_bp), Float.parseFloat(product_sp), account);
		int x = -100;
		try {
			x = RepositoryRegistry.get(IProductRepository.class).save(product);
			if(x > 0){
				x = RepositoryRegistry.get(IOutGoingRepository.class).save(new OutGoing(UUID.randomUUID(), JsonConverter.MapObject(product)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(x > 0){
			Toast.makeText(AddProduct.this, "Category Saved", Toast.LENGTH_LONG).show();
			finish();
		}
		Toast.makeText(AddProduct.this, "there was some error", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onBackPressed() {
		finish();
	}
}

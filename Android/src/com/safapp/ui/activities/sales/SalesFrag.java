package com.safapp.ui.activities.sales;

import java.util.List;
import java.util.UUID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.safapp.androidclient.R;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.ui.utils.SpinnerArrayAdapter;
import com.safapp.utils.RepositoryRegistry;

public class SalesFrag extends Fragment{

	private List<String[]> CatToDisplay = null;
	private List<String[]> ProductToDisplay = null;
	private UUID catId = null;
	private UUID prodId = null;
	private Spinner catSpinner;
	private Spinner prodSpinner;
	private EditText price;
	private EditText quantity;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			CatToDisplay = RepositoryRegistry.get(ICategoryRepsitory.class).getAForSpinner(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ProductToDisplay = RepositoryRegistry.get(ICategoryRepsitory.class).getAForSpinner(catId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.makesales, container, false);
		catSpinner = (Spinner)root.findViewById(R.id.sales_cat);
		prodSpinner = (Spinner)root.findViewById(R.id.sales_product);
		price = (EditText)root.findViewById(R.id.sale_price);
		quantity = (EditText)root.findViewById(R.id.sale_qnty);
		/*((Button)root.findViewById(R.id.btnsale_cancel)).setOnClickListener(l);
		((Button)root.findViewById(R.id.btnsale_summary).setOnClickListener(l);*/
		ArrayAdapter<String[]> adapter = new SpinnerArrayAdapter(getActivity(), R.layout.my_spinner_style, CatToDisplay);
		ArrayAdapter<String []> prodAdaptor = new SpinnerArrayAdapter(getActivity(), R.layout.my_spinner_style, ProductToDisplay);
		
		catSpinner.setAdapter(adapter);
		prodSpinner.setAdapter(prodAdaptor);
		
		catSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
				catId = UUID.fromString(CatToDisplay.get(position)[1]);
				try {
					ProductToDisplay = RepositoryRegistry.get(ICategoryRepsitory.class).getAForSpinner(catId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		prodSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view, int position, long arg3) {
				prodId = UUID.fromString(ProductToDisplay.get(position)[1]);
				if(catId == null){
					//TODO: set category Id
					//TODO: set default price
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}
		});
		
		return root;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}
}

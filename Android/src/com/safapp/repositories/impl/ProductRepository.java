package com.safapp.repositories.impl;

import java.util.List;
import java.util.UUID;

import com.safapp.entities.Product;
import com.safapp.repositories.IProductRepository;

public class ProductRepository extends BaseEntityRepository<Product> implements IProductRepository {

	public ProductRepository() {
		setDataClass();
	}

	@Override
	public void setDataClass() {
		dataClass = Product.class;
	}

	@Override
	public List<String[]> getAForSpinner(UUID fillter) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}

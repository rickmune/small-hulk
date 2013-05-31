package com.safapp.repositories.impl;

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

}

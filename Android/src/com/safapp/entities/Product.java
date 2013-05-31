package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable
public class Product extends BaseEntity {
	
	public Product() {
	}
	public Product(UUID id, Date createdOn, Date updatedOn, boolean isActive) {
		super(id, createdOn, updatedOn, isActive);
		// TODO Auto-generated constructor stub
	}
	public Product(UUID id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	public Product(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, String description,
			com.safapp.entities.Category category, float bPrice, float sPrice,
			Account account) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Description = description;
		Category = category;
		BPrice = bPrice;
		SPrice = sPrice;
		this.Account = account;
	}
	@DatabaseField
	private String Name;
	@DatabaseField
	private String Description;
	@DatabaseField(foreign = true)
	private Category Category;
	@DatabaseField
	private float BPrice;
	@DatabaseField
	private float SPrice;
	@DatabaseField(foreign = true)
	private Account Account;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Category getCategory() {
		return Category;
	}
	public void setCategory(Category category) {
		Category = category;
	}
	public float getBPrice() {
		return BPrice;
	}
	public void setBPrice(float bPrice) {
		BPrice = bPrice;
	}
	public float getSPrice() {
		return SPrice;
	}
	public void setSPrice(float sPrice) {
		SPrice = sPrice;
	}
	public Account getAccount() {
		return Account;
	}
	public void setAccount(Account account) {
		this.Account = account;
	}
}

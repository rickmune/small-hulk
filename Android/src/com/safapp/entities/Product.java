package com.safapp.entities;

import java.util.Date;
import java.util.UUID;

public class Product extends BaseEntity {

	public Product(UUID id, Date createdOn, Date updatedOn, boolean isActive,
			String name, String description,
			com.safapp.entities.Category category, float bPrice, float sPrice,
			User user) {
		super(id, createdOn, updatedOn, isActive);
		Name = name;
		Description = description;
		Category = category;
		BPrice = bPrice;
		SPrice = sPrice;
		this.user = user;
	}
	private String Name;
	private String Description;
	private Category Category;
	private float BPrice;
	private float SPrice;
	private User user;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}

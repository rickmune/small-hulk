package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.entities.Product;
import com.safapp.repositories.IAccountRepository;
import com.safapp.repositories.ICategoryRepsitory;
import com.safapp.utils.RepositoryRegistry;

public class ProductDTO extends BaseDTO {

	public ProductDTO(UUID id, boolean isActive, String name,
			String description, UUID categoryId, UUID accountId,
			double buyingPrice, double sellingPrice) {
		super(id, isActive);
		Name = name;
		Description = description;
		CategoryId = categoryId;
		AccountId = accountId;
		BuyingPrice = buyingPrice;
		SellingPrice = sellingPrice;
	}
	private String Name;
    private String Description;
    private UUID CategoryId;
    private UUID AccountId;
    private double BuyingPrice;
    private double SellingPrice;
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
	public UUID getCategoryId() {
		return CategoryId;
	}
	public void setCategoryId(UUID categoryId) {
		CategoryId = categoryId;
	}
	public UUID getAccountId() {
		return AccountId;
	}
	public void setAccountId(UUID accountId) {
		AccountId = accountId;
	}
	public double getBuyingPrice() {
		return BuyingPrice;
	}
	public void setBuyingPrice(double buyingPrice) {
		BuyingPrice = buyingPrice;
	}
	public double getSellingPrice() {
		return SellingPrice;
	}
	public void setSellingPrice(double sellingPrice) {
		SellingPrice = sellingPrice;
	}
    
	public Product Map() throws Exception{
		Date date = new Date();
		Account account = RepositoryRegistry.get(IAccountRepository.class).getById(AccountId);
		Category category = RepositoryRegistry.get(ICategoryRepsitory.class).getById(CategoryId);
		return new Product(getId(), date, date, isIsActive(), Name, Description, category, 
				BuyingPrice, SellingPrice, account);
	}
}

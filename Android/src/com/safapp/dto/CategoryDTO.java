package com.safapp.dto;

import java.util.Date;
import java.util.UUID;

import com.safapp.entities.Account;
import com.safapp.entities.Category;
import com.safapp.repositories.IAccountRepository;
import com.safapp.utils.RepositoryRegistry;

public class CategoryDTO extends BaseDTO {

	public CategoryDTO(UUID id, boolean isActive, String name,
			String description, UUID accountId) {
		super(id, isActive);
		Name = name;
		Description = description;
		AccountId = accountId;
	}

	private String Name;
	private String Description;
	private UUID AccountId;

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

	public UUID getAccountId() {
		return AccountId;
	}

	public void setAccountId(UUID accountId) {
		AccountId = accountId;
	}

	public Category Map() throws Exception{
		Date date = new Date();
		Account account = RepositoryRegistry.get(IAccountRepository.class).getById(AccountId);
		return new Category(getId(), date, date, isIsActive(), Name, Description, account);
	}
}

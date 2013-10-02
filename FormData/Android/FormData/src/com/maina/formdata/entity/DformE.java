package com.maina.formdata.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DformE extends DBaseE {
	
	public DformE() {
	}
	
	public DformE(UUID id, String name) {
		super(id);
		Name = name;
	} 
	
	public DformE(UUID id, String name, List<DformRespondentTypeE> respondentTypes,
			List<DformItemE> formItems) {
		super(id);
		Name = name;
		RespondentTypes = respondentTypes;
		FormItems = formItems;
	}

	@DatabaseField
	private String Name;
	private List<DformRespondentTypeE> RespondentTypes;
	private List<DformItemE> FormItems;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<DformRespondentTypeE> getRespondentTypes() {
		return RespondentTypes;
	}
	public void setRespondentTypes(List<DformRespondentTypeE> respondentTypes) {
		RespondentTypes = respondentTypes;
	}
	public List<DformItemE> getFormItems() {
		return FormItems;
	}
	public void setFormItems(List<DformItemE> formItems) {
		FormItems = formItems;
	}
	

	public void addFormItems(DformItemE formItem) {
		if(FormItems == null)FormItems = new ArrayList<DformItemE>();
		FormItems.add(formItem);
	}
	
	public void addRespondentTypes(DformRespondentTypeE respondentTypes) {
		if(RespondentTypes == null)RespondentTypes = new ArrayList<DformRespondentTypeE>();
		RespondentTypes.add(respondentTypes);
	}
}

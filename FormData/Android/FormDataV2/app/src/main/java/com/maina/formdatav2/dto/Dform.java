package com.maina.formdatav2.dto;

import java.util.List;
import java.util.UUID;

public class Dform extends DBase {

	public Dform() {
	}
	
	public Dform(UUID id, String name,
			List<DformRespondentType> respondentTypes,
			List<DformItem> formItems) {
		super(id);
		Name = name;
		RespondentTypes = respondentTypes;
		FormItems = formItems;
	}

	private String Name;
	private List<DformRespondentType> RespondentTypes;
	private List<DformItem> FormItems;
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public List<DformRespondentType> getRespondentTypes() {
		return RespondentTypes;
	}
	public void setRespondentTypes(List<DformRespondentType> respondentTypes) {
		RespondentTypes = respondentTypes;
	}
	public List<DformItem> getFormItems() {
		return FormItems;
	}
	public void setFormItems(List<DformItem> formItems) {
		FormItems = formItems;
	}

}

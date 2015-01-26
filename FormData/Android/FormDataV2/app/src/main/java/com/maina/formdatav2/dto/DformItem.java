package com.maina.formdatav2.dto;

import com.maina.formdatav2.enums.DformItemType;

import java.util.List;
import java.util.UUID;

public class DformItem extends DBase {

	public DformItem() {
	}
	
	public DformItem(UUID id, String label, DformItemType formItemType,
			List<DformItemRespondentType> formItemRespondentTypes,
			List<DformItemAnswer> formItemAnswer, boolean isRequired, int order,
			String validationText, String validationRegex) {
		super(id);
		Label = label;
		FormItemType = formItemType;
		FormItemRespondentTypes = formItemRespondentTypes;
		FormItemAnswer = formItemAnswer;
		IsRequired = isRequired;
		Order = order;
		ValidationText = validationText;
		ValidationRegex = validationRegex;
	}
	
	private String Label;
	private DformItemType FormItemType;
	private List<DformItemRespondentType> FormItemRespondentTypes;
	private List<DformItemAnswer> FormItemAnswer;
	private boolean IsRequired;
	private int Order;
	private String ValidationText;
	private String ValidationRegex;
	
	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public DformItemType getFormItemType() {
		return FormItemType;
	}

	public void setFormItemType(DformItemType formItemType) {
		FormItemType = formItemType;
	}

	public List<DformItemRespondentType> getFormItemRespondentTypes() {
		return FormItemRespondentTypes;
	}

	public void setFormItemRespondentTypes(
			List<DformItemRespondentType> formItemRespondentTypes) {
		FormItemRespondentTypes = formItemRespondentTypes;
	}

	public List<DformItemAnswer> getFormItemAnswer() {
		return FormItemAnswer;
	}

	public void setFormItemAnswer(List<DformItemAnswer> formItemAnswer) {
		FormItemAnswer = formItemAnswer;
	}

	public boolean isIsRequired() {
		return IsRequired;
	}

	public void setIsRequired(boolean isRequired) {
		IsRequired = isRequired;
	}

	public int getOrder() {
		return Order;
	}

	public void setOrder(int order) {
		Order = order;
	}

	public String getValidationText() {
		return ValidationText;
	}

	public void setValidationText(String validationText) {
		ValidationText = validationText;
	}

	public String getValidationRegex() {
		return ValidationRegex;
	}

	public void setValidationRegex(String validationRegex) {
		ValidationRegex = validationRegex;
	}
}

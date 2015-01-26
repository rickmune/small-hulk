package com.maina.formdatav2.entity;

import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.maina.formdatav2.enums.DformItemType;

@DatabaseTable
public class DformItemE extends DBaseE {
	
	public DformItemE() {
	}
	
	public DformItemE(UUID id, String label, DformItemType formItemType,
			boolean isRequired, DformE dformE, int order, String validationText, 
			String validationRegex) {
		super(id);
		Label = label;
		FormItemType = formItemType;
		IsRequired = isRequired;
		DformE = dformE;
		Order = order;
		ValidationText = validationText;
		ValidationRegex = validationRegex;
	}
	
	public DformItemE(UUID id, String label, DformItemType formItemType,
			List<DformItemRespondentTypeE> formItemRespondentTypes,
			List<DformItemAnswerE> formItemAnswer, boolean isRequired, int order,
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
	public static final String DformEID = "DformE";
	@DatabaseField
	private String Label;
	@DatabaseField
	private DformItemType FormItemType;
	private List<DformItemRespondentTypeE> FormItemRespondentTypes;
	private List<DformItemAnswerE> FormItemAnswer;
	@DatabaseField
	private boolean IsRequired;
	@DatabaseField(foreign = true, columnName = DformEID)
	private DformE DformE;
	@DatabaseField
	private int Order;
	@DatabaseField
	private String ValidationText;
	@DatabaseField
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

	public List<DformItemRespondentTypeE> getFormItemRespondentTypes() {
		return FormItemRespondentTypes;
	}

	public void setFormItemRespondentTypes(List<DformItemRespondentTypeE> formItemRespondentTypes) {
		FormItemRespondentTypes = formItemRespondentTypes;
	}

	public List<DformItemAnswerE> getFormItemAnswer() {
		return FormItemAnswer;
	}

	public void setFormItemAnswer(List<DformItemAnswerE> formItemAnswer) {
		FormItemAnswer = formItemAnswer;
	}

	public boolean isIsRequired() {
		return IsRequired;
	}

	public void setIsRequired(boolean isRequired) {
		IsRequired = isRequired;
	}

	public DformE getDformE() {
		return DformE;
	}

	public void setDformE(DformE dformE) {
		this.DformE = dformE;
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

    @Override
    public String toString() {
        return "DformItemE{" +
                "Label='" + Label + '\'' +
                ", FormItemType=" + FormItemType +
                ", FormItemRespondentTypes=" + FormItemRespondentTypes +
                ", FormItemAnswer=" + FormItemAnswer +
                ", IsRequired=" + IsRequired +
                ", DformE=" + DformE +
                ", Order=" + Order +
                ", ValidationText='" + ValidationText + '\'' +
                ", ValidationRegex='" + ValidationRegex + '\'' +
                '}';
    }
}

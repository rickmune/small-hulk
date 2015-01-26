package com.maina.formdatav2.entity;

import java.util.List;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DformResultItemE extends DBaseE {
	
	public DformResultItemE() {
	}
	
	public DformResultItemE(UUID id, UUID formItemId, List<String> formItemAnswer,
			DformResultE dformResultE, String itemAnswer, boolean image) {
		super(id);
		FormItemId = formItemId;
		FormItemAnswer = formItemAnswer;
		ItemAnswer = itemAnswer;
		DformResultE = dformResultE;
        Image = image;
	}
	
	@DatabaseField
	private UUID FormItemId;
	private List<String> FormItemAnswer;
	@DatabaseField
	private String ItemAnswer;
	@DatabaseField(foreign = true)
	private DformResultE DformResultE;
    private boolean Image;

    public boolean isImage() {
        return Image;
    }

    public void setImage(boolean image) {
        Image = image;
    }

    public UUID getFormItemId() {
		return FormItemId;
	}
	public void setFormItemId(UUID formItemId) {
		FormItemId = formItemId;
	}
	public List<String> getFormItemAnswer() {
		return FormItemAnswer;
	}
	public void setFormItemAnswer(List<String> formItemAnswer) {
		FormItemAnswer = formItemAnswer;
	}

	public String getItemAnswer() {
		return ItemAnswer;
	}

	public void setItemAnswer(String itemAnswer) {
		ItemAnswer = itemAnswer;
	}

	public DformResultE getDformResultE() {
		return DformResultE;
	}

	public void setDformResultE(DformResultE dformResultE) {
		DformResultE = dformResultE;
	}
     
}

package com.maina.formdata.entity;

import java.util.Date;
import java.util.UUID;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class DFormMessages extends DBaseE {

	public DFormMessages(UUID id, boolean read, String message, Date dateIn,
			int messageType) {
		super(id);
		Read = read;
		Message = message;
		DateIn = dateIn;
		MessageType = messageType;
	}

	public DFormMessages() {
	}

	public DFormMessages(UUID id) {
		super(id);
	}
	
	@DatabaseField
	private boolean Read;
	@DatabaseField
	private String Message;
	@DatabaseField
	private Date DateIn;
	@DatabaseField
	private int MessageType;
	public boolean isRead() {
		return Read;
	}

	public void setRead(boolean read) {
		Read = read;
	}

	public String getMessage() {
		return Message;
	}

	public void setMessage(String message) {
		Message = message;
	}

	public Date getDateIn() {
		return DateIn;
	}

	public void setDateIn(Date dateIn) {
		DateIn = dateIn;
	}

	public int getMessageType() {
		return MessageType;
	}

	public void setMessageType(int messageType) {
		MessageType = messageType;
	}
	
}

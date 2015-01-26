package com.maina.formdata.repository;

import java.util.UUID;

import com.maina.formdata.entity.DFormMessages;

import android.database.Cursor;

public interface IMessageRepository extends IRepositoryBase {

	public Cursor getMessages(int messageType) throws Exception;
	
	public void delete(UUID id) throws Exception;
	
	public DFormMessages markAsRead(UUID id) throws Exception;
}

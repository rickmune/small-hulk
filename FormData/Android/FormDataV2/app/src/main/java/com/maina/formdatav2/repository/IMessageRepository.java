package com.maina.formdatav2.repository;

import java.util.UUID;

import android.database.Cursor;

import com.maina.formdatav2.entity.DFormMessages;

public interface IMessageRepository extends IRepositoryBase {

	public Cursor getMessages(int messageType) throws Exception;
	
	public void delete(UUID id) throws Exception;
	
	public DFormMessages markAsRead(UUID id) throws Exception;
}

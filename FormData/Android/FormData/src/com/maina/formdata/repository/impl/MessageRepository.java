package com.maina.formdata.repository.impl;

import java.util.UUID;

import android.database.Cursor;

import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.entity.DFormMessages;
import com.maina.formdata.repository.IMessageRepository;
import com.maina.formdata.repository.RepositoryBase;

public class MessageRepository extends RepositoryBase implements IMessageRepository {

	public MessageRepository(IDataManager dataManager) {
		setClass();
		setDataManager(dataManager);
	}
	
	@Override
	public Cursor getMessages(int messageType) throws Exception {
		String sql = "SELECT Id as _id, Message as name, DateIn FROM DFormMessages fm" +
				(messageType == 100 ? "" : " WHERE fm.MessageType = " + messageType )+ " Order by DateIn desc";
		return getDataManager().executeQuery(sql);
	}

	@Override
	public void delete(UUID id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DFormMessages markAsRead(UUID id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setClass() {
		DataClass = DFormMessages.class;
	}

	@Override
	public void setDataManager(IDataManager dataManager) {
		setData(dataManager);
	}

}

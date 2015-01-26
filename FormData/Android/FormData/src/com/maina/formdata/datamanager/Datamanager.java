package com.maina.formdata.datamanager;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.maina.formdata.enums.Entities;
import com.maina.formdata.utils.SortedArray;
import com.maina.formdata.utils.ui.ListDataHolder;


public class Datamanager extends OrmLiteSqliteOpenHelper implements IDataManager {

	public Datamanager(Context context) {
		super(context, "DForm", null, 7);
	}
	
	@Override
	public void onOpen(SQLiteDatabase sqLiteDatabase) {
		if (!sqLiteDatabase.isOpen())
			SQLiteDatabase.openDatabase(sqLiteDatabase.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	@Override
	public void close() {
		super.close();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		try {
			for (Entities dir : EnumSet.allOf(Entities.class)) {
				TableUtils.createTable(connectionSource, dir.Value);
			}			
		}catch(Exception e) {
			e.printStackTrace();
			for (Entities dir : EnumSet.allOf(Entities.class)) {
				try {
					TableUtils.dropTable(connectionSource, dir.Value, true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource connectionSource, int arg2, int arg3) {
		try {
			for (Entities dir : EnumSet.allOf(Entities.class)) {
				TableUtils.dropTable(connectionSource, dir.Value, true);
				TableUtils.createTable(connectionSource, dir.Value);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

    @Override
	public <T> T save(T data, Class<T> dataClass) throws Exception {
		Log.d("Datamanager", "save");
		CreateOrUpdateStatus cus = getDao(dataClass).createOrUpdate(data);
		if(cus.isCreated())
			return data;
		if(cus.isUpdated())
			return data;
		return null;
	}

	@Override
	public <T> int saveBatch(final List<T> data, final Class<T> dataClass) throws Exception {
		getDao(dataClass).callBatchTasks(new Callable<Integer>() {
			int change = 0;
			CreateOrUpdateStatus wt = null;
			@Override
			public Integer call() throws Exception {
				for (T t : data) {
					wt = getDao(dataClass).createOrUpdate(t);
					change += wt.getNumLinesChanged();
				}
				Log.d("Datamanager", "saveBatch changes: " + change);
				return change;
			}
		});
		return 0;
	}

	@Override
	public Cursor executeQuery(String query) throws Exception {
		Cursor cursor = null;
		SQLiteDatabase database = this.getReadableDatabase();
		while(true) {
			try {
				if(database == null || !database.isOpen()) {
					database = getWritableDatabase();
				}
				cursor = database.rawQuery(query, null);
			}catch(Exception e) {
				if(e.getMessage() != null && e.getMessage().trim()
						.equalsIgnoreCase("Getting a writable database from SQLiteOpenHelper failed")) {
					continue;
				}
			}
			break;
		}
		return cursor;
	}

	@Override
	public <T> T getById(UUID id, Class<T> dataClass) throws Exception {
		return publicDao(dataClass).queryForId(id);
	}

	@Override
	public <T> Dao<T, UUID> publicDao(Class<T> dataClass) throws Exception {
		return getDao(dataClass);
	}

	@Override
	public <T> List<T> getAll(Class<T> dataClass) throws Exception {
		return publicDao(dataClass).queryForAll();
	}

	@Override
	public SortedArray getListDataHolder(String query) throws Exception {
		Cursor cursor = null;
		SortedArray dataHolders = new SortedArray();
		SQLiteDatabase database = this.getReadableDatabase();
		while(true) {
			try {
				if(database == null || !database.isOpen()) {
					database = getWritableDatabase();
				}
				cursor = database.rawQuery(query, null);
			}catch(Exception e) {
				if(e.getMessage() != null && e.getMessage().trim()
						.equalsIgnoreCase("Getting a writable database from SQLiteOpenHelper failed")) {
					continue;
				}
			}
			break;
		}
		if(cursor.moveToFirst()){
			while (!cursor.isAfterLast()) {
				ListDataHolder dataHolder = new ListDataHolder(
						UUID.fromString(cursor.getString(0)), 
						cursor.getString(1));
				dataHolders.insertSorted(dataHolder);
				cursor.moveToNext();
			}
		}
		return dataHolders;
	}

}

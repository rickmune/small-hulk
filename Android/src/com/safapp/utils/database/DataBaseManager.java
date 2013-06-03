package com.safapp.utils.database;

import java.sql.SQLException;
import java.util.EnumSet;
import java.util.List;
import java.util.UUID;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.Dao.CreateOrUpdateStatus;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.safapp.utils.enums.EntityCollection;

public class DataBaseManager extends OrmLiteSqliteOpenHelper implements IDataBaseManager {

	private static final String Tag = "DatabaseManager";
	private static final boolean L = true;
	
	public DataBaseManager(Context context) {
		super(context,DataBaseStatics.DATABASE_NAME, null, DataBaseStatics.DATABASE_VERSION);
		if(L)Log.d(Tag,"super(context, DATABASE_NAME, null, DATABASE_VERSION)" );
	}
	
	@Override
	public void onOpen(SQLiteDatabase sqLiteDatabase) {
		if(L)Log.d(Tag,"onOpen SQLiteDatabase: " + "Db: " + sqLiteDatabase.toString() + sqLiteDatabase.isOpen());
		if (!sqLiteDatabase.isOpen())
			SQLiteDatabase.openDatabase(sqLiteDatabase.getPath(), null, SQLiteDatabase.OPEN_READWRITE);
	}
	
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource connectionSource) {
		if(L)Log.d(Tag, "onCreate");
		try {
			for (EntityCollection dir : EnumSet.allOf(EntityCollection.class)) {
				TableUtils.createTable(connectionSource, dir.value);
				if(L)Log.d(Tag, "onOpen onCreate Table: " +dir.name());
			}			
		}catch(Exception e){
			e.printStackTrace();
			for (EntityCollection dir : EnumSet.allOf(EntityCollection.class)) {
				try {
					TableUtils.dropTable(connectionSource, dir.value, true);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	public <T> Dao<T, UUID> getDBDao( Class<T> dataClass) throws SQLException{
		return getDao(dataClass);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2, int arg3) {

	}
	
	@Override
	public <T> int save(T t, Class<T> dataClass) throws Exception {
		if(L)Log.d(Tag, "Saving: " + t.getClass().getName());
		CreateOrUpdateStatus wt = null;
		wt = getDBDao(dataClass).createOrUpdate(t);
		if(wt.isCreated()){
			if(L)Log.d(Tag, "Created");
			return wt.getNumLinesChanged();
		}else{
			if(L)Log.d(Tag, "Update");
			return wt.getNumLinesChanged();
		}
	}

	@Override
	public <T> T getById(UUID Id, Class<T> dataClass) throws Exception {
		return getDBDao(dataClass).queryForId(Id);
	}

	@Override
	public <T> List<T> getAll(Class<T> dataClass) throws Exception {
		List<T> list = getDBDao(dataClass).queryForAll();;
		return list;
	}

	@Override
	public <T> int deleteById(UUID Id, Class<T> dataClass) throws Exception {
		return getDBDao(dataClass).deleteById(Id);
	}

	@Override
	public <T> int deleteAll(Class<T> dataClass) throws Exception {
		return getDBDao(dataClass).delete(getAll(dataClass));
	}

	@Override
	public Cursor queryDB(String query) {
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

}

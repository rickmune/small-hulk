package com.maina.formdatav2;

import android.app.Application;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.maina.formdatav2.datamanager.Datamanager;
import com.maina.formdatav2.datamanager.IDataManager;

/**
 * Created by Patrick on 9/26/2014.
 */
public class Controller extends Application{

    protected IDataManager dataManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        dataManager = OpenHelperManager.getHelper(this, Datamanager.class);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenHelperManager.releaseHelper();
    }

    public IDataManager getDatabaseAccess(){
        if(dataManager == null) {
            dataManager = OpenHelperManager.getHelper(this, Datamanager.class);
        }
        return dataManager;
    }

}

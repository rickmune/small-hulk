package com.maina.formdata;

import android.app.Application;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.maina.formdata.datamanager.Datamanager;
import com.maina.formdata.datamanager.IDataManager;

/**
 * Created by Patrick on 9/26/2014.
 */
public class Controller extends Application{

    protected IDataManager dataManager = null;

    public IDataManager getDatabaseAccess(){
        if(dataManager == null) {
            dataManager = OpenHelperManager.getHelper(this, Datamanager.class);
        }
        return dataManager;
    }

}

package com.maina.formdata.newui.fragments;


import android.app.Activity;
import android.support.v4.app.Fragment;
import com.maina.formdata.datamanager.IDataManager;
import com.maina.formdata.newui.FormUi;

/**
 * Created by Patrick on 10/21/2014.
 */
public class BaseFragment extends Fragment {
    protected IDataManager dataManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        dataManager = ((FormUi)activity).getDataManager();
    }
}

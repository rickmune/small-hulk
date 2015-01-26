package com.maina.formdata.newui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.maina.formdata.FormData;
import com.maina.formdata.R;
import com.maina.formdata.dto.UserDto;
import com.maina.formdata.entity.PhonConfig;
import com.maina.formdata.newui.fragments.InforPieChartFragment;
import com.maina.formdata.repository.IPhonConfig;
import com.maina.formdata.repository.Repositoryregistry;
import com.maina.formdata.ui.BaseActivity;
import com.maina.formdata.ui.ChangePassword;
import com.maina.formdata.ui.SetSecurityQuestion;
import com.maina.formdata.utils.ui.GridviewAdapter;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Patrick on 10/19/2014.
 */
public class FormUi extends BaseActivity {

    private GridviewAdapter mAdapter;
    private ArrayList<String> listName;
    private ArrayList<Integer> listIcons;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle, mTitle;
    private String[] mActionTitles;
    private UserDto dto;
    private String ClientId;
    private PhonConfig config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formui_layout);
        mTitle = mDrawerTitle = getTitle();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setBackgroundDrawable();
        if(savedInstanceState == null) {
            dto = FromBundle(getIntent().getExtras(), dto);
            restore(dto);
        }
        prepareList();
        mAdapter = new GridviewAdapter(this,listName, listIcons);
        GridView gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new IconClicked());
        /*mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  *//* host Activity *//*
                mDrawerLayout,         *//* DrawerLayout object *//*
                R.drawable.ic_drawer,  *//* nav drawer image to replace 'Up' caret *//*
                R.string.drawer_open,  *//* "open drawer" description for accessibility *//*
                R.string.drawer_close  *//* "close drawer" description for accessibility *//*
        )

        {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };*/
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //openFileOutput(filename, Context.MODE_PRIVATE);
        //selectItem(0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = ToBundle(outState, dto);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        dto = FromBundle(savedInstanceState, dto);
        restore(dto);
        super.onRestoreInstanceState(savedInstanceState);
    }

    void restore(UserDto dto) {
        ClientId = dto.getClientId().toString();
    }

    private void prepareList(){
        listName = new ArrayList<String>();
        listName.add("Forms");
        listName.add("Infor");
        //listName.add("Details");

        listName.add("Settings");
        //listName.add("Sync");
        listName.add("Notifications");

        listIcons = new ArrayList<Integer>();
        listIcons.add(R.drawable.fd_logo);
        listIcons.add(R.drawable.infor);
        //listIcons.add(R.drawable.user);

        listIcons.add(R.drawable.settings);
        //listIcons.add(R.drawable.sync);
        listIcons.add(R.drawable.message_icon);
    }

    class IconClicked implements GridView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            Toast.makeText(FormUi.this, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            if(position == 0){
               //TODO: forms
            }  else if(position == 1){
               //TODO: infor report
                selectItem(new InforPieChartFragment());
            }  else if(position == 2){
               //TODO: settings
                SettingsList().show();
            }
        }
    }

    private void selectItem(Fragment fragment) {
        /*Bundle args = new Bundle();
        args = new Bundle();   */
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        //mDrawerList.setItemChecked(position, true);
        //setTitle(mActionTitles[position]);
        //mDrawerLayout.closeDrawer(mDrawerList);
    }

    private Dialog SettingsList(){
        AlertDialog.Builder alertB =  new AlertDialog.Builder(FormUi.this);
        alertB.setTitle("Select Action");
        String[] items = {"Change Password", "Change Security Question", "Change URL"};

        ListView modeList = new ListView(this);
        ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner_style,
                android.R.id.text1, items);
        modeList.setAdapter(modeAdapter);
        alertB.setView(modeList);
        final Dialog alertD = alertB.create();
        modeList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                alertD.dismiss();
                if(position == 0){
                    changePWD();
                }else if(position == 1){
                    securityQuestion();
                }else if(position == 2){
                    changeURL();
                }
            }
        });
        return alertD;
    }

    private void changeURL(){
        final Dialog dialog = new Dialog(this);
        LayoutInflater infalInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = infalInflater.inflate(R.layout.url_settings, null);
        dialog.setContentView(view);
        dialog.setTitle("URL Settings");
        final IPhonConfig phonConfig = Repositoryregistry.get(IPhonConfig.class, dataManager);
        final EditText urlEdit = (EditText)view.findViewById(R.id.url_input);
        try {
            config = phonConfig.getConfig();
            if(config != null && config.getURL() != null && !config.getURL().equals("")){
                if(!config.isEdit()){
                    urlEdit.setEnabled(false);
                    urlEdit.setFocusable(false);
                }
                Log.d("setURL", "config != null: " + config.getURL());
                ((TextView)view.findViewById(R.id.url_text)).setText(config.getURL());
                urlEdit.setText(config.getURL());
            }else {
                Log.d("setURL", "config == null");
                ((TextView)view.findViewById(R.id.url_text)).setText("Not Set");
            }
        } catch (Exception e) { e.printStackTrace(); }

        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Object object = urlEdit.getText();
                if(object == null || object.toString().equals("")){
                    Toast.makeText(FormUi.this, "Must enter url", Toast.LENGTH_LONG).show();
                }else{
                    Log.d("setURL", "config != null: " + object.toString());
                    dialog.dismiss();
                    if(config == null){
                        config = new PhonConfig();
                        config.setId(UUID.randomUUID());
                    }
                    config.setURL(object.toString());
                    try {
                        config.setEdit(false);
                        phonConfig.save(config);
                    } catch (Exception e) { e.printStackTrace(); }
                }
            }
        });
        dialog.show();
    }

    private void changePWD(){
        System.out.println("in changePWD");
        System.out.println(dto.toString());
        Bundle bundle = ToBundle( new Bundle(), dto);

        Intent intent = new Intent(FormUi.this, ChangePassword.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, FormData.CHANGEPWD);
    }

    private void securityQuestion(){
        System.out.println("in securityQuestion");
        System.out.println(dto.toString());
        Bundle bundle = ToBundle( new Bundle(), dto);

        Intent intent = new Intent(FormUi.this, SetSecurityQuestion.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, FormData.SECURITYQ);
    }
}

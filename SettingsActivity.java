package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.ExpandListAdapter;
import com.freeoffer.app.adapter.SettingExpandListAdapter;
import com.freeoffer.app.model.Child;
import com.freeoffer.app.model.Group;

import java.util.ArrayList;

/**
 * Created by Rupal on 15-06-2016.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivBack;
    //Button btnTerms,btnPrivacy,btnLicense;
    private ExpandableListView ExpandList;
    private SettingExpandListAdapter ExpAdapter;
    private ArrayList<Group> ExpListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        InitViews();
    }

    private void InitViews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
        ExpListItems = SetStandardGroups();

        ExpAdapter = new SettingExpandListAdapter(SettingsActivity.this, ExpListItems);
        ExpandList.setAdapter(ExpAdapter);
        for(int i=0; i < ExpAdapter.getGroupCount(); i++){
            ExpandList.expandGroup(i);
        }
        ExpandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                return true; // This way the expander cannot be collapsed
            }
        });

        ivBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;

        }
    }

    public ArrayList<Group> SetStandardGroups() {

        String group_names[] = { getResources().getString(R.string.pushnotifications),
                getResources().getString(R.string.emailnotifications)};
//
//		     &amp;


        String child_names[] = { getResources().getString(R.string.storeandproductreview),getResources().getString(R.string.someonefollowme),
                getResources().getString(R.string.activityonreview),getResources().getString(R.string.activityonphotos),
                getResources().getString(R.string.activityoncheckin),getResources().getString(R.string.myfriendjoin),
                getResources().getString(R.string.importantupdates),getResources().getString(R.string.specialofferupdates),
                getResources().getString(R.string.othersocialnotifications),getResources().getString(R.string.purchasedproduct),
                getResources().getString(R.string.storeandproductreview),getResources().getString(R.string.someonefollowme),
                getResources().getString(R.string.activityonreview),getResources().getString(R.string.activityonphotos),
                getResources().getString(R.string.activityoncheckin),getResources().getString(R.string.myfriendjoin),
                getResources().getString(R.string.importantupdates),getResources().getString(R.string.specialofferupdates),
                getResources().getString(R.string.othersocialnotifications),getResources().getString(R.string.weeklynewsletter)};

       /* int Images[] = { R.drawable.home_g, R.drawable.location_g,
                R.drawable.specialofffer_g, R.drawable.myoffer_g, R.drawable.pointcollection_g,
                R.drawable.notigication_g, R.drawable.setting_g, R.drawable.help_spport_g,
                R.drawable.rateus_g, R.drawable.share_g, R.drawable.aboutus_g,
                R.drawable.logout_g };*/

        ArrayList<Group> list = new ArrayList<Group>();

        ArrayList<Child> ch_list;

        int size = 10;
        int j = 0;

        for (String group_name : group_names) {
            Group gru = new Group();
            gru.setName(group_name);

            ch_list = new ArrayList<Child>();
            for (; j < size; j++) {
                Child ch = new Child();
                ch.setName(child_names[j]);
               // ch.setImage(Images[j]);
                ch_list.add(ch);
            }
            gru.setItems(ch_list);
            list.add(gru);

            size = size + 10;
        }

        return list;
    }

}

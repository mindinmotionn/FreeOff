package com.freeoffer.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.freeoffer.app.R;
import com.freeoffer.app.fragments.FollowersFragment;
import com.freeoffer.app.fragments.FollowingFragment;

import java.util.ArrayList;
import java.util.List;

public class CheckinStoreActivity extends Activity implements View.OnClickListener {


    ImageView ivBack;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinstore);

        InitView();
    }

    private void InitView(){

        ivBack=(ImageView)findViewById(R.id.ivback);
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

}

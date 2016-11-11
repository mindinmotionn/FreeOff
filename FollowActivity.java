package com.freeoffer.app.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.freeoffer.app.R;
import com.freeoffer.app.fragments.EightFragment;
import com.freeoffer.app.fragments.FiveFragment;
import com.freeoffer.app.fragments.FollowersFragment;
import com.freeoffer.app.fragments.FollowingFragment;
import com.freeoffer.app.fragments.FourFragment;
import com.freeoffer.app.fragments.NineFragment;
import com.freeoffer.app.fragments.OneFragment;
import com.freeoffer.app.fragments.SevenFragment;
import com.freeoffer.app.fragments.SixFragment;
import com.freeoffer.app.fragments.TenFragment;
import com.freeoffer.app.fragments.ThreeFragment;
import com.freeoffer.app.fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class FollowActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView ivBack;

    Context mContext;
    String strFollowerscount,strFollowingcount;
    LinearLayout llFindshopper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow);
        strFollowerscount="12";
        strFollowingcount="121";
        InitView();
    }

    private void InitView(){
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ivBack=(ImageView)findViewById(R.id.ivback);
        llFindshopper=(LinearLayout)findViewById(R.id.llfindshopper);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ivBack.setOnClickListener(this);
        llFindshopper.setOnClickListener(this);


    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new FollowersFragment(), "Followers " );
        adapter.addFrag(new FollowingFragment(), "Folllowing ");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;

            case R.id.llfindshopper:
                Intent i=new Intent(FollowActivity.this,FindShopperActivity.class);
                startActivity(i);
                break;
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}

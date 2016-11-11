package com.freeoffer.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.fragments.EightFragment;
import com.freeoffer.app.fragments.FiveFragment;
import com.freeoffer.app.fragments.FourFragment;
import com.freeoffer.app.fragments.LocalUpdateFragment;
import com.freeoffer.app.fragments.MyUpdateFragment;
import com.freeoffer.app.fragments.NineFragment;
import com.freeoffer.app.fragments.SevenFragment;
import com.freeoffer.app.fragments.SixFragment;
import com.freeoffer.app.fragments.StorlistFragment;
import com.freeoffer.app.fragments.TenFragment;
import com.freeoffer.app.fragments.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    LinearLayout llFollow,llFollowing;

    ImageView iveditPic;
   // LinearLayout llEditprofile;
    Context mContext;
   // private TabLayout tabLayout;
    //private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);
        mContext=this;
        InitView();
    }

    private void InitView(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        llFollow=(LinearLayout)findViewById(R.id.llfollow);
        llFollowing=(LinearLayout)findViewById(R.id.llfollowing);
       // llEditprofile=(LinearLayout)findViewById(R.id.lleditprofile);
        ivBack.setOnClickListener(this);
        llFollowing.setOnClickListener(this);
        llFollow.setOnClickListener(this);

        iveditPic=(ImageView)findViewById(R.id.ivuserprofile);
        iveditPic.setOnClickListener(this);
        //llEditprofile.setOnClickListener(this);

       /* viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new LocalUpdateFragment(), "Shopline");
        adapter.addFrag(new MyUpdateFragment(), "Reviews (250)");
        adapter.addFrag(new StorlistFragment(), "Check-In (200)");
        adapter.addFrag(new FourFragment(), "Photos (100)");
        /*adapter.addFrag(new FiveFragment(), "Furniture");
        adapter.addFrag(new SixFragment(), "Gifts");
        adapter.addFrag(new SevenFragment(), "Utensils");
        adapter.addFrag(new EightFragment(), " Entertainment");
        adapter.addFrag(new NineFragment(), "  Food");
        adapter.addFrag(new TenFragment(), "Malls");*/
        viewPager.setAdapter(adapter);
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_userprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_notification:
                Toast.makeText(UserProfileActivity.this, "notification", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_share:
                Toast.makeText(UserProfileActivity.this, "share", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.llfollow:
                Intent ifollow=new Intent(this,FollowActivity.class);
                startActivity(ifollow);
                break;
            case R.id.llfollowing:
                Intent ifollowing=new Intent(this,FollowActivity.class);
                startActivity(ifollowing);
                break;
            case R.id.ivuserprofile:
                Intent iuserpic=new Intent(this,EditUserprofileActivity.class);
                startActivity(iuserpic);
                break;

        }
    }
}


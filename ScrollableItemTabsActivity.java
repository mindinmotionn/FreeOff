package com.freeoffer.app.activity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.freeoffer.app.R;
import com.freeoffer.app.fragments.EightFragment;
import com.freeoffer.app.fragments.FiveFragment;
import com.freeoffer.app.fragments.FourFragment;
import com.freeoffer.app.fragments.NineFragment;
import com.freeoffer.app.fragments.OneFragment;
import com.freeoffer.app.fragments.SevenFragment;
import com.freeoffer.app.fragments.SixFragment;
import com.freeoffer.app.fragments.StorlistFragment;
import com.freeoffer.app.fragments.TenFragment;
import com.freeoffer.app.fragments.ThreeFragment;
import com.freeoffer.app.fragments.TwoFragment;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;

import java.util.ArrayList;
import java.util.List;


public class ScrollableItemTabsActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    AutoCompleteTextView AcSearch;
    TextView tvClose;
    LinearLayout llRight;
    ImageView ivSearch;
    TextView tvTital,tvSubtital;

    Context mContext;
    ImageView ivBack,ivFilter;//ivLocation;
    String[] strname={"himalaya","kayaKAlp","deepkale"};
    String strSelectTab="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollableitems_tabs);

        mContext=this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        android.support.v7.app.ActionBar actionbar=getSupportActionBar();
        actionbar.hide();
        //startingShow();
        strSelectTab= GlobalVar.getMyStringPref(mContext, CommonUtilities.SelectedTab);
        InitViews();
    }

    private void InitViews(){
        AcSearch=(AutoCompleteTextView)findViewById(R.id.acsearch);
        llRight=(LinearLayout)findViewById(R.id.llright);
        tvClose=(TextView)findViewById(R.id.tvclose);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        ivSearch=(ImageView)findViewById(R.id.ivsearch);
        ivBack=(ImageView)findViewById(R.id.ivback);
        ivFilter=(ImageView)findViewById(R.id.ivfilter);
        AcSearch.setVisibility(View.GONE);
        llRight.setVisibility(View.VISIBLE);
        tvTital=(TextView)findViewById(R.id.tvcity);
        tvSubtital=(TextView)findViewById(R.id.tvarea);

        AcSearch.setHint(getResources().getString(R.string.searchactionbartext));
        String strCity= GlobalVar.getMyStringPref(mContext, CommonUtilities.locationCity);
        String strArea= GlobalVar.getMyStringPref(mContext, CommonUtilities.locationArea);
        tvTital.setText(""+strCity);
        tvSubtital.setText(""+strArea);

        ivSearch.setOnClickListener(this);
        tvClose.setOnClickListener(this);
        llRight.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivFilter.setOnClickListener(this);

        if(!GeneralFunction.isEmptyCheck(strSelectTab)){
            if(GeneralFunction.isCompare(strSelectTab,"fashion")){
             viewPager.setCurrentItem(1);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"Electronic")){
                viewPager.setCurrentItem(2);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"salon")){
                viewPager.setCurrentItem(3);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"furniture")){
                viewPager.setCurrentItem(4);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"gift")){
                viewPager.setCurrentItem(5);
            }

            else if(GeneralFunction.isCompare(strSelectTab,"homeitems")){
                viewPager.setCurrentItem(6);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"game")){
                viewPager.setCurrentItem(7);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"food")){
                viewPager.setCurrentItem(8);
            }
            else if(GeneralFunction.isCompare(strSelectTab,"mall")){
                viewPager.setCurrentItem(9);
            }
        }
        else{
            viewPager.setCurrentItem(0);
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new StorlistFragment(), "All");
        adapter.addFrag(new TwoFragment(), "Fashion");
        adapter.addFrag(new StorlistFragment(), "Electronics");
        adapter.addFrag(new FourFragment(), "Salon");
        adapter.addFrag(new FiveFragment(), "Furniture");
        adapter.addFrag(new SixFragment(), "Gifts");
        adapter.addFrag(new SevenFragment(), "Utensils");
        adapter.addFrag(new EightFragment(), " Entertainment");
        adapter.addFrag(new NineFragment(), "  Food");
        adapter.addFrag(new TenFragment(), "Malls");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.ivfilter:
                Intent iloc=new Intent(ScrollableItemTabsActivity.this,StoreFilterActivity.class);
                startActivity(iloc);
                finish();
                break;
            case R.id.ivsearch:
                AcSearch.setVisibility(View.VISIBLE);
                llRight.setVisibility(View.GONE);
                break;
            case R.id.tvclose:
                GeneralFunction.hideKeyboard(ScrollableItemTabsActivity.this);
                AcSearch.setVisibility(View.GONE);
                llRight.setVisibility(View.VISIBLE);
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

package com.freeoffer.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.freeoffer.app.R;
import com.freeoffer.app.fragments.EightFragment;
import com.freeoffer.app.fragments.FiveFragment;
import com.freeoffer.app.fragments.FourFragment;
import com.freeoffer.app.fragments.NineFragment;
import com.freeoffer.app.fragments.OneFragment;
import com.freeoffer.app.fragments.SevenFragment;
import com.freeoffer.app.fragments.SixFragment;
import com.freeoffer.app.fragments.TenFragment;
import com.freeoffer.app.fragments.ThreeFragment;
import com.freeoffer.app.fragments.TwoFragment;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    Context mContext;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new OneFragment(), "ONE");
        adapter.addFrag(new TwoFragment(), "TWO");
        adapter.addFrag(new ThreeFragment(), "THREE");
        adapter.addFrag(new FourFragment(), "FOUR");
        adapter.addFrag(new FiveFragment(), "FIVE");
        adapter.addFrag(new SixFragment(), "SIX");
        adapter.addFrag(new SevenFragment(), "SEVEN");
        adapter.addFrag(new EightFragment(), "EIGHT");
        adapter.addFrag(new NineFragment(), "NINE");
        adapter.addFrag(new TenFragment(), "TEN");
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

    private class PrefetchData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // before making http calls
        }

        @Override
        protected Void doInBackground(Void... arg0) {

            try { 		 // Thread will sleep for 2 seconds
                Thread.sleep(4 * 1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            if(!GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(mContext, CommonUtilities.loginUserId))){
                Intent i=new Intent(CategoryActivity.this,LocationActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Intent i=new Intent(CategoryActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }

            /*if (GlobalVar.getMyBooleanPref(mContext, CommonUtilities.IsUSER_Login)) {
                String struserid=GlobalVar.getMyStringPref(mContext,CommonUtilities.AppUserId);
                if(struserid.equalsIgnoreCase("0")){
                    Intent i=new Intent(SplashActiivty.this,LoginActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(SplashActiivty.this,MapScreenDefault.class);
                    i.putExtra("CurrentActivity", "Splash");
                    startActivity(i);
                    finish();
                }
            }
            else{
                Intent i=new Intent(SplashActiivty.this,LoginActivity.class);
                startActivity(i);
                finish();
            }*/

        }
    }




}

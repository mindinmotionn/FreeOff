package com.freeoffer.app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.freeoffer.app.R;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;

public class SplashActivity extends AppCompatActivity {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mContext=this;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        new PrefetchData().execute();

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
                if(GeneralFunction.isCompare(GlobalVar.getMyStringPref(mContext,CommonUtilities.IslocationSelect),"true")){
                    Intent i=new Intent(SplashActivity.this,MainActivity_Home.class);
                    startActivity(i);
                    finish();
                }
                else{
                    Intent i=new Intent(SplashActivity.this,LocationActivity.class);
                    startActivity(i);
                    finish();
                }

            }
            else{
                Intent i=new Intent(SplashActivity.this,HomeActivity.class);
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

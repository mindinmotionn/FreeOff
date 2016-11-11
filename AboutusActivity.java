package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.freeoffer.app.R;

/**
 * Created by Rupal on 15-06-2016.
 */
public class AboutusActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView ivBack;
    Button btnTerms,btnPrivacy,btnLicense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        InitViews();
    }

    private void InitViews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        btnTerms=(Button)findViewById(R.id.btntermsofservice);
        btnPrivacy=(Button)findViewById(R.id.btnprivacypolicy);
        btnLicense=(Button)findViewById(R.id.btnlicens);

        ivBack.setOnClickListener(this);
        btnTerms.setOnClickListener(this);
        btnPrivacy.setOnClickListener(this);
        btnLicense.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.btntermsofservice:
                break;
            case R.id.btnprivacypolicy:
                break;
            case R.id.btnlicens:
                break;
        }
    }
}

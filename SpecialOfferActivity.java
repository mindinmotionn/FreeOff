package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.OfferAddapter;

public class SpecialOfferActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialoffer);
        Initviews();

    }

    private void Initviews(){
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

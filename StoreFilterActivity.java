package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.freeoffer.app.R;

/**
 * Created by Rupal on 16-06-2016.
 */
public class StoreFilterActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterstore);
        InitViews();
    }

    private void InitViews(){
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

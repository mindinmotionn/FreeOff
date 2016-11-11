package com.freeoffer.app.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.freeoffer.app.R;

/**
 * Created by Rupal on 15-06-2016.
 */
public class CartlistActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcart);
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

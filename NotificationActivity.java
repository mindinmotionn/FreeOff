package com.freeoffer.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.freeoffer.app.R;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack,ivSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        InitViews();
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "My offer Activity", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    private void InitViews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        ivSettings=(ImageView)findViewById(R.id.ivsettings);
        ivBack.setOnClickListener(this);
        ivSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.ivsettings:
                Intent isetting=new Intent(NotificationActivity.this,SettingsActivity.class);
                startActivity(isetting);
                break;

        }
    }
}

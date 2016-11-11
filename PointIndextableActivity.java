package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.FollowAddapter;
import com.freeoffer.app.model.GS_follow;

import java.util.ArrayList;

public class PointIndextableActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    ListView lvFollw;
    ArrayList<GS_follow> arrayFollowList=new ArrayList<>();
    FollowAddapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pointindextable);
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

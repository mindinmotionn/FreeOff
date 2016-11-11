package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.FollowAddapter;
import com.freeoffer.app.adapter.buyOfferAddapter;
import com.freeoffer.app.model.GS_Storeoffer;
import com.freeoffer.app.model.GS_follow;

import java.util.ArrayList;

public class FindShopperActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    ListView lvFollw;
    ArrayList<GS_follow> arrayFollowList=new ArrayList<>();
    FollowAddapter Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findshopper);
        Initviews();

    }

    private void Initviews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        lvFollw=(ListView)findViewById(R.id.listview);
        ivBack.setOnClickListener(this);
        lvFollw=(ListView)findViewById(R.id.listview);

        arrayFollowList=new ArrayList<>();
        for(int i=0;i<6;i++){
            GS_follow item=new GS_follow();
            item.setUsername("Abc");
            item.setFollowers("120");
            item.setReview("26 ");
            item.setFollowstatus("true");
            item.setProfileurl("");
            arrayFollowList.add(item);
        }
        Adapter = new FollowAddapter(this,this,R.layout.list_item_store_offer, arrayFollowList);
        lvFollw.setAdapter(Adapter);
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

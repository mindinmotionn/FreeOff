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
import com.freeoffer.app.adapter.UpdatelistAddapter;

public class MyofferActivity extends AppCompatActivity implements View.OnClickListener {

    ListView lvOffer;
    ImageView ivBack;
    OfferAddapter adapter;
    String[] textarry = {
            "Labs",
            "Medical Store",
            "Blood Bank",
            "More",
    } ;
    int[] imagearry = {
            R.drawable.freeoff_noimage,
            R.drawable.logo_orange,
            R.drawable.logo_orange_icon,
            R.drawable.specialoffersorangeicon
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myoffer);
        Initviews();

    }

    private void Initviews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        lvOffer=(ListView)findViewById(R.id.listview);
        adapter = new OfferAddapter(this,this,R.layout.list_item_row_store, textarry, imagearry);
        lvOffer.setAdapter(adapter);

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

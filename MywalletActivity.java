package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.OfferAddapter;

public class MywalletActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack;
    TextView tvTotaldes,tvHowworks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mywallet);

        InitViews();

    }

    private void InitViews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        tvTotaldes=(TextView)findViewById(R.id.tvtotaldes);
        tvHowworks=(TextView)findViewById(R.id.tvhowworks);

        String strdes="<b>"+getResources().getString(R.string.Rs)+" 25 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.onjoining)+"<br><b>"+
                getResources().getString(R.string.Rs)+" 50 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.level_one)+"<br><b>"+
                getResources().getString(R.string.Rs)+" 100 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.level_two)+"<br><b>"+
                getResources().getString(R.string.Rs)+" 150 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.level_three)+"<br><b>"+
                getResources().getString(R.string.Rs)+" 300 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.level_four)+"<br><b>"+
                getResources().getString(R.string.Rs)+" 150 "+getResources().getString(R.string.freebucks)+
                "</b>"+getResources().getString(R.string.level_final)+"<br><b>";
        //tvTotaldes.setOnClickListener(this);

        tvTotaldes.setText(Html.fromHtml(strdes));
        tvHowworks.setText(Html.fromHtml("<u>"+getResources().getString(R.string.howitworks)+"</u>"));

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

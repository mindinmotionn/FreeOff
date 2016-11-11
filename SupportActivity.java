package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeoffer.app.R;

/**
 * Created by Rupal on 16-06-2016.
 */
public class SupportActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivBack,ivDone;
    EditText etFeedback;
    TextView tvBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacksupport);
        InitViews();
    }

    private void InitViews(){
        ivBack=(ImageView)findViewById(R.id.ivback);
        ivDone=(ImageView)findViewById(R.id.ivdone);
        etFeedback=(EditText)findViewById(R.id.etfeedback);
        tvBottom=(TextView)findViewById(R.id.tvbottomtext);
        ivBack.setOnClickListener(this);
        ivDone.setOnClickListener(this);

        String strbottomtext=getResources().getString(R.string.ifyouhavesomething)+" <b>"+
                getResources().getString(R.string.feedbackemail)+"</b>";
        tvBottom.setText(Html.fromHtml(strbottomtext));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.ivdone:
                break;
        }
    }
}

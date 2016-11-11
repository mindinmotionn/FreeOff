package com.freeoffer.app.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.freeoffer.app.R;


/**
 * Created by sc-android on 5/31/2016.
 */
public class RatingActivity extends Activity implements OnClickListener, View.OnTouchListener {
    View view01, view03, view05, view07, view09;
    TextView rating_value, rating_name;

    ImageView ivBack;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customrating);
        view01 = (View) findViewById(R.id.View01);
        view03 = (View) findViewById(R.id.View03);
        view05 = (View) findViewById(R.id.View05);
        view07 = (View) findViewById(R.id.View07);
        view09 = (View) findViewById(R.id.View09);


        try {
            /*view01.setOnClickListener(this);
            view03.setOnClickListener(this);
            view05.setOnClickListener(this);
            view07.setOnClickListener(this);
            view09.setOnClickListener(this);*/

            view01.setOnTouchListener(this);
            view03.setOnTouchListener(this);
            view05.setOnTouchListener(this);
            view07.setOnTouchListener(this);
            view09.setOnTouchListener(this);

        }
        catch (Exception e){

        } InitView();
    }

    private void InitView(){

        ivBack=(ImageView)findViewById(R.id.ivback);
        ivBack.setOnClickListener(this);

    }



    @SuppressLint("ResourceAsColor")
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ivback:
                finish();
                break;
            /*case R.id.btnLocation:

                break;
            case R.id.btnselectmultipleiv:
                Intent intent = new Intent();
                intent.setType("image*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 100);
                break;*/
            case R.id.View01:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.Default);
                view05.setBackgroundResource(R.color.Default);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 1.0");
                rating_name.setText("Rating : Poor");
                break;
            case R.id.View03:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.Default);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 2.0");
                rating_name.setText("Rating : Fair");
                break;
            case R.id.View05:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 3.0");
                rating_name.setText("Rating : Average");
                break;
            case R.id.View07:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.color7);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 4.0");
                rating_name.setText("Rating : Good");
                break;
            case R.id.View09:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.color7);
                view09.setBackgroundResource(R.color.color9);
                rating_value.setText("Rating : 5.0");
                rating_name.setText("Rating : Excellent");
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.View01:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.Default);
                view05.setBackgroundResource(R.color.Default);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 1.0");
                rating_name.setText("Rating : Poor");
                break;
            case R.id.View03:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.Default);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 2.0");
                rating_name.setText("Rating : Fair");
                break;
            case R.id.View05:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.Default);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 3.0");
                rating_name.setText("Rating : Average");
                break;
            case R.id.View07:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.color7);
                view09.setBackgroundResource(R.color.Default);
                rating_value.setText("Rating : 4.0");
                rating_name.setText("Rating : Good");
                break;
            case R.id.View09:
                view01.setBackgroundResource(R.color.color1);
                view03.setBackgroundResource(R.color.color3);
                view05.setBackgroundResource(R.color.color5);
                view07.setBackgroundResource(R.color.color7);
                view09.setBackgroundResource(R.color.color9);
                rating_value.setText("Rating : 5.0");
                rating_name.setText("Rating : Excellent");
                break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
           // Parcelable[] parcelableUris = data.getParcelableArrayExtra(ImagePickerActivity.TAG_IMAGE_URI);

            //Java doesn't allow array casting, this is a little hack
           /* Uri[] uris = new Uri[parcelableUris.length];
            System.arraycopy(parcelableUris, 0, uris, 0, parcelableUris.length);*/
           /* adapter.clear();

            viewSwitcher.setDisplayedChild(1);
            String single_path = data.getStringExtra("single_path");
            imageLoader.displayImage("file://" + single_path, imgSinglePick);*/

        } else if (requestCode == 200 && resultCode == Activity.RESULT_OK) {
            /*String[] all_path = data.getStringArrayExtra("all_path");

            ArrayList<CustomGallery> dataT = new ArrayList<CustomGallery>();

            for (String string : all_path) {
                CustomGallery item = new CustomGallery();
                item.sdcardPath = string;

                dataT.add(item);
            }

            viewSwitcher.setDisplayedChild(0);
            adapter.addAll(dataT);*/
        }
    }
}

package com.freeoffer.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.ReviewlistAddapter;
import com.freeoffer.app.general.GeneralFunction;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreprofileActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivimage,ivBack;
    // Google Map
    private GoogleMap googleMap;
    Marker OriginMarker;
    String latitude,longitude;
    //recent review listview
    private ListView listview;
    private RelativeLayout rlTopbar;
    ScrollView scrollview;
    String[] webbottom = {
            "Labs",
            "Medical Store",
            "Blood Bank",
            "More",
    } ;
    int[] imageIdbottom = {
            R.drawable.no_picture_sign,
            R.drawable.bgmain,
            R.drawable.storepf1,
            R.drawable.storepf2
    };
    float startX = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_storeprofile);
            InitViews();

        }
        catch (Exception e){

        }

    }

    private void InitViews(){
        listview=(ListView)findViewById(R.id.lvreview);
        //llMain.setOnClickListener(this);
        ReviewlistAddapter adapter = new ReviewlistAddapter(this,this,R.layout.list_item_row_store, webbottom, imageIdbottom);
        listview.setAdapter(adapter);

        GeneralFunction.setListViewHeightBasedOnChildren(listview);
        listview.setFocusable(false);

       /* listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(StoreprofileActivity.this, UserProfileActivity.class);
                startActivity(i);
            }
        });*/
        ivimage=(ImageView)findViewById(R.id.ivimage);
        ivBack=(ImageView)findViewById(R.id.ivback);
        rlTopbar=(RelativeLayout)findViewById(R.id.rl_header);
        scrollview=(ScrollView)findViewById(R.id.scrollview);
        rlTopbar.setVisibility(View.GONE);

        ivBack.setOnClickListener(this);




        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        latitude="23.0225";
        longitude="72.5714";
        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
        googleMap.animateCamera(cameraUpdate);
        OriginMarker=googleMap.addMarker(new MarkerOptions().position(latLng).title("Store name")
                //.draggable(true)                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_currentlocation))
                //.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        );
/*
        scrollview.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

            }
        });*/
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {

               /* if(event.getAction()==MotionEvent.ACTION_UP){

                }*/
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                       // Toast.makeText(StoreprofileActivity.this, "Down movement", Toast.LENGTH_SHORT).show();
                        startX = event.getY();
                        //
                        break;
                    case MotionEvent.ACTION_UP:
                       //Toast.makeText(StoreprofileActivity.this, "Up movement", Toast.LENGTH_SHORT).show();
                        float currentX = event.getY();
                        if (startX > currentX + 200 ) {
                          //  nextText(v);
                            rlTopbar.setVisibility(View.VISIBLE);
                            //Toast.makeText(StoreprofileActivity.this, "Down movement", Toast.LENGTH_SHORT).show();
                        }
                        if (startX  < currentX - 100) {
                            //Toast.makeText(StoreprofileActivity.this, "up movement", Toast.LENGTH_SHORT).show();
                            rlTopbar.setVisibility(View.GONE);
                        }
                        break;
                }
                return false;
            }
        });

    }


    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to get Location", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                onBackPressed();
                break;
        }
    }
}

package com.freeoffer.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.ReviewlistAddapter;
import com.freeoffer.app.adapter.buyOfferAddapter;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.model.GS_Storeoffer;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;

public class AnimateToolbarCustom extends AppCompatActivity implements View.OnClickListener {
    CollapsingToolbarLayout collapsingToolbar;
    private GoogleMap googleMap;
    Marker OriginMarker;
    String latitude,longitude;
    //recent review listview
    private ListView listview,lvOffer;
    TextView tvStorename;
    //private RelativeLayout rlTopbar;
    Button btnReporerro,btnWritereview,btnQrcode;


    CustomDialog_Ok customdialog;

    buyOfferAddapter adapterBuy;
    ArrayList<GS_Storeoffer> arrayOfferList=new ArrayList<>();

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
    LinearLayout llCall,llcheckin,llReview,llOffer;
    String Storename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_storeprofile2);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // collapsingToolbar.setTitle("Suleiman Ali Shakir");
        Storename=getIntent().getStringExtra("Storename");
        setCollapsingToolbarLayoutTitle(Storename);


        /*Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.splash);
        int mutedColor = R.attr.colorPrimary;
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(mutedColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.accent_700);
            }
        });*/
        InitViews();

    }
    private void setCollapsingToolbarLayoutTitle(String title) {
        collapsingToolbar.setTitle(title);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        //collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBarPlus1);
       // collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBarPlus1);
    }

    private void InitViews(){
        customdialog=new CustomDialog_Ok(this);
        listview=(ListView)findViewById(R.id.lvreview);
        lvOffer=(ListView)findViewById(R.id.lvoffer);
        llCall=(LinearLayout)findViewById(R.id.llcontact);
        llcheckin=(LinearLayout)findViewById(R.id.llcheckin);
        llReview=(LinearLayout)findViewById(R.id.llreview);
        llOffer=(LinearLayout)findViewById(R.id.lloffer);
        tvStorename=(TextView)findViewById(R.id.tvstorename);
        btnReporerro=(Button)findViewById(R.id.btnreportfeedback);
        btnWritereview=(Button)findViewById(R.id.btnwritereview);
        btnQrcode=(Button)findViewById(R.id.btnqrcode);

        tvStorename.setText(Storename);
        //llMain.setOnClickListener(this);
        ReviewlistAddapter adapter = new ReviewlistAddapter(this,this,R.layout.list_item_row_store, webbottom, imageIdbottom);
        listview.setAdapter(adapter);

        arrayOfferList=new ArrayList<>();
        for(int i=0;i<2;i++){
            GS_Storeoffer item=new GS_Storeoffer();
            item.setOfferName("Unlimited Veg Lunch for 2");
            item.setOriginalRs("Rs " + "900");
            item.setOfferRs("Rs " + "250");
            arrayOfferList.add(item);
        }
        adapterBuy = new buyOfferAddapter(this,this,R.layout.list_item_store_offer, arrayOfferList);
        lvOffer.setAdapter(adapterBuy);
        GeneralFunction.setListViewHeightBasedOnChildren(listview);
        GeneralFunction.setListViewHeightBasedOnChildren(lvOffer);
        listview.setFocusable(false);

        llCall.setOnClickListener(this);
        llcheckin.setOnClickListener(this);
        llReview.setOnClickListener(this);
        llOffer.setOnClickListener(this);
        btnReporerro.setOnClickListener(this);
        btnWritereview.setOnClickListener(this);
        btnQrcode.setOnClickListener(this);
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

   /* @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                onBackPressed();
                break;
        }
    }*/


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_storeprofile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_bookmark:
                Toast.makeText(AnimateToolbarCustom.this, "bookmark", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_share:
                Toast.makeText(AnimateToolbarCustom.this, "share", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btnreportfeedback:
              Intent ireportfeedback=new Intent(AnimateToolbarCustom.this,SupportActivity.class);
              startActivity(ireportfeedback);
              break;
          case R.id.btnwritereview:
              /*Intent iwritereview=new Intent(AnimateToolbarCustom.this,.class);
              startActivity(iwritereview);*/
              break;
          case R.id.llcheckin:
              Intent icheckin=new Intent(AnimateToolbarCustom.this,CheckinStoreActivity.class);
              startActivity(icheckin);
              break;
          case R.id.llreview:
              Intent ireview=new Intent(AnimateToolbarCustom.this,RatingActivity.class);
              startActivity(ireview);
              break;
          case R.id.btnqrcode:
              try {
                  Intent iqrscan = new Intent(AnimateToolbarCustom.this, BarcodeScanActivity.class);
                  startActivity(iqrscan);
              }
              catch(Exception e){
                  GeneralFunction.DisplayMessage(customdialog,"Exception "+e);
              }
              break;
      }
    }
}

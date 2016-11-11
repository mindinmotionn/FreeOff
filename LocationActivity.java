package com.freeoffer.app.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.dialog.ProgressHUD;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.ConnectionDetector;
import com.freeoffer.app.general.GPSTracker;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.general.JSONParser;
import com.freeoffer.app.general.JSONParserHive;
import com.freeoffer.app.general.ServiceHandler;
import com.freeoffer.app.general.TopExceptionHandler;
import com.freeoffer.app.general.WS_Methods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, DialogInterface.OnCancelListener {

    CustomDialog_Ok Customdialog_Ok;
    ProgressHUD mProgressHUD;
    ConnectionDetector cdObj;

    ImageView ivGo;
    RelativeLayout rlContinue;
    TextView tvUsemyLocation;
    EditText etCity,etArea;

   // String strCity,strArea;
    Context mContext;
    boolean IsGpsEnable;

    private LocationListener locListener;

    public double origin_latitude;
    public double origin_longitude;
    private GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationManager locationManager;
    private static final LocationRequest REQUEST = LocationRequest.create()
            .setInterval(60 * 1000)         // 5 seconds
            .setFastestInterval(60 * 10000)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    Geocoder geocoder;
    List<Address> addresses;
    String strCity="",strArea="",strLatitude,strLongitude;
    String CurrentAddress = "";
    private GoogleMap mMap;

    double longitude, latitude;
    GPSTracker gps;
    TextView tvSkip;
    //ImageView ivDone;

    String strValidMsg="Try Again";
    // Get city list and area list

   // CustomDialog_Ok customDialog;
   JSONParserHive jParser = new JSONParserHive();
    ArrayList<String> arrayCitylist=new ArrayList<>(),arryArealist=new ArrayList<>();
    String webMethod="";
    JSONArray jsCatArray;
    String strUserid="";
    String Webresponse;
    boolean flagnext=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mContext=this;
        InitViews();
    }

    private void InitViews(){
        Customdialog_Ok=new CustomDialog_Ok(mContext);
        cdObj=new ConnectionDetector(mContext);

        ivGo=(ImageView)findViewById(R.id.ivgo);
        rlContinue=(RelativeLayout)findViewById(R.id.rl_bottom);
        tvUsemyLocation=(TextView)findViewById(R.id.tvusemylocation);
        tvSkip=(TextView)findViewById(R.id.tvskip);
        etCity=(EditText)findViewById(R.id.etcity);
        etArea=(EditText)findViewById(R.id.etarea);

       // ivGo.setOnClickListener(this);
        rlContinue.setOnClickListener(this);
        etCity.setOnClickListener(this);
        etArea.setOnClickListener(this);
        tvUsemyLocation.setOnClickListener(this);

        tvSkip.setOnClickListener(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        Criteria c = new Criteria();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        // setup map
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationChangeListener(myLocationChangeListener);

        }

    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {

            origin_latitude=location.getLatitude();
            origin_longitude=location.getLongitude();
            /*String currentLat = "" + location.getLatitude();
            String currentLog = "" + location.getLongitude();*/
            //GetcurrentAddress(location.getLatitude(),location.getLongitude());
        }
    };



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_bottom:
                if(ValidateControl()){
                    GlobalVar.setMyStringPref(mContext, CommonUtilities.locationArea,etArea.getText().toString());
                    GlobalVar.setMyStringPref(mContext, CommonUtilities.locationCity, etCity.getText().toString());
                    GlobalVar.setMyStringPref(mContext, CommonUtilities.locationLatitude,""+origin_latitude);
                    GlobalVar.setMyStringPref(mContext, CommonUtilities.locationLongitude,""+origin_latitude);
                    GlobalVar.setMyStringPref(mContext,CommonUtilities.IslocationSelect,"true");
                    Intent ihome=new Intent(LocationActivity.this,MainActivity_Home.class);
                    startActivity(ihome);
                    finish();
                }
                else{
                    Gpstrackarlatlong();
                    GeneralFunction.DisplayMessage(Customdialog_Ok, strValidMsg);
                   /* Intent ihome=new Intent(LocationActivity.this,MainActivity_Home.class);
                    startActivity(ihome);
                    finish();*/
                }

                break;
            case R.id.tvusemylocation:
                //Check Gps Avaibility
                IsGpsEnable = GeneralFunction.CheckGpsAvailable(mContext);
                if (IsGpsEnable) {
                    Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
                    strLatitude=""+origin_latitude;
                    strLongitude=""+origin_longitude;
                    //Gpstrackarlatlong();
                   if(strLatitude.equalsIgnoreCase("0.0") && GeneralFunction.isEmptyCheck(strLatitude)) {
                      Gpstrackarlatlong();
                    }
                    else{
                        GetcurrentAddress(origin_latitude, origin_longitude);
                    }
                    /*if(GeneralFunction.isEmptyCheck(strCity)){
                        Gpstrackarlatlong();
                    }
                    else if(GeneralFunction.isEmptyCheck(strArea)){
                        Gpstrackarlatlong();
                    }*/
                    //mProgressHUD.dismiss();
                    etCity.setText(strCity);
                    etArea.setText(strArea);
                    if(GeneralFunction.isEmptyCheck(strCity) && GeneralFunction.isEmptyCheck(strArea)){

                    }
                } else {
                    GeneralFunction.GpsAlertMessage(mContext, LocationActivity.this);
                }
               /* mProgressHUD = ProgressHUD.show(LocationActivity.this, "Wait...",
                        true, true, this);*/


                break;
            case R.id.etcity:
                if(arrayCitylist.size()>0){
                    showCityListWindow();
                }
                else{
                    webMethod = "city";
                    if (cdObj.isConnectingToInternet()) {
                        try {
                            TimeConsumingTask task = new TimeConsumingTask();
                            task.execute();
                        } catch (Exception ex) {
                            //Log.e(TAG, "Category Exception", ex);
                        }
                    }else{GeneralFunction.IntenetConnectionMsg(Customdialog_Ok);}
                }


                //Toast.makeText(mContext,"comming soon",Toast.LENGTH_SHORT).show();
                break;
            case R.id.etarea:
                //Toast.makeText(mContext,"Area comming soon",Toast.LENGTH_SHORT).show();

                break;
            case R.id.tvskip:/*
                if(strLatitude.equalsIgnoreCase("0.0") && GeneralFunction.isEmptyCheck(strLatitude)) {
                    Gpstrackarlatlong();
                }*/
                GlobalVar.setMyStringPref(mContext, CommonUtilities.locationArea, "Ratan Pole");
                GlobalVar.setMyStringPref(mContext, CommonUtilities.locationCity, "Ahmedabad");
                GlobalVar.setMyStringPref(mContext, CommonUtilities.locationLatitude,"23.027099");
                GlobalVar.setMyStringPref(mContext, CommonUtilities.locationLongitude, "72.5941450");
                GlobalVar.setMyStringPref(mContext, CommonUtilities.IslocationSelect, "true");
                Intent ihome=new Intent(LocationActivity.this,MainActivity_Home.class);
                startActivity(ihome);
                finish();
                break;
        }
    }


    private void Gpstrackarlatlong(){
        gps = new GPSTracker(LocationActivity.this);
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
        } else {
            gps.showSettingsAlert();
        }
        strLatitude=""+latitude;
        strLongitude=""+longitude;
        GetcurrentAddress(latitude, longitude);
    }

    @Override
    public void onConnected(Bundle bundle) {
     try{
         LocationServices.FusedLocationApi.requestLocationUpdates(
                 mGoogleApiClient, REQUEST, (LocationListener) this);
         mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
         if (mLastLocation != null) {
             latitude = mLastLocation.getLatitude();
             longitude = mLastLocation.getLongitude();
             //GetcurrentAddress(latitude,longitude);

         }

     }
     catch(Exception e){

     }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void GetcurrentAddress(Double latitude, Double longitude) {
        try {
            geocoder = new Geocoder(this, Locale.getDefault());

            try {
                addresses = geocoder.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                if(strLatitude.equalsIgnoreCase("0.0") || GeneralFunction.isEmptyCheck(strLatitude)) {
                    // flagnext=true;
                    Gpstrackarlatlong();
                }
                else {
                    webMethod = "GelLocationByApi";
                    if (cdObj.isConnectingToInternet()) {
                        try {
                            TimeConsumingTask task = new TimeConsumingTask();
                            task.execute();
                        } catch (Exception ex) {
                            //Log.e(TAG, "Category Exception", ex);
                        }
                    } else {
                        GeneralFunction.IntenetConnectionMsg(Customdialog_Ok);
                    }
                }
                /*if(cdObj.isConnectingToInternet()) {

                    GelLocationByApi("" + latitude, "" + longitude);
                }
                else{
                    GeneralFunction.IntenetConnectionMsg(Customdialog_Ok);
                }*/
                //GeneralFunction.DisplayMessage(Customdialog_Ok, "Location service is not available, Plesae reboot your device.");
            }

            if (addresses != null) {
                //addressline = addresses.get(0).getAddressLine(0);
                strCity = addresses.get(0).getLocality();// city
                strArea=addresses.get(0).getSubLocality();
                if(strArea==null){
                    strArea=addresses.get(0).getAddressLine(1);
                    //=addresses.get(0).getThoroughfare();
                }
                else if(strArea==null){
                    strArea=addresses.get(0).getThoroughfare();
                    //=addresses.get(0).getThoroughfare();
                }
                etCity.setText(strCity);
                etArea.setText(strArea);
                if(strCity==null||strArea==null){
                    webMethod = "GelLocationByApi";
                    if (cdObj.isConnectingToInternet()) {
                        try {
                            TimeConsumingTask task = new TimeConsumingTask();
                            task.execute();
                        } catch (Exception ex) {
                            //Log.e(TAG, "Category Exception", ex);
                        }
                    } else {
                        GeneralFunction.IntenetConnectionMsg(Customdialog_Ok);
                    }
                }
            }
            else{
               // if(strCity==null||strArea==null){
                    webMethod = "GelLocationByApi";
                    if (cdObj.isConnectingToInternet()) {
                        try {
                            TimeConsumingTask task = new TimeConsumingTask();
                            task.execute();
                        } catch (Exception ex) {
                            //Log.e(TAG, "Category Exception", ex);
                        }
                    } else {
                        GeneralFunction.IntenetConnectionMsg(Customdialog_Ok);
                    }
                //}
            }
        } catch (Exception e) {

        }
    }
// For Use my Location
    public boolean ValidateControl(){
        boolean isValidate=true;
        String strCity=etCity.getText().toString();
        String strArea=etArea.getText().toString();
        if(GeneralFunction.isEmptyCheck(strLatitude) && GeneralFunction.isEmptyCheck(strLongitude)){
            isValidate=false;
            strValidMsg="select Location";
        }

        else if(GeneralFunction.isEmptyCheck(strCity)){
           isValidate=false;
            strValidMsg="select City";
        }
        else if(GeneralFunction.isEmptyCheck(strArea) ){
            isValidate=false;
            strValidMsg="select Area";
        }
        return  isValidate;
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        mProgressHUD.dismiss();
    }

    // Time

    public class TimeConsumingTask extends AsyncTask<Void, String, Void> implements DialogInterface.OnCancelListener {
        ProgressHUD mProgressHUD;

        @Override
        protected void onPreExecute() {
            mProgressHUD = ProgressHUD.show(mContext, "Connecting", true, true, this);
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {

                //publishProgress("Connecting");
                Thread.sleep(500);
                publishProgress("Loading...");
                if (webMethod.equals("city")) {
                    WS_citylist("OnProcess");
                }else if (webMethod.equals("GelLocationByApi")) {
                    Url_GelLocationByApi("OnProcess");
                }


                Thread.sleep(1000);
                publishProgress("Done");

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mProgressHUD.setMessage(values[0]);
            mProgressHUD.setCanceledOnTouchOutside(false);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {

            if (webMethod.equals("city")) {
                WS_citylist("OnPost");
            } /*else if (webMethod.equals("RiderForgotPassword")) {
                WS_RiderForgotPassword("OnPost");
            }*/
            else if (webMethod.equals("GelLocationByApi")) {
                Url_GelLocationByApi("OnProcess");
            }

            mProgressHUD.dismiss();
            super.onPostExecute(result);
        }

        @Override
        public void onCancel(DialogInterface dialog) {
            this.cancel(true);
            mProgressHUD.dismiss();
        }
    }

    public void WS_city(String AsyncMethodType) {
        try {
            if (AsyncMethodType.equals("OnProcess")) {

                JSONParser jWeb = new JSONParser();
                HashMap<String, String> parameters = new HashMap<String, String>();
                parameters.put("State", "Florida");
                arrayCitylist=new ArrayList<>();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                String url_city = WS_Methods.MainUrl+"city.php";
                JSONObject json = jParser.makeHttpRequest(url_city, "GET", params);
                ServiceHandler sh = new ServiceHandler();
                List<NameValuePair> nameValuePairs = new ArrayList<>(1);
                Webresponse = sh.makeServiceCall1(WS_Methods.signin, 1, nameValuePairs);
                Log.d("Rup", "GetItemCategory" + json+ "Webresponse"+Webresponse);
                if (json.length() > 0) {
                    for (int i = 0; i < json.length(); i++) {
                        try {
                            arrayCitylist.add(json.getString("cities").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
            else{
                // Post Method
                if(arrayCitylist.size()>0){
                    showCityListWindow();
                }
                else{
                    Toast.makeText(LocationActivity.this, "City not Available now", Toast.LENGTH_SHORT).show();
                }
            }

        } catch (Exception ex) {
            //Log.d("Rup","Exception "+ex);
        }
    }

    private void showCityListWindow(){
        try {
            // Adapter For State
            final ArrayAdapter<String> spinner_state = new ArrayAdapter<String>(LocationActivity.this, R.layout.spinner_item,
                    arrayCitylist);
            new AlertDialog.Builder(LocationActivity.this)
                    .setTitle("Select County")
                    .setAdapter(spinner_state,
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int index) {
                                    String strold = etCity.getText().toString();
                                    String strnew = arrayCitylist.get(index).toString();
                                    etCity.setText(strnew);
                                    if (!GeneralFunction.isCompare(strold, strnew)) {
                                        etArea.setText("");
                                    }
                                    dialog.dismiss();
                                }
                            }).create().show();
        }
        catch(Exception e){}
    }

    public void WS_citylist(String AsyncMethodType) {

        try {
            if (AsyncMethodType.equals("OnProcess")) {
                JSONParserHive jParser = new JSONParserHive();
                //WS_Url="http://192.168.0.107/AayumedAPI/api/all/ValidatePatientLogin?username=test@test.com&password=test&facebookid=";
                ServiceHandler sh = new ServiceHandler();
                String jsondata = null;

                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
               /* nameValuePairs.add(new BasicNameValuePair("email", strEmail));
                nameValuePairs.add(new BasicNameValuePair("password_hash", strPwd));
                */// nameValuePairs.add(new BasicNameValuePair("city",""));
                // Webresponse = sh.postData(jsondata);
                //Webresponse = sh.makeServiceCall(WS_Methods.signup, 2, params);
                Webresponse = sh.makeServiceCall1(WS_Methods.city, 2, nameValuePairs);
                Log.d("Rup", "parameters " + jsondata);

                String Response = Webresponse.toLowerCase();

                if(!GeneralFunction.isEmptyCheck(Webresponse)){
                    JSONObject jsonObj = new JSONObject(Webresponse);
                    // strWS_Message = jsonObj.getString(CommonUtilities.TAG_Message);

                    //strUserId=o_data.getString("user_id");
                }
                else{
                    //AlertDialog();
                }

            } else {
                // Post Method
                // String strUserId="";
                if(!GeneralFunction.isEmptyCheck(Webresponse)){


                }


            }



        } catch (Exception ex) {
            Log.d("Rup", "Exception " + ex);
        }
    }

    //Gps Api through location address of latitude longitude
    public void Url_GelLocationByApi(String AsyncMethodType) {
        try {
            if (AsyncMethodType.equals("OnProcess")) {

                try {
                    String Address1 = "";
                    String Address2 = "";
                    String City = "";
                    String State = "";
                    String Country = "",County = "",PIN = "";

                    JSONParser parser_Json=new JSONParser();
                    String url="http://maps.googleapis.com/maps/api/geocode/json?latlng=" + strLatitude+ ","
                            + strLongitude + "&sensor=true";
                    url=url + "&key=AIzaSyAMOeS67bWVgAqVz0Srax-gjlItYKHTyno";

                    JSONObject jsonObj = parser_Json.getJSON_ObjectfromURL(url);
                    String Status = jsonObj.getString("status");
                    if (Status.equalsIgnoreCase("OK")) {
                        JSONArray Results = jsonObj.getJSONArray("results");
                        JSONObject zero = Results.getJSONObject(0);
                        JSONArray address_components = zero.getJSONArray("address_components");

                        for (int i = 0; i < address_components.length(); i++) {
                            JSONObject zero2 = address_components.getJSONObject(i);
                            String long_name = zero2.getString("long_name");
                            JSONArray mtypes = zero2.getJSONArray("types");
                            String Type = mtypes.getString(0);

                            if (TextUtils.isEmpty(long_name) == false || !long_name.equals(null) || long_name.length() > 0 || long_name != "") {

                                Address1 = Address1 + long_name;
                                City = long_name;
                                if (Type.equalsIgnoreCase("street_number")) {
                                    Address1 = long_name + " ";
                                } else if (Type.equalsIgnoreCase("route")) {
                                    Address1 = Address1 + long_name;
                                } else if (Type.equalsIgnoreCase("sublocality")) {
                                    Address2 = long_name;
                                } else if (Type.equalsIgnoreCase("locality")) {
                                    // Address2 = Address2 + long_name + ", ";
                                    City = long_name;
                                } else if (Type.equalsIgnoreCase("administrative_area_level_2")) {
                                    County = long_name;
                                } else if (Type.equalsIgnoreCase("administrative_area_level_1")) {
                                    State = long_name;
                                } else if (Type.equalsIgnoreCase("country")) {
                                    Country = long_name;
                                } else if (Type.equalsIgnoreCase("postal_code")) {
                                    PIN = long_name;
                                }
                            }
                            Address1 = Address1 + long_name;
                            City = long_name;
                            strCity=City;
                            strArea=Address1;

                            // JSONArray mtypes = zero2.getJSONArray("types");
                            // String Type = mtypes.getString(0);
                            // Log.e(Type,long_name);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                // Post Method
                etCity.setText(strCity);
                etArea.setText(strArea);
            }

        } catch (Exception ex) {
            //Log.d("Rup","Exception "+ex);
        }
    }



}

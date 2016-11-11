package com.freeoffer.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookActivity;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.freeoffer.app.R;
import com.freeoffer.app.general.ImageLoader;


import org.json.JSONObject;

import java.lang.Exception;import java.lang.Override;import java.util.Arrays;

public class Checkinactivity extends ActionBarActivity {

    Button btnCheckinFacebook;
    ProgressDialog progress;
    private CallbackManager callbackManager;
    Context mContext;

    ImageView ivimage;
    String strUrl="http://api.androidhive.info/json/movies/1.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkinactivity);
        mContext=this;
        //Check in with facebook related

        btnCheckinFacebook=(Button)findViewById(R.id.btncheckinfacebook);
        ivimage=(ImageView)findViewById(R.id.imageView);

        ImageLoader imageloader=new ImageLoader(mContext);
        imageloader.DisplayImage(strUrl,ivimage,"roundcorner");


        btnCheckinFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iSignup=new Intent(Checkinactivity.this,LocationActivity.class);
                startActivity(iSignup);
                /*webMethod = "signup";
                TimeConsumingTask task = new TimeConsumingTask();
                task.execute();*/
            }
        });
        /*btnCheckinFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle params = new Bundle();
                params.putString("access_token", Main.access);
                params.putString("place", "203682879660695");
                params.putString("Message","I m here in this place");
                JSONObject coordinates = new JSONObject();
                try {
                    coordinates.put("latitude", 23.038484);
                    coordinates.put("longitude", 72.512125);
                }
                catch (Exception e){}
                params.putString("coordinates",coordinates.toString());
                JSONArray frnd_data=new JSONArray();
                params.putString("tags", "xxxx");//where xx indicates the User Id
                String response = faceBook.request("me/checkins", params, "POST");
                Log.d("Response", response);
            }
        });*/
    }



    private void FacebookView(){

        progress=new ProgressDialog(Checkinactivity.this);
        progress.setMessage(getResources().getString(R.string.please_wait_facebooklogin));
        progress.setIndeterminate(false);
        progress.setCancelable(false);

        //for facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        //register callback object for facebook result
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    /*facebook_id=profile.getId();
                    f_name=profile.getFirstName();
                    m_name=profile.getMiddleName();
                    l_name=profile.getLastName();
                    full_name=profile.getName();
                    profile_image=profile.getProfilePictureUri(100, 100).toString();
                    etFirstname.setText("" + f_name);
                    etLastname.setText("" + l_name);
                    //Bitmap bitmap = ivTakephoto.getDrawingCache();
                    if(!GeneralFunction.isEmptyCheck(profile_image)) {
                        new ImageDownloaderTask(ivTakephoto).execute(profile_image);
                    }*/
                }
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                               /* try {
                                    email_id=object.getString("email");
                                } catch (JSONException e) {
                                    // Log.d("Rup","Exception: "+e);
                                }
                                etEmail.setText(""+email_id);*/
                                progress.dismiss();
                                /*Facebook authenticatedFacebook = new FacebookSdk(APP_ID);
                                Bundle params = new Bundle();
                                params.putString("access_token", String.valueOf(accessToken));
                                params.putString("place", "203682879660695");
                                params.putString("Message","I m here in this place");
                                JSONObject coordinates = new JSONObject();
                                try {
                                    coordinates.put("latitude", 23.038484);
                                    coordinates.put("longitude", 72.512125);
                                }
                                catch (Exception e){}
                                params.putString("coordinates",coordinates.toString());
                                JSONArray frnd_data=new JSONArray();
                                params.putString("tags", "xxxx");//where xx indicates the User Id
                                String response1 = faceBook.request("me/checkins", params, "POST");
                                Log.d("Response", response1);*/
                            }

                        });
                /*Bundle parameters = new Bundle();
                parameters.putString("fields", "id,birthday,link,email");
                request.setParameters(parameters);*/
                Bundle params = new Bundle();
                params.putString("access_token", String.valueOf(accessToken));
                params.putString("place", "203682879660695");
                params.putString("Message","I m here in this place");
                JSONObject coordinates = new JSONObject();
                try {
                    coordinates.put("latitude", 23.038484);
                    coordinates.put("longitude", 72.512125);
                }
                catch (Exception e){}
                params.putString("coordinates", coordinates.toString());
                request.setParameters(params);

                request.executeAsync();
            }


               /* new GraphRequest(
                        AccessToken.getCurrentAccessToken(),
                        "/{check-in-id}",
                        null,
                        HttpMethod.GET,
                        new GraphRequest.Callback() {
                            public void onCompleted(GraphResponse response) {

            *//* handle the result *//*
                            }
                        }
                ).executeAsync();
            }*/

            public void onCancel() {
                Toast.makeText(Checkinactivity.this, getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            public void onError(FacebookException error) {
                Toast.makeText(Checkinactivity.this,getResources().getString(R.string.login_canceled_facebooklogin),Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
        btnCheckinFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    LoginManager.getInstance().logInWithReadPermissions(Checkinactivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                } catch (Exception e) {
                    //GeneralFunction.DisplayMessage(customDialog, "Error: " + e.toString());
                }
            }
        });
    }

}

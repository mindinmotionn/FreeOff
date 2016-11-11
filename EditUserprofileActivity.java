package com.freeoffer.app.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.freeoffer.app.R;
import com.freeoffer.app.general.GeneralFunction;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import io.fabric.sdk.android.Fabric;

public class EditUserprofileActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rlContent;
//    TextView tvName,tvEmail,tvPhoneno,tvCity,tvWebsite;
    EditText etUsername,etName,etEmail,etMobileno,etCity,etWebsite,etBio;
    EditText etGender,etChangepwd;
    ImageView ivBack,ivDone,ivProfile;
    TextView tvChangepic;
    LinearLayout llChangepf;

    DigitsAuthButton digitsButton;
    Button btnDone;

    //Fabric digits
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "XszH87CVE1cSuORCQsbt6cz9T";
    private static final String TWITTER_SECRET = "CyTXqzHGkWTIFdFtovzGTuzZkX6I8c51Xt2NUV51RjsOMlB6Aa";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());

        setContentView(R.layout.activity_edit_userprofile);
        InitViews();
    }

    private void InitViews(){
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/
        rlContent=(RelativeLayout)findViewById(R.id.rlcontent);
        /*tvName=(TextView)rlContent.findViewById(R.id.tvname);
        tvEmail=(TextView)rlContent.findViewById(R.id.tvemailaddress);
        tvPhoneno=(TextView)rlContent.findViewById(R.id.tvphoneno);
        tvCity=(TextView)rlContent.findViewById(R.id.tvcity);
        tvWebsite=(TextView)rlContent.findViewById(R.id.tvwebsite);
*/
        etUsername=(EditText)rlContent.findViewById(R.id.etusername);
        etName=(EditText)rlContent.findViewById(R.id.etname);
        etWebsite=(EditText)rlContent.findViewById(R.id.etwebsite);
        etCity=(EditText)rlContent.findViewById(R.id.etcity);
        etBio=(EditText)rlContent.findViewById(R.id.etbio);

        etGender=(EditText)rlContent.findViewById(R.id.etgender);
        etEmail=(EditText)rlContent.findViewById(R.id.etemailaddress);
        etMobileno=(EditText)rlContent.findViewById(R.id.etmobile);
        etChangepwd=(EditText)rlContent.findViewById(R.id.etchangepwd);

        tvChangepic=(TextView)rlContent.findViewById(R.id.tvchangeprofile);
        ivProfile=(ImageView)rlContent.findViewById(R.id.ivprofile);
        llChangepf=(LinearLayout)rlContent.findViewById(R.id.llchangepic);
        btnDone=(Button)rlContent.findViewById(R.id.btndone);
        ivBack=(ImageView)findViewById(R.id.ivback);
        ivDone=(ImageView)findViewById(R.id.ivdone);

        String text = "<font color=#cc0029><b>+</b></font> <font color=#ffcc00>CHANGE PICTURE</font>";
        tvChangepic.setText(Html.fromHtml(text));

  /*      tvName.setVisibility(View.GONE);
        tvEmail.setVisibility(View.GONE);
        tvPhoneno.setVisibility(View.GONE);
        tvCity.setVisibility(View.GONE);
        tvWebsite.setVisibility(View.GONE);
*/
        EdittextTextChangeFunction();

        btnDone.setOnClickListener(this);
        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        btnDone.setVisibility(View.VISIBLE);
        digitsButton.setVisibility(View.GONE);
        Digits.getSessionManager().clearActiveSession();
        digitsButton.setAuthTheme(R.style.CustomDigitsTheme2);
        // strMobile="+91 7874707113";
        // digitsButton.setCallback(authCallback);
        digitsButton.setCallback(new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // TODO: associate the session userID with your user model
                //phoneNumber=GeneralFunction.IsNull(phoneNumber);
                try {
                    if (!GeneralFunction.isEmptyCheck(phoneNumber)) {
                       /* OtpFlag = true;
                        btnContinue.setVisibility(View.VISIBLE);
                        digitsButton.setVisibility(View.GONE);
                        strMobile = phoneNumber;
                        callSignUpWeb(OtpFlag);*/
                    } else {
                        //callSignUpWeb(true);
                    }
                } catch (Exception e) {
                }
                Toast.makeText(getApplicationContext(), "Authentication successful for "
                        + phoneNumber, Toast.LENGTH_LONG).show();

            }

            @Override
            public void failure(DigitsException exception) {
                Log.d("Digits", "Sign in with Digits failure", exception);
            }
        });


        ivBack.setOnClickListener(this);
        ivDone.setOnClickListener(this);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/


    }

    private void EdittextTextChangeFunction(){
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowTextlabelFunction();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowTextlabelFunction();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    /*    etPhoneno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowTextlabelFunction();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    */
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowTextlabelFunction();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        etWebsite.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ShowTextlabelFunction();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void ShowTextlabelFunction(){

        int namesize=etName.getText().length();
        int emaillength=etEmail.getText().length();
        int phonelength=etMobileno.getText().length();
        int citylength=etCity.getText().length();
        int websitelength=etWebsite.getText().length();

       // name
        /*if(namesize>0){
            tvName.setVisibility(View.VISIBLE);
        }
        else{
           // tvName.setVisibility(View.INVISIBLE);
            tvName.setVisibility(View.GONE);
        }

        // email
        if(emaillength>0){
            tvEmail.setVisibility(View.VISIBLE);
        }
        else{
            //tvEmail.setVisibility(View.INVISIBLE);
            tvEmail.setVisibility(View.GONE);
        }

        //phone
        if(phonelength>0){
            tvPhoneno.setVisibility(View.VISIBLE);
        }
        else{
            //tvPhoneno.setVisibility(View.INVISIBLE);
            tvPhoneno.setVisibility(View.GONE);
        }

        //city
        if(citylength>0){
            tvCity.setVisibility(View.VISIBLE);
        }
        else{
            //tvCity.setVisibility(View.INVISIBLE);
            tvCity.setVisibility(View.GONE);
        }

        //website
        if(websitelength>0){
            tvWebsite.setVisibility(View.VISIBLE);
        }
        else{
            //tvWebsite.setVisibility(View.INVISIBLE);
            tvWebsite.setVisibility(View.GONE);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                GeneralFunction.hideKeyboard(EditUserprofileActivity.this);
                finish();
                break;
            case R.id.ivdone:

                break;
        }
    }
}

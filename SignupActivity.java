package com.freeoffer.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsAuthButton;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.dialog.ProgressHUD;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.ConnectionDetector;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.JSONParserHive;
import com.freeoffer.app.general.RestClient;
import com.freeoffer.app.general.ServiceHandler;
import com.freeoffer.app.general.WS_Methods;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/*import com.android.volley.toolbox.StringRequest;*/
/*import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;*/

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    CustomDialog_Ok customDialog;

    ConnectionDetector cdObj;
    private Context mContext;

    EditText etName,etMobileno,etPwd,etEmail;
    Button btnContinue;
    ImageView ivFacebook,ivGooglePlus;
    ImageView ivBack;
    TextView tvTerms;

    String strEmail,strMobile,strpwd,strName,strFacebookid,strGoogleid;
    String strValidateMsg;
    String webMethod="";
    JSONArray jsCatArray;
    String strUserid="";
    String Webresponse;
    String strWS_Message,Error;
    String strUserId;
    boolean OtpFlag=false;
    private AuthCallback authCallback;
    DigitsAuthButton digitsButton;
    int TIMEOUT_MILLISEC = 10000; // = 10 seconds

    private ProgressDialog pDialog;
    private static final String TAG = SignupActivity.class.getSimpleName();
   //Fabric digits
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "XszH87CVE1cSuORCQsbt6cz9T";
    private static final String TWITTER_SECRET = "CyTXqzHGkWTIFdFtovzGTuzZkX6I8c51Xt2NUV51RjsOMlB6Aa";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());

        setContentView(R.layout.activity_signup);
        mContext=this;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }

        InitViews();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String str=Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("KeyHash:", "KeyHash>>" + str);
                //GeneralFunction.DisplayMessage(customDialog, Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {
        }
    }
    public void registerUser(){

        String name = etName.getText().toString();

        String email = etEmail.getText().toString();

        String password = etPwd.getText().toString();

        RequestParams params = new RequestParams();

        if(Utility.isNotNull(name) && Utility.isNotNull(email) && Utility.isNotNull(password)){

            if(Utility.validate(email)){

                params.put("name", name);

                params.put("email", email);

                params.put("password", password);

                invokeWS(params);
            }

            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        }

        else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }


    public void invokeWS(RequestParams params){

        pDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://freeoff.in/restservice/v1/register",params ,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {

                pDialog.hide();
                try {

                    JSONObject obj = new JSONObject(response);
                    if(!obj.getBoolean("error")){

                        setDefaultValues();

                        Toast.makeText(getApplicationContext(), "You are successfully registered!", Toast.LENGTH_LONG).show();
                    }

                    else{
                        tvTerms.setText(obj.getString("error_msg"));
                        Toast.makeText(getApplicationContext(), obj.getString("error_msg"), Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();

                }
            }

            @Override
            public void onFailure(int statusCode, Throwable error,
                                  String content) {

                pDialog.hide();

                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }

                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }

                else{
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void navigatetoLoginActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),MainActivity_Home.class);

        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void setDefaultValues(){
        etName.setText("");
       etEmail.setText("");
        etPwd.setText("");
    }


    private void InitViews(){

        cdObj = new ConnectionDetector(mContext);
        customDialog = new CustomDialog_Ok(mContext);
// Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        ivBack=(ImageView)findViewById(R.id.ivback);
        etName=(EditText)findViewById(R.id.etname);
        etMobileno=(EditText)findViewById(R.id.etmobile);
        etEmail=(EditText)findViewById(R.id.etemail);
        etPwd=(EditText)findViewById(R.id.etpwd);
        ivFacebook=(ImageView)findViewById(R.id.ivfacebook);
        ivGooglePlus=(ImageView)findViewById(R.id.ivgoogle);
        btnContinue=(Button)findViewById(R.id.btncontinue);
        tvTerms=(TextView)findViewById(R.id.textView2);

        String str=getResources().getString(R.string.terms)+ "<b> FreeOff </b>";
        tvTerms.setText(Html.fromHtml(str));

        ivBack.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        digitsButton = (DigitsAuthButton) findViewById(R.id.auth_button);
        btnContinue.setVisibility(View.VISIBLE);
        digitsButton.setVisibility(View.GONE);
        authCallback=((FreeOffApplication)getApplicationContext()).getAuthCallback();
        digitsButton.setCallback(authCallback);
        Digits.getSessionManager().clearActiveSession();
        digitsButton.setAuthTheme(R.style.CustomDigitsTheme2);
      //  Digits.authenticate(authCallback, "+34111111111");
       // strMobile="+91 7874707113";
        // digitsButton.setCallback(authCallback);
       digitsButton.setCallback(new AuthCallback() {
           @Override
           public void success(DigitsSession session, String phoneNumber) {
               // TODO: associate the session userID with your user model
               //phoneNumber=GeneralFunction.IsNull(phoneNumber);
               try {
                   if (!GeneralFunction.isEmptyCheck(phoneNumber)) {
                       OtpFlag = true;
                       btnContinue.setVisibility(View.VISIBLE);
                       digitsButton.setVisibility(View.GONE);
                       strMobile = phoneNumber;
                       callSignUpWeb(OtpFlag);
                   } else if (phoneNumber.equalsIgnoreCase("null")) {
                       Toast.makeText(mContext, "please try again", Toast.LENGTH_SHORT).show();
                       //callSignUpWeb(true);
                   } else {
                       callSignUpWeb(true);
                   }
               } catch (Exception e) {
                   Log.d("Rup", "Exception" + e);
               }
               Toast.makeText(getApplicationContext(), "Authentication successful for "
                       + phoneNumber, Toast.LENGTH_LONG).show();

           }

           @Override
           public void failure(DigitsException exception) {
               Log.d("Digits", "Sign in with Digits failure", exception);
           }
       });


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckValidateControlForButton();
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
                CheckValidateControlForButton();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CheckValidateControlForButton();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.btncontinue:
                OtpFlag=true;
                if (ValidateControl()) {
                    registerUser();
                }
                else{
                    GeneralFunction.IntenetConnectionMsg(customDialog);
                }

                break;
            case R.id.ivfacebook:

                break;
            case R.id.ivgoogle:

                break;
        }
    }

    public boolean ValidateControl() {

        boolean isValidate = true;
        strName = etName.getText().toString();
        strEmail = etEmail.getText().toString();
        strpwd = etPwd.getText().toString();
        if (GeneralFunction.isEmptyCheck(strName)) {
            strValidateMsg = "Please enter name";
            isValidate = false;
            etName.requestFocus();
        }
        else if (GeneralFunction.isEmptyCheck(strEmail)) {
            strValidateMsg = "Please enter a email address";
            isValidate = false;
            etEmail.requestFocus();
        }
        else if (!GeneralFunction.isEmailValid(strEmail)) {
            strValidateMsg = "Please enter a valid email address";
            isValidate = false;
            etEmail.requestFocus();
        } else if (GeneralFunction.isEmptyCheck(strpwd)) {
            strValidateMsg = "Please enter your password";
            isValidate = false;
            etPwd.requestFocus();
        } else if (strpwd.length() < 5) {
            strValidateMsg = "Please enter password of atleast 5 character";
            isValidate = false;
            etPwd.requestFocus();
        }

        return isValidate;
    }

    public boolean CheckValidateControlForButton() {

        boolean isValidate = true;
        strName = etName.getText().toString();
        strEmail = etEmail.getText().toString();
        strMobile = etMobileno.getText().toString();
        strpwd = etPwd.getText().toString();

        if (GeneralFunction.isEmptyCheck(strEmail)) {
            isValidate = false;
        }
        else if (!GeneralFunction.isEmailValid(strEmail)) {

            isValidate = false;

        } else if (GeneralFunction.isEmptyCheck(strpwd)) {
            isValidate = false;

        } else if (strpwd.length() < 5) {
            isValidate = false;

        }
       /* else if (GeneralFunction.isEmptyCheck(strMobile)) {
            isValidate = false;
        }
        else if( ! GeneralFunction.isValidMobile(strMobile)){
            isValidate = false;
        }*/

        if(isValidate){
            btnContinue.setVisibility(View.GONE);
         //   Digits.authenticate(authCallback, "+91"+strMobile);
            digitsButton.setVisibility(View.VISIBLE);

        }
        else{
            btnContinue.setVisibility(View.VISIBLE);
            digitsButton.setVisibility(View.GONE);
        }
        return isValidate;
    }

    private void callSignUpWeb(boolean flag){
        if (cdObj.isConnectingToInternet()) {
            if (ValidateControl()) {
               /* try {
                    String[] str = strMobile.split(" ");
                    strMobile = str[1];
                }
                catch(Exception e){}*/
                if(flag){
                    webMethod = "signup";
                    TimeConsumingTask task = new TimeConsumingTask();
                    task.execute();
                }
                else{
                    btnContinue.setVisibility(View.VISIBLE);
                    digitsButton.setVisibility(View.GONE);
                    Toast.makeText(mContext,"Otp button",Toast.LENGTH_SHORT).show();
                }

            }
            else {
                GeneralFunction.DisplayMessage(customDialog,strValidateMsg);
            }
        }
        else{
            GeneralFunction.IntenetConnectionMsg(customDialog);
        }
    }

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
                Thread.sleep(500);
                publishProgress("Loading...");
                if (webMethod.equals("signup")) {
                    WS_signup("OnProcess");
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
            if (webMethod.equals("signup")) {
                WS_signup("OnPost");
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

    public void WS_signup(String AsyncMethodType) {

        try {
            if (AsyncMethodType.equals("OnProcess")) {
                JSONParserHive jParser = new JSONParserHive();

                ServiceHandler sh = new ServiceHandler();
                strWS_Message = "";

                String jsondata = null;

                List<NameValuePair> nameValuePairs = new ArrayList<>(3);

                nameValuePairs.add(new BasicNameValuePair("name", strName));
                nameValuePairs.add(new BasicNameValuePair("email",strEmail));
                nameValuePairs.add(new BasicNameValuePair("password", strpwd));
             //   nameValuePairs.add(new BasicNameValuePair("mobile", strMobile));

                Webresponse = sh.makeServiceCall1(WS_Methods.signup, 2, nameValuePairs);
                Log.d("Rup", "parameters " + jsondata);

                String Response = Webresponse.toLowerCase();

               if(!GeneralFunction.isEmptyCheck(Webresponse)){
                   try {
                       JSONObject jsonObj = new JSONObject(Webresponse);
                       strWS_Message = jsonObj.getString(CommonUtilities.TAG_Message);
                       Error = jsonObj.getString("error");
                   }
                   catch(Exception e){

                   }

                }


            } else {
                // Post Method
                if(!GeneralFunction.isEmptyCheck(strWS_Message)){
                    if(GeneralFunction.isCompare(Error,"true")) {
                      GeneralFunction.DisplayMessage(customDialog,strWS_Message);
                    }
                    else{
                       /* int strid = Integer.valueOf(strUserId);
                        if (strid > 1) {
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginUserId, strUserId);
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserName, strName);
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserEmil, strEmail);*/
                            Toast.makeText(SignupActivity.this, "Sign up sucessfully done", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SignupActivity.this, SigninActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                       /* }
                        else{
                            Toast.makeText(SignupActivity.this, "sorry try again", Toast.LENGTH_SHORT).show();
                        }*/
                    }

                }
                else {
                    AlertDialog();
                }

            }



        } catch (Exception ex) {
            Log.d("Rup", "Exception " + ex);
        }
    }

    private void AlertDialog(){
        strWS_Message = Webresponse;
        customDialog.setMessage(Html.fromHtml(strWS_Message));
        customDialog.okButton.setText("Try Again");
        customDialog.show();
        customDialog.okButton
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        customDialog.dismiss();
                    }
                });
    }

    public void clickbuttonRecieve() {
        try {
            JSONObject json = new JSONObject();
            json.put("username", "test");
            json.put("email", "test@test.com");
            json.put("password", "test");
            HttpParams httpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParams,
                    TIMEOUT_MILLISEC);
            HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
            HttpClient client = new DefaultHttpClient(httpParams);
            //
            //String url = "http://10.0.2.2:8080/sample1/webservice2.php?" +
            //             "json={\"UserName\":1,\"FullName\":2}";
            String url = "http://www.soyeb.com/freeoff/webservice/register.php";

            HttpPost request = new HttpPost(url);
            request.setEntity(new ByteArrayEntity(json.toString().getBytes(
                    "UTF8")));
            request.setHeader("json", json.toString());
            HttpResponse response = client.execute(request);
            HttpEntity entity = response.getEntity();
            // If the response does not enclose an entity, there is no need
            if (entity != null) {
                InputStream instream = entity.getContent();

                String result = RestClient.convertStreamToString(instream);
                Log.i("Read from server", result);
                Toast.makeText(this,  result,
                        Toast.LENGTH_LONG).show();
            }
        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

   }

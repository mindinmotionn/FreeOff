package com.freeoffer.app.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.dialog.ProgressHUD;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.ConnectionDetector;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.general.JSONParserHive;
import com.freeoffer.app.general.ServiceHandler;
import com.freeoffer.app.general.WS_Methods;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    CustomDialog_Ok customDialog;
    // Connection Object Declare
    ConnectionDetector cdObj;
    private Context mContext;
    ProgressDialog prgDialog;
    EditText etEmail,etPwd;
    private Button btnSignIn;
    TextView tvForgotPwd;
    ImageView ivFacebook,ivGoogle;
    ImageView ivBack;

    TextView tvTerms;

    String strEmail,strPwd,strFacebookid,strGoogleid;
    String strValidateMsg;
    String webMethod="";
    JSONArray jsCatArray;
    String strUserid="";
    String Webresponse;
    String Ws_name,Ws_email,Ws_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        InitViews();
    }
    public void loginUser(){

        String email = etEmail.getText().toString();

        String password = etPwd.getText().toString();

        RequestParams params = new RequestParams();

        if(Utility.isNotNull(email) && Utility.isNotNull(password)){

            if(Utility.validate(email)){

                params.put("email", email);

                params.put("password", password);

                invokeWS(params);
            }

            else{
                Toast.makeText(getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG).show();
            }
        } else{
            Toast.makeText(getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG).show();
        }

    }
    public void invokeWS(RequestParams params){

        prgDialog.show();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://freeoff.in/restservice/v1/login",params ,new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(String response) {

                prgDialog.hide();
                try {

                    JSONObject obj = new JSONObject(response);

                    if(obj.getBoolean("status")){
                        Toast.makeText(getApplicationContext(), "You are successfully logged in!", Toast.LENGTH_LONG).show();

                        navigatetoHomeActivity();
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

                prgDialog.hide();

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
    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),MainActivity_Home.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    public void navigatetoRegisterActivity(View view){
        Intent loginIntent = new Intent(getApplicationContext(),SignupActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    private void InitViews(){
        cdObj = new ConnectionDetector(mContext);
        customDialog = new CustomDialog_Ok(mContext);

        etEmail=(EditText)findViewById(R.id.etemail);
        etPwd=(EditText)findViewById(R.id.etpwd);
        tvForgotPwd=(TextView)findViewById(R.id.tvforgotped);
        ivFacebook=(ImageView)findViewById(R.id.ivfacebook);
        ivGoogle=(ImageView)findViewById(R.id.ivgoogle);
        ivBack=(ImageView)findViewById(R.id.ivback);
        btnSignIn=(Button)findViewById(R.id.btnsignin);
        tvTerms=(TextView)findViewById(R.id.textView2);

        String str=getResources().getString(R.string.loginterms)+ "<b> FreeOff </b>";
        tvTerms.setText(Html.fromHtml(str));

        ivBack.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGoogle.setOnClickListener(this);
        tvForgotPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivback:
                finish();
                break;
            case R.id.btnsignin:

                if (cdObj.isConnectingToInternet()) {
                    if(validateControl()){
                       loginUser();
                    }
                    else {
                        GeneralFunction.DisplayMessage(customDialog,strValidateMsg);
                    }
                }
                else {
                    GeneralFunction.IntenetConnectionMsg(customDialog);
                }
                break;
            case R.id.ivfacebook:
                break;
            case R.id.ivgoogle:
                break;
            case R.id.tvforgotped:
                Intent iforgot=new Intent(SigninActivity.this,ForgotPwdActivity.class);
                startActivity(iforgot);
                break;
        }
    }

    private boolean validateControl(){
        boolean isValidate=true;
        strEmail=etEmail.getText().toString();
        strPwd=etPwd.getText().toString();
        if(GeneralFunction.isEmptyCheck(strEmail)){
            isValidate=false;
            strValidateMsg="Please Enter Email Id";
            etEmail.requestFocus();
        }
        else if (!GeneralFunction.isEmailValid(strEmail)) {
            // etPhone.setError(Html.fromHtml("<font color='red'>Please enter a valid email address</font>"));
            isValidate = false;
            etEmail.requestFocus();
            strValidateMsg = "Please enter a valid email address";
        }
        else if (GeneralFunction.isEmptyCheck(strPwd)) {
            // etPassword.setError(Html.fromHtml("<font color='red'>Please enter a password</font>"));
            isValidate = false;
            etPwd.requestFocus();strValidateMsg= "Please enter your password.";
        }

        return isValidate;

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

                //publishProgress("Connecting");
                Thread.sleep(500);
                publishProgress("Loading...");
                if (webMethod.equals("ValidateUserLogin")) {
                    WS_ValidateUserLogin("OnProcess");
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

            if (webMethod.equals("ValidateUserLogin")) {
                WS_ValidateUserLogin("OnPost");
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

    public void WS_ValidateUserLogin(String AsyncMethodType) {

        try {
            if (AsyncMethodType.equals("OnProcess")) {
                JSONParserHive jParser = new JSONParserHive();
                //WS_Url="http://192.168.0.107/AayumedAPI/api/all/ValidatePatientLogin?username=test@test.com&password=test&facebookid=";
                ServiceHandler sh = new ServiceHandler();
                String jsondata = null;

                List<NameValuePair> nameValuePairs = new ArrayList<>(2);
                nameValuePairs.add(new BasicNameValuePair("email", strEmail));
                nameValuePairs.add(new BasicNameValuePair("password", strPwd));
                Webresponse = sh.makeServiceCall1(WS_Methods.signin, 2, nameValuePairs);
                Log.d("Rup", "parameters " + jsondata);

                String Response = Webresponse.toLowerCase();

                if(!GeneralFunction.isEmptyCheck(Webresponse)){
                    JSONObject jsonObj = new JSONObject(Webresponse);
                   // strWS_Message = jsonObj.getString(CommonUtilities.TAG_Message);
                    Ws_Id=jsonObj.getString("id");
                    Ws_email=jsonObj.getString("email");
                    Ws_name=jsonObj.getString("name");
                    //strUserId=o_data.getString("user_id");
                }


            } else {
                // Post Method
               // String strUserId="";
                if(!GeneralFunction.isEmptyCheck(Webresponse)){
                    if(GeneralFunction.isEmptyCheck(Ws_Id)) {
                        GeneralFunction.DisplayMessage(customDialog, Webresponse);
                    }
                    else{
                        int strid = Integer.valueOf(Ws_Id);
                        if (strid > 1) {
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginUserId, Ws_Id);
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserName, Ws_name);
                            GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserEmil, strEmail);
                           // Toast.makeText(SigninActivity.this, "Sign up sucessfully done", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(SigninActivity.this, LocationActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(SigninActivity.this, "sorry try again", Toast.LENGTH_SHORT).show();
                        }
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

        customDialog.setMessage(Html.fromHtml(Webresponse));
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
    private void calldisplayerrormsg(){
        String tital=getResources().getString(R.string.app_name);
        customDialog.setTitle(tital);
        String Msg="You have typed an incorrect email and password combination. Please check again to ensure the information you typed in is correct.";
        //GeneralFunction.DisplayMessage(mContext, tital, Msg, CommonUtilities.CD_btnNo1,getResources().getString(R.string.ok),getResources().getString(R.string.cancel));

        GeneralFunction.DisplayMessage(customDialog, Msg);

    }


}

package com.freeoffer.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.dialog.ProgressHUD;
import com.freeoffer.app.general.ConnectionDetector;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.JSONParser;

import java.util.HashMap;


public class ForgotPwdActivity extends Activity implements OnClickListener {
	
	RelativeLayout mRLemail;
	EditText metmail;
	Button mbtnrecover;
	private ImageView ivback;
	LinearLayout llmain;
	TextView tvtitle;
	
	String stroutput="0";
	Context mContext;
	ConnectionDetector cdObj;
	CustomDialog_Ok customDialog;
	String ValidateMsg="";
	static String strcurrentscreen="";
	String responseOFWeb="";
	
	String strtital="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_pwd);
		mContext=this;
		cdObj = new ConnectionDetector(mContext);
		customDialog = new CustomDialog_Ok(mContext);
		customDialog.setTitle("Forgot Password");
		strtital="Forgot Password";
		responseOFWeb="A temporary password has been sent to your email";
		Intent getvalue=getIntent();
		strcurrentscreen=getvalue.getStringExtra("tital");
		
		/*if(strcurrentscreen.equalsIgnoreCase("Forgot Your Passcode?")){
			customDialog.setTitle("Forgot Passcode");
			strtital="Forgot Passcode";
			responseOFWeb="A temporary passcode has been sent to your email";
		}*/
		
		//mRLemail = (RelativeLayout) findViewById(R.id.rlemail);
		metmail = (EditText)findViewById(R.id.rlemail);
		/*tvtitle =(TextView)findViewById(R.id.tvtitle);
		tvtitle.setText(getvalue.getStringExtra("tital"));*/
		
		metmail.setHint("Email Address");
		metmail.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS );
	    //mtvclose=(TextView)findViewById(R.id.tvclose);
	    mbtnrecover=(Button)findViewById(R.id.buttonrecover);
	    llmain=(LinearLayout)findViewById(R.id.ll_main);
	    //mtvclose.setOnClickListener(this);
	    mbtnrecover.setOnClickListener(this);
		ivback=(ImageView)findViewById(R.id.ivback);
		ivback.setOnClickListener(this);

		// set max length of Edittext
		int maxLengthmail = 50;
		InputFilter[] fArray = new InputFilter[1];
		fArray[0] = new InputFilter.LengthFilter(maxLengthmail);
		metmail.setFilters(fArray);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ivback:
			  GeneralFunction.hideKeyboard(ForgotPwdActivity.this);
			  finish();

			break;
			
		case R.id.buttonrecover:
			if (cdObj.isConnectingToInternet()) {			
				 if(ValidateControl()){
					 TimeConsumingWeb_Forgotpwd t=new TimeConsumingWeb_Forgotpwd();
					 t.execute(); 
				 }
				 else{
					 customDialog.setTitle(strtital);
					// GeneralFunction.DisplayMessage(mContext, strtital, ValidateMsg, CommonUtilities.CD_btnNo1,getResources().getString(R.string.ok),getResources().getString(R.string.cancel));
					 GeneralFunction.DisplayMessage(customDialog, ValidateMsg);
				 }
			}
			else{
				GeneralFunction.IntenetConnectionMsg(customDialog);
			}
			
			break;

		default:
			break;
		}
	}
	
	public boolean ValidateControl() {
		boolean isValidate = true;
		if (GeneralFunction.isEmptyCheck(metmail.getText().toString())) {
			ValidateMsg = "Please enter email address";
			isValidate = false;
		}
		else if (!GeneralFunction.isEmailValid(metmail.getText().toString())) {
				ValidateMsg = "Please enter valid email address";
				isValidate = false;
			}
		
		return isValidate;	
		
	}
	
	public class TimeConsumingWeb_Forgotpwd extends AsyncTask<Void, String, Void>
	implements OnCancelListener {
		ProgressHUD mProgressHUD;
		
		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(ForgotPwdActivity.this, "Connecting",
					true, true, this);
			super.onPreExecute();

		}
		@Override
		protected Void doInBackground(Void... params) {
			try {

				// publishProgress("Connecting");
				// Thread.sleep(500);
				publishProgress("Loading...");
				if(strtital.equalsIgnoreCase("Forgot Password")){
					Web_ForgotPassword("OnProcess");	
				}
				else{
					//Web_ForgotPasscode("OnProcess");
				}
				Thread.sleep(500);
				publishProgress("Done");
				//System.out.println("hello 1");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;

		}
		@Override
		protected void onProgressUpdate(String... values) {
			mProgressHUD.setMessage(values[0]);
			mProgressHUD.setCancelable(false);
			mProgressHUD.setCanceledOnTouchOutside(false);
			super.onProgressUpdate(values);
		}
		@Override
		protected void onPostExecute(Void result) {
			if(strtital.equalsIgnoreCase("Forgot Password")){
				Web_ForgotPassword("OnPost");	
			}
			else{
				//Web_ForgotPasscode("OnPost");
			}
			
			
			mProgressHUD.dismiss();
			
			//System.out.println("hllo 0");
			super.onPostExecute(result);
		}
		@Override
		public void onCancel(DialogInterface dialog) {
			this.cancel(true);
			mProgressHUD.dismiss();
		}
	}
	
	public void Web_ForgotPassword(String AsyncMethodType) {
		try {
			if (AsyncMethodType.equals("OnProcess")) {
				JSONParser jWeb = new JSONParser();
				HashMap<String, String> parameters = new HashMap<String, String>();
				//System.out.println("heelo");				
				parameters.put("EmailId", metmail.getText().toString());								
				//parameters.put("ClientId", CommonUtilities.ClientId);
				
				//stroutput = jWeb.getStringObject("RiderForgotPassword",NAMESPACE + "RiderForgotPassword", parameters);
				
			}
			else{
				String msg = stroutput;
				if(msg.equalsIgnoreCase("")){
					llmain.setEnabled(false);				  						
					customDialog.setMessage(Html.fromHtml("The email you entered is incorrect."));
					customDialog.show();
					customDialog.okButton
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									customDialog.dismiss();
									llmain.setEnabled(true);
								}
							});
				}
				else if(stroutput.equalsIgnoreCase("Password sent to your mail box")){
				  	llmain.setEnabled(false);
					customDialog.setMessage(Html.fromHtml(responseOFWeb));
					customDialog.show();
					customDialog.okButton
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									customDialog.dismiss();
									llmain.setEnabled(true);
									finish();
								}
							});
				}
				else{
					llmain.setEnabled(false);
					customDialog.setMessage(Html.fromHtml("The Email you entered is incorrect"));
					customDialog.show();
					customDialog.okButton
							.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									customDialog.dismiss();
									llmain.setEnabled(true);
								}
							});

				}
			}

		} catch (Exception ex) {

		}
	}

}

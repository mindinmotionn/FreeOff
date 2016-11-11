package com.freeoffer.app.activity;



import org.json.JSONArray;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.Local_myUpdatePagerAdapter;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.dialog.CustomDialog_OkCancel;
import com.freeoffer.app.dialog.ProgressHUD;
import com.freeoffer.app.general.ConnectionDetector;

import com.freeoffer.app.model.FragmentLifecycle;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressWarnings("deprecation")
@SuppressLint("NewApi")
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SlideChangeLocal_my_UpdateActivity extends FragmentActivity implements ActionBar.TabListener, OnClickListener {

	private ViewPager viewPager;  // help us in sliding screens
	private Local_myUpdatePagerAdapter mAdapter;
	private ActionBar actionBar;	
	private Button btnLocalUpdate,btnMyUpdate;
	LinearLayout llUpdateStatus;
	private LinearLayout llStatus,llPhotos,llCheckin;
	ImageView ivFollow;
	
	private Context mContext;
	ConnectionDetector cdObj;
	private CustomDialog_Ok customdialogOk;
	private CustomDialog_OkCancel customDialog;
	boolean flag=true;
	boolean credit=true;
	//ContactInfoFragment fragment_contact=new ContactInfoFragment();
	String ValidateMsg="";
	int UserId = 0;
	float initialX;
	 private Cursor cursor;
	 private  int columnIndex, position = 0;
	 String stroutput="";
	 String webMethod="";
	 String webfname,weblname,webphno,webaddress,webstate,webcounty,webcity,webzip,webemail;
	 JSONArray jsCatArray;
	 
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		
		setContentView(R.layout.activity_local_my_update);
		mContext=this;
		InitViews();

	}

	private void InitViews(){
		viewPager = (ViewPager) findViewById(R.id.pager);
		actionBar = getActionBar();
		mAdapter = new Local_myUpdatePagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);

		try{
			LayoutInflater mInflater = LayoutInflater.from(this);
			ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
			View mCustomView = mInflater.inflate(R.layout.custom_actionbarupdare, null);
			LinearLayout mlinearLayout = (LinearLayout) mCustomView.findViewById(R.id.ll_back);
			TextView tvTitalBar = (TextView) mCustomView.findViewById(R.id.textView1);
			ivFollow=(ImageView)mCustomView.findViewById(R.id.ivfollow);
			mlinearLayout.setOnClickListener(this);
			actionBar.setCustomView(mCustomView, lp);
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);
			//tvTitalBar.setText("Change Password");

			customDialog=new CustomDialog_OkCancel(mContext);
			customdialogOk=new CustomDialog_Ok(mContext);
			cdObj = new ConnectionDetector(mContext);
			customDialog.setTitle("Update Account");
			customdialogOk.setTitle("Update Account");
			//	btnAccount=(Button)findViewById(R.id.btn_acinfo);
			btnLocalUpdate=(Button)findViewById(R.id.btnlocalupdate);
			btnMyUpdate=(Button)findViewById(R.id.btnmyupdate);
			llUpdateStatus=(LinearLayout)findViewById(R.id.llupdateStatus);
			llStatus=(LinearLayout)findViewById(R.id.llstatus);
			llPhotos=(LinearLayout)findViewById(R.id.llcamera);
			llCheckin=(LinearLayout)findViewById(R.id.llcheckin);
			//btnAccount.setOnClickListener(this);
			btnLocalUpdate.setOnClickListener(this);
			btnMyUpdate.setOnClickListener(this);
			llStatus.setOnClickListener(this);
			llPhotos.setOnClickListener(this);
			llCheckin.setOnClickListener(this);
			ivFollow.setOnClickListener(this);
			llUpdateStatus.setVisibility(View.GONE);
		}
		catch(Exception e){
			//Log.d("Rup", "Exception e" +e);
		}

		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			int currentPosition = 0;
			@Override
			public void onPageSelected(int newPosition) {

				if(newPosition==0){
					btnLocalUpdate.setBackgroundColor(getResources().getColor(R.color.orange));
					btnLocalUpdate.setTextColor(getResources().getColor(R.color.white));
					btnMyUpdate.setBackgroundColor(getResources().getColor(R.color.white));
					btnMyUpdate.setTextColor(getResources().getColor(R.color.orange));
				}
				else if(newPosition==1){
					btnMyUpdate.setBackgroundColor(getResources().getColor(R.color.orange));
					btnMyUpdate.setTextColor(getResources().getColor(R.color.white));
					btnLocalUpdate.setBackgroundColor(getResources().getColor(R.color.white));
					btnLocalUpdate.setTextColor(getResources().getColor(R.color.orange));
				}

				try{
					FragmentLifecycle fragmentToShow = (FragmentLifecycle)mAdapter.getItem(newPosition);  // fragment which is show
					fragmentToShow.onResumeFragment();
					FragmentLifecycle fragmentToHide = (FragmentLifecycle)mAdapter.getItem(currentPosition);  // fragment which is hide
					fragmentToHide.onPauseFragment();
					currentPosition = newPosition;
				}
				catch(Exception e){
					//Log.d("Rup", "the error is " +e);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				//Log.d("Rup", "the value of on page scroll is "+arg0+"\n"+arg1+"\n"+arg2);
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				//	Log.d("Rup", "test "+ mAdapter.getItem(arg0)+ " \n current item "+viewPager.getCurrentItem() );
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.ll_back:
			finish();
			break;

		case R.id.btnlocalupdate:
			
			btnLocalUpdate.setBackgroundColor(getResources().getColor(R.color.orange));
			btnLocalUpdate.setTextColor(getResources().getColor(R.color.white));
			btnMyUpdate.setBackgroundColor(getResources().getColor(R.color.white));
			btnMyUpdate.setTextColor(getResources().getColor(R.color.orange));
			viewPager.setCurrentItem(0);;
			
			break;
		case R.id.btnmyupdate:
			btnMyUpdate.setBackgroundColor(getResources().getColor(R.color.orange));
			btnMyUpdate.setTextColor(getResources().getColor(R.color.white));
			btnLocalUpdate.setBackgroundColor(getResources().getColor(R.color.white));
			btnLocalUpdate.setTextColor(getResources().getColor(R.color.orange));
			viewPager.setCurrentItem(1);

			break;
		case R.id.llstatus:
			if(llUpdateStatus.getVisibility()==View.VISIBLE){
				llUpdateStatus.setVisibility(View.GONE);
			}
			else{
				llUpdateStatus.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.llcamera:
			llUpdateStatus.setVisibility(View.GONE);
			Intent icamera=new Intent(SlideChangeLocal_my_UpdateActivity.this,CameraActivity.class);
			startActivity(icamera);
			break;
		case R.id.llcheckin:
			llUpdateStatus.setVisibility(View.GONE);
			break;

		case R.id.ivfollow:
			Intent i=new Intent(SlideChangeLocal_my_UpdateActivity.this,FollowActivity.class);
			startActivity(i);
			break;

		default:
			break;
		}
		
	}	
	
	



	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	public class TimeConsumingTask extends AsyncTask<Void, String, Void>implements OnCancelListener {
		ProgressHUD mProgressHUD;
	
		@Override
		protected void onPreExecute() {
			mProgressHUD = ProgressHUD.show(mContext, "Connecting", true, true,	this);
			super.onPreExecute();
	
		}
	
		@Override
		protected Void doInBackground(Void... params) {
			try {
	
				publishProgress("Connecting");
				Thread.sleep(500);
				publishProgress("Loading...");
			    if(webMethod.equals("GetDriverDetail")) {
					//GetDriverDetail("OnProcess");
				 }
			    else{
				  //InsertOrder("OnProcess");
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
			super.onProgressUpdate(values);
		}
	
		@Override
		protected void onPostExecute(Void result) {
	
			if(webMethod.equals("GetDriverDetail")) {
				//GetDriverDetail("OnPost");
			 }
		    else{
		    	//InsertOrder("OnPost");
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
	
	/*public void InsertOrder(String AsyncMethodType) {

		JSONParser jWeb = new JSONParser();			
		// Parameter for WebService
		HashMap<String, String> parameters = new HashMap<String, String>();				
		
		parameters.put("DriverId", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,CommonUtilities.AppUserId));			
		parameters.put("FirstName",GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_FirstName"));
		parameters.put("LastName", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_LastName"));
		parameters.put("PhoneNumber", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_PhoneNumber"));
		parameters.put("StreetAddress", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_Address"));
		parameters.put("State", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_State"));
		parameters.put("County", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_County"));
		parameters.put("City", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_City"));
		parameters.put("Zipcode", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_ZipCode"));
		parameters.put("Email", GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_Email"));
		parameters.put("DateOfBirth","" );
		parameters.put("DriverLicenseNumber", GlobalVar.getMyStringPref(mContext, "DD_DriverLicenseNumber"));		
		parameters.put("Password", GlobalVar.getMyStringPref(mContext, "DD_Password"));
		parameters.put("Passcocde", GlobalVar.getMyStringPref(mContext, "DD_Passcode"));		
		parameters.put("NameOnCard","");
		parameters.put("CardNumber", "");
		parameters.put("ExpiryDate", "");
		
		//Log.d("Rup", "the parameter is   " +parameters);
		try {
			
			// On Process
			if (AsyncMethodType.equals("OnProcess")) {
			   stroutput = jWeb.getStringObject("UpdateDriver", NAMESPACE + "UpdateDriver", parameters);
             //  Log.d("Rup", "the UserId Is    " +stroutput);
               
			} else {
				// On Post
				String msg = "Request Successfully send";				
				*//*if(UserId==-1){
					customDialog.setTitle("Create Account");
					GeneralFunction.DisplayMessage(customDialog, "Your email is already in use");
				}	
				else if(UserId==-2){
					customDialog.setTitle("Create Account");
					GeneralFunction.DisplayMessage(customDialog, "Your passcode is already in use");
				}*//*
				if(stroutput.equalsIgnoreCase("success")){
					ContactInfoFragment.checkedit=false;						
					webMethod="GetDriverDetail";
					TimeConsumingTask task=new TimeConsumingTask();
					task.execute();
					*//*customdialogOk.setTitle("Update Account");
					customdialogOk.setMessage(Html.fromHtml("Your account has been updated."));
					customdialogOk.show();
					customdialogOk.okButton
								.setOnClickListener(new View.OnClickListener() {
									@Override
									public void onClick(View v) {
										customdialogOk.dismiss();	
										viewPager.setCurrentItem(1);
									}
								});*//*
			    }
			}	

		} catch (Exception ex) {
			//Log.e("Rup", "InsertOrder", ex);
		}
	}
	
	public boolean ValidateControl() {

		boolean isValidate = true;
					
			if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_FirstName"))) {				
				ValidateMsg = "Please enter your first name.";
			//	metfirstname.requestFocus();
				isValidate = false;
			}
			
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_LastName"))) {
				ValidateMsg = "Please enter your last name.";
			//	metlastname.requestFocus();
				isValidate = false;
			}	
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_PhoneNumber"))) {
				ValidateMsg = "Please enter valid Phone Number.";
			//	metphoneno.requestFocus();
				isValidate = false;
			}
			else if (GeneralFunction.isValidMobile(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_PhoneNumber"))) {
				ValidateMsg = "Please enter valid Phone Number.";
				//metphoneno.requestFocus();
				isValidate = false;
			}
			else if(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_PhoneNumber").length() < 14){
				ValidateMsg = "Please enter valid Phone Number.";
				//metphoneno.requestFocus();
				isValidate = false;
			}
			
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_Address"))) {
				ValidateMsg = "Please enter address.";	
				//metaddress.requestFocus();
				isValidate = false;
			}	
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_County"))) {
				ValidateMsg = "Please enter county.";
				//etcounty.requestFocus();
				isValidate = false;
			}	
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_City"))) {
				ValidateMsg = "Please enter city.";
				//etcity.requestFocus();
				isValidate = false;
			}	
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_ZipCode"))) {
				ValidateMsg = "Please enter zipcode.";
				//metzip.requestFocus();
				isValidate = false;
			}	
			else if (GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_FirstName"))) {				
				//etstate.setText("Florida");				
			}			
			else if (GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_ZipCode").length() < 5) {
				ValidateMsg = "Please enter valid zipcode.";				
				isValidate = false;
			}
			else if (GlobalVar.getMyStringPref(SlideChangePwd_passcodeActivity.this,"CF_ZipCode").length() > 5) {
				ValidateMsg = "Please enter valid zipcode.";				
				isValidate = false;
			}	
			return isValidate;
	}
	
	
	public void GetDriverDetail(String AsyncMethodType) {
		JSONParser jWeb = new JSONParser();
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("DriverId", GlobalVar.getMyStringPref(mContext,CommonUtilities.AppUserId));
		final ArrayList<String> serviceList = new ArrayList<String>();
		try {
			if (AsyncMethodType.equals("OnProcess")) {
				jsCatArray = jWeb.getJsonArray("GetDriverDetail", NAMESPACE	+ "GetDriverDetail", parameters);
				//Log.d("Rup", "GetItemCategory" + jsCatArray);

			} else {
				// Post Method
				if (jsCatArray.length() > 0) {
	          
					for (int i = 0; i < jsCatArray.length(); i++) {
						JSONObject element = jsCatArray.getJSONObject(i);
						//GS_Ticketlist item = new GS_Ticketlist();
						
						webfname=element.get("FirstName").toString();
					    weblname=element.get("LastName").toString();
					    webphno=element.get("PhoneNumber").toString();
					    webaddress=element.get("Address").toString();
					    webstate=element.get("State").toString();
					    webcounty=element.get("County").toString();
					    webcity=element.get("City").toString();
					    webzip=element.get("ZipCode").toString();
					    webemail=element.get("Email").toString();
					    
						GlobalVar.setMyStringPref(mContext, "DD_DateOfBirth", element.get("DateOfBirth").toString());	
						GlobalVar.setMyStringPref(mContext, "DD_County", webcounty);	
						GlobalVar.setMyStringPref(mContext, "DD_City", webcity);	
						GlobalVar.setMyStringPref(mContext, "DD_Email",webemail);
						GlobalVar.setMyStringPref(mContext, "DD_Password",element.get("Password").toString());	
						GlobalVar.setMyStringPref(mContext, "DD_State", webstate);	
						GlobalVar.setMyStringPref(mContext, "DD_Address",webaddress);	
						GlobalVar.setMyStringPref(mContext, "DD_FirstName",webfname);	
						GlobalVar.setMyStringPref(mContext, "DD_PhoneNumber",webphno);	
						GlobalVar.setMyStringPref(mContext, "DD_Passcode", element.get("Passcode").toString());	
						GlobalVar.setMyStringPref(mContext, "DD_LastName", weblname);	
						GlobalVar.setMyStringPref(mContext, "DD_ZipCode", webzip);	
						GlobalVar.setMyStringPref(mContext, "DD_DriverLicenseNumber", element.get("DriverLicenseNumber").toString());	
					
					
						*//*
						webMethod = "GetRiderCreditCard";
						// call Async function for data
						TimeConsumingTask task = new TimeConsumingTask();
						task.execute();	*//*
					    
						GlobalVar.setMyStringPref(mContext, "CF_DateOfBirth", element.get("DateOfBirth").toString());	
						GlobalVar.setMyStringPref(mContext, "CF_County", webcounty);	
						GlobalVar.setMyStringPref(mContext, "CF_City", webcity);	
						GlobalVar.setMyStringPref(mContext, "CF_Email",webemail);
						GlobalVar.setMyStringPref(mContext, "CF_Password",element.get("Password").toString());	
						GlobalVar.setMyStringPref(mContext, "CF_State", webstate);	
						GlobalVar.setMyStringPref(mContext, "CF_Address",webaddress);	
						GlobalVar.setMyStringPref(mContext, "CF_FirstName",webfname);	
						GlobalVar.setMyStringPref(mContext, "CF_PhoneNumber",webphno);	
						GlobalVar.setMyStringPref(mContext, "CF_Passcode", element.get("Passcode").toString());	
						GlobalVar.setMyStringPref(mContext, "CF_LastName", weblname);	
						GlobalVar.setMyStringPref(mContext, "CF_ZipCode", webzip);	
						GlobalVar.setMyStringPref(mContext, "CF_DriverLicenseNumber", element.get("DriverLicenseNumber").toString());	
					
						
						customdialogOk.setTitle("Update Account");
						customdialogOk.setMessage(Html.fromHtml("Your account has been updated."));
						customdialogOk.show();
						customdialogOk.okButton
									.setOnClickListener(new OnClickListener() {
										@Override
										public void onClick(View v) {
											customdialogOk.dismiss();
											viewPager.setCurrentItem(1);
										}
									});
						}
						*//*
						
						item.setEditDOB(element.get("DateOfBirth").toString());
						item.setEditcounty(element.get("County").toString());
						item.setEditcity(element.get("City").toString());
						item.setEditemail(element.get("Email").toString());
						item.setEditpwd(element.get("Password").toString());
						item.setEditstate(element.get("State").toString());
						item.setEditaddress(element.get("Address").toString());
						item.setEditfirstname(element.get("FirstName").toString());
						item.setEditphonenumber(element.get("PhoneNumber").toString());
						item.setEditpasscode(element.get("Passcode").toString());
						item.setEditlastname(element.get("LastName").toString());
						item.setEditzipcode(element.get("ZipCode").toString());
						item.setEditdriverlicenceno(element.get("DriverLicenseNumber").toString());
						Log.d("Rup", "Mail is "+item.getEditemail());
						arrServiceList.add(item);*//*
					}					

				}	
				
			
			
		} catch (Exception ex) {
			// Log.e(TAG, "Login", ex);
		}
	}*/
	
	
}

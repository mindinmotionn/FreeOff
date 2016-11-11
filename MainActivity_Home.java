package com.freeoffer.app.activity;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.adapter.ExpandListAdapter;
import com.freeoffer.app.adapter.NavDrawerListAdapter;
import com.freeoffer.app.fragments.HomeFragment;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.model.Child;
import com.freeoffer.app.model.Group;
import com.freeoffer.app.model.NavDrawerItem;

import java.util.ArrayList;

@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
public class MainActivity_Home extends AppCompatActivity implements View.OnClickListener {
	private DrawerLayout mDrawerLayout;
	//private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	// nav drawer title
	private CharSequence mDrawerTitle;
	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	ImageView ivMenu;
	RelativeLayout rl_Drawer;

	Context mContext;
	String strCity,strArea;

	boolean mSlideState=false;


	LinearLayout llSearchAutocomplete,ll_menu,llRight;
	AutoCompleteTextView AcSearch;
	ImageView ivShoppingcart,ivSearch;
	TextView tvClose;
    ImageView ivBlurimg;
	TextView tvName,tvDes_user;
	RelativeLayout rlProfilebox;

	private ExpandListAdapter ExpAdapter;
	private ArrayList<Group> ExpListItems;
	private ExpandableListView ExpandList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mainhome);

		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
			Window window=getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
		}
        mContext=this;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		rl_Drawer=(RelativeLayout)findViewById(R.id.rldrawerPane);
		ivBlurimg=(ImageView)findViewById(R.id.ivblurimg);
		tvName=(TextView)findViewById(R.id.tvusername);
		tvDes_user=(TextView)findViewById(R.id.tvuserdesc);


		if(GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(mContext, CommonUtilities.loginuserEmil))){
			tvName.setText("Guest");
			tvDes_user.setText("");
		}
		else{
			tvName.setText(""+GlobalVar.getMyStringPref(mContext,CommonUtilities.loginuserName));
			tvDes_user.setText(""+GlobalVar.getMyStringPref(mContext,CommonUtilities.loginuserEmil));
		}


		mTitle = mDrawerTitle = getTitle();
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bgmain);
		ivBlurimg.setImageBitmap(GeneralFunction.Blurimage(bitmap, MainActivity_Home.this));

		strCity= GlobalVar.getMyStringPref(mContext, CommonUtilities.locationCity);
		strArea= GlobalVar.getMyStringPref(mContext, CommonUtilities.locationArea);
		//getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_actionbar);
		View viewActionBar =getSupportActionBar().getCustomView();

		//View viewActionBar = actionBar.getCustomView();
		//View viewActionBar = getLayoutInflater().inflate(R.layout.custom_actionbar, null);
		TextView textviewTitle = (TextView) viewActionBar.findViewById(R.id.tvcity);
		TextView textviewsubTitle = (TextView) viewActionBar.findViewById(R.id.tvarea);

		textviewTitle.setText(""+strCity);
		textviewsubTitle.setText(""+strArea);
		ivMenu=(ImageView) viewActionBar.findViewById(R.id.ivmenu);
		ivShoppingcart=(ImageView)viewActionBar.findViewById(R.id.ivshoppingcart);
		ivSearch=(ImageView)viewActionBar.findViewById(R.id.ivsearch);

		ll_menu=(LinearLayout)viewActionBar.findViewById(R.id.ll_menu);
		AcSearch=(AutoCompleteTextView)viewActionBar.findViewById(R.id.acsearch);
		llRight=(LinearLayout) viewActionBar.findViewById(R.id.llright);

		tvClose=(TextView)viewActionBar.findViewById(R.id.tvclose);
		ivMenu.setOnClickListener(this);
		ivShoppingcart.setOnClickListener(this);
		ivSearch.setOnClickListener(this);
		tvClose.setOnClickListener(this);

		AcSearch.setVisibility(View.GONE);
		llRight.setVisibility(View.VISIBLE);

		AcSearch.setHint(getResources().getString(R.string.searchactionbartext));
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, //nav menu toggle icon
				R.string.app_name, // nav drawer open - description for accessibility
				R.string.app_name // nav drawer close - description for accessibility
		)

		{
			public void onDrawerClosed(View view) {
				mSlideState=false;//is Closed
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
		    	invalidateOptionsMenu();
				mSlideState=true;//is Opened
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
		rlProfilebox=(RelativeLayout)findViewById(R.id.rlprofileBox);
		ExpListItems = SetStandardGroups();

		rlProfilebox.setOnClickListener(this);
		// by default
		ArrayList<Child> chList = ExpListItems.get(0).getItems();
		String name=chList.get(0).getName();
		//Toast.makeText(MainActivity_Home.this, "name \n"+name, Toast.LENGTH_SHORT).show();
		GlobalVar.setMyStringPref(mContext, CommonUtilities.ExpandChildSelectValue,name);



		ExpAdapter = new ExpandListAdapter(MainActivity_Home.this, ExpListItems);
		ExpandList.setAdapter(ExpAdapter);
		for(int i=0; i < ExpAdapter.getGroupCount(); i++){
			ExpandList.expandGroup(i);
		}

			//ExpandList.setOnChildClickListener(this);
		ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //String str=parent.getExpandableListAdapter().getChildId(groupPosition,childPosition);
				ArrayList<Child> chList = ExpListItems.get(groupPosition).getItems();
				String name=chList.get(childPosition).getName();
				//Toast.makeText(MainActivity_Home.this, ""+ExpListItems.get(groupPosition).getName().toString()+"\n"+name, Toast.LENGTH_SHORT).show();
				GlobalVar.setMyStringPref(mContext, CommonUtilities.ExpandChildSelectValue, name);
				if(groupPosition==0){
					displayView(childPosition);
				}
				else{
					displayGroup1View(childPosition);
				}
				return false;
			}
		});
		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}

		ExpandList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
										int groupPosition, long id) {
				return true; // This way the expander cannot be collapsed
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ivmenu:
				if(mSlideState){
					mDrawerLayout.closeDrawer(rl_Drawer);
				}else{
					mDrawerLayout.openDrawer(rl_Drawer);
				}
				//mDrawerLayout.openDrawer(rl_Drawer);
				break;
			case R.id.ivshoppingcart:
				Intent icartlist=new Intent(MainActivity_Home.this,CartlistActivity.class);
				startActivity(icartlist);
				break;
			case R.id.ivsearch:
				AcSearch.setVisibility(View.VISIBLE);
				llRight.setVisibility(View.GONE);
				break;
			case R.id.tvclose:
				GeneralFunction.hideKeyboard(MainActivity_Home.this);
				AcSearch.setVisibility(View.GONE);
				llRight.setVisibility(View.VISIBLE);
				break;
			case R.id.rlprofileBox:
				mDrawerLayout.closeDrawer(rl_Drawer);
				if(GeneralFunction.isEmptyCheck(GlobalVar.getMyStringPref(mContext, CommonUtilities.loginuserEmil))){
					Intent iprofile=new Intent(MainActivity_Home.this,HomeActivity.class);
					startActivity(iprofile);
					finish();
				}
				else{
					Intent iprofile=new Intent(MainActivity_Home.this,UserProfileActivity.class);
					startActivity(iprofile);
				}

				break;
		}
	}

	private void displayView(int position) {
		// update the main content by replacing fragments
		//mDrawerLayout.closeDrawer(rl_Drawer);
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			Intent i=new Intent(MainActivity_Home.this,LocationActivity.class);
			startActivity(i);
			break;
		case 2:
			Intent ispec_offer=new Intent(MainActivity_Home.this,SpecialOfferActivity.class);
			startActivity(ispec_offer);
			//finish();
			break;
		case 3:
			Intent ibookmarker=new Intent(MainActivity_Home.this,MyofferActivity.class);
			startActivity(ibookmarker);
			//finish();
			break;
		case 4:
			Intent imywallet=new Intent(MainActivity_Home.this,MywalletActivity.class);
			startActivity(imywallet);
			//finish();
			break;
		case 5:
			Intent inotification=new Intent(MainActivity_Home.this,NotificationActivity.class);
			startActivity(inotification);
			//finish();
			break;
			//finish();
		/*case 2:
			fragment = new PhotosFragment();
			break;
		case 3:
			fragment = new CommunityFragment();
			break;
		case 4:
			fragment = new PagesFragment();
			break;
		case 5:
			fragment = new WhatsHotFragment();
			break;*/

		default:
			break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			ExpandList.setItemChecked(position, true);
			ExpandList.setSelection(position);
			setTitle(ExpListItems.get(position).getName());
			mDrawerLayout.closeDrawer(rl_Drawer);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}


	private void displayGroup1View(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
		case 0:
			Intent isettings=new Intent(MainActivity_Home.this,SettingsActivity.class);
			startActivity(isettings);//fragment = new HomeFragment();
			break;
		case 1:
			Intent ihelpsupprot=new Intent(MainActivity_Home.this,SupportActivity.class);
			startActivity(ihelpsupprot);
			//finish();
			/*break;
			Toast.makeText(MainActivity_Home.this, "help", Toast.LENGTH_SHORT).show();
			fragment = new FindPeopleFragment();*/
			break;
		 case 2:
			 Toast.makeText(MainActivity_Home.this, "rate us", Toast.LENGTH_SHORT).show();
			//fragment = new PhotosFragment();
			break;
		case 3:
			//Toast.makeText(MainActivity_Home.this, "share and earn", Toast.LENGTH_SHORT).show();
			Intent iPit=new Intent(MainActivity_Home.this,PointIndextableActivity.class);
			startActivity(iPit);
			//fragment = new CommunityFragment();
			break;
		case 4:
			Intent iaboutus=new Intent(MainActivity_Home.this,AboutusActivity.class);
			startActivity(iaboutus);
			//fragment = new PagesFragment();
			break;
		case 5:
			//Toast.makeText(MainActivity_Home.this, "log Out", Toast.LENGTH_SHORT).show();
			//fragment = new WhatsHotFragment();
			GlobalVar.setMyStringPref(mContext, CommonUtilities.loginUserId, "");
			GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserName,"");
			GlobalVar.setMyStringPref(mContext, CommonUtilities.loginuserEmil, "");
			Intent i = new Intent(MainActivity_Home.this, HomeActivity.class);
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(i);
			break;

			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			ExpandList.setItemChecked(position, true);
			ExpandList.setSelection(position);
			setTitle(ExpListItems.get(1).getName());
			mDrawerLayout.closeDrawer(rl_Drawer);
		} else {
			// error in creating fragment
			mDrawerLayout.closeDrawer(rl_Drawer);
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	public ArrayList<Group> SetStandardGroups() {

		String group_names[] = { "ACTIONS", "OTHERS" };
//
//		     &amp;


		String child_names[] = { "HOME", "LOCATION", "SPECIAL OFFERS", "MY OFFERS",
				"POINT COLLECTION", "NOTIFICATION", "SETTINGS", "HELP & SUPPORT", "RATE US",
				"SHARE & EARN", "ABOUT US", "LOGOUT"};

		int Images[] = { R.drawable.home_g, R.drawable.location_g,
				R.drawable.specialofffer_g, R.drawable.myoffer_g, R.drawable.pointcollection_g,
				R.drawable.notigication_g, R.drawable.setting_g, R.drawable.help_spport_g,
				R.drawable.rateus_g, R.drawable.share_g, R.drawable.aboutus_g,
				R.drawable.logout_g };

		ArrayList<Group> list = new ArrayList<Group>();

		ArrayList<Child> ch_list;

		int size = 6;
		int j = 0;

		for (String group_name : group_names) {
			Group gru = new Group();
			gru.setName(group_name);

			ch_list = new ArrayList<Child>();
			for (; j < size; j++) {
				Child ch = new Child();
				ch.setName(child_names[j]);
				ch.setImage(Images[j]);
				ch_list.add(ch);
			}
			gru.setItems(ch_list);
			list.add(gru);

			size = size + 6;
		}

		return list;
	}


}

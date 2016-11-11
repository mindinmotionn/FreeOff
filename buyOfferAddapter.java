package com.freeoffer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.model.GS_Storeoffer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;


public class buyOfferAddapter extends ArrayAdapter <GS_Storeoffer>{
	private ArrayList<GS_Storeoffer> items;

	//CustomDialog customDialog;
	// Internet Connection Object
	//ConnectionDetector cdObj;

	Context context;
	Activity activity;
	int resource;
	ViewHolder holder = null;
	String listviewName="";
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());


	public buyOfferAddapter(Context context, Activity a, int resourceId,ArrayList<GS_Storeoffer> tempArrayList) {
		super(context, resourceId, tempArrayList);
		activity = a;
		this.context = context;
		this.items = new ArrayList<GS_Storeoffer>();
		this.items.addAll(items);
		this.resource = resourceId;

		// Assign Internet Connection Detector Object
		//cdObj = new ConnectionDetector(context);
		// Assign Message Pop Up Window
		//customDialog = new CustomDialog(context);
		//customDialog.setTitle(CommonUtilities.AlertDialog_Title);

	}
	
	
	/*public HomeGridTop(Context mContext, int resourceId,ArrayList<OpenVideoItem> tempArrayList) {
		// TODO Auto-generated constructor stub
		super(mContext, resourceId, tempArrayList);		
		this.context = mContext;
		this.items = new ArrayList<OpenVideoItem>();
		this.items.addAll(tempArrayList);
		this.resource = resourceId;
	}*/

	private class ViewHolder {
		TextView tvOffername,tvOrginalrs,tvOfferrs;
		//ImageView ivBookG,ivBookO,ivStorepf;
		//ImageView image;
	}

	public View getView(int position, View convertView, final ViewGroup parent) {
		
		final GS_Storeoffer Item = getItem(position);
		try {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_store_offer, null);
				holder = new ViewHolder();

				holder.tvOffername = (TextView) convertView.findViewById(R.id.tvoffer_name);
				holder.tvOrginalrs = (TextView) convertView.findViewById(R.id.tvoriginalrs);
				holder.tvOfferrs = (TextView) convertView.findViewById(R.id.tvofferrs);

				convertView.setTag(holder);
			} 
			else {
				holder = (ViewHolder) convertView.getTag();
			}


			holder.tvOrginalrs.setPaintFlags(holder.tvOrginalrs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			holder.tvOffername.setText("" + Item.getOfferName());
			holder.tvOrginalrs.setText("" + Item.getOriginalRs());
			holder.tvOfferrs.setText("" + Item.getOfferRs());



		} catch (Exception e) {
			Log.d("rup","Exception "+e);
		}

		return convertView;
	}
	
	/*public static <UIImage> UIImage ScaleAndCropImage(UIImage sourceImage, Size targetSize){
		
		
		return sourceImage;
		
	}
	
	*/

}

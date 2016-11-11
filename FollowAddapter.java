package com.freeoffer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.model.GS_Storeoffer;
import com.freeoffer.app.model.GS_follow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;


public class FollowAddapter extends ArrayAdapter <GS_follow>{
	private ArrayList<GS_follow> items;

	//CustomDialog customDialog;
	// Internet Connection Object
	//ConnectionDetector cdObj;

	Context context;
	Activity activity;
	int resource;
	ViewHolder holder = null;
	String listviewName="";
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());


	public FollowAddapter(Context context, Activity a, int resourceId,ArrayList<GS_follow> tempArrayList) {
		super(context, resourceId, tempArrayList);
		activity = a;
		this.context = context;
		this.items = new ArrayList<GS_follow>();
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
		TextView tvLabel,tvSubtital;
		ImageView ivimage,ivFollowgrey,ivFolloworange;
		//ImageView ivBookG,ivBookO,ivStorepf;
		//ImageView image;
	}

	public View getView(int position, View convertView, final ViewGroup parent) {

		final GS_follow Item = getItem(position);
		try {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_follow, null);
				holder = new ViewHolder();

				holder.tvLabel = (TextView) convertView.findViewById(R.id.tvlabel);
				holder.tvSubtital = (TextView) convertView.findViewById(R.id.tvsubtital);
				holder.ivFollowgrey = (ImageView) convertView.findViewById(R.id.ivfollowg);
				holder.ivFolloworange = (ImageView) convertView.findViewById(R.id.ivfolloworange);

				convertView.setTag(holder);
			}
			else {
				holder = (ViewHolder) convertView.getTag();
			}


			holder.tvLabel.setText("" + Item.getUsername());
			holder.tvSubtital.setText(Item.getReview()+" Reviews, " +Item.getFollowers()+ " Followers");

			final ImageView ivbookg=holder.ivFollowgrey,ivbooko=holder.ivFolloworange;

			holder.ivFollowgrey.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivbookg.setVisibility(View.INVISIBLE);
					ivbooko.setVisibility(View.VISIBLE);
				}
			});
			holder.ivFolloworange.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivbooko.setVisibility(View.INVISIBLE);
					ivbookg.setVisibility(View.VISIBLE);
				}
			});


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

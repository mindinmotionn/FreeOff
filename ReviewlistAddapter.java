package com.freeoffer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.activity.CommentonReviewActivity;
import com.freeoffer.app.activity.UserProfileActivity;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;


public class ReviewlistAddapter extends ArrayAdapter {
	//private ArrayList<OpenVideoItem> items;

	//CustomDialog customDialog;
	// Internet Connection Object
	//ConnectionDetector cdObj;

	Context context;
	Activity activity;
	int resource;
	ViewHolder holder = null;
	String listviewName="";
	private Map<ImageView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());
	int[] img;
	String[] label;

	public ReviewlistAddapter(Context context, Activity a, int resourceId, String[] s1, int[] img1) {
		super(context, resourceId, s1);
		activity = a;
		this.context = context;
		/*this.items = new ArrayList<OpenVideoItem>();
		this.items.addAll(items);*/
		this.resource = resourceId;
		img=img1;
		label=s1;
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
		TextView tvlabel;
		ImageView image;
		TextView tvDescription;
	}

	public View getView(int position, View convertView, final ViewGroup parent) {
		
		//final OpenVideoItem BragItemList = getItem(position);
		try {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_review, null);
				holder = new ViewHolder();

				holder.tvlabel = (TextView) convertView.findViewById(R.id.tvname);
				holder.image = (ImageView) convertView.findViewById(R.id.ivimage);
                holder.tvDescription=(TextView)convertView.findViewById(R.id.tvdescription);

				
				convertView.setTag(holder);
			} 
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvlabel.setText(label[position]);

			try {
				holder.image.setImageResource(img[position]);
			}
			catch(Exception e){}

			holder.image.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, UserProfileActivity.class);
					context.startActivity(i);
				}
			});
			holder.tvDescription.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(context, CommentonReviewActivity.class);
					context.startActivity(i);
				}
			});
				/*ImageView imageThumbnail = holder.ivvideothumb;
				ImageView imgplay=holder.ivPlay;
			    String thumbimageurl=BragItemList.getThumbImageUrl();	
			    holder.ivPlay.setVisibility(View.VISIBLE);
				
			if (!GeneralFunction.isEmptyCheck(thumbimageurl)) {
					ImageLoader imgLoader = new ImageLoader(getContext());
					imgLoader.DisplayImage(thumbimageurl,imageThumbnail);				
				}*/
			
			
			
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

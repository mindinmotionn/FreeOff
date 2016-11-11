package com.freeoffer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.freeoffer.app.R;
import com.freeoffer.app.general.GeneralFunction;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;


public class StorelistAddapter extends ArrayAdapter {
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
	String[] Rate;

	public StorelistAddapter(Context context, Activity a, int resourceId, String[] s1, int[] img1,String[] strrate) {
		super(context, resourceId, s1);
		activity = a;
		this.context = context;
		/*this.items = new ArrayList<OpenVideoItem>();
		this.items.addAll(items);*/
		this.resource = resourceId;
		img=img1;
		label=s1;
		Rate=strrate;
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
		//TextView tvlabel;
		//ImageView image;
		ImageView ivStorepf,ivRating,ivstore1,ivstore2,ivstore3,ivstore4,ivstore5;
		TextView tvStorename,tvUserLocation,tvProduct,tvOffer,tvcountphoto;

	}

	public View getView(int position, View convertView, final ViewGroup parent) {
		
		//final OpenVideoItem BragItemList = getItem(position);
		try {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_row_store, null);
				holder = new ViewHolder();

				holder.ivStorepf = (ImageView) convertView.findViewById(R.id.ivstoreprofile);
				holder.ivRating= (ImageView) convertView.findViewById(R.id.ivrating);
				holder.ivstore1= (ImageView) convertView.findViewById(R.id.iv1);
				holder.ivstore2= (ImageView) convertView.findViewById(R.id.iv2);
				holder.ivstore3= (ImageView) convertView.findViewById(R.id.iv3);
				holder.ivstore4= (ImageView) convertView.findViewById(R.id.iv4);
				holder.ivstore5= (ImageView) convertView.findViewById(R.id.iv5);

				holder.tvStorename = (TextView) convertView.findViewById(R.id.tvstorename);
				holder.tvUserLocation = (TextView) convertView.findViewById(R.id.tvstorelocation);
				holder.tvProduct = (TextView) convertView.findViewById(R.id.tvproduct);
				holder.tvOffer = (TextView) convertView.findViewById(R.id.tvoffer);
				holder.tvcountphoto=(TextView)convertView.findViewById(R.id.tvcountphoto);

				convertView.setTag(holder);
			} 
			else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.tvStorename.setText(label[position]);

			try {
				holder.ivStorepf.setImageResource(img[position]);
			}
			catch(Exception e){}
			String strrate=Rate[position];
			Bitmap bitmap=GeneralFunction.RateFunction(strrate, context);
			holder.ivRating.setImageBitmap(bitmap);
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

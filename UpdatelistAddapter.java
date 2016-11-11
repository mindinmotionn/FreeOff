package com.freeoffer.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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


public class UpdatelistAddapter extends ArrayAdapter {
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

	public UpdatelistAddapter(Context context, Activity a, int resourceId, String[] s1, int[] img1,String[] strrate) {
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
		TextView tvStorename,tvStorelocation,tvUsername,tvReview_Follow,tvPosttime,tvDescription,tvlikecomment_post;
		ImageView ivStorepf,ivUserpf,ivBookG,ivBookO,ivFollowG,ivFollowO,ivPostImage;
		ImageView ivLike,ivUnlike,ivComment,ivShare;
		ImageView ivRate;
	}

	public View getView(int position, View convertView, final ViewGroup parent) {
		
		//final OpenVideoItem BragItemList = getItem(position);
		try {
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.list_item_row_update, null);
				holder = new ViewHolder();

				holder.tvStorename = (TextView) convertView.findViewById(R.id.tvstorename);
				holder.tvStorelocation = (TextView) convertView.findViewById(R.id.tvstorelocation);
				holder.tvUsername = (TextView) convertView.findViewById(R.id.tvusername);
				holder.tvReview_Follow = (TextView) convertView.findViewById(R.id.tvreview_followers);
				holder.tvPosttime = (TextView) convertView.findViewById(R.id.tvposttime);
				holder.tvDescription = (TextView) convertView.findViewById(R.id.tvdescription);
				holder.tvlikecomment_post = (TextView) convertView.findViewById(R.id.tvpostlikecomment);

				holder.ivStorepf = (ImageView) convertView.findViewById(R.id.ivstoreprofile);
				holder.ivUserpf = (ImageView) convertView.findViewById(R.id.ivuserprofile);
				holder.ivBookG = (ImageView) convertView.findViewById(R.id.ivbookmark);
				holder.ivBookO = (ImageView) convertView.findViewById(R.id.ivbookmarkorange);
				holder.ivFollowG = (ImageView) convertView.findViewById(R.id.ivfollow);
				holder.ivFollowO = (ImageView) convertView.findViewById(R.id.ivfolloworange);
				holder.ivPostImage = (ImageView) convertView.findViewById(R.id.ivpostimage);
				holder.ivLike = (ImageView) convertView.findViewById(R.id.ivlike);
				holder.ivUnlike = (ImageView) convertView.findViewById(R.id.ivunlike);
				holder.ivComment = (ImageView) convertView.findViewById(R.id.ivcomment);
				holder.ivShare = (ImageView) convertView.findViewById(R.id.ivshare);
				holder.ivRate = (ImageView) convertView.findViewById(R.id.ivrating);

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

			String strreview="1";
			String strFollow="5";
			String strPosttime="1 hour ago";
			String strlike="2";
			String strComment="0";
			holder.tvReview_Follow.setText(strreview+" Reviews | " +strFollow+ " Followers");
			holder.tvPosttime.setText("Posted "+strPosttime);
			holder.tvlikecomment_post.setText(strlike+" LIKES | "+strComment+ " COMMENTS");

			String strrate=Rate[position];

			Bitmap bitmap= GeneralFunction.RateFunction(strrate,context);
			holder.ivRate.setImageBitmap(bitmap);

			final ImageView ivbookg=holder.ivBookG,ivbooko=holder.ivBookO,ivfollowg=holder.ivFollowG;
			final ImageView ivfollowo=holder.ivFollowO,ivlike=holder.ivLike,ivunlike=holder.ivUnlike;

			holder.ivBookG.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivbookg.setVisibility(View.INVISIBLE);
					ivbooko.setVisibility(View.VISIBLE);
				}
			});
			holder.ivBookO.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivbooko.setVisibility(View.INVISIBLE);
					ivbookg.setVisibility(View.VISIBLE);
				}
			});
			holder.ivFollowG.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivfollowg.setVisibility(View.INVISIBLE);
					ivfollowo.setVisibility(View.VISIBLE);
				}
			});
			holder.ivFollowO.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivfollowo.setVisibility(View.INVISIBLE);
					ivfollowg.setVisibility(View.VISIBLE);
				}
			});
			holder.ivLike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivlike.setVisibility(View.GONE);
					ivunlike.setVisibility(View.VISIBLE);
				}
			});
			holder.ivUnlike.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ivunlike.setVisibility(View.GONE);
					ivlike.setVisibility(View.VISIBLE);
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

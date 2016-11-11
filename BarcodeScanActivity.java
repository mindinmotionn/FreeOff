package com.freeoffer.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freeoffer.app.R;
import com.freeoffer.app.general.ExceptionHandler;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.general.TopExceptionHandler;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;


public class BarcodeScanActivity extends Activity {
	    private Camera mCamera;
		private CameraPreview mPreview;
		private Handler autoFocusHandler;

		TextView scanText;
		private final Context context = this;
		ImageScanner scanner;

		private boolean barcodeScanned = false;
		private boolean previewing = true;
		private boolean isLighOn = false;
		private ImageView tvline;
		private RelativeLayout ll_frame;
		private TextView tvline1;

		static {
			System.loadLibrary("iconv");
		}

		Context mContext;
		
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));
			getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			setContentView(R.layout.activity_qrscan);

			mContext = this;
			final FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
			Button btncance=(Button)findViewById(R.id.buttoncancel);
			Button btnlight=(Button)findViewById(R.id.buttonlight);
			tvline=(ImageView)findViewById(R.id.viewline);
			GlobalVar.setMyStringPref(mContext, "Ticketno", "");
			//tvline1=(TextView)findViewById(R.id.viewline1);
			//ll_frame=(LinearLayout)findViewById(R.id.ll_frame);
			btncance.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onBackPressed();
				}
			});
			
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			
			Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
		
			
		
			try {				
				
				//Camera.Parameters params= mCamera.getParameters();
				// Exception Handler Call For Crash 
				Thread.setDefaultUncaughtExceptionHandler(new TopExceptionHandler(this));
				
				autoFocusHandler = new Handler();
				mCamera = getCameraInstance();
		        Log.d("Rup", "camera " + mCamera);
		        
				
				// Instance barcode scanner
				scanner = new ImageScanner();
				scanner.setConfig(0, Config.X_DENSITY, 3);
				scanner.setConfig(0, Config.Y_DENSITY, 3);
		
				mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
			//	FrameLayout preview = (FrameLayout) findViewById(R.id.cameraPreview);
				preview.addView(mPreview);
		
				scanText = (TextView) findViewById(R.id.scanText);
		
			} catch (Exception ex) {
				GeneralFunction.messageBox(mContext, "onCreate" + "MainActivity",
						Log.getStackTraceString(ex));
			}
			
			// camera flash light related 
			
			PackageManager pm = context.getPackageManager();
			if (!pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
				Log.d("Rup", "Device has no camera!");
				return;
			}
			//mCamera = Camera.open();
			
			
			btnlight.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final Parameters p = mCamera.getParameters();
					if (isLighOn) {
						 
						Log.i("info", "torch is turn off!");
	 
						p.setFlashMode(Parameters.FLASH_MODE_OFF);
						mCamera.setParameters(p);
						mCamera.startPreview();
						isLighOn = false;
	 
					} else {
	 
						Log.i("info", "torch is turn on!");
	 
						p.setFlashMode(Parameters.FLASH_MODE_TORCH);
	 
						mCamera.setParameters(p);
						mCamera.startPreview();
						isLighOn = true;
	 
					}
				}	 
			});
			
			/*int top =preview.getTop();
			int left=preview.getLeft();
			int bottom=preview.getBottom();
			int right=preview.getRight();
			Log.d("rup", "the height is " + ll_frame.getHeight());
			Log.d("rup", "the height is " + top);
			Log.d("rup", "the height is " + left);
			Log.d("rup", "the height is " + bottom);
			Log.d("rup", "the height is " + right);*/
			preview.getViewTreeObserver().addOnGlobalLayoutListener(
		            new ViewTreeObserver.OnGlobalLayoutListener(){

		                @Override
		                public void onGlobalLayout() {

		                    int botttom = preview.getBottom();
		                    int top = preview.getTop();
		                    //Toast the height and top...
		                    TranslateAnimation ta=new TranslateAnimation(0, 0, top,botttom);
		        			ta.setDuration(8000);
		        			/*ta.setFillAfter(true);
		        			ta.setFillBefore(true);	*/	        		
		        			ta.setRepeatMode(Animation.REVERSE);
		        			ta.setRepeatCount(Animation.INFINITE);
		        			ta.setInterpolator(new LinearInterpolator());
		        			tvline.startAnimation(ta);
		                }

		            });
			
			
			/*Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
			tvline.startAnimation(animation);*/
		}		
			 
			
		public void onPause() {
			super.onPause();
			try{
			//releaseCamera();
			//finish();
			}
			catch(Exception e){
				Log.d("Rup", "Exception e" + e);
			}
		}
		
		/** A safe way to get an instance of the Camera object. */
		public static Camera getCameraInstance() {
			Camera c = null;
			try {
				c = Camera.open();
			} catch (Exception e) {
				
			}
			return c;
		}
		
		private void releaseCamera() {
			if (mCamera != null) {
				previewing = false;
				mCamera.setPreviewCallback(null);
				mCamera.release();
				mCamera = null;
			}
		}
		
		private Runnable doAutoFocus = new Runnable() {
			public void run() {
				if (previewing)
					mCamera.autoFocus(autoFocusCB);
			}
		};
		
		/***
		 * Device Hardware Back Button Click Event
		 */
		
		@Override
		public void onBackPressed() {			
			releaseCamera();
			finish();
		}
		
		PreviewCallback previewCb = new PreviewCallback() {
			public void onPreviewFrame(byte[] data, Camera camera) {
				
		
				try {
					
					Parameters parameters = camera.getParameters();
					Size size = parameters.getPreviewSize();
		
					Image barcode = new Image(size.width, size.height, "Y800");
					barcode.setData(data);
					
					int result = scanner.scanImage(barcode);
					if (result != 0) {
						previewing = false;
						mCamera.setPreviewCallback(null);
						mCamera.stopPreview();
		
						SymbolSet syms = scanner.getResults();
						for (Symbol sym : syms) {
		
							//Log.d("Rup","barcode result "+ sym.getData());
							if (!GeneralFunction.isEmptyCheck(sym.getData().toString())) {
								
								/*Intent i=new Intent(BarcodeScanActivity.this,NewTicketActivity.class);
								i.putExtra("Ticketno",sym.getData());
								startActivity(i);*/
								GlobalVar.setMyStringPref(mContext, "Ticketno", sym.getData());
								releaseCamera();
								Toast.makeText(BarcodeScanActivity.this, "Scan no: "+sym.getData(), Toast.LENGTH_SHORT).show();
								finish();
								/*if (GeneralFunction.isInteger(sym.getData())) {
		
									scanText.setText("barcode result "+ sym.getData());
									barcodeScanned = true;
									RiderPaymentTaxiPlanetConfirmationActivity
									Intent intent = new Intent(RiderQRScanActivity.this,RiderPaymentTaxiPlanetConfirmationActivity.class);
									intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
									intent.putExtra("TransactionId",
											Integer.parseInt(sym.getData()));
									startActivity(intent);
								} else {
									 OpenDialogOnInvalidQRScan("Transaction id is not valid");
								}*/
							} else {
								OpenDialogOnInvalidQRScan("No scan..");
							}
						}
					}
				} catch (Exception e) {
					OpenDialogOnInvalidQRScan(Log.getStackTraceString(e));
		
				}
		
			}
		};
		
		// Mimic continuous auto-focusing
		AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
			public void onAutoFocus(boolean success, Camera camera) {
				autoFocusHandler.postDelayed(doAutoFocus, 1000);
			}
		};
		
		private void OpenDialogOnInvalidQRScan(String msg)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
			builder.setTitle("Ticket Planet")
					.setMessage("Ticket is not valid")
					.setCancelable(false)
					.setPositiveButton("Ok",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialog,
										int id) {
									onBackPressed();
		
								}
							});
		
			AlertDialog alert = builder.create();
			alert.show();
		}
}
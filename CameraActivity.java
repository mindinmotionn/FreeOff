package com.freeoffer.app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;

import android.hardware.Camera.Parameters;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;


import com.freeoffer.app.AppCamera.Constants;
import com.freeoffer.app.AppCamera.Preview;
import com.freeoffer.app.AppCamera.UploadPhotoModel;
import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.general.GeneralFunction;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {

    CustomDialog_Ok customDialog;
    Context mContext;
    Activity activity;
    Preview preview;
    FrameLayout frameLayout;
    LinearLayout llGallery;
    ImageView ivCamera,ivGallery;
    private RelativeLayout imageTab;

    Camera camera;
    Parameters params;
    private final Context context = this;

    ImageView ivFlashLight;
    boolean flag_flash=true;
    private static final String TAG = "CamTestActivity";
    String strImagePath="";
    private ArrayList<UploadPhotoModel> alstPath = new ArrayList<UploadPhotoModel>();
    private boolean isCamera = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FullScreencall();
        mContext = this;
        activity = this;
        setContentView(R.layout.activity_camera);
        InitViews();
    }

    public void InitViews(){
        customDialog=new CustomDialog_Ok(mContext);
        frameLayout = (FrameLayout) findViewById(R.id.frmLayout);
        ivFlashLight=(ImageView)findViewById(R.id.ivflashlight);
        llGallery = (LinearLayout) findViewById(R.id.llGallery);
        ivGallery = (ImageView) findViewById(R.id.imgGallery);
        imageTab = (RelativeLayout) findViewById(R.id.imageTab);
        ivCamera = (ImageView) findViewById(R.id.imgCapture);

        ivCamera.setOnClickListener(this);
        ivGallery.setOnClickListener(this);
        alstPath.clear();
        final PackageManager pm = context.getPackageManager();
        if(!isCameraSupported(pm)){
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("No Camera");
            alertDialog.setMessage("The device's doesn't support camera.");
            alertDialog.setButton(RESULT_OK, "OK", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int which) {
                    Log.e("err", "The device's doesn't support camera.");
                }
            });
            alertDialog.show();
        }
        else {
            camera = Camera.open();
            params = camera.getParameters();
            preview = new Preview(CameraActivity.this, (SurfaceView) findViewById(R.id.surfaceView));
            preview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            frameLayout.addView(preview);
            preview.setKeepScreenOn(true);
            ivFlashLight.setOnClickListener(this);
        }
        // check Flash
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ivflashlight:
                Flashlight(flag_flash);
                break;
            case R.id.imgGallery:
                Toast.makeText(CameraActivity.this, "Comming soon", Toast.LENGTH_SHORT).show();
                break;
            case R.id.imgCapture:
                CapturePhoto();
                break;
        }
    }

    Camera.ShutterCallback shutterCallback = new Camera.ShutterCallback() {
        public void onShutter() {
            Log.e("", "shutterCallback ::::");
        }
    };

    Camera.PictureCallback rawCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "rawCallback");
        }
    };

    Camera.PictureCallback jpegCallback = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            addImage(strImagePath, data);
            resetCam();
            // if (getIntent().getExtras() != null) {
            //
            // }
            // new SaveImageTask(data).execute(data);
            // Log.d(TAG, "jpegCallback - jpeg");
        }
    };

    private void Flashlight(boolean flashvalue){
        PackageManager pm=context.getPackageManager();
        if(isFlashSupported(pm)){
            if (flashvalue) {   // Turn on
                Log.i("info", "torch is turn on!");
                if (camera == null || params == null) {
                    return;
                }
                params = camera.getParameters();
                params.setFlashMode(Parameters.FLASH_MODE_TORCH);
                camera.setParameters(params);
                camera.startPreview();

                flag_flash=false;
            } else {
                if (camera == null || params == null) {
                    return;
                }
                Log.i("info", "torch is turn off!");
                params = camera.getParameters();
                params.setFlashMode(Parameters.FLASH_MODE_OFF);
                camera.setParameters(params);
                camera.stopPreview();
                flag_flash=true;
                camera.startPreview();
            }
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("No Camera Flash");
            alertDialog.setMessage("The device's camera doesn't support flash.");
            alertDialog.setButton(RESULT_OK, "OK", new DialogInterface.OnClickListener() {
                public void onClick(final DialogInterface dialog, final int which) {
                    Log.e("err", "The device's camera doesn't support flash.");
                }
            });
            alertDialog.show();
        }
    }


    // For hide back button in activity and show full screen
    public void FullScreencall() {
        if (Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    private boolean isFlashSupported(PackageManager packageManager){
        // if device support camera flash?
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            return true;
        }
        return false;
    }

    /**
     * @param packageManager
     * @return true <b>if the device support camera</b><br/>
     * false <b>if the device doesn't support camera</b>
     */
    private boolean isCameraSupported(PackageManager packageManager){
        // if device support camera?
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("", "On RESUME ::::");
        FullScreencall();

        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                if (camera == null)
                    camera = Camera.open();
                camera.setDisplayOrientation(90);
                camera.startPreview();
                preview.setCamera(camera);
            } catch (RuntimeException ex) {
                Log.e("", "On RESUME::::");
            }
        }
        // setUpMenu();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("", "On onStart::::");
        int numCams = Camera.getNumberOfCameras();
        if (numCams > 0) {
            try {
                camera = Camera.open(0);
                camera.setDisplayOrientation(90);
                camera.startPreview();
                preview.setCamera(camera);
            } catch (RuntimeException ex) {
                Log.e("", "On Start::::");
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("", "onPause::::");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("", "onDestroy::::");
        if (camera != null) {
            camera.stopPreview();
            preview.setCamera(null);
            camera.release();
            camera = null;
        }
        // unregisterReceiver(imageBroadcastReceiver);
        // unregisterReceiver(videoBroadcastReceiver);
    }

    private void resetCam() {
        if (camera == null)
            camera = Camera.open();
        camera.setDisplayOrientation(90);
        camera.startPreview();
        preview.setCamera(camera);
    }

    public void addImage(String strImagePath, byte[] myByte) {
        byte[] mByte = myByte;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap ThumbImage = BitmapFactory.decodeByteArray(myByte, 0, myByte.length, options);

        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(ThumbImage,ThumbImage.getWidth(),ThumbImage.getHeight(),true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap , 0, 0, scaledBitmap .getWidth(), scaledBitmap .getHeight(), matrix, true);
//		ByteArrayOutputStream stream = new ByteArrayOutputStream();
//		//rotatedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//		myByte = stream.toByteArray();
        final View addMediaView = getLayoutInflater().inflate(R.layout.add_image_layout, null);
        UploadPhotoModel mUploadPhotoModel = new UploadPhotoModel();
        // mUploadPhotoModel.setStrPath(strImagePath);
        mUploadPhotoModel.setType(Constants.TYPE_IMAGE);
        mUploadPhotoModel.setMyByte(mByte);
        mUploadPhotoModel.setBitmap(ThumbImage);
        ImageView imgMedia = (ImageView) addMediaView.findViewById(R.id.imgMedia);
        imgMedia.setImageBitmap(Bitmap.createScaledBitmap(rotatedBitmap, 120, 120, false));
        imgMedia.setTag(mUploadPhotoModel);
        ImageView imgPlay = (ImageView) addMediaView.findViewById(R.id.imgPlay);
        imgPlay.setTag(mUploadPhotoModel);
        imgPlay.setVisibility(View.GONE);
        ImageView imgDelete = (ImageView) addMediaView.findViewById(R.id.imgDelete);
        imgDelete.setTag(mUploadPhotoModel);
        alstPath.add(mUploadPhotoModel);
        // alstMediaPath.add(strImagePath);
        // alstVideoPath.add(strVideoFilePath);
        llGallery.addView(addMediaView);

        /*imgMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPhotoModel uploadPhotoModel = (UploadPhotoModel) v.getTag();
                int intPos = alstPath.indexOf(uploadPhotoModel);
                // CameraImageViewPagerAdapter mgGalleryViewPagerAdapter;
                // mgGalleryViewPagerAdapter = new
                // CameraImageViewPagerAdapter(getSupportFragmentManager(),
                // mContext, alstPath, 1);
                // framelayoutViewPager.setVisibility(View.VISIBLE);
                // viewPager.setAdapter(mgGalleryViewPagerAdapter);
                Constants.alstFullView.clear();
                Intent intent = new Intent(MainActivity.this, FullScreenViewActivity.class);
                intent.putExtra(Constants.IMAGE_POS, intPos);
                Constants.alstFullView.addAll(alstPath);
                // Bundle args = new Bundle();
                // args.putSerializable(Constants.IMAGE_ARRAYLIST,
                // (Serializable) alstPath);
                // intent.putExtra("BUNDLE", args);
                startActivity(intent);
            }
        });*/

        /*imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    final UploadPhotoModel uploadPhotoModel = (UploadPhotoModel) v.getTag();
                    final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
                    dialog.setContentView(R.layout.custom_video_dialog);
                    Window dialog_window = dialog.getWindow();
                    dialog_window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

                    LinearLayout view = (LinearLayout) dialog.findViewById(R.id.layout_root);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });

                    TextView txtTitle = (TextView) dialog.findViewById(R.id.txt_choose_from);
                    txtTitle.setText("Do you want to delete this image?");

                    Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
                    btnYes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                            alstPath.remove(uploadPhotoModel);
                            llGallery.removeView(addMediaView);
                            imageTab.setBackgroundColor(getResources().getColor(R.color.white));
                        }
                    });

                    Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
                    btnNo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
            }
        });*/
        resetCam();
        // } else {
        // Utils.showMessageDialog(UploadPhotoActivity.this,
        // getResources().getString(R.string.Alert),
        // "You can not capture more than five images");
        // }
    }


    private void CapturePhoto(){

        if (getIntent() != null) {
            if (getIntent().getAction() != null) {
                if (getIntent().getAction().equals(Constants.ADD_POST_PREF.IS_FROM_ADD)) {
                    if (alstPath != null) {
                        if (alstPath.size() > 0 && alstPath != null) {
                           /* if (alstPath.get(0).getType() == Constants.TYPE_VIDEO) {
                                dialogVideo();
                            } else*/ if (alstPath.get(0).getType() == Constants.TYPE_IMAGE) {
                                if (alstPath.size() <= 4) {
                                    try {
                                        isCamera = true;
                                        strImagePath = "";
                                        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                                    } catch (Exception e) {

                                    }
                                } else {
                                    dialogMsg("You can not capture more than five images");
                                }
                            }
                        } else {
                            try {
                                isCamera = true;
                                strImagePath = "";
                                camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                            } catch (Exception e) {

                            }
                        }
                    } else {
                        try {
                            isCamera = true;
                            strImagePath = "";
                            camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                        } catch (Exception e) {

                        }
                    }
                }
            } else {
                if (alstPath != null) {
                    if (alstPath.size() > 0 && alstPath != null) {
                        if (alstPath.get(0).getType() == Constants.TYPE_IMAGE) {
                            if (alstPath.size() <= 4) {
                                try {
                                    isCamera = true;
                                    strImagePath = "";
                                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                                } catch (Exception e) {

                                }
                            } else {
                                //cus
                                // Utils.showMessageDialog(UploadPhotoActivity.this,
                                // getResources().getString(R.string.Alert),
                                // getResources().getString(R.string.you_can_not_upload_more_than_five_images));
                            }
                        } /*else if (alstPath.get(0).getType() == Constants.TYPE_VIDEO) {
                            dialogVideo();
                        }*/
                    } else {
                        try {
                            isCamera = true;
                            strImagePath = "";
                            camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                        } catch (Exception e) {
                            // TODO: handle exception
                            Log.e("", "camera:::::::::::::" + e.toString());
                        }

                        // dialogMsg(getResources().getString(R.string.no_media_found));
                    }
                } else {
                    try {
                        isCamera = true;
                        strImagePath = "";
                        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                    } catch (Exception e) {

                    }
                }
            }
        } else {
            if (alstPath != null) {
                if (alstPath.size() > 0 && alstPath != null) {
                    if (alstPath.get(0).getType() == Constants.TYPE_IMAGE) {
                        if (alstPath.size() <= 4) {
                            try {
                                isCamera = true;
                                strImagePath = "";
                                camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                            } catch (Exception e) {

                            }
                        } else {
                            dialogMsg("You can not capture more than five images");

                        }
                    }
                    // else{
                    // isCamera = true;
                    // strImagePath = "";
                    // camera.takePicture(shutterCallback, rawCallback,
                    // jpegCallback);
                    // }
                } else {
                    try {
                        isCamera = true;
                        strImagePath = "";
                        camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                    } catch (Exception e) {

                    }
                }
            } else {
                try {
                    isCamera = true;
                    strImagePath = "";
                    camera.takePicture(shutterCallback, rawCallback, jpegCallback);
                } catch (Exception e) {

                }
            }
        }
    }

    public void dialogMsg(String Msg) {
        GeneralFunction.DisplayMessage(customDialog,Msg);
    }


}

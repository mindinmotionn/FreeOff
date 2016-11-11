package com.freeoffer.app.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.freeoffer.app.R;
import com.freeoffer.app.dialog.CustomDialog_Ok;
import com.freeoffer.app.general.CommonUtilities;
import com.freeoffer.app.general.ConnectionDetector;
import com.freeoffer.app.general.GeneralFunction;
import com.freeoffer.app.general.GlobalVar;
import com.freeoffer.app.gmailIntegration.AbstractGetNameTask;
import com.freeoffer.app.gmailIntegration.GetNameInForeground;
import com.google.android.gms.auth.GoogleAuthUtil;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.UUID;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSignup,btnSignin;
    private TextView tvSkiplogin;
    ImageView ivFacebook,ivGooglePlus;

    CustomDialog_Ok customDialog;
    // Connection Object Declare
    ConnectionDetector cdObj;
    private Context mContext;
    ProgressDialog progress;
    //Upload Photo
    protected static final int RESULT_LOAD_IMAGE = 0;
    int serverResponseCode = 0;
    ProgressDialog dialog = null;
    String upLoadServerUri = null;

    /**********
     * File Path
     *************/

    final String uploadFilePath = "/mnt/sdcard/";
    String ProfileImageUrl = ".jpg";
    Boolean photoupload = false;
    final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            + "/picFolder/";
    File newdir = new File(dir);
    int count = 0;
    String strPhotoUrl;

    // facebook related
    private CallbackManager callbackManager;
    //private Button facebook_button;


    private String facebook_id, f_name, m_name, l_name, gender, profile_image, full_name, email_id;

    String changephoto_email = "";
    String webstrmobile = "";

    //Google Plus Related
// Gmail Related
    //Context mContext = LoginActivity_tp.this;
    String Googleid="";
    AccountManager mAccountManager;
    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/userinfo.profile";
    String Firstname,Lastname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            Window window=getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        mContext=this;
        InitViews();
    }

    private void InitViews(){

        cdObj = new ConnectionDetector(mContext);
        customDialog = new CustomDialog_Ok(mContext);

        btnSignup=(Button)findViewById(R.id.btnsignup);
        btnSignin=(Button)findViewById(R.id.btnsignin);
        tvSkiplogin=(TextView)findViewById(R.id.tvskip);
        ivFacebook=(ImageView)findViewById(R.id.ivfacebook);
        ivGooglePlus=(ImageView)findViewById(R.id.ivgoogle);

        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        tvSkiplogin.setOnClickListener(this);
        ivFacebook.setOnClickListener(this);
        ivGooglePlus.setOnClickListener(this);

        FacebookView();

    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnsignin:
                Intent iSignin=new Intent(HomeActivity.this,SigninActivity.class);
                startActivity(iSignin);
                break;
            case R.id.btnsignup:
                Intent iSignup=new Intent(HomeActivity.this,SignupActivity.class);
                startActivity(iSignup);
                break;
            case R.id.tvskip:
                if(GeneralFunction.isCompare(GlobalVar.getMyStringPref(mContext, CommonUtilities.IslocationSelect),"true")){
                    Intent i=new Intent(HomeActivity
                            .this,MainActivity_Home.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent iSkip=new Intent(HomeActivity.this,LocationActivity.class);
                    startActivity(iSkip);
                    finish();
                }

                break;
            case R.id.ivfacebook:
                /*try {
                    LoginManager.getInstance().logInWithReadPermissions(HomeActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                }
                catch(Exception e){
                    GeneralFunction.DisplayMessage(customDialog,"Error: "+e.toString());
                }*/
                //LoginManager.getInstance().logInWithReadPermissions(HomeActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                break;
            case R.id.ivgoogle:
                syncGoogleAccount();
                break;
        }
    }



    private void FacebookView() {

        final ImageView ivTakephoto=new ImageView(mContext);
        progress = new ProgressDialog(HomeActivity.this);
        progress.setMessage(getResources().getString(R.string.please_wait_facebooklogin));
        progress.setIndeterminate(false);
        progress.setCancelable(false);
        facebook_id = f_name = m_name = l_name = gender = profile_image = full_name = email_id = "";
        //for facebook
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        //register callback object for facebook result
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                progress.show();
                Profile profile = Profile.getCurrentProfile();
                if (profile != null) {
                    facebook_id = profile.getId();
                    f_name = profile.getFirstName();
                    m_name = profile.getMiddleName();
                    l_name = profile.getLastName();
                    full_name = profile.getName();
                    profile_image = profile.getProfilePictureUri(200, 200).toString();
                    //etFirstname.setText("" + f_name);
                    //etLastname.setText("" + l_name);
                    if(!GeneralFunction.isEmptyCheck(profile_image)) {
                        new ImageDownloaderTask(ivTakephoto).execute(profile_image);
                    }
                  /*firstName = {String@831952533328} "Rupkuvarba"
                    id = {String@831950031648} "917982391631961"
                    lastName = {String@831951018104} "Barad"
                    linkUri = {Uri$StringUri@831951554496} "https://www.facebook.com/app_scoped_user_id/917982391631961/"
                    middleName = {String@831952489944} ""
                    name = {String@831952483192} "Rupkuvarba Barad"*//**//**/

                }
                final AccessToken accessToken = loginResult.getAccessToken();
                GraphRequest request = GraphRequest.newMeRequest(accessToken,
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    email_id = object.getString("email");
                                } catch (JSONException e) {
                                    Log.d("Rup", "Exception: " + e);
                                    // TODO Auto-generated catch block
                                    //  e.printStackTrace();
                                }
                                // etEmail.setText("" + email_id);
                                // etbirthdate.setText(""+strBirthdate);
                                progress.dismiss();
                                if(GeneralFunction.isEmptyCheck(f_name)){
                                    try {
                                        LoginManager.getInstance().logInWithReadPermissions(HomeActivity.this, Arrays.asList("public_profile", "user_friends", "email"));
                                    }
                                    catch(Exception e){
                                        GeneralFunction.DisplayMessage(customDialog,"Error: "+e.toString());
                                    }
                                }
                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,birthday,link,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            public void onCancel() {
                Toast.makeText(HomeActivity.this, getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            public void onError(FacebookException error) {
                Toast.makeText(HomeActivity.this, getResources().getString(R.string.login_canceled_facebooklogin), Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });

    }

    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadBitmap(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                        uploadphoto(bitmap);
                    }
                }
            }
        }
    }

    private Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            urlConnection.disconnect();
            Log.w("ImageDownloader", "Error downloading image from " + url);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private void uploadphoto(Bitmap bitmap) {
        final Bitmap uploaddedBitmapPhoto=bitmap;
        upLoadServerUri = CommonUtilities.WebserviceUploadImageUrl;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        final byte[] byteArray = stream.toByteArray();
        dialog = ProgressDialog.show(mContext, "", "Uploading file...", true);
        new Thread(new Runnable() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        // messageText.setText("uploading started.....");
                    }
                });
                try {
                    HttpURLConnection conn = null;
                    DataOutputStream dos = null;
                    String lineEnd = "\r\n";
                    String twoHyphens = "--";
                    String boundary = "*****";
                    String uuid = UUID.randomUUID().toString();
                    URL url = new URL(upLoadServerUri);
                    String filename = uuid + ".jpg";
                    ProfileImageUrl = filename;
                    // Open a HTTP  connection to  the URL
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // Allow Inputs
                    conn.setDoOutput(true); // Allow Outputs
                    conn.setUseCaches(false); // Don't use a Cached Copy
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                    conn.setRequestProperty("uploaded_file", filename);
                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"userfile\";filename=\"" + filename + "\"" + lineEnd);
                    dos.writeBytes(lineEnd);
                    dos.write(byteArray, 0, byteArray.length);
                    // send multipart form data necesssary after file data...
                    dos.writeBytes(lineEnd);
                    dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                    // Responses from the server (code and message)
                    serverResponseCode = conn.getResponseCode();
                    String serverResponseMessage = conn.getResponseMessage();
		                     /*  Log.i("uploadFile", "HTTP Response is : "
		                               + serverResponseMessage + ": " + serverResponseMessage);*/
                    if (serverResponseCode == 200) {
                        runOnUiThread(new Runnable() {
                            public void run() {
                                String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                        + " http://www.androidexample.com/media/uploads/"
                                        + ProfileImageUrl;
                                // System.out.println("uploadsuccess"+msg);
                                // messageText.setText(msg);
                                Log.d("Rup", "upload file " + ProfileImageUrl);
                                photoupload = true;
                                strPhotoUrl = ProfileImageUrl;
                                ImageView ivTakephoto=new ImageView(mContext);
                                ivTakephoto.setImageBitmap(uploaddedBitmapPhoto);

                            }
                        });
                    } else {
                        String msg = "Upload profile photo failed, please try again";
                        Toast.makeText(mContext, ""+msg, Toast.LENGTH_SHORT).show();
                        //GeneralFunction.DisplayMessage(customDialog, msg);
                    }
                    //close the streams //
                    dos.flush();
                    dos.close();

                } catch (MalformedURLException ex) {
                    dialog.dismiss();
                    ex.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "Upload profile photo failed, please try again",Toast.LENGTH_SHORT).show();
                            //messageText.setText("MalformedURLException Exception : check script url.");
                            //Toast.makeText(RegisterPhotoActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //  Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
                } catch (Exception e) {
                    dialog.dismiss();
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(mContext, "Upload profile photo failed, please try again",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                dialog.dismiss();
                // return serverResponseCode;
            }
        }).start();
    }


    // Google plus start

    public void syncGoogleAccount() {

        if (cdObj.isConnectingToInternet()) {
            String[] accountarrs = getAccountNames();

            if (accountarrs.length > 0) {
                //you can set here account for login
                email_id=accountarrs[0];
                getTask(HomeActivity.this, accountarrs[0], SCOPE).execute();
            } else {

                Toast.makeText(HomeActivity.this, "No Google Account Sync!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            GeneralFunction.IntenetConnectionMsg(customDialog);
        }
    }

    private String[] getAccountNames() {
        mAccountManager = AccountManager.get(this);
        Account[] accounts = mAccountManager.getAccountsByType(GoogleAuthUtil.GOOGLE_ACCOUNT_TYPE);
        String[] names = new String[accounts.length];
        for (int i = 0; i < names.length; i++) {
            names[i] = accounts[i].name;
        }
        return names;
    }

    private AbstractGetNameTask getTask(HomeActivity activity, String email,String scope) {


		/*JSONObject profileData = new JSONObject(
				AbstractGetNameTask.GOOGLE_USER_DATA);

		if (profileData.has("picture")) {
			userImageUrl = profileData.getString("picture");
			//new GetImageFromUrl().execute(userImageUrl);
		}*/

        return new GetNameInForeground(activity, email, scope);
    }

    public void GetGoogleLoginDetail(){
        try {
            System.out.println("On Home Page***" + AbstractGetNameTask.GOOGLE_USER_DATA);
            JSONObject profileData = new JSONObject(AbstractGetNameTask.GOOGLE_USER_DATA);
            if (profileData.has("id")) {
                Googleid = profileData.getString("id");
            }
            if (profileData.has("given_name")) {
                Firstname = profileData.getString("given_name");
            }
            if (profileData.has("family_name")) {
                Lastname = profileData.getString("family_name");
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //Toast.makeText(mContext,"Gogle id is "+Googleid,Toast.LENGTH_SHORT).show();
       // metmail.setText(email_id);
        if(!GeneralFunction.isEmptyCheck(Googleid) && !(GeneralFunction.isEmptyCheck(email_id))){
            //Toast.makeText(mContext,"Gogle id is "+Googleid,Toast.LENGTH_SHORT).show();
           /* GlobalVar.setMyStringPref(mContext, CommonUtilities.TWITTER_userID, "");
            GlobalVar.setMyStringPref(mContext, CommonUtilities.LINKEDIN_userID, "");
            GlobalVar.setMyStringPref(mContext, CommonUtilities.FACEBOOK_userID, "");
            GlobalVar.setMyStringPref(mContext, CommonUtilities.GMAIL_userID, Googleid);
            TimeConsumingtask t = new TimeConsumingtask();
            t.execute();*/
        }

    }

}

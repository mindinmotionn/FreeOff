package com.freeoffer.app.activity;

import android.app.Application;

import android.os.StrictMode;
import android.text.TextUtils;

import com.digits.sdk.android.AuthCallback;
import com.digits.sdk.android.Digits;
import com.digits.sdk.android.DigitsException;
import com.digits.sdk.android.DigitsSession;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

/*
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;*/
import io.fabric.sdk.android.Fabric;

/**
 * Created by sc-android on 5/21/2016.
 */
public class FreeOffApplication extends Application {
    private AuthCallback authCallback;
    private static final String TWITTER_KEY = "XszH87CVE1cSuORCQsbt6cz9T";
    private static final String TWITTER_SECRET = "CyTXqzHGkWTIFdFtovzGTuzZkX6I8c51Xt2NUV51RjsOMlB6Aa";

    @Override
    public void onCreate() {
        super.onCreate();
       // mInstance = this;
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());

        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .build());
        TwitterAuthConfig authConfig =  new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new TwitterCore(authConfig), new Digits());
        authCallback = new AuthCallback() {
            @Override
            public void success(DigitsSession session, String phoneNumber) {
                // Do something with the session
            }

            @Override
            public void failure(DigitsException exception) {
                // Do something on failure
            }
        };
    }

    public AuthCallback getAuthCallback(){
        return authCallback;
    }


  /*  public static final String TAG = FreeOffApplication.class.getSimpleName();

    private RequestQueue mRequestQueue;

    private static FreeOffApplication mInstance;



    public static synchronized FreeOffApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
*/
}

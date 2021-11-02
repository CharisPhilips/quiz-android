package com.quizapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.quizapp.api.Api;
import com.quizapp.common.Constants;
import com.quizapp.common.listener.AuthenticationListener;

public class Application extends MultiDexApplication { // implements ILoginListner
    private static final String TAG = "BookDataFinderApplication";
    public static Application s_Application = null;

    public Api api = null;
    private boolean isFinishLoading = false;
    private AuthenticationListener authenticationListener;
    private SharedPreferences userSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new BDFActivityLifecycleCallbacks(this));
        s_Application = this;

        this.api = new Api();
//        this.dbHelper = new DBHelper(this);
//        this.dbHelper.getWritableDatabase();
        this.userSharedPreferences = getSharedPreferences(Constants.KEY_USER_PREFERENCE, Context.MODE_PRIVATE);
        readSharedPreferenceEmailData();
        isFinishLoading = true;
    }

    public void setAuthenticationListener(AuthenticationListener listener) {
        this.authenticationListener = listener;
    }

    private void readSharedPreferenceEmailData() {
        String currUserEmail = userSharedPreferences.getString(Constants.KEY_USER_PREFERENCE_EMAIL, null);
        String currUserPass = userSharedPreferences.getString(Constants.KEY_USER_PREFERENCE_PASS, null);
        if(currUserEmail!=null && currUserPass!=null) {
        }
    }

    public void writeSharedPreferenceEmailData(String email, String pass, String token) {
        SharedPreferences.Editor edit = userSharedPreferences.edit();
        edit.putString(Constants.KEY_USER_PREFERENCE_EMAIL, email);
        edit.putString(Constants.KEY_USER_PREFERENCE_PASS, pass);
        edit.putString(Constants.KEY_USER_PREFERENCE_TOKEN, token);
        edit.commit();
    }

    public void removeSharedPreferenceEmailData() {
        SharedPreferences.Editor edit = userSharedPreferences.edit();
        edit.remove(Constants.KEY_USER_PREFERENCE_EMAIL);
        edit.remove(Constants.KEY_USER_PREFERENCE_PASS);
        edit.remove(Constants.KEY_USER_PREFERENCE_TOKEN);
        edit.commit();
    }

    public boolean isFinishLoading() {
        return isFinishLoading;
    }


//    @Override
//    public void onSuccess(UserProfile user) {
//        if (user != null) {
//            //login success
//            Global.saveUserData(user);
//        }
//    }
//
//    @Override
//    public void onError(String message) {
//        ViewUtils.showToast(this, message);
//    }

    private class BDFActivityLifecycleCallbacks implements ActivityLifecycleCallbacks {

        private int foregroundActivities;
        private boolean isChangingConfiguration;
        private long time;

        public BDFActivityLifecycleCallbacks(Application tcApplication) {

        }

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用进入前台
                time = System.currentTimeMillis();
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }
}


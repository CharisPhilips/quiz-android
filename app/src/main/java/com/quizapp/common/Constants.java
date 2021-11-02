package com.quizapp.common;

public class Constants {
    /////////////////////////Constants
    public final static int SPLASH_TIMEOUT = 3000;
    public final static int DOUBLE_BACKKEY_TIMEOUT = 2000;
    //payment relation
    public static String BACKEND_URL = "http://35.177.51.253/api/"; //"http://192.168.101.21:8000/api/";//

    //sharepreference
    public final static String KEY_USER_PREFERENCE = "UserPref";
    public final static String KEY_USER_PREFERENCE_EMAIL = "CurrUserEmail";
    public final static String KEY_USER_PREFERENCE_PASS = "CurrUserPass";
    public final static String KEY_USER_PREFERENCE_TOKEN = "CurrUserToken";

    public final static int STATUS_FILE_CANNOT_DOWNLOAD = 0;
    public final static int STATUS_FILE_CAN_DOWNLOAD = 1;
    public final static int STATUS_FILE_ALREADY_EXIST = 2;


}

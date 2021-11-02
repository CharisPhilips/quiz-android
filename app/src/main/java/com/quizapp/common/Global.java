package com.quizapp.common;

import com.quizapp.Application;
import com.quizapp.api.Api;
import com.quizapp.common.data.User;

import java.util.Random;

public class Global {
    public static int DISPLAY_HEIGHT;
    public static int DISPLAY_WIDTH;
    public static int STATUSBAR_HEGIHT;
    public static int ACTIONBAR_HEGIHT;

    public static Api getApi() { return Application.s_Application.api; }

    public static Random g_random = new Random();
    //user
    public static User g_user = null;
    public static String g_accessToken = null;

    public static boolean isLogin() {
        if(g_user!=null) {
            return true;
        }
        return false;
    }

    public static int getUserId() {
        return -1;
    }
}

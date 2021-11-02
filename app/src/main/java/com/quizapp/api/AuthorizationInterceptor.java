package com.quizapp.api;

import com.quizapp.common.Global;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request mainRequest = chain.request();
        Response mainResponse = null;
        if (Global.isLogin()) {
            Request.Builder builder = mainRequest.newBuilder().header("Authorization", getAuthorizationHeader()).method(mainRequest.method(), mainRequest.body());
            mainResponse = chain.proceed(builder.build());
        } else {
            mainResponse = chain.proceed(chain.request());
        }
        return mainResponse;
    }

    /**
     * this method is API implemetation specific
     * might not work with other APIs
     **/
    public String getAuthorizationHeader() {
        return "bearer "+ Global.g_accessToken;
    }
}

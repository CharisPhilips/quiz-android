package com.quizapp.api;

import com.quizapp.common.request.GetQuestionRequest;
import com.quizapp.common.request.LoginRequest;
import com.quizapp.common.request.MainRequest;
import com.quizapp.common.request.RegisterRequest;
import com.quizapp.common.request.VerifyQuestionRequest;
import com.quizapp.common.response.GetQuestionIDResponse;
import com.quizapp.common.response.GetQuestionResponse;
import com.quizapp.common.response.LoginResponse;
import com.quizapp.common.response.MainList;
import com.quizapp.common.response.RegisterResponse;
import com.quizapp.common.response.VerifyAnsResponse;

import kotlin.Metadata;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebApi {

    @GET("translation/answer/{questionId}/{direction}]/{userid}")
    Call<GetQuestionResponse> getAnswer(@Path("userid") int userid, @Path("questionId") int qId, @Path("direction") int dir);

    @POST("quizlist/get")
    Call<MainList> getMainList(@Body MainRequest request);

    @POST("translation/question")
    Call<GetQuestionResponse> getQuestion(@Body GetQuestionRequest request);

    @GET("quizlist/translationids/{sp2_id}/{userid}")
    Call<GetQuestionIDResponse> getQuestionIds(@Path("sp2_id") int sp2Id, @Path("userid") int userid);

    @POST("translation/checkanswer/{q_id}")
    Call<VerifyAnsResponse> verifyAns(@Path("q_id") int qId, @Body VerifyQuestionRequest request);

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/register")
    Call<RegisterResponse> register(@Body RegisterRequest request);

}

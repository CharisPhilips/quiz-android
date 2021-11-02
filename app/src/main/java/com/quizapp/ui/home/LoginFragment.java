package com.quizapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quizapp.Application;
import com.quizapp.R;
import com.quizapp.common.Global;
import com.quizapp.common.listener.ILoginListener;
import com.quizapp.common.listener.IProgressListener;
import com.quizapp.common.listener.IStartQuizListListener;
import com.quizapp.common.request.GetQuestionRequest;
import com.quizapp.common.request.LoginRequest;
import com.quizapp.common.request.VerifyQuestionRequest;
import com.quizapp.common.response.GetQuestionIDResponse;
import com.quizapp.common.response.GetQuestionResponse;
import com.quizapp.common.response.LoginResponse;
import com.quizapp.common.response.VerifyAnsResponse;
import com.quizapp.common.utils.ViewUtils;
import com.quizapp.ui.quiz.QuizBaseFragment;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.btn_login)
    Button btnLogin;

    protected IProgressListener progressBarListener = null;
    protected ILoginListener loginListener = null;

    public LoginFragment() {
    }

    public void setProgressBarListener(IProgressListener progressBarListener) { this.progressBarListener = progressBarListener; }
    public void setLoginListener(ILoginListener loginListener) { this.loginListener = loginListener; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_login, container, false));
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListeners();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {

    }

    protected void initListeners() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    postLogin();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postLogin() {
        this.progressBarListener.showProgress();
        try {
            LoginRequest request = new LoginRequest();
            request.setEmail(etEmail.getText().toString());
            request.setPassword(etPassword.getText().toString());
            Call<LoginResponse> callLogin = Global.getApi().getApiService().login(request);
            callLogin.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse loginResponse = response.body();
                    if (loginListener != null) {
                        loginListener.loginSuccess(loginResponse);
                    }
                    progressBarListener.hideProgress();
                    System.out.println(loginResponse);
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

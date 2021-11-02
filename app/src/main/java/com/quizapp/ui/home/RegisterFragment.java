package com.quizapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quizapp.R;
import com.quizapp.common.Global;
import com.quizapp.common.listener.IProgressListener;
import com.quizapp.common.listener.IRegisterListener;
import com.quizapp.common.request.RegisterRequest;
import com.quizapp.common.response.RegisterResponse;
import com.quizapp.common.utils.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    @BindView(R.id.et_nickname)
    EditText etNickName;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.et_confirmPassword)
    EditText etConfirmPassword;

    @BindView(R.id.btn_register)
    Button btnRegister;

    protected IProgressListener progressBarListener = null;
    protected IRegisterListener registerListener = null;

    public RegisterFragment() {
    }

    public void setProgressBarListener(IProgressListener progressBarListener) { this.progressBarListener = progressBarListener; }
    public void setRegisterListener(IRegisterListener registerListener) { this.registerListener = registerListener; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_register, container, false));
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
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (!etPassword.getText().toString().equals(etConfirmPassword.getText().toString())) {
                        ViewUtils.alertShow(RegisterFragment.this.getContext(), "Alert", "Please check password");
                        return;
                    }
                    postRegister();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void postRegister() {
        this.progressBarListener.showProgress();
        try {
            RegisterRequest request = new RegisterRequest();
            request.setNickname(etNickName.getText().toString());
            request.setEmail(etEmail.getText().toString());
            request.setPassword(etPassword.getText().toString());
            Call<RegisterResponse> callLogin = Global.getApi().getApiService().register(request);
            callLogin.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    RegisterResponse registerResponse = response.body();
                    if (registerListener != null) {
                        registerListener.registerSuccess(registerResponse);
                    }
                    progressBarListener.hideProgress();
                    System.out.println(registerResponse);
                }
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

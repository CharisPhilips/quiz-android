package com.quizapp.common.listener;

import com.quizapp.common.response.LoginResponse;

public interface ILoginListener {
    public void loginSuccess(LoginResponse loginResponse);
}

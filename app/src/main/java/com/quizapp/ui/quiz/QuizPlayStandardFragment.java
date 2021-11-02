package com.quizapp.ui.quiz;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.quizapp.R;
import com.quizapp.common.response.GetQuestionResponse;

import butterknife.ButterKnife;

public class QuizPlayStandardFragment extends QuizBaseFragment{

    public QuizPlayStandardFragment(int quizMainId) {
        super(MODE_STANDARD, quizMainId);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_quiz_play_standard, container, false));
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListeners();
        fetchInit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {
    }

    protected void initListeners() {
        super.initListeners();
    }

    @Override
    void onSuccessQuestionData(GetQuestionResponse questionData) {
        tvQuiz_word.setText(questionData.getVocabulary());
        etAnswer.setText("");
        tvAnswer.setText("");
    }
}
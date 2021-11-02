
package com.quizapp.ui.quiz;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.quizapp.Application;
import com.quizapp.R;
import com.quizapp.common.Global;
import com.quizapp.common.data.MainListItem;
import com.quizapp.common.listener.IStartQuizPlayListener;
import com.quizapp.common.request.MainRequest;
import com.quizapp.common.response.MainList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class QuizListFragment extends Fragment {

    @BindView(R.id.spinner_quiz_mode)
    public Spinner spinnerQuizMode;

    @BindView(R.id.spinner_quiz_list)
    public Spinner spinnerQuizList;

    @BindView(R.id.btn_start_quiz)
    public Button buttonStartQuiz;

    IStartQuizPlayListener startQuizPlayListener = null;

    List<String> quizMode = new ArrayList<String>();
    MainList quizList = new MainList();

    private Integer mSelectedQuizModeIdx = null;

    public void setEventListener(IStartQuizPlayListener startQuizPlayListener) { this.startQuizPlayListener = startQuizPlayListener; }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_quiz_list, container, false));
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListeners();
        fetchInitData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initView() {

        quizMode.add("Standard Mode");
        quizMode.add("Small Keyboard Mode");
        quizMode.add("1 ~ 4");

        quizMode.add(0, "Please select quiz mode.");

        if (this.getActivity() != null) {
            final ArrayAdapter<String> quizModeArrayAdapter = new ArrayAdapter<String>(this.getActivity(), R.layout.support_simple_spinner_dropdown_item, quizMode) {
                @Override
                public boolean isEnabled(int position){
                    if(position == 0) {
                        return false;
                    }
                    else {
                        return true;
                    }
                }
                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    TextView tv = (TextView) view;
                    if(position == 0){
                        // Set the hint text color gray
                        tv.setTextColor(Color.GRAY);
                    }
                    else {
                        tv.setTextColor(Color.BLACK);
                    }
                    return view;
                }
            };
            spinnerQuizMode.setAdapter(quizModeArrayAdapter);
        }

        buttonStartQuiz.setEnabled(false);
    }

    private void initListeners() {
        this.spinnerQuizMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(mSelectedQuizModeIdx == null || mSelectedQuizModeIdx != position) {
                    MainRequest request = new MainRequest();
                    request.setId(mSelectedQuizModeIdx);
                    request.setIs_public(true);
                    request.setUser_id(-1);

                    setQuizListener(request);
                }
                QuizListFragment.this.mSelectedQuizModeIdx = position;
                buttonStartQuiz.setEnabled(QuizListFragment.this.mSelectedQuizModeIdx != null && QuizListFragment.this.mSelectedQuizModeIdx != 0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        this.buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QuizListFragment.this.startQuizPlayListener!=null) {
                    QuizListFragment.this.startQuizPlayListener.onStartPlayQuiz(QuizListFragment.this.mSelectedQuizModeIdx - 1, quizList.get(spinnerQuizList.getSelectedItemPosition()));
                }
            }
        });
    }

    private void setQuizListener(MainRequest request) {
        try {
            Call<MainList> callMainList = Global.getApi().getApiService().getMainList(request);
            callMainList.enqueue(new Callback<MainList>() {
                @Override
                public void onResponse(Call<MainList> call, Response<MainList> response) {
                    quizList = response.body();
                    if (quizList != null && QuizListFragment.this.getActivity() != null) {
                        final ArrayAdapter<MainListItem> quizListArrayAdapter = new ArrayAdapter<MainListItem>(QuizListFragment.this.getActivity(), R.layout.support_simple_spinner_dropdown_item, quizList){
                            @Override
                            public boolean isEnabled(int position){
                                return true;
                            }
                            @Override
                            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                                View view = super.getDropDownView(position, convertView, parent);
                                TextView tv = (TextView) view;
                                tv.setTextColor(Color.BLACK);
                                return view;
                            }
                        };
                        spinnerQuizList.setAdapter(quizListArrayAdapter);
                    }
                }
                @Override
                public void onFailure(Call<MainList> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchInitData() {
    }

}
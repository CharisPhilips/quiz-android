package com.quizapp.ui.quiz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quizapp.R;
import com.quizapp.common.response.GetQuestionResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizPlay1_4Fragment extends QuizBaseFragment{

    @BindView(R.id.rv_list1_4)
    RecyclerView rvList_1_4;

    private int mSelectedPosition = -1;
    List1_4ItemAdapter adapter = null;

    public QuizPlay1_4Fragment(int quizMainId) {
        super(MODE_1_4, quizMainId);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_quiz_play_1_4, container, false));
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
        this.btnVerify = null;
    }

    protected void initListeners() {
        super.initListeners();
    }

    @Override
    void onSuccessQuestionData(GetQuestionResponse questionData) {
        tvQuiz_word.setText(questionData.getVocabulary());
        etAnswer.setText("");
        tvAnswer.setText("");

        List<String> questions = questionData.getQuestions();

        rvList_1_4.setVisibility(View.VISIBLE);
        adapter = new List1_4ItemAdapter(getContext(), questions);
        rvList_1_4.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvList_1_4.setHasFixedSize(true);
        rvList_1_4.setAdapter(adapter);
        mSelectedPosition = -1;
    }

    private void onClickList1_4(String label) {
        fetchCheckAnswer(label);
    }

    class List1_4ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        private LayoutInflater layoutInflater;
        List<String> data;

        public List1_4ItemAdapter(Context context, List<String> data) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_list1_4, parent, false);
            return new ListView1_4Holder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ListView1_4Holder) {
                ((ListView1_4Holder) holder).setText(this.data.get(position), position);
            }
        }

        @Override
        public int getItemCount() {
            if(data==null) {
                return 0;
            }
            return data.size();
        }
    }

    class ListView1_4Holder extends RecyclerView.ViewHolder {

        Button btnList1_4;
        String label;
        int position;
        public ListView1_4Holder(@NonNull View itemView) {
            super(itemView);
            this.btnList1_4 = itemView.findViewById(R.id.btn_list1_4);
            this.btnList1_4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    QuizPlay1_4Fragment.this.onClickList1_4(label);
                    mSelectedPosition = position;
                    adapter.notifyDataSetChanged();
                }
            });
        }

        public void setText(String text, int position) {
            this.label = text;
            this.btnList1_4.setText(this.label);
            this.position = position;
            if (this.position == mSelectedPosition) {
                btnList1_4.setBackgroundResource(R.drawable.shape_button_green);
            } else {
                btnList1_4.setBackgroundResource(R.drawable.shape_button_white);
            }
        }
    }
}
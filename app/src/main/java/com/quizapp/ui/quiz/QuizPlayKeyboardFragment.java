package com.quizapp.ui.quiz;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quizapp.R;
import com.quizapp.common.response.GetQuestionResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuizPlayKeyboardFragment extends QuizBaseFragment{

    @BindView(R.id.rv_keyboard)
    RecyclerView rvKeyboard;

    private LayoutInflater layoutInflater;

    public QuizPlayKeyboardFragment(int quizMainId) {
        super(MODE_KEYBOARD, quizMainId);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = (View) (inflater.inflate(R.layout.fragment_quiz_play_keyboard, container, false));
        ButterKnife.bind(this, root);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        String pattern = questionData.getPattern();
        int length = pattern.length();
        int cellSize = (int)Math.ceil(Math.sqrt(length));

        List<List<String>> lists = new ArrayList<List<String>>();
        List<String> row = null;
        for (int i = 0; i < length; i++) {
            if (i % cellSize == 0) {
                row = new ArrayList<String>();
                lists.add(row);
            }
            row.add(pattern.substring(i, i + 1));
        }

        rvKeyboard.setVisibility(View.VISIBLE);
        RowItemAdapter adapter = new RowItemAdapter(getContext(), lists);
        rvKeyboard.setLayoutManager(new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false));
        rvKeyboard.setHasFixedSize(true);
        rvKeyboard.setAdapter(adapter);
    }

    public void onClickKeyboard(String label, boolean appendOrRemove) {
        String orgAnswer = this.etAnswer.getText().toString();
        if (appendOrRemove) {
            String newText = orgAnswer + label;
            this.etAnswer.setText(newText);
        } else {
            int indexOf = orgAnswer.lastIndexOf(label);
            if (indexOf != -1) {
                String newText = orgAnswer.substring(0, indexOf) + orgAnswer.substring(indexOf + label.length());
                this.etAnswer.setText(newText);
            }
        }
    }

    class RowItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        Context context;
        private LayoutInflater layoutInflater;
        List<List<String>> data;

        public RowItemAdapter(Context context, List<List<String>> data) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
        }

        public void setData(List<List<String>> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_keyboard_row, parent, false);
            return new RowViewHolder(view, context);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof RowViewHolder) {
                ((RowViewHolder) holder).setData(this.data.get(position));
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

    class RowViewHolder extends RecyclerView.ViewHolder {

        Context context;
        List<String> data;
        RecyclerView rvColumns;

        public RowViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            this.rvColumns = itemView.findViewById(R.id.rv_keyboard_columns);
        }

        public void setData(List<String> data) {
            this.data = data;
            ColumnItemAdapter adapter = new ColumnItemAdapter(context, data);
            this.rvColumns.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            this.rvColumns.setHasFixedSize(true);
            this.rvColumns.setAdapter(adapter);
        }
    }

    class ColumnItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        Context context;
        private LayoutInflater layoutInflater;
        List<String> data;

        public ColumnItemAdapter(Context context, List<String> data) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = layoutInflater.inflate(R.layout.item_keyboard_col, parent, false);
            return new ColumnViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ColumnViewHolder) {
                ((ColumnViewHolder) holder).setLabel(data.get(position));
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

    class ColumnViewHolder extends RecyclerView.ViewHolder {

        Button btnkeyboard;
        String label;
        boolean isChecked = false;

        public ColumnViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btnkeyboard = itemView.findViewById(R.id.btn_keyboard);
            this.btnkeyboard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    QuizPlayKeyboardFragment.this.etAnswer.setText(QuizPlayKeyboardFragment.this.etAnswer.getText().toString() + label);
                    isChecked = !isChecked;
                    if (isChecked) {
                        btnkeyboard.setBackgroundResource(R.drawable.shape_button_green);
                    } else {
                        btnkeyboard.setBackgroundResource(R.drawable.shape_button_blue);
                    }
                    QuizPlayKeyboardFragment.this.onClickKeyboard(label, isChecked);
                }
            });
        }

        public void setLabel(String text) {
            this.label = text;
            this.btnkeyboard.setText(this.label);
        }
    }
}
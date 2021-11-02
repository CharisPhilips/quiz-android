package com.quizapp.ui.quiz;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.quizapp.R;
import com.quizapp.common.Global;
import com.quizapp.common.listener.IProgressListener;
import com.quizapp.common.listener.IStartQuizListListener;
import com.quizapp.common.request.GetQuestionRequest;
import com.quizapp.common.request.VerifyQuestionRequest;
import com.quizapp.common.response.GetQuestionIDResponse;
import com.quizapp.common.response.GetQuestionResponse;
import com.quizapp.common.response.VerifyAnsResponse;

import java.util.List;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class QuizBaseFragment extends Fragment {

    public static final int MODE_STANDARD = 0;
    public static final int MODE_KEYBOARD = 1;
    public static final int MODE_1_4 = 2;

    @BindView(R.id.quiz_list_title)
    TextView tvTitle;

    @BindView(R.id.quiz_list_subtitle)
    TextView tvSubtitle;

    @BindView(R.id.quiz_word)
    TextView tvQuiz_word;

    @BindView(R.id.et_answer)
    TextView etAnswer;

    @BindView(R.id.iv_swap)
    ImageView ivSwap;

    @BindView(R.id.tv_error)
    TextView tvError;

    @BindView(R.id.tv_answer)
    TextView tvAnswer;

    @BindView(R.id.btn_verify)
    Button btnVerify;

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_skip)
    Button btnSkip;

    @BindView(R.id.btn_finish)
    Button btnFinish;

    @BindView(R.id.btn_show_answer)
    Button btnShowAnswer;

    @BindView(R.id.btn_check_status)
    Button btnCheckStatus;

    @BindView(R.id.btn_again)
    Button btnAgain;

    @BindView(R.id.btn_back)
    Button btnBack;

    protected GetQuestionIDResponse mQuestionIds = null;
    protected List<Integer> mQuestionLeftIds = null;
    protected GetQuestionRequest mRequest = null;
    protected IProgressListener progressBarListener = null;
    protected IStartQuizListListener startQuizListListener = null;

    protected int mQuizPageMode = -1;
    protected int mQuizListId = -1;
    protected boolean mQuizDirection = true;

    public QuizBaseFragment(int quizMode, int quizMainId) {
        this.mQuizPageMode = quizMode;
        this.mQuizListId = quizMainId;
    }

    public void setProgressBarListener(IProgressListener progressBarListener) { this.progressBarListener = progressBarListener; }
    public void setEventListener(IStartQuizListListener startQuizListListener) { this.startQuizListListener = startQuizListListener; }

    protected void initListeners() {

        if (this.btnVerify != null) {
            this.btnVerify.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fetchCheckAnswer(etAnswer.getText().toString());
                }
            });
        }

        this.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.GONE);
                btnSkip.setVisibility(View.GONE);
                if(btnVerify != null) {
                    btnVerify.setVisibility(View.VISIBLE);
                }
                fetchQuestionData();
            }
        });

        this.btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNext.setVisibility(View.GONE);
                btnSkip.setVisibility(View.GONE);
                tvError.setVisibility(View.GONE);
                if(btnVerify != null) {
                    btnVerify.setVisibility(View.VISIBLE);
                }
                fetchQuestionData();
            }
        });

        this.btnShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchShowAnswer();
            }
        });

        this.ivSwap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuizBaseFragment.this.mQuizDirection = !QuizBaseFragment.this.mQuizDirection;
                QuizBaseFragment.this.fetchInit();
            }
        });

        this.btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnVerify != null) {
                    QuizBaseFragment.this.btnVerify.setVisibility(View.GONE);
                }

                QuizBaseFragment.this.btnShowAnswer.setVisibility(View.GONE);
                QuizBaseFragment.this.btnFinish.setVisibility(View.GONE);

                QuizBaseFragment.this.btnCheckStatus.setVisibility(View.VISIBLE);
                QuizBaseFragment.this.btnAgain.setVisibility(View.VISIBLE);
            }
        });

        this.btnCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        this.btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnVerify != null) {
                    QuizBaseFragment.this.btnVerify.setVisibility(View.VISIBLE);
                }

                QuizBaseFragment.this.btnShowAnswer.setVisibility(View.VISIBLE);

                QuizBaseFragment.this.btnCheckStatus.setVisibility(View.GONE);
                QuizBaseFragment.this.btnAgain.setVisibility(View.GONE);

                QuizBaseFragment.this.etAnswer.setText("");
                QuizBaseFragment.this.tvError.setVisibility(View.GONE);

                fetchInit();
            }
        });

        this.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startQuizListListener != null) {
                    startQuizListListener.onStartPlayList();
                }
            }
        });
    }

    protected void fetchInit() {//int quizlistId, int pageMode, boolean direction
        this.progressBarListener.showProgress();
        try {
            GetQuestionRequest request = new GetQuestionRequest();
            request.setQuizlistId(mQuizListId);
            Call<GetQuestionIDResponse> callMainList = Global.getApi().getApiService().getQuestionIds(mQuizListId, Global.getUserId());
            callMainList.enqueue(new Callback<GetQuestionIDResponse>() {
                @Override
                public void onResponse(Call<GetQuestionIDResponse> call, Response<GetQuestionIDResponse> response) {
                    QuizBaseFragment.this.progressBarListener.hideProgress();
                    mQuestionIds = response.body();
                    mQuestionLeftIds = (List<Integer>) mQuestionIds.clone();
                    fetchQuestionData();
                }
                @Override
                public void onFailure(Call<GetQuestionIDResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fetchQuestionData() {
        try {

            int nRandomSelectIndex = Global.g_random.nextInt(mQuestionLeftIds.size());

            mRequest = new GetQuestionRequest();
            mRequest.setId(mQuestionLeftIds.get(nRandomSelectIndex));//random select
            mRequest.setQuizlistId(mQuizListId);
            mRequest.setQuizMode(mQuizPageMode);
            mRequest.setUserId(Global.getUserId());
            mRequest.setDirection(mQuizDirection);

            mQuestionLeftIds.remove(nRandomSelectIndex);

            QuizBaseFragment.this.progressBarListener.showProgress();
            Call<GetQuestionResponse> callGetQuestion = Global.getApi().getApiService().getQuestion(mRequest);
            callGetQuestion.enqueue(new Callback<GetQuestionResponse>() {
                @Override
                public void onResponse(Call<GetQuestionResponse> call, Response<GetQuestionResponse> response) {
                    GetQuestionResponse question = response.body();
                    onSuccessQuestionData(question);
                    QuizBaseFragment.this.progressBarListener.hideProgress();
                }

                @Override
                public void onFailure(Call<GetQuestionResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fetchCheckAnswer(String editAnswer) {
        //int qId, VerifyQuestionRequest request
        VerifyQuestionRequest verifyRequest = new VerifyQuestionRequest();
        verifyRequest.setQuizlistId(mRequest.getQuizlistId());
        verifyRequest.setUserId(Global.getUserId());
        verifyRequest.setVocabulary(editAnswer);
        verifyRequest.setDirection(mQuizDirection);

        try {
            QuizBaseFragment.this.progressBarListener.showProgress();
            Call<VerifyAnsResponse> callGetQuestion = Global.getApi().getApiService().verifyAns(mRequest.getId(), verifyRequest);
            callGetQuestion.enqueue(new Callback<VerifyAnsResponse>() {
                @Override
                public void onResponse(Call<VerifyAnsResponse> call, Response<VerifyAnsResponse> response) {
                    VerifyAnsResponse checkResult = response.body();
                    if (checkResult.isVocabulary()) {
                        onCheckAnswerSuccess();
                    } else {
                        onCheckAnswerFailed();
                    }
                    QuizBaseFragment.this.progressBarListener.hideProgress();
                }
                @Override
                public void onFailure(Call<VerifyAnsResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void fetchShowAnswer() {
        try {
            Call<GetQuestionResponse> callGetQuestion = Global.getApi().getApiService().getAnswer(Global.getUserId(), mRequest.getId(), mQuizDirection ? 1: 0);
            callGetQuestion.enqueue(new Callback<GetQuestionResponse>() {
                @Override
                public void onResponse(Call<GetQuestionResponse> call, Response<GetQuestionResponse> response) {
                    GetQuestionResponse question = response.body();
                    onSetTextAnswer(question.getVocabulary());
                }

                @Override
                public void onFailure(Call<GetQuestionResponse> call, Throwable error) {
                    System.out.println("error");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void onSetTextAnswer(String text) {
        tvAnswer.setText(text);
    }

    void onCheckAnswerSuccess() {
        tvError.setVisibility(View.GONE);

        if (this.btnVerify != null) {
            btnVerify.setVisibility(View.GONE);
        }
        if (mQuestionLeftIds.size() > 0) {
            btnNext.setVisibility(View.VISIBLE);
            btnSkip.setVisibility(View.GONE);
        } else {
            btnNext.setVisibility(View.GONE);
            btnFinish.setVisibility(View.VISIBLE);
        }
    }

    void onCheckAnswerFailed() {
        tvError.setVisibility(View.VISIBLE);

        if (mQuestionLeftIds.size() > 0) {
            btnSkip.setVisibility(View.VISIBLE);
        } else {
            btnFinish.setVisibility(View.VISIBLE);
        }
    }

    abstract void onSuccessQuestionData(GetQuestionResponse questionData);
}

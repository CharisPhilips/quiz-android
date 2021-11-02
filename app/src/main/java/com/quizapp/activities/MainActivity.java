package com.quizapp.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.quizapp.Application;
import com.quizapp.R;
import com.quizapp.common.Constants;
import com.quizapp.common.Global;
import com.quizapp.common.data.MainListItem;
import com.quizapp.common.listener.ILoginListener;
import com.quizapp.common.listener.IProgressListener;
import com.quizapp.common.listener.IRegisterListener;
import com.quizapp.common.listener.IStartQuizListListener;
import com.quizapp.common.listener.IStartQuizPlayListener;
import com.quizapp.common.response.LoginResponse;
import com.quizapp.common.response.RegisterResponse;
import com.quizapp.common.utils.ViewUtils;
import com.quizapp.ui.home.LoginFragment;
import com.quizapp.ui.home.RegisterFragment;
import com.quizapp.ui.quiz.QuizBaseFragment;
import com.quizapp.ui.quiz.QuizListFragment;
import com.quizapp.ui.quiz.QuizPlay1_4Fragment;
import com.quizapp.ui.quiz.QuizPlayKeyboardFragment;
import com.quizapp.ui.quiz.QuizPlayStandardFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.
        OnNavigationItemSelectedListener,
        IStartQuizPlayListener,
        IStartQuizListListener,
        IProgressListener,
        ILoginListener,
        IRegisterListener
{

    public static final int QUIZ_PAGE_LIST = 0;
    public static final int QUIZ_PAGE_PLAY = 1;

    private View fragment;
    private NavController navController;
    private BottomNavigationView bottomNavigationView;

    //child bottom bars
    private QuizListFragment childFragQuizList = null;

    private QuizBaseFragment childFragQuizPlay = null;

    private LoginFragment childFragLogin = null;

    private RegisterFragment childFragRegister = null;

    //double return key
    private boolean doubleBackToExitPressedOnce = false;

    private int mQuizPageType = QUIZ_PAGE_LIST;   //0: new 1: update 2: btnSearch 3: view

    private int mQuizPageMode = -1;
    private MainListItem mQuizMainData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //init views
        initViews();
        // initializing the listeners
        initListeners();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void initViews() {

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        Global.DISPLAY_HEIGHT = displayMetrics.heightPixels;
        Global.DISPLAY_WIDTH = displayMetrics.widthPixels;

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            Global.STATUSBAR_HEGIHT = getResources().getDimensionPixelSize(resourceId);
        }
        TypedValue tv = new TypedValue();
        if (getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            Global.ACTIONBAR_HEGIHT = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        this.bottomNavigationView = findViewById(R.id.nav_bottomMenuView);
        if (Global.isLogin()) {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_logined);
        } else {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_nologined);
        }

        this.fragment = (View) findViewById(R.id.nav_host_fragment);
        this.navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(this.bottomNavigationView, this.navController);
        navigateControl(R.id.navigation_quiz);
    }

    private void initListeners() {
        this.bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        ViewUtils.showToast(this, getResources().getString(R.string.message_back_again_to_exit));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, Constants.DOUBLE_BACKKEY_TIMEOUT);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        navigateControl(item.getItemId());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (Global.isLogin()) {
//            getMenuInflater().inflate(R.menu.top_nav_menu_logined, menu);
        } else {
//            getMenuInflater().inflate(R.menu.top_nav_menu_nologined, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        navigateControl(item.getItemId());
        return false;
    }

    private void navigateControl(int nResId) {
        Fragment fragment = null;
        switch (nResId) {
            case R.id.navigation_quiz:
                if (mQuizPageType == QUIZ_PAGE_LIST) {
                    this.childFragQuizList = new QuizListFragment();
                    this.childFragQuizList.setEventListener(this);
                    fragment = this.childFragQuizList;
                    loadFragment(fragment);
                } else if (mQuizPageType == QUIZ_PAGE_PLAY) {
                    switch(this.mQuizPageMode) {
                        case QuizBaseFragment.MODE_STANDARD://standard mode
                            this.childFragQuizPlay = new QuizPlayStandardFragment(this.mQuizMainData.getId());
                            this.childFragQuizPlay.setEventListener(this);
                            this.childFragQuizPlay.setProgressBarListener(this);
                            fragment = this.childFragQuizPlay;
                            break;
                        case QuizBaseFragment.MODE_KEYBOARD://keyboard mode
                            this.childFragQuizPlay = new QuizPlayKeyboardFragment(this.mQuizMainData.getId());
                            this.childFragQuizPlay.setEventListener(this);
                            this.childFragQuizPlay.setProgressBarListener(this);
                            fragment = this.childFragQuizPlay;
                            break;
                        case QuizBaseFragment.MODE_1_4:
                            if (mQuizMainData.getTranslationCount() < 4) {
                                ViewUtils.alertShow(this, "Please add more translations to this list", "1 of 4 requires at least 2 translations and you have only 1\n");
                            } else {
                                this.childFragQuizPlay = new QuizPlay1_4Fragment(this.mQuizMainData.getId());
                                this.childFragQuizPlay.setEventListener(this);
                                this.childFragQuizPlay.setProgressBarListener(this);
                                fragment = this.childFragQuizPlay;
                            }
                            break;
                    }
                    loadFragment(fragment);
                }
                break;
            case R.id.navigation_login:
                this.childFragLogin = new LoginFragment();
                this.childFragLogin.setProgressBarListener(this);
                this.childFragLogin.setLoginListener(this);
                fragment = this.childFragLogin;
                loadFragment(fragment);
                break;
            case R.id.navigation_register:
                this.childFragRegister = new RegisterFragment();
                this.childFragRegister.setProgressBarListener(this);
                this.childFragRegister.setRegisterListener(this);
                fragment = this.childFragRegister;
                loadFragment(fragment);
                break;
            case R.id.navigation_logout:
                Global.g_user = null;
                Global.g_accessToken = null;
                Application.s_Application.removeSharedPreferenceEmailData();
                invalidateNavBars();
                navigateControl(R.id.navigation_quiz);
                break;
            default:
        }
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    //IStartQuizPlayListener
    @Override
    public void onStartPlayQuiz(int mode, MainListItem item) {
        this.mQuizPageType = QUIZ_PAGE_PLAY;
        this.mQuizPageMode = mode;
        this.mQuizMainData = item;
        navigateControl(R.id.navigation_quiz);
    }

    @Override
    public void onStartPlayList() {
        this.mQuizPageType = QUIZ_PAGE_LIST;
        navigateControl(R.id.navigation_quiz);
    }

    @Override
    public void showProgress() {
        View container = findViewById(R.id.progressBar);
        container.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgress() {
        View container = findViewById(R.id.progressBar);
        container.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void loginSuccess(LoginResponse loginResponse) {
        if (loginResponse != null) {
            Global.g_user = loginResponse.getUser();
            Global.g_accessToken = loginResponse.getAccessToken();
            Application.s_Application.writeSharedPreferenceEmailData(loginResponse.getUser().getEmail(), "", loginResponse.getAccessToken());
            invalidateNavBars();
            navigateControl(R.id.navigation_quiz);
        }
    }

    @Override
    public void registerSuccess(RegisterResponse registerResponse) {
        if (registerResponse != null) {
            Global.g_user = registerResponse.getUser();
            Global.g_accessToken = registerResponse.getAccessToken();
            Application.s_Application.writeSharedPreferenceEmailData(registerResponse.getUser().getEmail(), "", registerResponse.getAccessToken());
            invalidateNavBars();
            navigateControl(R.id.navigation_quiz);
        }
    }

    private void invalidateNavBars() {
        if (Global.isLogin()) {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_logined);
        } else {
            this.bottomNavigationView.getMenu().clear();
            this.bottomNavigationView.inflateMenu(R.menu.bottom_nav_menu_nologined);
        }
        invalidateOptionsMenu();
    }
}
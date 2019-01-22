package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.Switch;

import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.viewmodel.GameViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.ViewModelFactory;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GameActivity extends BaseActivity {
    public static final String KEY_RID = "KEY_RID";
    public static final String KEY_AID = "KEY_AID";

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private int currentIndex = 0;
    private GameViewModel viewModel;
    private int aid;
    private int rid;
    private List<Question> questions;
    private List<Answer> answers = new ArrayList<>();
    private int totalScore = 0;

    public static void startActivity(Context context, int rid, int aid) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(KEY_AID, aid);
        intent.putExtra(KEY_RID, rid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(GameViewModel.class);
        viewModel.getQuestions().observe(this, list -> {
            questions = list;
            MainFragment mainFragment = MainFragment.newInstance();
            startFragment(mainFragment, MainFragment.class.getSimpleName(), false);
        });

        aid = getIntent().getIntExtra(KEY_AID, 0);
        rid = getIntent().getIntExtra(KEY_RID, 0);

        viewModel.getQuestion(aid);
    }

    void goTypeFragment() {
        if (questions.size() > currentIndex) {
            switch (questions.get(currentIndex).getAtype()) {
                case 1:
                    TypeOneFragment fragment = TypeOneFragment.newInstance();
                    startFragment(fragment, TypeOneFragment.class.getSimpleName(), true);
                    break;
                case 2:
                    TypeTwoFragment fragment2 = TypeTwoFragment.newInstance();
                    startFragment(fragment2, TypeTwoFragment.class.getSimpleName(), true);
                    break;
                case 3:
                    TypeThreeFragment fragment3 = TypeThreeFragment.newInstance();
                    startFragment(fragment3, TypeThreeFragment.class.getSimpleName(), true);
                    break;
                case 4:
                    TypeFourFragment fragment4 = TypeFourFragment.newInstance();
                    startFragment(fragment4, TypeFourFragment.class.getSimpleName(), true);
                    break;
            }
        }
    }

    private void startFragment(BaseFragment fragment, String tag, Boolean isToBack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isToBack) {
            ft.addToBackStack(null);
        }
        ft.add(R.id.frame_layout, fragment, tag);
        ft.commit();
    }

    int getCurrentIndex() {
        return currentIndex;
    }

    String getLevelTitle() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab1();
        }
        return "";
    }

    String getLevelBtnLab() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab3();
        }
        return "";
    }

    String getLevelTitle2() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab2();
        }
        return "";
    }

    GameViewModel getViewModel() {
        return viewModel;
    }

    Question getCurrentQuestion() {
        if (questions.size() > currentIndex) {
            return questions.get(currentIndex);
        }
        return null;
    }

    void addAnswer(String title, String ans, String img, int score){
        Answer answer = new Answer();
        answer.setRid(rid);
        answer.setTitle(title);
        answer.setAnswer(ans);
        answer.setImg(img);

        answers.add(answer);
        totalScore += score;
        currentIndex += 1;
        if (currentIndex < questions.size()) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName());
            if (fragment != null) {
                ((MainFragment)fragment).replace();
            }
        } else {
            FinishActivity.startActivity(this);
        }
    }

    public enum Level {
        L1("第一關", "第一題", "第一關問題"),
        L2("第二關", "第二題", "第二關問題"),
        L3("第三關", "第三題", "第三關問題"),
        L4("第四關", "第四題", "第四關問題"),
        L5("第五關", "第五題", "第五關問題"),
        L6("第六關", "第六題", "第六關問題"),
        L7("第七關", "第七題", "第七關問題"),
        L8("第八關", "第八題", "第八關問題");

        Level(String s, String s2, String s3) {
            this.s = s;
            this.s2 = s2;
            this.s3 = s3;
        }

        private String s;
        private String s2;
        private String s3;

        public static Level getLevel(int i) {
            switch (i) {
                case 0:
                    return L1;
                case 1:
                    return L2;
                case 2:
                    return L3;
                case 3:
                    return L4;
                case 4:
                    return L5;
                case 5:
                    return L6;
                case 6:
                    return L7;
                case 7:
                    return L8;
                default:
                    return null;
            }

        }

        public String getLab1() {
            return this.s;
        }

        public String getLab2() {
            return this.s2;
        }

        public String getLab3() {
            return this.s3;
        }
    }
}

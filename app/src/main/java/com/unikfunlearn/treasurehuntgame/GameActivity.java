package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.viewmodel.GameViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.MainViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.ViewModelFactory;

import java.util.List;

import androidx.annotation.Nullable;
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

    private void startFragment(BaseFragment fragment, String tag, Boolean isToBack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isToBack) {
            ft.addToBackStack(null);
        }
        ft.add(R.id.frame_layout, fragment, tag);
        ft.commit();
    }

    public enum Level {
        L1("第一關", "第一題"),
        L2("第二關", "第二題"),
        L3("第三關", "第三題"),
        L4("第四關", "第四題"),
        L5("第五關", "第五題"),
        L6("第六關", "第六題"),
        L7("第七關", "第七題"),
        L8("第八關", "第八題");

        private Level(String s, String s2) {
            this.s = s;
            this.s2 = s2;
        }

        private String s;
        private String s2;

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
    }
}

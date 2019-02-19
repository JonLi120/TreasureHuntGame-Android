package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.SchoolDao;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.choice_school_selector)
    TextView choiceSchoolSelector;
    @BindView(R.id.choice_game_selector)
    TextView choiceGameSelector;
    @BindView(R.id.confirm_btn)
    Button confirmBtn;
    @BindView(R.id.school_spinner)
    AppCompatSpinner schoolSpinner;
    @BindView(R.id.game_spinner)
    AppCompatSpinner gameSpinner;

    private List<School> schools;
    private List<Game> games;

    private int selectedSCID = -1;
    private int selectedAID = -1;
    private GameDao gameDao;
    private SchoolDao schoolDao;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);

        gameDao = db.gameDao();
        schoolDao = db.schoolDao();

        schoolSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (schools != null && schools.size() != 0) {
                    choiceSchoolSelector.setText(schools.get(i).getName());
                    choiceGameSelector.setText("");
                    selectedSCID = schools.get(i).getSCID();
                    selectedAID = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        gameSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (games != null && games.size() != 0) {
                    choiceGameSelector.setText(games.get(i).getTitle());
                    selectedAID = games.get(i).getAID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    @OnClick({R.id.choice_school_selector, R.id.choice_game_selector, R.id.back_btn, R.id.confirm_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.choice_school_selector:
                schools = schoolDao.getSchoolAll();

                List<String> list = new ArrayList<>();
                for (School item : schools) {
                    list.add(item.getName());
                }

                ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                schoolSpinner.setAdapter(adapter);
                schoolSpinner.setDropDownWidth(choiceSchoolSelector.getMeasuredWidth());
                schoolSpinner.performClick();
                break;
            case R.id.choice_game_selector:
                games = gameDao.getGameBySCID(selectedSCID);

                List<String> list1 = new ArrayList<>();
                for (Game item : games) {
                    list1.add(item.getTitle());
                }

                ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list1);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                gameSpinner.setAdapter(adapter1);
                gameSpinner.setDropDownWidth(choiceGameSelector.getMeasuredWidth());
                gameSpinner.performClick();

                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.confirm_btn:
                if (selectedSCID == -1) {
                    Toast.makeText(this, "請選擇選校", Toast.LENGTH_LONG).show();
                } else if (selectedAID == -1){
                    Toast.makeText(this, "請選擇遊戲", Toast.LENGTH_LONG).show();
                } else {
                    UserActivity.startActivity(this, selectedSCID, selectedAID);
                }
                break;
        }
    }

}

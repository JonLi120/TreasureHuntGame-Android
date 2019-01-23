package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.repo.local.AppDatabase;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class FinishActivity extends BaseActivity {
    public static final String AID = "AID";

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.finish_img)
    ImageView finishImg;
    @BindView(R.id.main_lab)
    TextView mainLab;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.send_btn)
    Button sendBtn;
    @BindView(R.id.return_btn)
    Button returnBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;

    public static void startActivity(Context context, int aid) {
        Intent intent = new Intent(context, FinishActivity.class);
        intent.putExtra(AID, aid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        ButterKnife.bind(this);

        int aid = getIntent().getIntExtra(AID, 0);
        GameDao gameDao = AppDatabase.getInstance(this).gameDao();

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);
        Glide.with(this).load(R.drawable.ic_finish).into(finishImg);

        List<Game> list = gameDao.getGameByAID(aid);
        if (list != null && list.size() != 0) {
            Game game = list.get(0);
            String html = String.format(HTMLFROMT, game.getNote());
            webview.loadData(html, "text/html", null);
        }
    }

    @OnClick({R.id.send_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.send_btn:
                goHome();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goHome();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void goHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}

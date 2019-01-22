package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;
import com.unikfunlearn.treasurehuntgame.viewmodel.MainViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.ViewModelFactory;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class DescriptionActivity extends BaseActivity {

    public static final String KEY_RID = "KEY_RID";
    public static final String KEY_AID = "KEY_AID";

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.description_lab)
    TextView descriptionLab;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.begin_btn)
    Button beginBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;

    private MainViewModel viewModel;
    private Game game;
    private int rid;
    private int aid;

    public static void startActivity(Context context, int rid, int aid) {
        Intent intent = new Intent(context, DescriptionActivity.class);
        intent.putExtra(KEY_RID, rid);
        intent.putExtra(KEY_AID, aid);

        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);
        viewModel.getGames().observe(this, list ->{
            if (list.size() > 0) {
                game = list.get(0);
                String html = String.format(HTMLFROMT, game.getContent());
                webview.loadData(html, "text/html", null);
            }
        });

        rid = getIntent().getIntExtra(KEY_RID, 0);
        aid = getIntent().getIntExtra(KEY_AID, 0);

        viewModel.getGame(aid);

    }

    @OnClick({R.id.back_btn, R.id.begin_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.begin_btn:
                GameActivity.startActivity(this, rid, aid);
                break;
        }
    }
}

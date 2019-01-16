package com.unikfunlearn.treasurehuntgame;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.viewmodel.MainViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.ViewModelFactory;

import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.download_btn)
    Button downloadBtn;
    @BindView(R.id.upload_btn)
    Button uploadBtn;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.bg_img)
    ImageView bgImg;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);

        Glide.with(this).load(R.drawable.bg_launch).into(bgImg);
        Glide.with(this).load(R.drawable.ic_logo).into(img);
    }

    @OnClick({R.id.download_btn, R.id.upload_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.download_btn:
                viewModel.download();
                break;
            case R.id.upload_btn:
                break;
        }
    }
}

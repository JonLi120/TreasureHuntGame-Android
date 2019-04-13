package com.unikfunlearn.treasurehuntgame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.version_lab)
    TextView versionLab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Glide.with(this).load(R.drawable.bg_launch).into(bgImg);
        Glide.with(this).load(R.drawable.ic_logo).into(img);

        String ver = "0.0";
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), 0);
            ver = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionLab.setText("v " + ver);
    }

    @OnClick({R.id.setting, R.id.login_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.setting:
                SettingActivity.startActivity(this);
                break;
            case R.id.login_btn:
                LoginActivity.startActivity(this);
                break;
        }
    }
}

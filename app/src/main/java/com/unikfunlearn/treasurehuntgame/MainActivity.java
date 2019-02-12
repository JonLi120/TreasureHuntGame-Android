package com.unikfunlearn.treasurehuntgame;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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

    private ProgressDialog dialog;
    private ConnectivityManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(MainViewModel.class);

        viewModel.getLoading().observe(this, isLoading->{
            if (isLoading) {
                dialog.show();
            } else {
                dialog.dismiss();
            }
        });

        viewModel.getUploadStatus().observe(this, status ->{
            if (status) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("資料已經上傳成功");
                builder.setPositiveButton("確定", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                });
                builder.create().show();
            }
        });

        Glide.with(this).load(R.drawable.bg_launch).into(bgImg);
        Glide.with(this).load(R.drawable.ic_logo).into(img);

        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @OnClick({R.id.download_btn, R.id.upload_btn, R.id.login_btn})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.download_btn:
                if (isFastClick()) {
                    if (manager.getActiveNetworkInfo()!= null && manager.getActiveNetworkInfo().isConnected()) {
                        viewModel.download();
                    } else {
                        Toast.makeText(this, "請檢察網路是否連線", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.upload_btn:
                if (isFastClick()) {
                    if (manager.getActiveNetworkInfo()!= null && manager.getActiveNetworkInfo().isConnected()) {
                        viewModel.upload();
                    } else {
                        Toast.makeText(this, "請檢察網路是否連線", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.login_btn:
                LoginActivity.startActivity(this);
                break;
        }
    }

    private static long lastClickTime = 0;
    private static int spaceTime = 1500;

    private boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        boolean isAllowClick;
        if (currentTime - lastClickTime > spaceTime) {
            isAllowClick = true;
        } else {
            isAllowClick = false;
        }
        lastClickTime = currentTime;
        return isAllowClick;
    }
}

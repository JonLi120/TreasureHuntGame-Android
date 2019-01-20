package com.unikfunlearn.treasurehuntgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends BaseFragment {

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_lab)
    TextView mainLab;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.qus_btn)
    Button qusBtn;
    @BindView(R.id.found_btn)
    Button foundBtn;
    @BindView(R.id.return_btn)
    Button returnBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.guideline2)
    Guideline guideline2;
    private Unbinder unbinder;

    public static MainFragment newInstance() {
        Bundle args = new Bundle();

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder = ButterKnife.bind(this, view);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);
        return view;
    }

    @OnClick({R.id.back_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                mActivity.finish();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

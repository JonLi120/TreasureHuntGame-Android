package com.unikfunlearn.treasurehuntgame;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.viewmodel.GameViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class MainFragment extends BaseFragment {

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.main_lab)
    TextView mainLab;
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

    private Unbinder unbinder;
    private GameActivity activity;
    private Question question;

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

        activity = (GameActivity) mActivity;

        setTitle();
        setBtnLab();
        setQuestion();

        activity.setCallback((id, rssi) -> {
            if (id == Integer.parseInt(question.getBluesn())) {
                if (qusBtn != null) {
                    qusBtn.setEnabled(true);
                }
            }
        });

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            boolean status = bluetoothAdapter.enable();

            if (!status) {

            }
        }

        return view;
    }

    private void setTitle() {
        String str = activity.getLevelTitle();
        title.setText(str);
        mainLab.setText(str);
    }

    private void setBtnLab() {
        String str = activity.getLevelBtnLab();
        qusBtn.setText(str != null? str : "回答問題");
    }

    private void setQuestion() {
        question = activity.getCurrentQuestion();
        if (question != null) {
            activity.startBeacon();
            String html = String.format(HTMLFROMT, question.getTitle());
            webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
        qusBtn.setEnabled(false);
    }

    @OnClick({R.id.back_btn, R.id.return_btn, R.id.qus_btn, R.id.found_btn, R.id.back_lab})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                activity.finish();
                break;
            case R.id.return_btn:
                activity.finish();
                break;
            case R.id.back_lab:
                activity.finish();
                break;
            case R.id.qus_btn:
                activity.goTypeFragment();
                break;
            case R.id.found_btn:
                if (getFragmentManager().findFragmentByTag(HintDialog.class.getSimpleName()) == null) {
                    question = activity.getCurrentQuestion();
                    if (question != null) {
                        HintDialog dialog = HintDialog.newInstance(question.getHit(), question.isSkip());
                        dialog.setCallback(() -> activity.addAnswer(question.getTitle(), "", "", 0));
                        dialog.show(getFragmentManager(), HintDialog.class.getSimpleName());
                    }
                }
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    void replace() {
        setTitle();
        setBtnLab();
        setQuestion();
    }
}

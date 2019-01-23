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
import com.unikfunlearn.treasurehuntgame.models.tables.Question;

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

public class TypeThreeFragment extends BaseFragment {

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
    @BindView(R.id.ans_btn)
    Button ansBtn;
    @BindView(R.id.return_btn)
    Button returnBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;
    @BindView(R.id.guideline)
    Guideline guideline;
    @BindView(R.id.guideline2)
    Guideline guideline2;
    private Unbinder unbinder;
    private GameActivity activity;
    private Question question;

    static TypeThreeFragment newInstance() {
        Bundle args = new Bundle();

        TypeThreeFragment fragment = new TypeThreeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_three, container, false);
        unbinder = ButterKnife.bind(this, view);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);

        activity = (GameActivity) mActivity;
        question = activity.getCurrentQuestion();
        setTitle();
        setWeb();
        return view;
    }

    private void setTitle() {
        String str = activity.getLevelTitle2();
        title.setText(str);
        mainLab.setText(str);
    }

    private void setWeb() {
        if (question != null) {
            String html = String.format(HTMLFROMT, question.getTitle());
            webview.loadData(html, "text/html", null);
        }
    }

    @OnClick({R.id.back_btn, R.id.return_btn, R.id.ans_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.return_btn:
                activity.finish();
                break;
            case R.id.ans_btn:
                activity.addAnswer(question.getTitle(), "","", question.getFraction());
                getFragmentManager().popBackStack();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

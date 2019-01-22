package com.unikfunlearn.treasurehuntgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
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

public class TypeOneFragment extends BaseFragment {

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.main_lab)
    TextView mainLab;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.input_box)
    EditText inputBox;
    @BindView(R.id.pic_btn)
    Button picBtn;
    @BindView(R.id.ans_btn)
    Button ansBtn;
    @BindView(R.id.return_btn)
    Button returnBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;
    private Unbinder unbinder;
    private GameActivity activity;
    private Question question;

    static TypeOneFragment newInstance() {
        Bundle args = new Bundle();

        TypeOneFragment fragment = new TypeOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_one, container, false);
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
                if (inputBox.getText().length() != 0) {
                    activity.addAnswer(question.getTitle(), inputBox.getText().toString(), "", question.getFraction());
                    getFragmentManager().popBackStack();
                }
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

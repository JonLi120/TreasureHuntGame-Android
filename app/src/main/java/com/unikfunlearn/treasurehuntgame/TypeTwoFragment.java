package com.unikfunlearn.treasurehuntgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class TypeTwoFragment extends BaseFragment {

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
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.rcv)
    RecyclerView rcv;
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
    private MyAdapter adapter;
    private int score;

    static TypeTwoFragment newInstance() {
        Bundle args = new Bundle();

        TypeTwoFragment fragment = new TypeTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_two, container, false);
        unbinder = ButterKnife.bind(this, view);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);

        activity = (GameActivity) mActivity;
        question = activity.getCurrentQuestion();
        setTitle();
        setWeb();

        rcv.setLayoutManager(new LinearLayoutManager(mActivity));
        rcv.setHasFixedSize(true);
        rcv.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));

        List<String> list = new ArrayList<>();
        if (question != null) {
            Gson gson = new Gson();
            Bean bean = gson.fromJson(question.getChoose(), Bean.class);
            if (bean != null) {
                list.add(bean.getChoose1());
                list.add(bean.getChoose2());
                list.add(bean.getChoose3());
                list.add(bean.getChoose4());
            }
        }
        adapter = new MyAdapter(list);
        rcv.setAdapter(adapter);

        return view;
    }

    private void setTitle() {
        String str = activity.getLevelTitle2();
        title.setText(str);
        mainLab.setText(str);
    }

    private void setWeb() {
        if (question != null) {
            String html = String.format(HTMLFROMT, question.getQuestion());
            webview.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);
        }
    }

    @OnClick({R.id.back_btn, R.id.return_btn, R.id.ans_btn, R.id.back_lab})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.return_btn:
                activity.finish();
                break;
            case R.id.back_lab:
                activity.finish();
                break;
            case R.id.ans_btn:
                score = 0;
                if (getFragmentManager().findFragmentByTag(AnswerDialog.class.getSimpleName()) == null) {
                    question = activity.getCurrentQuestion();
                    if (question != null) {
                        if (adapter.getClickPos() + 1 == question.getAnswer()) {
                            score = question.getFraction();
                        }
                        AnswerDialog dialog = AnswerDialog.newInstance(score, adapter.getClickPos() + 1 == question.getAnswer());
                        dialog.setCallback(() ->{
                            activity.addAnswer(question.getTitle(), String.valueOf(adapter.getClickPos() + 1), "", score);
                            getFragmentManager().popBackStack();
                        });
                        dialog.show(getFragmentManager(), AnswerDialog.class.getSimpleName());
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

    private class Bean{
        private String choose1;
        private String choose2;
        private String choose3;
        private String choose4;

        public String getChoose1() {
            return choose1;
        }

        public void setChoose1(String choose1) {
            this.choose1 = choose1;
        }

        public String getChoose2() {
            return choose2;
        }

        public void setChoose2(String choose2) {
            this.choose2 = choose2;
        }

        public String getChoose3() {
            return choose3;
        }

        public void setChoose3(String choose3) {
            this.choose3 = choose3;
        }

        public String getChoose4() {
            return choose4;
        }

        public void setChoose4(String choose4) {
            this.choose4 = choose4;
        }
    }
}

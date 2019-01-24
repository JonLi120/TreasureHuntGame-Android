package com.unikfunlearn.treasurehuntgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AnswerDialog extends BaseDialogFragment {
    private static final String KEY_SCORE = "KEY_SCORE";
    private static final String KEY_STATUS = "KEY_STATUS";

    @BindView(R.id.dialog_img)
    ImageView dialogImg;
    @BindView(R.id.dialog_title)
    TextView dialogTitle;
    @BindView(R.id.dialog_content)
    TextView dialogContent;
    @BindView(R.id.dialog_ok_btn)
    Button dialogOkBtn;
    private Unbinder unbinder;
    private boolean status;
    private int score;
    private DismissCallback callback;

    public interface DismissCallback{
        void onClick();
    }

    public static AnswerDialog newInstance(int score, boolean status) {
        Bundle args = new Bundle();
        args.putInt(KEY_SCORE, score);
        args.putBoolean(KEY_STATUS, status);

        AnswerDialog fragment = new AnswerDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            status = bundle.getBoolean(KEY_STATUS);
            score = bundle.getInt(KEY_SCORE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_answer, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (status) {
            Glide.with(this).load(R.drawable.ic_success).into(dialogImg);
            dialogTitle.setText("答對了");
            dialogContent.setText("獲得 " + score + " 分");
        } else {
            Glide.with(this).load(R.drawable.ic_error).into(dialogImg);
            dialogTitle.setText("答錯了");
            dialogContent.setText("繼續加油");
        }

        return view;
    }

    @OnClick({R.id.dialog_ok_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_ok_btn:
                if (callback != null) {
                    getDialog().dismiss();
                }
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (callback != null) {
            callback.onClick();
        }
    }

    void setCallback(DismissCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        removeThis(AnswerDialog.class);
        super.onDestroyView();
    }
}

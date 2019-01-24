package com.unikfunlearn.treasurehuntgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.unikfunlearn.treasurehuntgame.core.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class HintDialog extends BaseDialogFragment {

    public static final String KEY_CONTENT = "KEY_CONTENT";
    public static final String KEY_SKIP = "KEY_SKIP";

    @BindView(R.id.hint_lab)
    TextView hintLab;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.skip_btn)
    Button skipBtn;
    @BindView(R.id.hint_cancel_btn)
    ImageView hintCancelBtn;
    private Unbinder unbinder;
    private SkipCallback callback;
    private String content;
    private boolean skip;

    public interface SkipCallback {
        void onClick();
    }

    public static HintDialog newInstance(String content, boolean skip) {

        Bundle args = new Bundle();
        args.putString(KEY_CONTENT, content);
        args.putBoolean(KEY_SKIP, skip);

        HintDialog fragment = new HintDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            content = bundle.getString(KEY_CONTENT);
            skip = bundle.getBoolean(KEY_SKIP);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null && activity != null) {
            int phoneWidth = activity.getResources().getDisplayMetrics().widthPixels;
            float scale = activity.getResources().getDisplayMetrics().density;
            window.setLayout((int) (phoneWidth * 0.85), (int) (420 * scale + 0.5f));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_hint, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (skip) {
            skipBtn.setVisibility(View.VISIBLE);
        } else {
            skipBtn.setVisibility(View.INVISIBLE);
        }

        String html = String.format(HTMLFROMT, content);
        webview.loadData(html, "text/html", null);

        return view;
    }

    @OnClick({R.id.skip_btn, R.id.hint_cancel_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.skip_btn:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("確定後將無法返回上一關卡，是否確認跳過？")
                        .setPositiveButton("確定", ((dialogInterface, i) -> {
                            if (callback != null) {
                                callback.onClick();
                                dialogInterface.dismiss();
                                getDialog().dismiss();
                            }
                        })).setNegativeButton("取消", (dialogInterface, i) -> {
                            dialogInterface.dismiss();
                }).create().show();
                break;
            case R.id.hint_cancel_btn:
                getDialog().dismiss();
                break;
        }
    }

    void setCallback(SkipCallback callback) {
        this.callback = callback;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        removeThis(HintDialog.class);
        super.onDestroyView();
    }
}

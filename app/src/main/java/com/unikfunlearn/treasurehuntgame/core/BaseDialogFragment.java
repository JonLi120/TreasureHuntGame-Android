package com.unikfunlearn.treasurehuntgame.core;

import android.content.Context;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;

import com.unikfunlearn.treasurehuntgame.R;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class BaseDialogFragment extends DialogFragment {
    protected BaseActivity activity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.BaseDialogTheme);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null && activity != null) {
            int phoneWidth = activity.getResources().getDisplayMetrics().widthPixels;
            window.setLayout((int) (phoneWidth * 0.85), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    protected void removeThis(Class T) {
        super.onDestroyView();
        Fragment fragment = getFragmentManager().findFragmentByTag(T.getSimpleName());
        if (fragment != null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.remove(fragment);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}

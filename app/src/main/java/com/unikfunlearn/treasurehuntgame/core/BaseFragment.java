package com.unikfunlearn.treasurehuntgame.core;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    protected AppCompatActivity activity;
    protected BaseApplication baseApplication;

    public BaseFragment() {
        baseApplication = BaseApplication.getInstance();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = (AppCompatActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
}

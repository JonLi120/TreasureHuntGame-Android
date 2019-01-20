package com.unikfunlearn.treasurehuntgame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.unikfunlearn.treasurehuntgame.core.BaseFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TypeOneFragment extends BaseFragment {

    public static TypeOneFragment newInstance() {
        Bundle args = new Bundle();

        TypeOneFragment fragment = new TypeOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_one, container, false);

        return view;
    }
}

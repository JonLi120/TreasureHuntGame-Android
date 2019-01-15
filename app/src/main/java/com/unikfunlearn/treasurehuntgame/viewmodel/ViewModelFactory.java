package com.unikfunlearn.treasurehuntgame.viewmodel;

import android.content.Context;

import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private DataRepository dataRepository = new DataRepository();

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)) {
            return modelClass.cast(new MainViewModel(dataRepository));
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

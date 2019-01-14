package com.unikfunlearn.treasurehuntgame.core;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

public class BaseViewModel extends ViewModel {
    protected CompositeDisposable disposable = new CompositeDisposable();
    protected BaseApplication baseApplication = BaseApplication.getInstance();

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }
}

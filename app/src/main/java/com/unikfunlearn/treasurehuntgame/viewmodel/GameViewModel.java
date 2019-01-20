package com.unikfunlearn.treasurehuntgame.viewmodel;

import com.unikfunlearn.treasurehuntgame.core.BaseViewModel;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.SchoolResponse;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;

public class GameViewModel extends BaseViewModel {
    private DataRepository repository;

    private MutableLiveData<List<Question>> questions = new MutableLiveData<>();

    GameViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Question>> getQuestions() {
        return questions;
    }

    public void getQuestion(int aid) {
        disposable.add(Single.fromCallable(() -> repository.getQuestionByAID(aid))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list -> questions.postValue(list)));
    }
}

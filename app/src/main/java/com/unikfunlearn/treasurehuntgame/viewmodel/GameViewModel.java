package com.unikfunlearn.treasurehuntgame.viewmodel;

import com.unikfunlearn.treasurehuntgame.core.BaseViewModel;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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

    public void insertAnswer(List<Answer> list) {

    }

    public void insertRecord(List<Answer> list, int score, int rid) {
        disposable.add(Single.fromCallable(() -> repository.updateRecordScore(rid, score))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}

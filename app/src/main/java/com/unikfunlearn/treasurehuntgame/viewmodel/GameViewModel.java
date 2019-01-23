package com.unikfunlearn.treasurehuntgame.viewmodel;

import com.unikfunlearn.treasurehuntgame.core.BaseViewModel;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import androidx.lifecycle.MutableLiveData;
import io.reactivex.Single;
import io.reactivex.SingleSource;
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

    public void insertRecord(List<Answer> list, int score, int rid) {
        disposable.add(Single.fromCallable(() -> repository.updateRecordScore(rid, score))
                .subscribeOn(Schedulers.io())
                .flatMap((io.reactivex.functions.Function<Integer, SingleSource<?>>) i -> {
                    Answer[] ans_arr = new Answer[list.size()];
                    ans_arr = list.toArray(ans_arr);
                    repository.insertAnswer(ans_arr);
                    return Single.just(i);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }
}

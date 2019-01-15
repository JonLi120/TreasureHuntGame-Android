package com.unikfunlearn.treasurehuntgame.viewmodel;

import com.unikfunlearn.treasurehuntgame.core.BaseViewModel;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.tables.Act;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends BaseViewModel {
    private DataRepository repository;

    MainViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public void download() {
        disposable.add(repository.downloadData()
                .subscribeOn(Schedulers.io())
                .flatMap((Function<DownloadResponse, SingleSource<DownloadResponse>>) downloadResponse -> {

                    if (downloadResponse.getCode().equals("100")) {
                        insertData(downloadResponse);
                    } else {
                        throw new RuntimeException("Data download failed.");
                    }
                    return Single.just(downloadResponse);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    public void insertData(DownloadResponse response) {
        repository.delActAll();
        repository.delQuestionAll();

        List<DownloadResponse.ActivityBean> actList = response.getActivity();
        for (DownloadResponse.ActivityBean item : actList) {
            Act act = new Act();
            act.setAID(item.getAID());
            act.setSCID(item.getSCID());
            act.setSchool(item.getSchool());
            act.setTitle(item.getTitle());
            act.setContent(item.getContent());
            repository.insertAct(act);

            List<DownloadResponse.ActivityBean.QuestionBean> qList = item.getQuestion();
            for (DownloadResponse.ActivityBean.QuestionBean item2 : qList) {
                Question question = new Question();
                question.setAID(item2.getAID());
                question.setQID(item2.getQID());
                question.setAnswer(item2.getAnswer());
                question.setAtype(item2.getAtype());
                question.setBluesn(item2.getBluesn());
                question.setChoose(item2.getChoose());
                question.setFraction(item2.getFraction());
                question.setHit(item2.getHit());
                question.setQuestion(item2.getQuestion());
                question.setSkip(item2.getSkip());
                question.setSort(item2.getSort());
                question.setTitle(item2.getTitle());
                repository.insertQuestion(question);
            }
        }
    }
}

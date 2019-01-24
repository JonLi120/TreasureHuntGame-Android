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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainViewModel extends BaseViewModel {
    private DataRepository repository;
    private MutableLiveData<List<Game>> games = new MutableLiveData<>();

    MainViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Game>> getGames() {
        return games;
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
                }).flatMap((Function<DownloadResponse, SingleSource<Integer>>) status -> {
                    Call<SchoolResponse> call = repository.callSchoolApi();
                    Response<SchoolResponse> response = call.execute();
                    if (response.isSuccessful()) {

                        repository.delSchoolAll();

                        SchoolResponse body = response.body();
                        assert body != null;
                        List<SchoolResponse.SchoolBean> list = body.getSchool();

                        for (SchoolResponse.SchoolBean item : list) {
                            School school = new School();
                            school.setSCID(item.getSCID());
                            school.setName(item.getName());
                            repository.insertSchool(school);
                        }
                        return Single.just(body.getCode());
                    } else {
                        throw new RuntimeException("Call school api failed.");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
    }

    private void insertData(DownloadResponse response) {
        repository.delActAll();
        repository.delQuestionAll();

        List<DownloadResponse.ActivityBean> actList = response.getActivity();
        for (DownloadResponse.ActivityBean item : actList) {
            Game game = new Game();
            game.setAID(item.getAID());
            game.setSCID(item.getSCID());
            game.setSchool(item.getSchool());
            game.setTitle(item.getTitle());
            game.setContent(item.getContent());
            game.setNote(item.getNote());
            repository.insertAct(game);

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
                question.setSkip(item2.getSkip().equals("1"));
                question.setSort(item2.getSort());
                question.setTitle(item2.getTitle());
                question.setDistance(item2.getDistance());
                repository.insertQuestion(question);
            }
        }
    }

    public void getGame(int id) {
        disposable.add(Single.fromCallable(() -> repository.getGameByAID(id))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(games1 -> games.postValue(games1)));
    }
}

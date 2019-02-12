package com.unikfunlearn.treasurehuntgame.viewmodel;

import com.google.gson.Gson;
import com.unikfunlearn.treasurehuntgame.core.BaseViewModel;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.SchoolResponse;
import com.unikfunlearn.treasurehuntgame.models.UploadRequest;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.models.tables.Record;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.DataRepository;

import java.util.ArrayList;
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
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private MutableLiveData<Boolean> uploadStatus = new MutableLiveData<>();

    MainViewModel(DataRepository repository) {
        this.repository = repository;
    }

    public MutableLiveData<List<Game>> getGames() {
        return games;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getUploadStatus() {
        return uploadStatus;
    }

    public void download() {
        loading.postValue(true);
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
                .subscribe(code -> loading.postValue(false), throwable -> loading.postValue(false)));
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

    public void upload() {
        loading.postValue(true);
        disposable.add(Single.fromCallable(() -> repository.getFinishedRecord())
                .subscribeOn(Schedulers.io())
                .flatMap((Function<List<Record>, SingleSource<List<UploadRequest>>>) list -> {
                    List<UploadRequest> requests = new ArrayList<>();

                    for (Record item : list) {
                        UploadRequest request = new UploadRequest();
                        request.setAID(item.getAID());
                        request.setRID(item.getRID());
                        request.setSCID(item.getSCID());
                        request.setExplain("");
                        request.setMClass(item.getMyClass());
                        request.setSeatNumber(item.getSeatNumber());
                        request.setName(item.getName());

                        List<UploadRequest.AnswerBean> beanList = new ArrayList<>();

                        List<Answer> answers = repository.getAnswerByRid(item.getRID());
                        for (Answer answer : answers) {
                            UploadRequest.AnswerBean bean = new UploadRequest.AnswerBean();
                            bean.setTitle(answer.getTitle());
                            bean.setAnswer(answer.getAnswer());
                            bean.setImg(answer.getImg());

                            beanList.add(bean);
                        }

                        request.setAnswer(beanList);
                        requests.add(request);
                    }

                    return Single.just(requests);
                }).flatMap((Function<List<UploadRequest>, SingleSource<Boolean>>) requests -> {
                    String json = new Gson().toJson(requests);
                    Call<ResponseBody> call = repository.callUploadApi(json);
                    Response<ResponseBody> response = call.execute();
                    if (response.isSuccessful()) {
                        repository.deleteRecordAll();
                        repository.deleteAnswerAll();
                    }
                    return Single.just(response.isSuccessful());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(status -> {
                    uploadStatus.postValue(status);
                    loading.postValue(false);
                }, throwable -> loading.postValue(false)));
    }
}

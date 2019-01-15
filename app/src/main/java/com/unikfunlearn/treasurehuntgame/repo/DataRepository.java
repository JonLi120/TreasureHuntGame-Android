package com.unikfunlearn.treasurehuntgame.repo;

import com.unikfunlearn.treasurehuntgame.core.BaseApplication;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.tables.Act;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.repo.local.AppDatabase;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.ActDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.QuestionDao;
import com.unikfunlearn.treasurehuntgame.repo.remote.RetrofitClient;
import com.unikfunlearn.treasurehuntgame.repo.remote.service.DataService;

import io.reactivex.Single;

public class DataRepository {

    private static DataRepository repository;
    private DataService dataService;
    private ActDao actDao;
    private QuestionDao questionDao;

    public static DataRepository getInstance() {
        return repository;
    }

    public DataRepository() {
        AppDatabase db = AppDatabase.getInstance(BaseApplication.getInstance());
        dataService = RetrofitClient.getInstance().getDataService();
        actDao = db.actDao();
        questionDao = db.questionDao();
        repository = this;
    }

    public Single<DownloadResponse> downloadData(){
        return dataService.downloadData();
    }

    public void delActAll() {
        actDao.delAll();
    }

    public void insertAct(Act act) {
        actDao.insert(act);
    }

    public void delQuestionAll() {
        questionDao.delQuestionAll();
    }

    public void insertQuestion(Question question){
        questionDao.insertQuestion(question);
    }
}

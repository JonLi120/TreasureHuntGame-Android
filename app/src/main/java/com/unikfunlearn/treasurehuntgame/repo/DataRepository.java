package com.unikfunlearn.treasurehuntgame.repo;

import com.unikfunlearn.treasurehuntgame.core.BaseApplication;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.SchoolResponse;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.local.AppDatabase;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.QuestionDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.SchoolDao;
import com.unikfunlearn.treasurehuntgame.repo.remote.RetrofitClient;
import com.unikfunlearn.treasurehuntgame.repo.remote.service.DataService;

import java.util.List;

import io.reactivex.Single;
import retrofit2.Call;

public class DataRepository {

    private static DataRepository repository;
    private DataService dataService;
    private GameDao gameDao;
    private QuestionDao questionDao;
    private SchoolDao schoolDao;

    public static DataRepository getInstance() {
        return repository;
    }

    public DataRepository() {
        AppDatabase db = AppDatabase.getInstance(BaseApplication.getInstance());
        dataService = RetrofitClient.getInstance().getDataService();
        gameDao = db.gameDao();
        questionDao = db.questionDao();
        schoolDao = db.schoolDao();
        repository = this;
    }

    public Single<DownloadResponse> downloadData(){
        return dataService.downloadData();
    }

    public void delActAll() {
        gameDao.delAll();
    }

    public void insertAct(Game game) {
        gameDao.insert(game);
    }

    public void delQuestionAll() {
        questionDao.delQuestionAll();
    }

    public void insertQuestion(Question question){
        questionDao.insertQuestion(question);
    }

    public void insertSchool(School school){
        schoolDao.insertSchool(school);
    }

    public Call<SchoolResponse> callSchoolApi() {
        return dataService.callSchoolApi();
    }

    public void delSchoolAll() {
        schoolDao.delAll();
    }

    public List<Game> getGameByAID(int id) {
        return gameDao.getGameByAID(id);
    }

    public List<Question> getQuestionByAID(int id) {
        return questionDao.getQuestionByAID(id);
    }
}

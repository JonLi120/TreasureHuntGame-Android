package com.unikfunlearn.treasurehuntgame.repo;

import com.unikfunlearn.treasurehuntgame.core.BaseApplication;
import com.unikfunlearn.treasurehuntgame.models.DownloadResponse;
import com.unikfunlearn.treasurehuntgame.models.SchoolResponse;
import com.unikfunlearn.treasurehuntgame.models.UploadRequest;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Game;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.models.tables.Record;
import com.unikfunlearn.treasurehuntgame.models.tables.School;
import com.unikfunlearn.treasurehuntgame.repo.local.AppDatabase;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.AnswerDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.GameDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.QuestionDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.RecordDao;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.SchoolDao;
import com.unikfunlearn.treasurehuntgame.repo.remote.RetrofitClient;
import com.unikfunlearn.treasurehuntgame.repo.remote.service.DataService;

import java.util.List;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class DataRepository {

    private static DataRepository repository;
    private DataService dataService;
    private GameDao gameDao;
    private QuestionDao questionDao;
    private SchoolDao schoolDao;
    private RecordDao recordDao;
    private AnswerDao answerDao;

    public static DataRepository getInstance() {
        return repository;
    }

    public DataRepository() {
        AppDatabase db = AppDatabase.getInstance(BaseApplication.getInstance());
        dataService = RetrofitClient.getInstance().getDataService();
        gameDao = db.gameDao();
        questionDao = db.questionDao();
        schoolDao = db.schoolDao();
        recordDao = db.recordDao();
        answerDao = db.answerDao();
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

    public Call<ResponseBody> callUploadApi(String data) {
        return dataService.callUploadApi(data);
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

    public int updateRecordScore(int rid, int score) {
        return recordDao.updateRecordScore(rid, score);
    }

    public void insertAnswer(Answer... answers) {
        answerDao.insert(answers);
    }

    public List<Record> getFinishedRecord(){
        return recordDao.getFinishedRecord();
    }

    public List<Answer> getAnswerByRid(int rid) {
        return answerDao.getAnswer(rid);
    }

    public void deleteRecordAll() {
        recordDao.deleteAll();
    }

    public void deleteAnswerAll(){
        answerDao.deleteAll();
    }
}

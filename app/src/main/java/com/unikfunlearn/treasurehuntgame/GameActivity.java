package com.unikfunlearn.treasurehuntgame;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Answer;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;
import com.unikfunlearn.treasurehuntgame.viewmodel.GameViewModel;
import com.unikfunlearn.treasurehuntgame.viewmodel.ViewModelFactory;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class GameActivity extends BaseActivity implements BeaconConsumer {
    public static final String KEY_RID = "KEY_RID";
    public static final String KEY_AID = "KEY_AID";
    private static final String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION};

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;

    private int currentIndex = 0;
    private GameViewModel viewModel;
    private int aid;
    private int rid;
    private List<Question> questions;
    private List<Answer> answers = new ArrayList<>();
    private int totalScore = 0;
    private BeaconManager beaconManager;
    private BeaconCallback callback;

    public interface BeaconCallback{
        void found(int id, int rssi);
    }

    public static void startActivity(Context context, int rid, int aid) {
        Intent intent = new Intent(context, GameActivity.class);
        intent.putExtra(KEY_AID, aid);
        intent.putExtra(KEY_RID, rid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, new ViewModelFactory()).get(GameViewModel.class);
        viewModel.getQuestions().observe(this, list -> {
            questions = list;
            MainFragment mainFragment = MainFragment.newInstance();
            startFragment(mainFragment, MainFragment.class.getSimpleName(), false);
        });

        aid = getIntent().getIntExtra(KEY_AID, 0);
        rid = getIntent().getIntExtra(KEY_RID, 0);

        viewModel.getQuestion(aid);
        hasPermission();
    }

    @AfterPermissionGranted(1)
    private void hasPermission() {
        if (EasyPermissions.hasPermissions(this, PERMISSIONS)) {
            beaconManager = BeaconManager.getInstanceForApplication(this);
            beaconManager.getBeaconParsers().add(new BeaconParser().
                    setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
            beaconManager.bind(this);
        } else {
            EasyPermissions.requestPermissions(this, "iBeacon需要相關權限", 1, PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    void goTypeFragment() {
        if (questions.size() > currentIndex) {
            switch (questions.get(currentIndex).getAtype()) {
                case 1:
                    TypeOneFragment fragment = TypeOneFragment.newInstance();
                    startFragment(fragment, TypeOneFragment.class.getSimpleName(), true);
                    break;
                case 2:
                    TypeTwoFragment fragment2 = TypeTwoFragment.newInstance();
                    startFragment(fragment2, TypeTwoFragment.class.getSimpleName(), true);
                    break;
                case 3:
                    TypeThreeFragment fragment3 = TypeThreeFragment.newInstance();
                    startFragment(fragment3, TypeThreeFragment.class.getSimpleName(), true);
                    break;
                case 4:
                    TypeFourFragment fragment4 = TypeFourFragment.newInstance();
                    startFragment(fragment4, TypeFourFragment.class.getSimpleName(), true);
                    break;
            }
            stopBeacon();
        }
    }

    private void startFragment(BaseFragment fragment, String tag, Boolean isToBack) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isToBack) {
            ft.addToBackStack(null);
        }
        ft.add(R.id.frame_layout, fragment, tag);
        ft.commit();
    }

    int getCurrentIndex() {
        return currentIndex;
    }

    String getLevelTitle() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab1();
        }
        return "";
    }

    String getLevelBtnLab() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab3();
        }
        return "";
    }

    String getLevelTitle2() {
        Level level = Level.getLevel(currentIndex);
        if (level != null) {
            return level.getLab2();
        }
        return "";
    }

    GameViewModel getViewModel() {
        return viewModel;
    }

    Question getCurrentQuestion() {
        if (questions.size() > currentIndex) {
            return questions.get(currentIndex);
        }
        return null;
    }

    void setCallback(BeaconCallback callback) {
        this.callback = callback;
    }

    void startBeacon() {
        hasPermission();
    }

    void stopBeacon() {
        beaconManager.unbind(this);
    }

    void addAnswer(String title, String ans, String img, int score){
        Answer answer = new Answer();
        answer.setRid(rid);
        answer.setTitle(title);
        answer.setAnswer(ans);
        answer.setImg(img);

        answers.add(answer);
        totalScore += score;
        currentIndex += 1;
        if (currentIndex < questions.size()) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(MainFragment.class.getSimpleName());
            if (fragment != null) {
                ((MainFragment)fragment).replace();
            }
        } else {
            viewModel.insertRecord(answers, totalScore, rid);
            FinishActivity.startActivity(this, aid);
        }

    }

    @Override
    public void onBeaconServiceConnect() {
        beaconManager.removeAllMonitorNotifiers();
        beaconManager.addRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0 && callback != null) {

                    for (Beacon beacon : beacons) {
                        int major = beacon.getId2().toInt();
                        int minor = beacon.getId3().toInt();

                        int id = (((major >> 4) & 0x03) << 8) | ((minor >> 8) & 0xFF);
                        Log.w("test", beacon.getId1().toString() + " / " + Integer.toHexString(id));
                        Log.d("test", "RSSI: " + beacon.getRssi());
                        callback.found(Integer.valueOf(Integer.toHexString(id)), beacon.getRssi());
                    }

                }
            }
        });
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("F2C56DB5-DFFB-48D2-B060-D0F5A71096E0", null, null, null));
            beaconManager.startRangingBeaconsInRegion(new Region("F2C56DB5-DFFB-48D2-B060-D0F5A71096E2", null, null, null));
            beaconManager.startRangingBeaconsInRegion(new Region("F2C56DB5-DFFB-48D2-B060-D0F5A71096E6", null, null, null));
        } catch (RemoteException e) {    }
    }

    public enum Level {
        L1("第一關", "第一題", "第一關問題"),
        L2("第二關", "第二題", "第二關問題"),
        L3("第三關", "第三題", "第三關問題"),
        L4("第四關", "第四題", "第四關問題"),
        L5("第五關", "第五題", "第五關問題"),
        L6("第六關", "第六題", "第六關問題"),
        L7("第七關", "第七題", "第七關問題"),
        L8("第八關", "第八題", "第八關問題");

        Level(String s, String s2, String s3) {
            this.s = s;
            this.s2 = s2;
            this.s3 = s3;
        }

        private String s;
        private String s2;
        private String s3;

        public static Level getLevel(int i) {
            switch (i) {
                case 0:
                    return L1;
                case 1:
                    return L2;
                case 2:
                    return L3;
                case 3:
                    return L4;
                case 4:
                    return L5;
                case 5:
                    return L6;
                case 6:
                    return L7;
                case 7:
                    return L8;
                default:
                    return null;
            }

        }

        public String getLab1() {
            return this.s;
        }

        public String getLab2() {
            return this.s2;
        }

        public String getLab3() {
            return this.s3;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        beaconManager.unbind(this);
        super.onDestroy();
    }
}

package com.unikfunlearn.treasurehuntgame;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseActivity;
import com.unikfunlearn.treasurehuntgame.models.tables.Record;
import com.unikfunlearn.treasurehuntgame.repo.local.dao.RecordDao;

import androidx.annotation.Nullable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserActivity extends BaseActivity {

    public static final String KEY_SCID = "KEY_SCID";
    public static final String KEY_AID = "KEY_AID";

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.input_class_lab)
    TextView inputClassLab;
    @BindView(R.id.input_seat_lab)
    TextView inputSeatLab;
    @BindView(R.id.input_name_lab)
    TextView inputNameLab;
    @BindView(R.id.enter_btn)
    Button enterBtn;
    @BindView(R.id.class_edit)
    EditText classEdit;
    @BindView(R.id.seat_edit)
    EditText seatEdit;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    private int scid;
    private int aid;
    private RecordDao recordDao;

    public static void startActivity(Context context, int scid, int aid) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(KEY_SCID, scid);
        intent.putExtra(KEY_AID, aid);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);

        scid = getIntent().getIntExtra(KEY_SCID, 0);
        aid = getIntent().getIntExtra(KEY_AID, 0);

        recordDao = db.recordDao();

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);


    }

    @OnClick({R.id.back_btn, R.id.enter_btn})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.enter_btn:
                if (classEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "請輸入您的班級", Toast.LENGTH_LONG).show();
                } else if (seatEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "請輸入您的座號", Toast.LENGTH_LONG).show();
                } else if (nameEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "請輸入您的姓名", Toast.LENGTH_LONG).show();
                } else {
                    Record record = new Record();
                    record.setAID(aid);
                    record.setSCID(scid);
                    record.setFinished(false);
                    record.setMyClass(classEdit.getText().toString());
                    record.setSeatNumber(seatEdit.getText().toString());
                    record.setName(nameEdit.getText().toString());

                    long id = recordDao.insertRecord(record);
                    DescriptionActivity.startActivity(this, (int)id, aid);
                }
                break;
        }
    }
}

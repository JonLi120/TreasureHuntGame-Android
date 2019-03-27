package com.unikfunlearn.treasurehuntgame;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.unikfunlearn.treasurehuntgame.core.BaseFragment;
import com.unikfunlearn.treasurehuntgame.models.tables.Question;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.app.Activity.RESULT_OK;
import static com.unikfunlearn.treasurehuntgame.core.Constant.HTMLFROMT;

public class TypeOneFragment extends BaseFragment {

    private static final String[] PERMISSIONS = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @BindView(R.id.bg_img)
    ImageView bgImg;
    @BindView(R.id.back_btn)
    ImageView backBtn;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.main_lab)
    TextView mainLab;
    @BindView(R.id.divide_line)
    View divideLine;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.input_box)
    EditText inputBox;
    @BindView(R.id.pic_btn)
    Button picBtn;
    @BindView(R.id.ans_btn)
    Button ansBtn;
    @BindView(R.id.return_btn)
    Button returnBtn;
    @BindView(R.id.bg_layout)
    ConstraintLayout bgLayout;
    @BindView(R.id.pic_img)
    ImageView picImg;
    @BindView(R.id.pic_lab)
    TextView picLab;
    private Unbinder unbinder;
    private GameActivity activity;
    private Question question;
    private String img = "";

    static TypeOneFragment newInstance() {
        Bundle args = new Bundle();

        TypeOneFragment fragment = new TypeOneFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_one, container, false);
        unbinder = ButterKnife.bind(this, view);

        Glide.with(this).load(R.drawable.bg_common).into(bgImg);

        activity = (GameActivity) mActivity;
        question = activity.getCurrentQuestion();
        setTitle();
        setWeb();

        return view;
    }

    private void setTitle() {
        String str = activity.getLevelTitle2();
        title.setText(str);
        mainLab.setText(str);
    }

    private void setWeb() {
        if (question != null) {
            String html = String.format(HTMLFROMT, question.getQuestion());
            webview.loadData(html, "text/html", null);

        }
    }

    @OnClick({R.id.back_btn, R.id.return_btn, R.id.ans_btn, R.id.pic_btn, R.id.back_lab})
    protected void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_btn:
                getFragmentManager().popBackStack();
                break;
            case R.id.return_btn:
                activity.finish();
                break;
            case R.id.ans_btn:
                if (inputBox.getText().length() != 0) {
                    activity.addAnswer(question.getTitle(), inputBox.getText().toString(), img, question.getFraction());
                    getFragmentManager().popBackStack();
                }
                break;
            case R.id.pic_btn:
                hasPermission();
                break;
            case R.id.back_lab:
                activity.finish();
                break;
        }
    }

    @AfterPermissionGranted(100)
    private void hasPermission() {
        if (EasyPermissions.hasPermissions(activity, PERMISSIONS)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 200);
        } else {
            EasyPermissions.requestPermissions(TypeOneFragment.this, "拍照需要相關權限", 100, PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200) {
            Bitmap bitmap = data.getParcelableExtra("data");
            picImg.setVisibility(View.VISIBLE);
            picLab.setVisibility(View.VISIBLE);
            bitmap = compressBitmap(bitmap);
            Glide.with(this).load(bitmap).into(picImg);
            encodeImg(bitmap);
        }
    }

    private Bitmap compressBitmap(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        byte[] bytes = bos.toByteArray();
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return getRotatedBitmap(bitmap);
    }

    private Bitmap getRotatedBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        int degree = readCameraDegree();
        matrix.postRotate(degree - 90);

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);
    }

    private int readCameraDegree() {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(Camera.CameraInfo.CAMERA_FACING_BACK, info);
        int rotation = activity.getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        } else {
            result = (info.orientation - degrees + 360) % 360;
        }
        return result;
    }

    private void encodeImg(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

        byte[] bytes = bos.toByteArray();
        byte[] encode = Base64.encode(bytes,Base64.DEFAULT);
        img = new String(encode);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }
}

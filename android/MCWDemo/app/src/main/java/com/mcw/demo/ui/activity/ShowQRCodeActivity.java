package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.config.Constant;
import com.mcw.demo.model.UserInfo;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowQRCodeActivity extends BaseActivity {

    private Map<String, String> params;

    @BindView(R.id.qr_code_iv)
    ImageView qrCodeIv;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_show_qrcode;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);

        String meetingId = getIntent().getStringExtra("meetingId");
        String signId = getIntent().getStringExtra("signId");
        params = new HashMap<>();
        params.put("meetingId", meetingId);
        params.put("userId", UserInfo.getInstance().getId());
        params.put("signId", signId);

        setTitle("扫码签到");

        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                subscriber.onNext(generateQRCode());
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Bitmap>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Bitmap bitmap) {
                qrCodeIv.setImageBitmap(bitmap);
            }
        });

    }

    private Bitmap generateQRCode() {
        return QRCodeEncoder.syncEncodeQRCode(new Gson().toJson(params), BGAQRCodeUtil.dp2px(this, 200));
    }

    public static Map<String,String> getScanResult(String meetingId, String scanResult) {
        Type type = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> params = new Gson().fromJson(scanResult, type);
        String resMeetingId = params.get("meetingId");
        if (!meetingId.equals(resMeetingId)) {
            return null;
        } else {
            return params;
        }
    }

    public static Observable<ActivityResult> navToShowQRCode(Activity activity, String meetingId,String signId) {
        Intent intent = new Intent(activity, ShowQRCodeActivity.class);
        intent.putExtra("meetingId", meetingId);
        intent.putExtra("signId", signId);
        return RxActivityResult.getInstance(DemoApplication.getInstance().getApplicationContext()).from(activity)
                .startActivityForResult(intent, Constant.START_ACTIVITY_FLAG_NAV_TO_SHOW_QR_CODE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mContext.setResult(RESULT_OK);
            mContext.finish();
        }
        return false;
    }
}

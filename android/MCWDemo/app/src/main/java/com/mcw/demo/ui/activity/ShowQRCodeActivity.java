package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mcw.R;
import com.mcw.demo.model.UserInfo;

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
        params = new HashMap<>();
        params.put("meetingId", meetingId);
        params.put("userId", UserInfo.getInstance().getId());

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

    public static String getScanUserId(String meetingId, String scanResult) {
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> params = new Gson().fromJson(scanResult, type);
        String resMeetingId = params.get("meetingId");
        if (!meetingId.equals(resMeetingId)){
            return null;
        }else{
            return params.get("userId");
        }
    }

    public static void navToShowQRCode(Activity activity, String meetingId) {
        Intent intent = new Intent(activity, ShowQRCodeActivity.class);
        intent.putExtra("meetingId", meetingId);
        activity.startActivity(intent);
    }
}

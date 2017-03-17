package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.mcw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.qrcode.core.BGAQRCodeUtil;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ShowQRCodeActivity extends BaseActivity {

    @BindView(R.id.qr_code_iv)
    ImageView qrCodeIv;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_show_qrcode;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
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

    private Bitmap generateQRCode(){
        return QRCodeEncoder.syncEncodeQRCode(""+1, BGAQRCodeUtil.dp2px(this,200));
    }

    public static void navToShowQRCode(Activity activity,String meetingId){
        Intent intent = new Intent(activity,ShowQRCodeActivity.class);
        intent.putExtra("meetingId",meetingId);
        activity.startActivity(intent);
    }
}

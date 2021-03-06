package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.api.DemoApiFactory;
import com.mcw.demo.config.Constant;
import com.mcw.demo.util.StringUtils;
import com.mcw.demo.util.ToastMaster;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import rx.Observable;
import rx.Subscriber;

public class ScanQRCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    private String meetingId;
    private String signId,userId;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_scan_qrcode;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("二维码签到");
        meetingId = getIntent().getStringExtra("meetingId");
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        mQRCodeView.setDelegate(this);
    }

    private static final String TAG = ScanQRCodeActivity.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private QRCodeView mQRCodeView;

    @Override
    protected void onStart() {
        super.onStart();
        mQRCodeView.startCamera();
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(500);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        Log.i(TAG, "result:" + result);
        Map<String,String> scanAnalyze = ShowQRCodeActivity.getScanResult(meetingId, result);
        signId = scanAnalyze.get("signId");
        userId = scanAnalyze.get("userId");
        Log.i(TAG, "signId:" + signId);
        if (StringUtils.isEmpty(signId)) {
            ToastMaster.popToast(mContext, "scan err");
        } else {
            DemoApiFactory.getInstance().meetingSign(signId).subscribe(new Subscriber<Boolean>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    ToastMaster.popToast(mContext, "签到失败");
                }

                @Override
                public void onNext(Boolean aBoolean) {
                    if (aBoolean.booleanValue()) {
                        ToastMaster.popToast(mContext, "签到成功");
                        Intent intent = getIntent();
                        intent.putExtra("userId",userId);
                        mContext.setResult(RESULT_OK,intent);
                        mContext.finish();
                    } else {
                        ToastMaster.popToast(mContext, "签到失败");
                    }
                }
            });
        }

    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_spot:
                mQRCodeView.startSpot();
                break;
            case R.id.stop_spot:
                mQRCodeView.stopSpot();
                break;
            case R.id.start_spot_showrect:
                mQRCodeView.startSpotAndShowRect();
                break;
            case R.id.stop_spot_hiddenrect:
                mQRCodeView.stopSpotAndHiddenRect();
                break;
            case R.id.show_rect:
                mQRCodeView.showScanRect();
                break;
            case R.id.hidden_rect:
                mQRCodeView.hiddenScanRect();
                break;
            case R.id.start_preview:
                mQRCodeView.startCamera();
                break;
            case R.id.stop_preview:
                mQRCodeView.stopCamera();
                break;
            case R.id.open_flashlight:
                mQRCodeView.openFlashlight();
                break;
            case R.id.close_flashlight:
                mQRCodeView.closeFlashlight();
                break;
            case R.id.scan_barcode:
                mQRCodeView.changeToScanBarcodeStyle();
                break;
            case R.id.scan_qrcode:
                mQRCodeView.changeToScanQRCodeStyle();
                break;
            case R.id.choose_qrcde_from_gallery:
                /*
                从相册选取二维码图片，这里为了方便演示，使用的是
                https://github.com/bingoogolapple/BGAPhotoPicker-Android
                这个库来从图库中选择二维码图片，这个库不是必须的，你也可以通过自己的方式从图库中选择图片
                 */
                startActivityForResult(BGAPhotoPickerActivity.newIntent(this, null, 1, null, false), REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mQRCodeView.showScanRect();

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY) {
            final String picturePath = BGAPhotoPickerActivity.getSelectedImages(data).get(0);

            /*
            这里为了偷懒，就没有处理匿名 AsyncTask 内部类导致 Activity 泄漏的问题
            请开发在使用时自行处理匿名内部类导致Activity内存泄漏的问题，处理方式可参考 https://github.com/GeniusVJR/LearningNotes/blob/master/Part1/Android/Android%E5%86%85%E5%AD%98%E6%B3%84%E6%BC%8F%E6%80%BB%E7%BB%93.md
             */
            new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... params) {
                    return QRCodeDecoder.syncDecodeQRCode(picturePath);
                }

                @Override
                protected void onPostExecute(String result) {
                    if (TextUtils.isEmpty(result)) {
                        Toast.makeText(ScanQRCodeActivity.this, "未发现二维码", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ScanQRCodeActivity.this, result, Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
    }

    public static Observable<ActivityResult> navToScanQRCode(Activity activity, String meetingId) {
        Intent intent = new Intent(activity, ScanQRCodeActivity.class);
        intent.putExtra("meetingId", meetingId);
        return RxActivityResult.getInstance(DemoApplication.getInstance().getApplicationContext()).from(activity)
                .startActivityForResult(intent, Constant.START_ACTIVITY_FLAG_NAV_TO_SCAN_QR_CODE);
    }
}

package com.mcw.demo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class UploadMeetingSummaryActivity extends BaseActivity implements BGASortableNinePhotoLayout.Delegate, EasyPermissions.PermissionCallbacks, BasicRecyViewHolder.OnItemClickListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;

    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 2;

    private static final String FRAG_TAG_START_DATE_PICKER = "FRAG_TAG_START_DATE_PICKER";
    private static final String FRAG_TAG_START_TIME_PICKER = "FRAG_TAG_START_TIME_PICKER";
    private static final String FRAG_TAG_END_DATE_PICKER = "FRAG_TAG_END_DATE_PICKER";
    private static final String FRAG_TAG_END_TIME_PICKER = "FRAG_TAG_END_TIME_PICKER";

    private static final String EXTRA_MOMENT = "EXTRA_MOMENT";

    @BindView(R.id.preview_btn)
    Button previewBtn;

    @BindView(R.id.snpl_meeting_content_add_pic)
    BGASortableNinePhotoLayout mPicSnpl;

    @BindView(R.id.people_select_rv)
    RecyclerView peopleSelectRv;

    PeopleSelectRecyclerViewAdapter adapter;

    @BindView(R.id.real_start_date_et)
    EditText realStartDateEt;
    @BindView(R.id.real_start_time_et)
    EditText realStartTimeEt;
    @BindView(R.id.real_end_date_et)
    EditText realEndDateEt;
    @BindView(R.id.real_end_time_et)
    EditText realEndTimeEt;


    @OnClick(R.id.preview_btn)
    public void previewUploadMeetingSummary() {
        MeetingDetailActivity.navToMeetingDetail(this, 1, 1);
    }

    @OnClick({R.id.real_start_date_et,R.id.real_start_time_et,R.id.real_end_date_et,R.id.real_end_time_et})
    public void setRealDateAndTime(View view) {
        switch (view.getId()){
            case R.id.real_start_date_et:
                openDatePicker(FRAG_TAG_START_DATE_PICKER);
                break;
            case R.id.real_start_time_et:
                openTimePicker(FRAG_TAG_START_TIME_PICKER);
                break;
            case R.id.real_end_date_et:
                openDatePicker(FRAG_TAG_END_DATE_PICKER);
                break;
            case R.id.real_end_time_et:
                openTimePicker(FRAG_TAG_END_TIME_PICKER);
                break;
            default:
                break;
        }
    }

    @OnFocusChange({R.id.real_start_date_et,R.id.real_start_time_et,R.id.real_end_date_et,R.id.real_end_time_et})
    public void setRealStartDate(View view, boolean hasFocus) {
        if (hasFocus) {
            switch (view.getId()){
                case R.id.real_start_date_et:
                    openDatePicker(FRAG_TAG_START_DATE_PICKER);
                    break;
                case R.id.real_start_time_et:
                    openTimePicker(FRAG_TAG_START_TIME_PICKER);
                    break;
                case R.id.real_end_date_et:
                    openDatePicker(FRAG_TAG_END_DATE_PICKER);
                    break;
                case R.id.real_end_time_et:
                    openTimePicker(FRAG_TAG_END_TIME_PICKER);
                    break;
                default:
                    break;
            }
        }
    }

    private void openDatePicker(String tag) {
        if ("".equals(tag))
            return;
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setThemeLight()
                .setOnDateSetListener(UploadMeetingSummaryActivity.this);
        cdp.show(getSupportFragmentManager(), tag);
    }

    private void openTimePicker(String tag) {
        if ("".equals(tag))
            return;
        RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(UploadMeetingSummaryActivity.this)
                .setThemeLight();
        rtpd.show(getSupportFragmentManager(), tag);
    }


    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_upload_meeting_summary;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle("上传会议纪要");


        mPicSnpl.setMaxItemCount(9);
        mPicSnpl.setEditable(true);
        mPicSnpl.setPlusEnable(true);
        mPicSnpl.setSortable(true);
        mPicSnpl.setDelegate(this);
        ArrayList<String> data =  new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png"));
        mPicSnpl.setData(data);

        if (adapter == null) {
            adapter = new PeopleSelectRecyclerViewAdapter();
            adapter.setItemClickListener(this);
            adapter.addSubViewListener(R.id.delete_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(UploadMeetingSummaryActivity.this, " 你点击了第 " + view.getTag().toString() + " 个删除button", Toast.LENGTH_LONG).show();
                    adapter.removeData(Integer.parseInt(view.getTag().toString()));
                }
            });
        }

        peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        peopleSelectRv.setLayoutManager(manager);

        List<SelectedUserEntity> list = new ArrayList<>();
        SelectedUserEntity entity = new SelectedUserEntity();
        entity.setName("1122");
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1322");
        list.add(entity);
        adapter.appendDatas(list);
    }


    public static void navToUploadMeetingSummary(Activity activity, int meetingId) {
        Intent intent = new Intent(activity, UploadMeetingSummaryActivity.class);
        intent.putExtra("meetingId", meetingId);
        activity.startActivity(intent);
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        choicePhotoWrapper();
    }

    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    private void choicePhotoWrapper() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, true ? takePhotoDir : null, mPicSnpl.getMaxItemCount() - mPicSnpl.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        mPicSnpl.removeItem(position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, mPicSnpl.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(this, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE_PHOTO) {
            mPicSnpl.addMoreData(BGAPhotoPickerActivity.getSelectedImages(data));
        } else if (requestCode == REQUEST_CODE_PHOTO_PREVIEW) {
            mPicSnpl.setData(BGAPhotoPickerPreviewActivity.getSelectedImages(data));
        }
    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {

    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        switch (dialog.getTag()){
            case FRAG_TAG_START_DATE_PICKER:
                realStartDateEt.setText(String.format("%d年%d月%d日", year, monthOfYear + 1, dayOfMonth));
                break;
            case FRAG_TAG_END_DATE_PICKER:
                realEndDateEt.setText(String.format("%d年%d月%d日", year, monthOfYear + 1, dayOfMonth));
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        switch (dialog.getTag()) {
            case FRAG_TAG_START_TIME_PICKER:
                realStartTimeEt.setText(String.format("%d:%d", hourOfDay, minute));
                break;
            case FRAG_TAG_END_TIME_PICKER:
                realEndTimeEt.setText(String.format("%d:%d", hourOfDay, minute));
                break;
            default:
                break;
        }
    }
}

package com.mcw.demo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.liuguangqiang.materialdialog.MaterialDialog;
import com.mcw.R;
import com.mcw.demo.model.MeetingContentPicEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPreviewActivity;
import cn.bingoogolapple.photopicker.imageloader.BGARVOnScrollListener;
import cn.bingoogolapple.photopicker.widget.BGANinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MeetingDetailActivity extends BaseActivity implements BasicRecyViewHolder.OnItemClickListener, BGANinePhotoLayout.Delegate {
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_ADD_MOMENT = 1;
    private int meetingId, creatorId;

    @BindView(R.id.start_date_tv)
    TextView startDateTv;
    @BindView(R.id.end_date_tv)
    TextView endDateTv;
    @BindView(R.id.people_select_rv)
    RecyclerView peopleSelectRv;
    @BindView(R.id.actual_start_date_tv)
    TextView actualStartDateTv;
    @BindView(R.id.actual_start_time_tv)
    TextView actualStartTimeTv;
    @BindView(R.id.actual_end_date_tv)
    TextView actualEndDateTv;
    @BindView(R.id.actual_end_time_tv)
    TextView actualEndTimeTv;
    @BindView(R.id.people_join_rv)
    RecyclerView peopleJoinRv;
    @BindView(R.id.meeting_action_btn)
    Button meetingActionBtn;

    @BindView(R.id.content_pic_list_rv)
    RecyclerView contentPicListRv;
    private MeetingContentPicAdapter mContentPicAdapter;
    private BGANinePhotoLayout mCurrentClickNpl;

    PeopleSelectRecyclerViewAdapter peopleSelectAdapter;
    PeopleSelectRecyclerViewAdapter peopleJoinAdapter;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_meeting_detail;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle("会议详情");
        meetingId = getIntent().getIntExtra("meetingId", 0);
//        creatorId = getIntent().getIntExtra("creatorId", 0);
        creatorId = 2;
        if (meetingId == 0) {
            Toast.makeText(this, "wrong meetingId", Toast.LENGTH_SHORT).show();
            return;
        }

        if (peopleSelectAdapter == null) {
            peopleSelectAdapter = new PeopleSelectRecyclerViewAdapter();
            peopleSelectAdapter.setItemClickListener(this);
        }

        peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        peopleSelectRv.setAdapter(peopleSelectAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(peopleSelectAdapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        peopleSelectRv.setLayoutManager(manager);

        List<SelectedUserEntity> list = new ArrayList<>();
        SelectedUserEntity entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1322");
        entity.setPreview(true);
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1422");
        entity.setPreview(true);
        list.add(entity);
        peopleSelectAdapter.appendDatas(list);


        if (peopleJoinAdapter == null) {
            peopleJoinAdapter = new PeopleSelectRecyclerViewAdapter();
        }
        peopleJoinRv.addItemDecoration(new HFGridVerDecoration());
        peopleJoinRv.setItemAnimator(new DefaultItemAnimator());
        peopleJoinRv.setAdapter(peopleJoinAdapter);
        GridLayoutManager manager2 = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup2 = new HFGridSpanSizeLookup();
        spanSizeLookup2.setAdapter(peopleJoinAdapter);
        spanSizeLookup2.setLayoutManager(manager2);
        manager2.setSpanSizeLookup(spanSizeLookup2);
        peopleJoinRv.setLayoutManager(manager2);

        List<SelectedUserEntity> list2 = new ArrayList<>();
        SelectedUserEntity entity2 = new SelectedUserEntity();
        entity2.setName("1122");
        entity2.setPreview(true);
        list2.add(entity2);
        entity2 = new SelectedUserEntity();
        entity2.setName("1222");
        entity2.setPreview(true);
        list2.add(entity2);
        entity2 = new SelectedUserEntity();
        entity2.setName("1322");
        entity2.setPreview(true);
        list2.add(entity2);
        peopleJoinAdapter.appendDatas(list2);

        mContentPicAdapter = new MeetingContentPicAdapter(contentPicListRv);
        contentPicListRv.addOnScrollListener(new BGARVOnScrollListener(this));
        contentPicListRv.setLayoutManager(new LinearLayoutManager(this));
        contentPicListRv.setAdapter(mContentPicAdapter);
        addNetImageTestData();
    }

    /**
     * 添加网络图片测试数据
     */
    private void addNetImageTestData() {
        List<MeetingContentPicEntity> entities = new ArrayList<>();
        entities.add(new MeetingContentPicEntity("9张网络图片", new ArrayList<>(Arrays.asList("http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered11.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered12.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered13.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered14.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered15.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered16.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered17.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered18.png", "http://7xk9dj.com1.z0.glb.clouddn.com/refreshlayout/images/staggered19.png"))));
        mContentPicAdapter.setData(entities);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (creatorId != 0 && creatorId != 1) {
            getMenuInflater().inflate(R.menu.meeting_detail_participation_action, menu);
        } else {
            getMenuInflater().inflate(R.menu.meeting_detail_owner_action, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.scan) {
            Intent intent = new Intent(this, ScanQRCodeActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.my_vote) {
            Intent intent = new Intent(this, MyVoteActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.new_vote) {
            Intent intent = new Intent(this, CreateVoteActivity.class);
            startActivity(intent);
            return true;
        } else if (item.getItemId() == R.id.end_meeting) {
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    public static void navToMeetingDetail(Activity activity, int meetingId, int creatorId) {
        Intent toDetail = new Intent(activity, MeetingDetailActivity.class);
        toDetail.putExtra("meetingId", meetingId);
        toDetail.putExtra("creatorId", creatorId);
        activity.startActivity(toDetail);
    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {
        new MaterialDialog.Builder(this)
                .setTitle("提醒")
                .setMessage("确认拨打 xxx 15666666666")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("拨打", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        //url:统一资源定位符
                        //uri:统一资源标示符（更广）
                        intent.setData(Uri.parse("tel:" + "136666666666"));
                        //开启系统拨号器
                        startActivity(intent);
                    }
                })
                .cancelable(true)
                .build()
                .show();
    }

    @OnClick(R.id.meeting_action_btn)
    public void meetingAction() {
//        ShowQRCodeActivity.navToShowQRCode(this,meetingId);
        UploadMeetingSummaryActivity.navToUploadMeetingSummary(this, meetingId);
    }

    @Override
    public void onClickNinePhotoItem(BGANinePhotoLayout ninePhotoLayout, View view, int position, String model, List<String> models) {
        mCurrentClickNpl = ninePhotoLayout;
        photoPreviewWrapper();
    }

    /**
     * 图片预览，兼容6.0动态权限
     */
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PREVIEW)
    private void photoPreviewWrapper() {
        if (mCurrentClickNpl == null) {
            return;
        }
        // 保存图片的目录，改成你自己要保存图片的目录。如果不传递该参数的话就不会显示右上角的保存按钮
        File downloadDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerDownload");
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            if (mCurrentClickNpl.getItemCount() == 1) {
                // 预览单张图片
                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, mCurrentClickNpl.getCurrentClickItem()));
            } else if (mCurrentClickNpl.getItemCount() > 1) {
                // 预览多张图片
                startActivity(BGAPhotoPreviewActivity.newIntent(this, downloadDir, mCurrentClickNpl.getData(), mCurrentClickNpl.getCurrentClickItemPosition()));
            }
        } else {
            EasyPermissions.requestPermissions(this, "图片预览需要以下权限:\n\n1.访问设备上的照片", REQUEST_CODE_PERMISSION_PHOTO_PREVIEW, perms);
        }
    }

    private class MeetingContentPicAdapter extends BGARecyclerViewAdapter<MeetingContentPicEntity> {

        public MeetingContentPicAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.layout_recy_item_meeting_content_pic);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, MeetingContentPicEntity entity) {
            BGANinePhotoLayout ninePhotoLayout = helper.getView(R.id.npl_item_meeting_content_pic);
            ninePhotoLayout.setDelegate(MeetingDetailActivity.this);
            ninePhotoLayout.setData(entity.photos);
        }
    }
}

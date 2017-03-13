package com.mcw.demo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.igeek.hfrecyleviewlib.HFGridMultiTypeGapDecoration;
import com.igeek.hfrecyleviewlib.HFMultiTypeRecyAdapter;
import com.igeek.hfrecyleviewlib.HolderTypeData;
import com.mcw.R;
import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.model.SelectedUserListEntity;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.model.VoteInfoEntity;
import com.mcw.demo.ui.adapter.GridMuiltTypeSpanSizeLookup;
import com.mcw.demo.ui.adapter.bean.MeetingBaseInfoType;
import com.mcw.demo.ui.adapter.bean.SelectedUserType;
import com.mcw.demo.ui.adapter.bean.SummaryInfoType;
import com.mcw.demo.ui.adapter.bean.VoteInfoType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class CreateMeetingV2Activity extends BaseActivity implements SelectedUserType.SelectedUserTypeSubViewOnClickListener, SummaryInfoType.NinePhotoLayoutListener {
    private static final int REQUEST_CODE_PHOTO_PREVIEW = 1;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 2;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 3;
    RecyclerView contentRv;
    HFMultiTypeRecyAdapter adapter;

    public static final int TYPE_MEETING_BASE_INFO = 1;
    public static final int TYPE_SELECTED_USER = 2;
    public static final int TYPE_VOTE_LIST = 3;
    public static final int TYPE_SUMMARY_INFO = 4;
    @BindView(R.id.create_meeting_btn)
    Button createMeetingBtn;


    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_create_meeting_v2;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle("新建会议");
        contentRv = (RecyclerView) findViewById(R.id.content_rv);
        if (adapter == null) {
            adapter = new HFMultiTypeRecyAdapter();
        }
        contentRv.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        GridMuiltTypeSpanSizeLookup spanSizeLookup = new GridMuiltTypeSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        contentRv.setLayoutManager(gridLayoutManager);
        HFGridMultiTypeGapDecoration gapDecoration = new HFGridMultiTypeGapDecoration(8);
        gapDecoration.setOffsetTopEnabled(false);
        contentRv.addItemDecoration(gapDecoration);
        adapter.refreshDatas(getTestDate());
        adapter.addSubViewListener(R.id.add_vote_btn, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyVoteItemEntity myVoteItemEntity = new MyVoteItemEntity();
                myVoteItemEntity.setVoteCreatorName("z333");
                myVoteItemEntity.setVoteId(1);
                myVoteItemEntity.setVoteStatus(1);
                myVoteItemEntity.setVoteTitle("投票3");
                myVoteList.add(myVoteItemEntity);
                adapter.notifyItemChanged(2);
            }
        });
    }

    private List<MyVoteItemEntity> myVoteList;
    private List<SelectedUserEntity> selectedUserList;


    private List<HolderTypeData> getTestDate() {
        List<HolderTypeData> datas = new ArrayList<>();

        MeetingBaseInfoType meetingBaseInfoType = new MeetingBaseInfoType();
        MeetingBaseInfoEntity entity = new MeetingBaseInfoEntity();
        entity.setType(TYPE_MEETING_BASE_INFO);
        meetingBaseInfoType.setData(entity);
        datas.add(meetingBaseInfoType);

        SelectedUserType selectedUserType = new SelectedUserType(this);
        SelectedUserListEntity entity1 = new SelectedUserListEntity();
        entity1.setType(TYPE_SELECTED_USER);
        selectedUserList = new ArrayList<>();
        SelectedUserEntity selectedUserEntity = new SelectedUserEntity();
        selectedUserEntity.setName("1");
        selectedUserList.add(selectedUserEntity);
        selectedUserEntity = new SelectedUserEntity();
        selectedUserEntity.setName("2");
        selectedUserList.add(selectedUserEntity);
        selectedUserEntity = new SelectedUserEntity();
        selectedUserEntity.setName("3");
        selectedUserList.add(selectedUserEntity);
        selectedUserEntity = new SelectedUserEntity();
        selectedUserEntity.setName("4");
        selectedUserEntity.setPreview(true);
        selectedUserList.add(selectedUserEntity);
        selectedUserEntity = new SelectedUserEntity();
        selectedUserEntity.setName("5");
        selectedUserEntity.setPreview(true);
        selectedUserList.add(selectedUserEntity);
        entity1.setList(selectedUserList);
        selectedUserType.setData(entity1);
        datas.add(selectedUserType);

        VoteInfoType voteInfoType = new VoteInfoType();
        VoteInfoEntity entity2 = new VoteInfoEntity();
        entity2.setType(TYPE_VOTE_LIST);
        myVoteList = new ArrayList<>();
        MyVoteItemEntity myVoteItemEntity = new MyVoteItemEntity();
        myVoteItemEntity.setVoteCreatorName("z1");
        myVoteItemEntity.setVoteId(1);
        myVoteItemEntity.setVoteStatus(1);
        myVoteItemEntity.setVoteTitle("投票1");
        myVoteList.add(myVoteItemEntity);
        myVoteItemEntity = new MyVoteItemEntity();
        myVoteItemEntity.setVoteCreatorName("z2");
        myVoteItemEntity.setVoteId(2);
        myVoteItemEntity.setVoteStatus(2);
        myVoteItemEntity.setVoteTitle("投票2");
        myVoteList.add(myVoteItemEntity);
        entity2.setList(myVoteList);
        voteInfoType.setData(entity2);
        datas.add(voteInfoType);

        SummaryInfoType summaryInfoType = new SummaryInfoType(this);
        SummaryInfoEntity entity3 = new SummaryInfoEntity();
        entity3.setType(TYPE_SUMMARY_INFO);
        summaryInfoType.setData(entity3);
        datas.add(summaryInfoType);

        return datas;
    }

    @Override
    public void onDeleteUser(View view) {
        selectedUserList.remove(Integer.parseInt(view.getTag().toString()));
        adapter.notifyItemChanged(1);
    }

    @OnClick(R.id.create_meeting_btn)
    public void createMeeting(){
        this.setResult(RESULT_OK);
        this.finish();
    }

    public static void navToMeetingDetail(Activity activity, int model){
        Intent intent = new Intent(activity,CreateMeetingV2Activity.class);
        intent.putExtra("model",1);
        activity.startActivity(intent);
    }

    @Override
    public void onItemDelete(BGASortableNinePhotoLayout layout,int position) {
        layout.removeItem(position);
    }

    @Override
    public void onItemClick(BGASortableNinePhotoLayout layout,int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(this, layout.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @Override
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    public void onItemAdd(BGASortableNinePhotoLayout layout) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            startActivityForResult(BGAPhotoPickerActivity.newIntent(this, true ? takePhotoDir : null, layout.getMaxItemCount() - layout.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            EasyPermissions.requestPermissions(this, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }
}

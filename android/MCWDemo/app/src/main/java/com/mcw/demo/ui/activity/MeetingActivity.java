package com.mcw.demo.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.google.gson.Gson;
import com.igeek.hfrecyleviewlib.HFGridMultiTypeGapDecoration;
import com.igeek.hfrecyleviewlib.HFMultiTypeRecyAdapter;
import com.igeek.hfrecyleviewlib.HolderTypeData;
import com.liuguangqiang.materialdialog.MaterialDialog;
import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.api.DemoApiFactory;
import com.mcw.demo.config.Constant;
import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingDetailEntity;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.model.SelectedUserListEntity;
import com.mcw.demo.model.SignInfoEntity;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.model.UserInfo;
import com.mcw.demo.model.VoteInfoEntity;
import com.mcw.demo.ui.adapter.GridMuiltTypeSpanSizeLookup;
import com.mcw.demo.ui.adapter.PeopleSelectAdapterListener;
import com.mcw.demo.ui.adapter.bean.MeetingBaseInfoType;
import com.mcw.demo.ui.adapter.bean.SelectedUserType;
import com.mcw.demo.ui.adapter.bean.SummaryInfoType;
import com.mcw.demo.ui.adapter.bean.VoteInfoType;
import com.mcw.demo.util.StringUtils;
import com.mcw.demo.util.ToastMaster;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerPreviewActivity;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import rx.Observable;
import rx.Subscriber;

public class MeetingActivity extends BaseActivity implements SelectedUserType.SelectedUserTypeSubViewOnClickListener, SummaryInfoType.NinePhotoLayoutListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener, PeopleSelectAdapterListener {

    public static final int MODEL_TYPE_NOT_DEFINE = 0x1000;
    public static final int MODEL_TYPE_CREATE_MEETING = 0x1001;  //创建会议
    public static final int MODEL_TYPE_VIEW_DETAIL = 0x1002;     //会议完全结束后查看会议详情
    public static final int MODEL_TYPE_INPUT_SUMMARY = 0x1003;  //录入会议纪要
    public static final int MODEL_TYPE_START_SIGN_IN = 0x1004;  //打开二维码
    public static final int MODEL_TYPE_IN_MEETING = 0x1005;  //扫描二维码
    public static final int MODEL_TYPE_OPEN_SIGN_IN = 0x1006;  //打开签到

    public static final int TYPE_MEETING_BASE_INFO = 0x2001;
    public static final int TYPE_SELECTED_USER = 0x2002;
    public static final int TYPE_VOTE_LIST = 0x2003;
    public static final int TYPE_SUMMARY_INFO = 0x2004;

    private static final int REQUEST_CODE_PHOTO_PREVIEW = 0x3001;
    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 0x3002;
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 0x3003;

    @BindView(R.id.meeting_action_btn)
    Button meetingActionBtn;
    @BindView(R.id.content_rv)
    RecyclerView contentRv;
    HFMultiTypeRecyAdapter adapter;

    private String meetingId, createdBy, statusCode;
    private int modelType;
    List<HolderTypeData> datas;

    private List<MyVoteItemEntity> myVoteList;
    private List<SelectedUserEntity> selectedUserList;
    private MeetingBaseInfoEntity meetingBaseInfoEntity;
    private SelectedUserListEntity selectedUserListEntity;
    private SummaryInfoEntity summaryInfoEntity;
    private MeetingActivity mContext;

    private View.OnTouchListener customTimeEditViewOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                switch (view.getId()) {
                    case R.id.plan_start_date_et:
                    case R.id.real_start_date_et:
                    case R.id.plan_end_date_et:
                    case R.id.real_end_date_et:
                        openDatePicker(view.getId());
                        break;
                    case R.id.plan_start_time_et:
                    case R.id.plan_end_time_et:
                    case R.id.real_start_time_et:
                    case R.id.real_end_time_et:
                        openTimePicker(view.getId());
                        break;
                    default:
                        break;
                }
            }
            return false;
        }
    };

    private View.OnClickListener onAddVoteBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CreateVoteActivity.navToCreateVote(mContext).subscribe(new Subscriber<ActivityResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }

                @Override
                public void onNext(ActivityResult activityResult) {
                    if (activityResult.isOk()) {
                        MyVoteItemEntity myVoteItemEntity = new MyVoteItemEntity();
                        myVoteItemEntity.setUserId(UserInfo.getInstance().getId());
                        myVoteItemEntity.setVoteId(UUID.randomUUID().toString());
                        myVoteItemEntity.setStatusCode("INVOTING");
                        myVoteItemEntity.setVoteContent(activityResult.getData().getStringExtra("voteContent"));
                        myVoteItemEntity.setAnonymity(activityResult.getData().getIntExtra("noName", 1));
                        myVoteList.add(myVoteItemEntity);
                        adapter.notifyItemChanged(2);
                    }
                }
            });
        }
    };

    public static Observable<ActivityResult> navToCreateMeeting(Activity activity) {
        Intent intent = new Intent(activity, MeetingActivity.class);
        intent.putExtra("modelType", MODEL_TYPE_CREATE_MEETING);
        return RxActivityResult.getInstance(activity).from(activity).startActivityForResult(intent, Constant.START_ACTIVITY_FLAG_NAV_TO_CREATE_MEETING);
    }

    public static Observable<ActivityResult> navToViewMeetingDetail(Activity activity, String meetingId, String createdBy, String statusCode) {
        Intent intent = new Intent(activity, MeetingActivity.class);
        intent.putExtra("modelType", MODEL_TYPE_VIEW_DETAIL);
        intent.putExtra("meetingId", meetingId);
        intent.putExtra("createdBy", createdBy);
        intent.putExtra("statusCode", statusCode);
        return RxActivityResult.getInstance(activity).from(activity).startActivityForResult(intent, Constant.START_ACTIVITY_FLAG_NAV_TO_VIEW_MEETING_DETAIL);
    }

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_meeting;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mContext = this;
        modelType = getIntent().getIntExtra("modelType", MODEL_TYPE_NOT_DEFINE);
        meetingId = getIntent().getStringExtra("meetingId");
        createdBy = getIntent().getStringExtra("createdBy");
        statusCode = getIntent().getStringExtra("statusCode");

        datas = new ArrayList<>();
        adapter = new HFMultiTypeRecyAdapter();
        contentRv.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        GridMuiltTypeSpanSizeLookup spanSizeLookup = new GridMuiltTypeSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(spanSizeLookup);
        contentRv.setLayoutManager(gridLayoutManager);
        HFGridMultiTypeGapDecoration gapDecoration = new HFGridMultiTypeGapDecoration(8);
        gapDecoration.setOffsetTopEnabled(false);
        contentRv.addItemDecoration(gapDecoration);
        adapter.addSubViewListener(R.id.add_vote_btn, onAddVoteBtnClick);

        initData();
    }


    private void initData() {
        //基础信息
        MeetingBaseInfoType meetingBaseInfoType = new MeetingBaseInfoType(customTimeEditViewOnTouchListener);
        meetingBaseInfoEntity = new MeetingBaseInfoEntity();
        meetingBaseInfoEntity.setType(TYPE_MEETING_BASE_INFO);
        meetingBaseInfoEntity.setModelType(modelType);
        meetingBaseInfoType.setData(meetingBaseInfoEntity);
        datas.add(meetingBaseInfoType);
        //与会人员
        SelectedUserType selectedUserType = new SelectedUserType(mContext);
        selectedUserType.setPeopleSelectAdapterListener(mContext);
        selectedUserListEntity = new SelectedUserListEntity();
        selectedUserListEntity.setType(TYPE_SELECTED_USER);
        selectedUserListEntity.setModelType(modelType);
        selectedUserList = new ArrayList<>();
        selectedUserListEntity.setList(selectedUserList);
        selectedUserType.setData(selectedUserListEntity);
        datas.add(selectedUserType);

        if (modelType == MODEL_TYPE_CREATE_MEETING) {
            setTitle("新建会议");
            meetingActionBtn.setText("创建");
            //投票信息
            VoteInfoType voteInfoType = new VoteInfoType();
            VoteInfoEntity voteInfoEntity = new VoteInfoEntity();
            voteInfoEntity.setType(TYPE_VOTE_LIST);
            myVoteList = new ArrayList<>();
            voteInfoEntity.setList(myVoteList);
            voteInfoType.setData(voteInfoEntity);
            datas.add(voteInfoType);
            loadUsers();
        } else {
            setTitle("会议详情");
            if ("SUMMARY".equals(statusCode) || "FINISHED".equals(statusCode)) {
                if ("SUMMARY".equals(statusCode) && createdBy.equals(UserInfo.getInstance().getId())) {
                    modelType = MODEL_TYPE_INPUT_SUMMARY;
                }
                SummaryInfoType summaryInfoType = new SummaryInfoType(mContext, customTimeEditViewOnTouchListener);
                summaryInfoType.setPeopleSelectAdapterListener(mContext);
                summaryInfoEntity = new SummaryInfoEntity();
                summaryInfoEntity.setType(TYPE_SUMMARY_INFO);
                summaryInfoEntity.setModelType(modelType);
                summaryInfoType.setData(summaryInfoEntity);
                datas.add(summaryInfoType);
            }
            loadMeetingInfoFromNet();
        }
        adapter.refreshDatas(datas);

    }

    private void loadUsers() {
        DemoApiFactory.getInstance().getAllUser().subscribe(new Subscriber<List<SelectedUserEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<SelectedUserEntity> selectedUserEntities) {
                if (selectedUserEntities.size() > 0) {
                    selectedUserList.addAll(selectedUserEntities);
                    if (adapter.getDatas().size() > 1) {
                        adapter.notifyItemChanged(1);
                    }
                }
            }
        });
    }

    @Override
    public void onDeleteUser(View view) {
        selectedUserList.remove(Integer.parseInt(view.getTag().toString()));
        adapter.notifyItemChanged(1);
    }

    @OnClick(R.id.meeting_action_btn)
    public void meetingAction() {
        switch (modelType) {
            case MODEL_TYPE_CREATE_MEETING:
                createMeeting();
                break;
            case MODEL_TYPE_OPEN_SIGN_IN:
                startToSignIn();
                break;
            case MODEL_TYPE_START_SIGN_IN:
                String signId = "";
                for (SelectedUserEntity entity : selectedUserList) {
                    if (UserInfo.getInstance().getId().equals(entity.getUserId())) {
                        signId = entity.getSignId();
                        break;
                    }
                }
                ShowQRCodeActivity.navToShowQRCode(mContext, meetingId,signId).subscribe(new Subscriber<ActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActivityResult activityResult) {
                        if (activityResult.isOk()) {
                            ToastMaster.popToast(mContext, "签到成功");
                            setTitle("会议详情（已签到）");
                        } else {
                            ToastMaster.popToast(mContext, "未签到成功");
                        }
                    }
                });
                break;
            case MODEL_TYPE_IN_MEETING:
                ScanQRCodeActivity.navToScanQRCode(mContext, meetingId).subscribe(new Subscriber<ActivityResult>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(ActivityResult activityResult) {
                        if (activityResult.isOk()) {
                            String userId = activityResult.getData().getStringExtra("userId");
                            for (SelectedUserEntity entity : selectedUserListEntity.getList()) {
                                if (entity.getUserId().equals(userId)) {
                                    entity.setIsSigned(1);
                                    ToastMaster.popToast(mContext, entity.getName() + "已签到");
                                    adapter.notifyItemChanged(1);
                                    break;
                                }
                            }
                        }
                    }
                });
                break;
            case MODEL_TYPE_INPUT_SUMMARY:
                createSummary();
                break;
            default:
                break;
        }
    }

    private void startToSignIn() {
        //调用开始签到接口，改变会议状态
        DemoApiFactory.getInstance().startMeeting(meetingId).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    meetingBaseInfoEntity.setStatusCode("INMEETING");
                    configBottomBtn("INMEETING", meetingBaseInfoEntity.getCreatedBy());
                    adapter.notifyItemChanged(2);
                    ToastMaster.popToast(mContext, "准备签到");
                } else {
                    ToastMaster.popToast(mContext, "请求失败");
                }
            }
        });
    }

    private void createSummary() {
        String summaryInfoId = UUID.randomUUID().toString();
        meetingBaseInfoEntity.setSummaryInfoId(summaryInfoId);
        summaryInfoEntity.setSummaryInfoId(summaryInfoId);
        DemoApiFactory.getInstance().createSummary(summaryInfoEntity, meetingId).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    meetingBaseInfoEntity.setStatusCode("FINISHED");
                    configBottomBtn("FINISHED", meetingBaseInfoEntity.getCreatedBy());
                    adapter.notifyItemChanged(2);
                    ToastMaster.popToast(mContext, "上传成功");
                } else {
                    ToastMaster.popToast(mContext, "上传失败");
                }
            }
        });
    }

    private void createMeeting() {
        meetingId = UUID.randomUUID().toString();
        meetingBaseInfoEntity.setMeetingId(meetingId);
        meetingBaseInfoEntity.setStatusCode("CREATED");
        meetingBaseInfoEntity.setCreatedBy(UserInfo.getInstance().getId());
        DemoApiFactory.getInstance().createMeeting(meetingBaseInfoEntity).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastMaster.popToast(mContext, "基础信息录入失败");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ToastMaster.popToast(mContext, "基础信息录入成功");

                    DemoApiFactory.getInstance().createVoteList(meetingBaseInfoEntity.getMeetingId(), myVoteList).subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastMaster.popToast(mContext, "投票录入失败");
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean.booleanValue()) {
                                ToastMaster.popToast(mContext, "投票信息录入成功");
                                Intent intent = getIntent();
                                intent.putExtra("meetingInfo", new Gson().toJson(meetingBaseInfoEntity));
                                mContext.setResult(RESULT_OK, intent);
                                mContext.finish();
                            } else {
                                ToastMaster.popToast(mContext, "投票录入失败");
                            }
                        }
                    });
                } else {
                    ToastMaster.popToast(mContext, "基础信息录入失败");
                }
            }
        });
    }

    private void endMeeting() {
        DemoApiFactory.getInstance().endMeeting(meetingId).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    SummaryInfoType summaryInfoType = new SummaryInfoType(mContext, customTimeEditViewOnTouchListener);
                    summaryInfoType.setPeopleSelectAdapterListener(mContext);
                    summaryInfoEntity = new SummaryInfoEntity();
                    summaryInfoEntity.setType(TYPE_SUMMARY_INFO);
                    summaryInfoType.setData(summaryInfoEntity);
                    meetingBaseInfoEntity.setStatusCode("SUMMARY");
                    configBottomBtn("SUMMARY", meetingBaseInfoEntity.getCreatedBy());
                    adapter.appendData(summaryInfoType);
                    contentRv.smoothScrollToPosition(2);
                } else {
                    ToastMaster.popToast(mContext, "请求失败");
                }
            }
        });
    }

    private void loadMeetingInfoFromNet() {
        DemoApiFactory.getInstance().getMeetingDetail(meetingId).subscribe(new Subscriber<List<MeetingDetailEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastMaster.popToast(mContext, "无会议信息");
            }

            @Override
            public void onNext(List<MeetingDetailEntity> meetingBaseInfoEntities) {
                if (meetingBaseInfoEntities.size() > 0) {
                    MeetingBaseInfoEntity tmpBaseInfoEntity = meetingBaseInfoEntities.get(0).getBaseInfo();
                    meetingBaseInfoEntity.setTitle(tmpBaseInfoEntity.getTitle());
                    meetingBaseInfoEntity.setLocation(tmpBaseInfoEntity.getLocation());
                    meetingBaseInfoEntity.setEndDatePlan(tmpBaseInfoEntity.getEndDatePlan());
                    meetingBaseInfoEntity.setStartDatePlan(tmpBaseInfoEntity.getStartDatePlan());
                    meetingBaseInfoEntity.setMeetingRequire(tmpBaseInfoEntity.getMeetingRequire());
                    meetingBaseInfoEntity.setStatusCode(tmpBaseInfoEntity.getStatusCode());
                    meetingBaseInfoEntity.setCreatedBy(tmpBaseInfoEntity.getCreatedBy());
                    meetingBaseInfoEntity.setSummaryInfoId(tmpBaseInfoEntity.getSummaryInfoId());
                    meetingBaseInfoEntity.setMeetingId(tmpBaseInfoEntity.getMeetingId());
                    meetingBaseInfoEntity.setCreationDate(tmpBaseInfoEntity.getCreationDate());
                    List<SignInfoEntity> infos = meetingBaseInfoEntities.get(0).getParticipants();
                    if (infos != null && infos.size() > 0) {
                        for (SignInfoEntity tmp : infos) {
                            SelectedUserEntity entity = new SelectedUserEntity();
                            entity.setSignId(tmp.getSignId());
                            entity.setUserId(tmp.getParticipantId());
                            entity.setName(tmp.getName());
                            entity.setPhotoUrl(tmp.getPhotoUrl());
                            entity.setPhone(tmp.getPhone());
                            if (tmp.getSignDate() != null) {
                                entity.setIsSigned(1);
                            } else {
                                entity.setIsSigned(0);
                            }
                            selectedUserList.add(entity);
                        }
                    }
                    adapter.notifyItemRangeChanged(0, 2);
                    if (!StringUtils.isEmpty(meetingBaseInfoEntity.getSummaryInfoId())) {
                        getSummaryInfo();
                    }
                    configBottomBtn(meetingBaseInfoEntity.getStatusCode(), meetingBaseInfoEntity.getCreatedBy());
                } else {
                    ToastMaster.popToast(mContext, "无会议记录");
                }
            }
        });
    }

    private void createVote() {
        CreateVoteActivity.navToCreateVote(mContext).subscribe(new Subscriber<ActivityResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(ActivityResult activityResult) {
                if (activityResult.isOk()) {
                    MyVoteItemEntity myVoteItemEntity = new MyVoteItemEntity();
                    myVoteItemEntity.setUserId(UserInfo.getInstance().getId());
                    myVoteItemEntity.setVoteId(UUID.randomUUID().toString());
                    myVoteItemEntity.setStatusCode("INVOTING");
                    myVoteItemEntity.setVoteContent(activityResult.getData().getStringExtra("voteContent"));
                    myVoteItemEntity.setAnonymity(activityResult.getData().getIntExtra("noName", 1));

                    DemoApiFactory.getInstance().createVote(meetingId, myVoteItemEntity).subscribe(new Subscriber<Boolean>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            ToastMaster.popToast(mContext, "创建投票失败");
                        }

                        @Override
                        public void onNext(Boolean aBoolean) {
                            if (aBoolean.booleanValue()) {
                                MyVoteActivity.navToMyVote(mContext, meetingId);
                            } else {
                                ToastMaster.popToast(mContext, "创建投票失败");
                            }
                        }
                    });

                }
            }
        });
    }

    private void getSummaryInfo() {

        DemoApiFactory.getInstance().getSummaryInfo(meetingBaseInfoEntity.getSummaryInfoId()).subscribe(new Subscriber<List<SummaryInfoEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastMaster.popToast(mContext, "获取会议纪要失败");
            }

            @Override
            public void onNext(List<SummaryInfoEntity> summaryInfoEntities) {
                if (summaryInfoEntities != null && summaryInfoEntities.size() > 0) {
                    SummaryInfoEntity tmp = summaryInfoEntities.get(0);
                    summaryInfoEntity.setMeetingRecorder(tmp.getMeetingRecorder());
                    summaryInfoEntity.setSummaryInfoId(tmp.getSummaryInfoId());
                    summaryInfoEntity.setMeetingCompere(tmp.getMeetingCompere());
                    summaryInfoEntity.setRealEndDate(tmp.getRealEndDate());
                    summaryInfoEntity.setRealStartDate(tmp.getRealStartDate());
                    summaryInfoEntity.setInvitedUsers(tmp.getInvitedUsers());
                    summaryInfoEntity.setMeetingPics(tmp.getMeetingPics());
//                                String[] invitedUsers = summaryInfoEntity.getInvitedUsers().split(",");
//                                String[] meetingPics = summaryInfoEntity.getMeetingPics().split(",");
                    adapter.notifyItemChanged(2);
                }
            }
        });
    }

    private void configBottomBtn(String statusCode, String createdBy) {
        //获取会议信息，根据会议信息设置按钮状态 CREATED/INMEETING/SUMMARY/FINISHED
        String buttonText = "";
        switch (statusCode) {
            case "CREATED":
                if (createdBy.equals(UserInfo.getInstance().getId())) {
                    buttonText = "开始签到";
                    modelType = MODEL_TYPE_OPEN_SIGN_IN;
                } else {
                    modelType = MODEL_TYPE_VIEW_DETAIL;
                    meetingActionBtn.setVisibility(View.GONE);
                }
                break;
            case "INMEETING":
                if (createdBy.equals(UserInfo.getInstance().getId())) {
                    buttonText = "扫描二维码";
                    modelType = MODEL_TYPE_IN_MEETING;
                } else {
                    buttonText = "打开二维码";
                    modelType = MODEL_TYPE_START_SIGN_IN;
                }
                break;
            case "SUMMARY":
                if (createdBy.equals(UserInfo.getInstance().getId())) {
                    buttonText = "上传会议纪要";
                    modelType = MODEL_TYPE_INPUT_SUMMARY;
                } else {
                    modelType = MODEL_TYPE_VIEW_DETAIL;
                    meetingActionBtn.setVisibility(View.GONE);
                }
                break;
            case "FINISHED":
                modelType = MODEL_TYPE_VIEW_DETAIL;
                meetingActionBtn.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        meetingActionBtn.setText(buttonText);
        changeOptionsMenu();
        if (meetingBaseInfoEntity != null) {
            meetingBaseInfoEntity.setModelType(modelType);
        }
        if (summaryInfoEntity != null) {
            summaryInfoEntity.setModelType(modelType);
        }
        if (selectedUserListEntity != null) {
            selectedUserListEntity.setModelType(modelType);
        }
    }

    @Override
    public void onMeetingPicDelete(BGASortableNinePhotoLayout layout, int position) {
        layout.removeItem(position);
    }

    @Override
    public void onMeetingPicClick(BGASortableNinePhotoLayout layout, int position, String model, ArrayList<String> models) {
        startActivityForResult(BGAPhotoPickerPreviewActivity.newIntent(mContext, layout.getMaxItemCount(), models, models, position, false), REQUEST_CODE_PHOTO_PREVIEW);
    }

    @Override
    @AfterPermissionGranted(REQUEST_CODE_PERMISSION_PHOTO_PICKER)
    public void onMeetingPicAdd(final BGASortableNinePhotoLayout layout) {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // 拍照后照片的存放目录，改成你自己拍照后要存放照片的目录。如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(Environment.getExternalStorageDirectory(), "BGAPhotoPickerTakePhoto");
            RxActivityResult.getInstance(DemoApplication.getInstance().getApplicationContext())
                    .from(mContext)
                    .startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext, true ? takePhotoDir : null, layout.getMaxItemCount() - layout.getItemCount(), null, false), REQUEST_CODE_CHOOSE_PHOTO)
                    .subscribe(new Subscriber<ActivityResult>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ActivityResult activityResult) {
                            if (activityResult.isOk())
                                layout.addMoreData(BGAPhotoPickerActivity.getSelectedImages(activityResult.getData()));
                        }
                    });
        } else {
            EasyPermissions.requestPermissions(mContext, "图片选择需要以下权限:\n\n1.访问设备上的照片\n\n2.拍照", REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    private Menu mMenu;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenu = menu;
        changeOptionsMenu();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.my_vote) {
            MyVoteActivity.navToMyVote(mContext, meetingId);
            return true;
        } else if (item.getItemId() == R.id.new_vote) {
            createVote();
            return true;
        } else if (item.getItemId() == R.id.end_meeting) {
            endMeeting();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            Intent i = getIntent();
            i.putExtra("newStatusCode", meetingBaseInfoEntity.getStatusCode());
            this.setResult(RESULT_CANCELED, i);
            this.finish(); // back button
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void changeOptionsMenu() {
        if (mMenu == null)
            return;
        mMenu.clear();
        switch (modelType) {
            case MODEL_TYPE_NOT_DEFINE:
            case MODEL_TYPE_CREATE_MEETING:
                break;
            case MODEL_TYPE_VIEW_DETAIL:
            case MODEL_TYPE_INPUT_SUMMARY:
                getMenuInflater().inflate(R.menu.meeting_detail_finished_meeting, mMenu);
                break;
            case MODEL_TYPE_START_SIGN_IN:
            case MODEL_TYPE_OPEN_SIGN_IN:
                if (UserInfo.getInstance().getId().equals(createdBy)) {
                    getMenuInflater().inflate(R.menu.meeting_detail_not_start_meeting, mMenu);
                } else {
                    getMenuInflater().inflate(R.menu.meeting_detail_finished_meeting, mMenu);
                }
                break;
            case MODEL_TYPE_IN_MEETING:
                if (!UserInfo.getInstance().getId().equals(createdBy)) {
                    getMenuInflater().inflate(R.menu.meeting_detail_participation_action, mMenu);
                } else {
                    getMenuInflater().inflate(R.menu.meeting_detail_owner_action, mMenu);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        Calendar newDay = Calendar.getInstance();
        int evId = Integer.parseInt(dialog.getTag());
        switch (evId) {
            case R.id.plan_start_date_et:
                newDay.setTimeInMillis(meetingBaseInfoEntity.getStartDatePlan());
                newDay.set(Calendar.YEAR, year);
                newDay.set(Calendar.MONTH, monthOfYear);
                newDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                meetingBaseInfoEntity.setStartDatePlan(newDay.getTimeInMillis());
                adapter.notifyItemChanged(0);
                break;
            case R.id.real_start_date_et:
                newDay.setTimeInMillis(summaryInfoEntity.getRealStartDate());
                newDay.set(Calendar.YEAR, year);
                newDay.set(Calendar.MONTH, monthOfYear);
                newDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                summaryInfoEntity.setRealStartDate(newDay.getTimeInMillis());
                adapter.notifyItemChanged(2);
                break;
            case R.id.plan_end_date_et:
                newDay.setTimeInMillis(meetingBaseInfoEntity.getEndDatePlan());
                newDay.set(Calendar.YEAR, year);
                newDay.set(Calendar.MONTH, monthOfYear);
                newDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                meetingBaseInfoEntity.setEndDatePlan(newDay.getTimeInMillis());
                adapter.notifyItemChanged(0);
                break;
            case R.id.real_end_date_et:
                newDay.setTimeInMillis(summaryInfoEntity.getRealEndDate());
                newDay.set(Calendar.YEAR, year);
                newDay.set(Calendar.MONTH, monthOfYear);
                newDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                summaryInfoEntity.setRealEndDate(newDay.getTimeInMillis());
                adapter.notifyItemChanged(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        Calendar newDay = Calendar.getInstance();
        int evId = Integer.parseInt(dialog.getTag());
        switch (evId) {
            case R.id.plan_start_time_et:
                newDay.setTimeInMillis(meetingBaseInfoEntity.getStartDatePlan());
                newDay.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDay.set(Calendar.MINUTE, minute);
                meetingBaseInfoEntity.setStartDatePlan(newDay.getTimeInMillis());
                adapter.notifyItemChanged(0);
                break;
            case R.id.plan_end_time_et:
                newDay.setTimeInMillis(meetingBaseInfoEntity.getEndDatePlan());
                newDay.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDay.set(Calendar.MINUTE, minute);
                meetingBaseInfoEntity.setEndDatePlan(newDay.getTimeInMillis());
                adapter.notifyItemChanged(0);
                break;
            case R.id.real_start_time_et:
                newDay.setTimeInMillis(summaryInfoEntity.getRealStartDate());
                newDay.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDay.set(Calendar.MINUTE, minute);
                summaryInfoEntity.setRealStartDate(newDay.getTimeInMillis());
                adapter.notifyItemChanged(2);
                break;
            case R.id.real_end_time_et:
                newDay.setTimeInMillis(summaryInfoEntity.getRealEndDate());
                newDay.set(Calendar.HOUR_OF_DAY, hourOfDay);
                newDay.set(Calendar.MINUTE, minute);
                summaryInfoEntity.setRealEndDate(newDay.getTimeInMillis());
                adapter.notifyItemChanged(2);
                break;
            default:
                break;
        }
    }

    private CalendarDatePickerDialogFragment cdp;

    private void openDatePicker(int evId) {
        if (cdp == null) {
            cdp = new CalendarDatePickerDialogFragment()
                    .setThemeLight()
                    .setOnDateSetListener(mContext);
        }
        cdp.show(getSupportFragmentManager(), String.valueOf(evId));
    }

    private RadialTimePickerDialogFragment rtpd;

    private void openTimePicker(int evId) {
        if (rtpd == null) {
            rtpd = new RadialTimePickerDialogFragment()
                    .setOnTimeSetListener(mContext)
                    .setThemeLight();
        }
        rtpd.show(getSupportFragmentManager(), String.valueOf(evId));
    }

    @Override
    public void onClickPlusItem() {
        UserPickerActivity.navToSelectPeople(mContext);
    }


    private MaterialDialog phoneDialog = null;

    @Override
    public void onClickNormalItem(final SelectedUserEntity entity) {
        ToastMaster.popToast(mContext, entity.getName());
        if (phoneDialog == null) {
            phoneDialog = new MaterialDialog.Builder(mContext).cancelable(true)
                    .setMessage("确认呼叫 " + entity.getName() + "(" + entity.getPhone() + ")" + " ?")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + entity.getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .build();
        }
        phoneDialog.show();
    }
}

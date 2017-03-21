package com.mcw.demo.api;

import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingDetailEntity;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.model.UserInfo;
import com.mcw.demo.model.VoteDetailEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import rx.Observable;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/14
 */
public class DemoApiFactory extends ApiFactory {
    private static DemoApiFactory mInstance;
    private static ApiService apiService;

    public static DemoApiFactory getInstance() {
        if (mInstance == null) {
            apiService = getService(ApiService.class);
            mInstance = new DemoApiFactory();
        }
        return mInstance;
    }

    public Observable<List<MeetingListItemEntity>> getMeetingList(String userId, int pageNo, int pageSize) {
        return apiService.getMeetingList(userId, pageNo, pageSize).map(new HttpResultFunc<List<MeetingListItemEntity>>()).compose(SchedulersCompat.<List<MeetingListItemEntity>>applyExecutorSchedulers());
    }

    public Observable<Boolean> createMeeting(MeetingBaseInfoEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", entity.getMeetingId());
        map.put("userId", UserInfo.getInstance().getId());
        map.put("title", entity.getTitle());
        map.put("startDatePlan", entity.getStartDatePlan());
        map.put("endDatePlan", entity.getEndDatePlan());
        map.put("meetingRequire", entity.getMeetingRequire());
        map.put("location", entity.getLocation());
        return apiService.createMeeting(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<Boolean> startMeeting(String meetingId) {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("userId", UserInfo.getInstance().getId());
        return apiService.startMeeting(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<Boolean> endMeeting(String meetingId) {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("userId", UserInfo.getInstance().getId());
        return apiService.endMeeting(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<List<MeetingDetailEntity>> getMeetingDetail(String meetingId) {
        return apiService.getMeetingDetail(meetingId).map(new HttpResultFunc<List<MeetingDetailEntity>>()).compose(SchedulersCompat.<List<MeetingDetailEntity>>applyExecutorSchedulers());
    }

    public Observable<List<SelectedUserEntity>> getAllUser() {
        return apiService.getAllUser().map(new HttpResultFunc<List<SelectedUserEntity>>()).compose(SchedulersCompat.<List<SelectedUserEntity>>applyExecutorSchedulers());
    }

    public Observable<List<MyVoteItemEntity>> getVoteList(String meetingId) {
        return apiService.getVoteList(meetingId).map(new HttpResultFunc<List<MyVoteItemEntity>>()).compose(SchedulersCompat.<List<MyVoteItemEntity>>applyExecutorSchedulers());
    }


    public Observable<Boolean> createSummary(SummaryInfoEntity entity, String meetingId) {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("summaryInfoId", entity.getSummaryInfoId());
        map.put("userId", UserInfo.getInstance().getId());
        map.put("realStartDate", entity.getRealStartDate());
        map.put("realEndDate", entity.getRealEndDate());
        map.put("meetingCompere", entity.getMeetingCompere());
        map.put("meetingRecorder", entity.getMeetingRecorder());
        map.put("invitedUsers", entity.getInvitedUsers());
        map.put("meetingPics", entity.getMeetingPics());
        return apiService.createSummary(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }


    public Observable<List<SummaryInfoEntity>> getSummaryInfo(String summaryInfoId) {
        return apiService.getSummaryInfo(summaryInfoId).map(new HttpResultFunc<List<SummaryInfoEntity>>()).compose(SchedulersCompat.<List<SummaryInfoEntity>>applyExecutorSchedulers());
    }

    public Observable<Boolean> createVoteList(String meetingId, List<MyVoteItemEntity> list) {
        Map<String, Object> map = new HashMap<>();
        map.put("meetingId", meetingId);
        map.put("list", list);
        return apiService.createVoteList(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<List<VoteDetailEntity>> getVoteDetail(String voteId) {
        return apiService.getVoteDetail(voteId, UserInfo.getInstance().getId()).map(new HttpResultFunc<List<VoteDetailEntity>>()).compose(SchedulersCompat.<List<VoteDetailEntity>>applyExecutorSchedulers());
    }

    public Observable<Boolean> createVoteRecord(String resultCode, String voteId) {
        Map<String, Object> map = new HashMap<>();
        map.put("resultCode", resultCode);
        map.put("recordId", UUID.randomUUID().toString());
        map.put("voteId", voteId);
        map.put("userId", UserInfo.getInstance().getId());
        return apiService.createVoteRecord(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<Boolean> createVote(String meetingId, MyVoteItemEntity entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("voteId", entity.getVoteId());
        map.put("meetingId", meetingId);
        map.put("voteContent", entity.getVoteContent());
        map.put("anonymity", entity.getAnonymity());
        map.put("userId", UserInfo.getInstance().getId());
        return apiService.createVote(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<Boolean> meetingSign(String sign) {
        Map<String, Object> map = new HashMap<>();
        map.put("signId", sign);
        return apiService.meetingSign(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }


}

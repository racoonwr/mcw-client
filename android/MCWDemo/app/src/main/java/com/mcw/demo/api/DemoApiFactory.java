package com.mcw.demo.api;

import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.model.UserInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public Observable<List<MeetingListItemEntity>> getMeetingList(String userId) {
        return apiService.getMeetingList(userId).map(new HttpResultFunc<List<MeetingListItemEntity>>()).compose(SchedulersCompat.<List<MeetingListItemEntity>>applyExecutorSchedulers());
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

    public Observable<List<MeetingBaseInfoEntity>> getMeetingBaseInfo(String meetingId) {
        return apiService.getMeetingBaseInfo(meetingId).map(new HttpResultFunc<List<MeetingBaseInfoEntity>>()).compose(SchedulersCompat.<List<MeetingBaseInfoEntity>>applyExecutorSchedulers());
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
}

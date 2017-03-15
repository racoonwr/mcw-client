package com.mcw.demo.api;

import com.mcw.demo.model.MeetingBaseInfoEntity;
import com.mcw.demo.model.MeetingListItemEntity;

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

    public Observable<Boolean> createMeeting(MeetingBaseInfoEntity entity){
        Map<String,Object> map = new HashMap<>();
        map.put("meetingId",entity.getMeetingId());
        map.put("userId","d17948d8-0802-11e7-93ae-92361f002671");
        map.put("title",entity.getTitle());
        map.put("startDatePlan",entity.getStartDatePlan());
        map.put("endDatePlan",entity.getEndDatePlan());
        map.put("meetingRequire",entity.getRequire());
        map.put("location",entity.getLocation());
        return apiService.createMeeting(map).map(new HttpResultFunc<Boolean>()).compose(SchedulersCompat.<Boolean>applyExecutorSchedulers());
    }

    public Observable<List<MeetingBaseInfoEntity>> getMeetingBaseInfo(String meetingId) {
        return apiService.getMeetingBaseInfo(meetingId).map(new HttpResultFunc<List<MeetingBaseInfoEntity>>()).compose(SchedulersCompat.<List<MeetingBaseInfoEntity>>applyExecutorSchedulers());
    }
}

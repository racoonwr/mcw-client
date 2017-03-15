package com.mcw.demo.presenter;

import com.mcw.demo.api.DemoApiFactory;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.ui.fragment.IMeetingFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/1
 */
public class MeetingFragmentPresenter {

    private IMeetingFragment fragment;

    public MeetingFragmentPresenter(IMeetingFragment fragment) {
        this.fragment = fragment;
    }

    private int pageSize = 3;
    private int lastId = 0;
    private int maxId = 5;
    private boolean hasMoreData = true;

    public void loadNetData(){
        DemoApiFactory.getInstance().getMeetingList("d17948d8-0802-11e7-93ae-92361f002671").subscribe(new Subscriber<List<MeetingListItemEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                fragment.finishLoadNetMeetingData(null);
            }

            @Override
            public void onNext(List<MeetingListItemEntity> meetingListItemEntities) {
                fragment.finishLoadNetMeetingData(meetingListItemEntities);
            }
        });
//        List<MeetingListItemEntity> moreData = new ArrayList<>();
//        MeetingListItemEntity entity = new MeetingListItemEntity();
//        entity.setStartDatePlan(1111111l);
//        entity.setEndDatePlan(1111111l);
//        entity.setMeetingId(String.valueOf(++maxId + 1));
//        entity.setLocation("会议室2");
//        entity.setStatusCode("1");
//        entity.setCreatedBy("1");
//        moreData.add(entity);
//        MeetingListItemEntity entity2 = new MeetingListItemEntity();
//        entity2.setStartDatePlan(1111111l);
//        entity2.setEndDatePlan(1111111l);
//        entity2.setMeetingId(""+maxId);
//        entity2.setLocation("会议室3");
//        entity2.setStatusCode("1");
//        entity2.setCreatedBy("1");
//        moreData.add(entity2);
//        maxId++;
//        fragment.finishLoadNetMeetingData(moreData);
    }

    public void loadLocalData() {
        //加载本地数据
        fragment.startLoadLocalMeetingList();
        List<MeetingListItemEntity> moreData = testData();
        if (moreData.size() < pageSize){
            fragment.noMoreLocalMeetingData();
        }
        fragment.finishLoadLocalMeetingList(moreData,hasMoreData);
    }

    private List<MeetingListItemEntity> testData() {
        List<MeetingListItemEntity> meetings = new ArrayList<>();
        if (hasMoreData) {
            if (lastId == 0) {
                MeetingListItemEntity entity = new MeetingListItemEntity();
                entity.setStartDatePlan(1111111l);
                entity.setEndDatePlan(1111111l);
                entity.setMeetingId("5");
                entity.setLocation("会议室2");
                entity.setStatusCode("1");
                entity.setCreatedBy("1");
                meetings.add(entity);
                MeetingListItemEntity entity2 = new MeetingListItemEntity();
                entity2.setStartDatePlan(1111111l);
                entity2.setEndDatePlan(1111111l);
                entity2.setMeetingId("4");
                entity2.setLocation("会议室3");
                entity2.setStatusCode("1");
                entity2.setCreatedBy("1");
                meetings.add(entity2);
                MeetingListItemEntity entity3 = new MeetingListItemEntity();
                entity3.setStartDatePlan(1111111l);
                entity3.setEndDatePlan(1111111l);
                entity3.setMeetingId("3");
                entity3.setLocation("会议室1");
                entity3.setStatusCode("1");
                entity3.setCreatedBy("1");
                meetings.add(entity3);
                lastId = 3;
                maxId = 5;
            } else {
                MeetingListItemEntity entity = new MeetingListItemEntity();
                entity.setStartDatePlan(1111111l);
                entity.setEndDatePlan(1111111l);
                entity.setMeetingId("2");
                entity.setLocation("会议室2");
                entity.setStatusCode("1");
                entity.setCreatedBy("1");
                meetings.add(entity);
                MeetingListItemEntity entity2 = new MeetingListItemEntity();
                entity2.setStartDatePlan(1111111l);
                entity2.setEndDatePlan(1111111l);
                entity2.setMeetingId("1");
                entity2.setLocation("会议室3");
                entity2.setStatusCode("1");
                entity2.setCreatedBy("1");
                meetings.add(entity2);
                lastId = 1;
            }
        }
        if (meetings.size() < pageSize) {
            hasMoreData = false;
        }
        return meetings;
    }
}

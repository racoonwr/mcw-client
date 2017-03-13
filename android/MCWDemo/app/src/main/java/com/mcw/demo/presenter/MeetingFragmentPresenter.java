package com.mcw.demo.presenter;

import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.ui.fragment.IMeetingFragment;

import java.util.ArrayList;
import java.util.List;

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
        List<MeetingListItemEntity> moreData = new ArrayList<>();
        MeetingListItemEntity entity = new MeetingListItemEntity();
        entity.setMeetingDate(1111111l);
        entity.setCreationDate(1111111l);
        entity.setMeetingId(++maxId + 1);
        entity.setMeetingLocation("会议室2");
        entity.setMeetingStatus(1);
        entity.setCreator(1);
        moreData.add(entity);
        MeetingListItemEntity entity2 = new MeetingListItemEntity();
        entity2.setMeetingDate(1111111l);
        entity2.setCreationDate(1111111l);
        entity2.setMeetingId(maxId);
        entity2.setMeetingLocation("会议室3");
        entity2.setMeetingStatus(1);
        entity2.setCreator(1);
        moreData.add(entity2);
        maxId++;
        fragment.finishLoadNetMeetingData(moreData);
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
                entity.setMeetingDate(1111111l);
                entity.setCreationDate(1111111l);
                entity.setMeetingId(5);
                entity.setMeetingLocation("会议室2");
                entity.setMeetingStatus(1);
                entity.setCreator(1);
                meetings.add(entity);
                MeetingListItemEntity entity2 = new MeetingListItemEntity();
                entity2.setMeetingDate(1111111l);
                entity2.setCreationDate(1111111l);
                entity2.setMeetingId(4);
                entity2.setMeetingLocation("会议室3");
                entity2.setMeetingStatus(1);
                entity2.setCreator(1);
                meetings.add(entity2);
                MeetingListItemEntity entity3 = new MeetingListItemEntity();
                entity3.setMeetingDate(1111111l);
                entity3.setCreationDate(1111111l);
                entity3.setMeetingId(3);
                entity3.setMeetingLocation("会议室1");
                entity3.setMeetingStatus(1);
                entity3.setCreator(1);
                meetings.add(entity3);
                lastId = 3;
                maxId = 5;
            } else {
                MeetingListItemEntity entity = new MeetingListItemEntity();
                entity.setMeetingDate(1111111l);
                entity.setCreationDate(1111111l);
                entity.setMeetingId(2);
                entity.setMeetingLocation("会议室2");
                entity.setMeetingStatus(1);
                entity.setCreator(1);
                meetings.add(entity);
                MeetingListItemEntity entity2 = new MeetingListItemEntity();
                entity2.setMeetingDate(1111111l);
                entity2.setCreationDate(1111111l);
                entity2.setMeetingId(1);
                entity2.setMeetingLocation("会议室3");
                entity2.setMeetingStatus(1);
                entity2.setCreator(1);
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

package com.mcw.demo.ui.fragment;

import com.mcw.demo.model.MeetingListItemEntity;

import java.util.List;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public interface IMeetingFragment {
    void gotoMeetingDetail(int meetingId);
    void loadMoreMeetingInfo(List<MeetingListItemEntity> newMeetings);
    void newMeeting();


    void finishLoadLocalMeetingList(List<MeetingListItemEntity> localMeetings, boolean hasMore);
    void noMoreLocalMeetingData();
    void startLoadLocalMeetingList();

    void finishLoadNetMeetingData(List<MeetingListItemEntity> newMeetingData);
}

package com.mcw.demo.model;

import java.util.List;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/10
 */
public class SummaryInfoEntity {
    private int type;
    private String meetingCompere;
    private String meetingRecorder;
    private long realStartDate;
    private long realEndDate;
    private List<Object> meetingPics;
    private List<SelectedUserEntity> invitedUsers;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMeetingCompere() {
        return meetingCompere;
    }

    public void setMeetingCompere(String meetingCompere) {
        this.meetingCompere = meetingCompere;
    }

    public String getMeetingRecorder() {
        return meetingRecorder;
    }

    public void setMeetingRecorder(String meetingRecorder) {
        this.meetingRecorder = meetingRecorder;
    }

    public long getRealStartDate() {
        return realStartDate;
    }

    public void setRealStartDate(long realStartDate) {
        this.realStartDate = realStartDate;
    }

    public long getRealEndDate() {
        return realEndDate;
    }

    public void setRealEndDate(long realEndDate) {
        this.realEndDate = realEndDate;
    }

    public List<Object> getMeetingPics() {
        return meetingPics;
    }

    public void setMeetingPics(List<Object> meetingPics) {
        this.meetingPics = meetingPics;
    }

    public List<SelectedUserEntity> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(List<SelectedUserEntity> invitedUsers) {
        this.invitedUsers = invitedUsers;
    }
}

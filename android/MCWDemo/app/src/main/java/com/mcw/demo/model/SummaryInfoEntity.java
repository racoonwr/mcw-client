package com.mcw.demo.model;

import com.google.gson.annotations.Expose;

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
    @Expose
    private int type;
    @Expose
    private int modelType;
    private String summaryInfoId;
    private long realStartDate;
    private long realEndDate;
    private String meetingCompere;
    private String meetingRecorder;
    private String invitedUsers;
    private String meetingPics;
    @Expose
    private List<Object> meetingPicsList;
    @Expose
    private List<SelectedUserEntity> invitedUsersList;

    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getSummaryInfoId() {
        return summaryInfoId;
    }

    public void setSummaryInfoId(String summaryInfoId) {
        this.summaryInfoId = summaryInfoId;
    }

    public String getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(String invitedUsers) {
        this.invitedUsers = invitedUsers;
    }

    public String getMeetingPics() {
        return meetingPics;
    }

    public void setMeetingPics(String meetingPics) {
        this.meetingPics = meetingPics;
    }

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

    public List<Object> getMeetingPicsList() {
        return meetingPicsList;
    }

    public void setMeetingPicsList(List<Object> meetingPicsList) {
        this.meetingPicsList = meetingPicsList;
    }

    public List<SelectedUserEntity> getInvitedUsersList() {
        return invitedUsersList;
    }

    public void setInvitedUsersList(List<SelectedUserEntity> invitedUsersList) {
        this.invitedUsersList = invitedUsersList;
    }
}

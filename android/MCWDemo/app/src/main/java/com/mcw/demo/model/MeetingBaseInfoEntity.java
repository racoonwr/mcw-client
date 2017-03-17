package com.mcw.demo.model;

import com.google.gson.annotations.Expose;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/9
 */
public class MeetingBaseInfoEntity {

    @Expose
    private int type;
    @Expose
    private int modelType;
    private String meetingId;
    private String title;
    private String location;
    private String meetingRequire;
    private long startDatePlan;
    private long endDatePlan;
    private String statusCode;
    private String summaryInfoId;
    private String createdBy;
    private String creationDate;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getModelType() {
        return modelType;
    }

    public void setModelType(int modelType) {
        this.modelType = modelType;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getSummaryInfoId() {
        return summaryInfoId;
    }

    public void setSummaryInfoId(String summaryInfoId) {
        this.summaryInfoId = summaryInfoId;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetingRequire() {
        return meetingRequire;
    }

    public void setMeetingRequire(String meetingRequire) {
        this.meetingRequire = meetingRequire;
    }

    public long getStartDatePlan() {
        return startDatePlan;
    }

    public void setStartDatePlan(long startDatePlan) {
        this.startDatePlan = startDatePlan;
    }

    public long getEndDatePlan() {
        return endDatePlan;
    }

    public void setEndDatePlan(long endDatePlan) {
        this.endDatePlan = endDatePlan;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}

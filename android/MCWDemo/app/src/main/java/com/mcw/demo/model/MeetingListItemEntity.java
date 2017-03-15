package com.mcw.demo.model;

import java.io.Serializable;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public class MeetingListItemEntity implements Serializable {
    private String meetingId;
    private String createdBy;
    private String title;
    private long startDatePlan;
    private long endDatePlan;
    private String statusCode;
    private String location;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public long getEndDatePlan() {
        return endDatePlan;
    }

    public void setEndDatePlan(long endDatePlan) {
        this.endDatePlan = endDatePlan;
    }

    public long getStartDatePlan() {
        return startDatePlan;
    }

    public void setStartDatePlan(long startDatePlan) {
        this.startDatePlan = startDatePlan;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

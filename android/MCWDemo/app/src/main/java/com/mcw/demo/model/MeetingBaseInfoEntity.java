package com.mcw.demo.model;

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
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
    private String title;
    private String location;
    private String require;
    private long startDatePlan;
    private long endDatePlan;

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

    public String getRequire() {
        return require;
    }

    public void setRequire(String require) {
        this.require = require;
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
}

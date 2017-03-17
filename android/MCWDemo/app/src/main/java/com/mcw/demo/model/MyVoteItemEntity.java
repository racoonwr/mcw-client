package com.mcw.demo.model;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/6
 */
public class MyVoteItemEntity implements Serializable {
    private String voteContent;
    private String voteId;
    private String statusCode;
    @Expose
    private String voteCreatorName;
    private int anonymity;
    private String userId;

    public String getVoteContent() {
        return voteContent;
    }

    public void setVoteContent(String voteContent) {
        this.voteContent = voteContent;
    }

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getVoteCreatorName() {
        return voteCreatorName;
    }

    public void setVoteCreatorName(String voteCreatorName) {
        this.voteCreatorName = voteCreatorName;
    }

    public int getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(int anonymity) {
        this.anonymity = anonymity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

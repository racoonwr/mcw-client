package com.mcw.demo.model;

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
public class MyVoteItemEntity implements Serializable{
    private String voteTitle;
    private int voteId;
    private int voteStatus;
    private String voteCreatorName;

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public int getVoteStatus() {
        return voteStatus;
    }

    public void setVoteStatus(int voteStatus) {
        this.voteStatus = voteStatus;
    }

    public String getVoteCreatorName() {
        return voteCreatorName;
    }

    public void setVoteCreatorName(String voteCreatorName) {
        this.voteCreatorName = voteCreatorName;
    }
}

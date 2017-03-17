package com.mcw.demo.model;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/17
 */
public class VoteDetailEntity {


    /**
     * voteId : 1
     * voteContent : 1
     * anonymity : 1
     * statusCode : 1
     * countAgree : 0
     * countReject : 0
     * countGiveup : 0
     * countKeep : 0
     * hasVoted : 1
     */

    private String voteId;
    private String voteContent;
    private int anonymity;
    private String statusCode;
    private int countAgree;
    private int countReject;
    private int countGiveup;
    private int countKeep;
    private int hasVoted;

    public String getVoteId() {
        return voteId;
    }

    public void setVoteId(String voteId) {
        this.voteId = voteId;
    }

    public String getVoteContent() {
        return voteContent;
    }

    public void setVoteContent(String voteContent) {
        this.voteContent = voteContent;
    }

    public int getAnonymity() {
        return anonymity;
    }

    public void setAnonymity(int anonymity) {
        this.anonymity = anonymity;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public int getCountAgree() {
        return countAgree;
    }

    public void setCountAgree(int countAgree) {
        this.countAgree = countAgree;
    }

    public int getCountReject() {
        return countReject;
    }

    public void setCountReject(int countReject) {
        this.countReject = countReject;
    }

    public int getCountGiveup() {
        return countGiveup;
    }

    public void setCountGiveup(int countGiveup) {
        this.countGiveup = countGiveup;
    }

    public int getCountKeep() {
        return countKeep;
    }

    public void setCountKeep(int countKeep) {
        this.countKeep = countKeep;
    }

    public int getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(int hasVoted) {
        this.hasVoted = hasVoted;
    }
}

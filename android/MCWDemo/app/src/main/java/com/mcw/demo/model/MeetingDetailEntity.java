package com.mcw.demo.model;

import java.util.List;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/22
 */
public class MeetingDetailEntity {
    private MeetingBaseInfoEntity baseInfo;
    private List<SignInfoEntity> participants;

    public MeetingBaseInfoEntity getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(MeetingBaseInfoEntity baseInfo) {
        this.baseInfo = baseInfo;
    }

    public List<SignInfoEntity> getParticipants() {
        return participants;
    }

    public void setParticipants(List<SignInfoEntity> participants) {
        this.participants = participants;
    }
}

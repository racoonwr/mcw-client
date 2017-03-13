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
public class VoteInfoEntity {
    private int type;
    private List<MyVoteItemEntity> list;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MyVoteItemEntity> getList() {
        return list;
    }

    public void setList(List<MyVoteItemEntity> list) {
        this.list = list;
    }
}

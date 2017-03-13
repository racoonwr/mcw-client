package com.mcw.demo.model;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/8
 */
public class SortModelEntity {

    private int userId;
    private String sortLetter;
    private String userName;
    private int color;

    public String getSortLetter() {
        return sortLetter;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setSortLetter(String sortLetter) {
        this.sortLetter = sortLetter;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

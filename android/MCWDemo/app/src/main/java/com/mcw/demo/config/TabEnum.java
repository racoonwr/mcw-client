package com.mcw.demo.config;

import com.mcw.R;
import com.mcw.demo.ui.fragment.MeetingFragment;
import com.mcw.demo.ui.fragment.TabItemFragment;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public enum TabEnum {

    MEETING(0, R.string.tab_item_meeting, R.drawable.tab_icon_tweet,
            MeetingFragment.class),

    CONTACTS(1, R.string.tab_item_contacts, R.drawable.tab_icon_new,
            TabItemFragment.class),

    CIRCLE(2, R.string.tab_item_circle, R.drawable.tab_icon_explore,
            TabItemFragment.class);


    private int idx;
    private int resName;
    private int resIcon;
    private Class<?> clz;

    TabEnum(int idx, int resName, int resIcon, Class<?> clz) {
        this.idx = idx;
        this.resName = resName;
        this.resIcon = resIcon;
        this.clz = clz;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}

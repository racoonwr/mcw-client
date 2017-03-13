package com.mcw.demo.ui.widght;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public class CustomFragmentTabHost extends FragmentTabHost {

    private String mCurrentTag;
    private String mNoTabChangedTag;

    public CustomFragmentTabHost(Context context, AttributeSet attrs) {
        super(context,attrs);
    }

    @Override
    public void onTabChanged(String tag) {
        if (tag.equals(mNoTabChangedTag)) {
            setCurrentTabByTag(mCurrentTag);
        } else {
            super.onTabChanged(tag);
            mCurrentTag = tag;
        }
    }



    public void setNoTabChangedTag(String tag) {
        this.mNoTabChangedTag = tag;
    }
}

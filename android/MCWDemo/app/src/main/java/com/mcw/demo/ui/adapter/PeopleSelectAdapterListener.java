package com.mcw.demo.ui.adapter;

import com.mcw.demo.model.SelectedUserEntity;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/20
 */
public interface PeopleSelectAdapterListener {
    void onClickPlusItem();
    void onClickNormalItem(SelectedUserEntity entity);
}

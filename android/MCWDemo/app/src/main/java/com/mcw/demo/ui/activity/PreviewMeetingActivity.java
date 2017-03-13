package com.mcw.demo.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class PreviewMeetingActivity extends BaseActivity implements BasicRecyViewHolder.OnItemClickListener {
    private RecyclerView peopleSelectRv;
    PeopleSelectRecyclerViewAdapter adapter;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_preview_meeting;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("会议预览");
        peopleSelectRv = (RecyclerView) findViewById(R.id.people_select_rv);

        if (adapter == null) {
            adapter = new PeopleSelectRecyclerViewAdapter();
        }

        peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        peopleSelectRv.setLayoutManager(manager);

        List<SelectedUserEntity> list = new ArrayList<>();
        SelectedUserEntity entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        list.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        list.add(entity);
        adapter.appendDatas(list);

    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {

    }
}

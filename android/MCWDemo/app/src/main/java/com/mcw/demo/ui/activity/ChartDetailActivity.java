package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SelectedUserEntity;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartDetailActivity extends BaseActivity {
    int voteId = 0;
    PeopleSelectRecyclerViewAdapter agreeAdapter;
    PeopleSelectRecyclerViewAdapter rejectAdapter;
    PeopleSelectRecyclerViewAdapter giveUpAdapter;
    PeopleSelectRecyclerViewAdapter keepAdapter;

    @BindView(R.id.people_agree_rv)
    RecyclerView peopleAgreeRv;
    @BindView(R.id.people_reject_rv)
    RecyclerView peopleRejectRv;
    @BindView(R.id.people_give_up_rv)
    RecyclerView peopleGiveUpRv;
    @BindView(R.id.people_keep_rv)
    RecyclerView peopleKeepRv;


    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_chart_detail;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("数据详情");
        ButterKnife.bind(this);
        voteId = getIntent().getIntExtra("voteId", 0);

        if (agreeAdapter == null) {
            agreeAdapter = new PeopleSelectRecyclerViewAdapter(false,false);
        }
        peopleAgreeRv.addItemDecoration(new HFGridVerDecoration());
        peopleAgreeRv.setItemAnimator(new DefaultItemAnimator());
        peopleAgreeRv.setAdapter(agreeAdapter);
        GridLayoutManager manager = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(agreeAdapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        peopleAgreeRv.setLayoutManager(manager);
        List<SelectedUserEntity> agreeList = new ArrayList<>();
        SelectedUserEntity entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        agreeList.add(entity);




        agreeAdapter.appendDatas(agreeList);

        if (rejectAdapter == null) {
            rejectAdapter = new PeopleSelectRecyclerViewAdapter(false,false);
        }
        peopleRejectRv.addItemDecoration(new HFGridVerDecoration());
        peopleRejectRv.setItemAnimator(new DefaultItemAnimator());
        peopleRejectRv.setAdapter(rejectAdapter);
        GridLayoutManager manager2 = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup2 = new HFGridSpanSizeLookup();
        spanSizeLookup2.setAdapter(rejectAdapter);
        spanSizeLookup2.setLayoutManager(manager2);
        manager2.setSpanSizeLookup(spanSizeLookup2);
        peopleRejectRv.setLayoutManager(manager2);
        List<SelectedUserEntity> rejectList = new ArrayList<>();
        entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        rejectList.add(entity);

        rejectAdapter.appendDatas(rejectList);


        if (giveUpAdapter == null) {
            giveUpAdapter = new PeopleSelectRecyclerViewAdapter(false,false);
        }
        peopleGiveUpRv.addItemDecoration(new HFGridVerDecoration());
        peopleGiveUpRv.setItemAnimator(new DefaultItemAnimator());
        peopleGiveUpRv.setAdapter(giveUpAdapter);
        spanSizeLookup.setAdapter(giveUpAdapter);
        GridLayoutManager manager3 = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup3 = new HFGridSpanSizeLookup();
        spanSizeLookup3.setAdapter(giveUpAdapter);
        spanSizeLookup3.setLayoutManager(manager3);
        manager3.setSpanSizeLookup(spanSizeLookup3);
        peopleGiveUpRv.setLayoutManager(manager3);
        List<SelectedUserEntity> giveUpList = new ArrayList<>();
        entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        giveUpList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        giveUpList.add(entity);
        giveUpAdapter.appendDatas(giveUpList);

        if (keepAdapter == null) {
            keepAdapter = new PeopleSelectRecyclerViewAdapter(false,false);
        }
        peopleKeepRv.addItemDecoration(new HFGridVerDecoration());
        peopleKeepRv.setItemAnimator(new DefaultItemAnimator());
        peopleKeepRv.setAdapter(keepAdapter);
        GridLayoutManager manager4 = new GridLayoutManager(this, 4);
        HFGridSpanSizeLookup spanSizeLookup4 = new HFGridSpanSizeLookup();
        spanSizeLookup4.setAdapter(keepAdapter);
        spanSizeLookup4.setLayoutManager(manager4);
        manager4.setSpanSizeLookup(spanSizeLookup4);
        peopleKeepRv.setLayoutManager(manager4);
        List<SelectedUserEntity> keepList = new ArrayList<>();
        entity = new SelectedUserEntity();
        entity.setName("1122");
        entity.setPreview(true);
        keepList.add(entity);
        entity = new SelectedUserEntity();
        entity.setName("1222");
        entity.setPreview(true);
        keepList.add(entity);
        keepAdapter.appendDatas(keepList);
    }

    public static void navToChartDetail(Activity activity, String voteId) {
        Intent intent = new Intent(activity, ChartDetailActivity.class);
        intent.putExtra("voteId", voteId);
        activity.startActivity(intent);
    }
}

package com.mcw.demo.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.mcw.R;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.ui.adapter.MyVoteRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MyVoteActivity extends BaseActivity implements BasicRecyViewHolder.OnItemClickListener {
    private RecyclerView myVoteRv;
    private SwipeRefreshLayout myVoteSrl;
    private MyVoteRecyclerViewAdapter adapter;
    private List<MyVoteItemEntity> votes;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_my_vote;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("我的投票");
        myVoteRv = (RecyclerView) findViewById(R.id.my_vote_list_rv);
        myVoteSrl = (SwipeRefreshLayout) findViewById(R.id.my_vote_srl);
        if (adapter == null) {
            adapter = new MyVoteRecyclerViewAdapter();
            adapter.setItemClickListener(this);
            adapter.addSubViewListener(R.id.to_vote_detail_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VoteDetailActivity.navToVoteDetail(MyVoteActivity.this, Integer.parseInt(view.getTag().toString()));
                }
            });
            votes = new ArrayList<>();
            MyVoteItemEntity entity = new MyVoteItemEntity();
            entity.setVoteCreatorName("z1");
            entity.setVoteId(1);
            entity.setVoteStatus(1);
            entity.setVoteTitle("投票1");
            votes.add(entity);
            entity = new MyVoteItemEntity();
            entity.setVoteCreatorName("z2");
            entity.setVoteId(2);
            entity.setVoteStatus(2);
            entity.setVoteTitle("投票2");
            votes.add(entity);
        }
        myVoteRv.setAdapter(adapter);
        myVoteRv.setItemAnimator(new DefaultItemAnimator());
        myVoteRv.setLayoutManager(new LinearLayoutManager(this));
        adapter.refreshDatas(votes);
        myVoteSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myVoteSrl.setRefreshing(false);
            }
        });

    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {
        VoteDetailActivity.navToVoteDetail(MyVoteActivity.this, adapter.getData(adapterPosition).getVoteId());
    }
}

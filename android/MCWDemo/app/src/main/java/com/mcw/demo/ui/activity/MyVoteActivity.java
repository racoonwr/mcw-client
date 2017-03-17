package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFLineVerComDecoration;
import com.mcw.R;
import com.mcw.demo.api.DemoApiFactory;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.ui.adapter.MyVoteRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

public class MyVoteActivity extends BaseActivity implements BasicRecyViewHolder.OnItemClickListener {
    private String meetingId;
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
        meetingId = getIntent().getStringExtra("meetingId");
        myVoteRv = (RecyclerView) findViewById(R.id.my_vote_list_rv);
        myVoteSrl = (SwipeRefreshLayout) findViewById(R.id.my_vote_srl);
        if (adapter == null) {
            adapter = new MyVoteRecyclerViewAdapter();
            adapter.setItemClickListener(this);
            adapter.addSubViewListener(R.id.to_vote_detail_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VoteDetailActivity.navToVoteDetail(MyVoteActivity.this, view.getTag().toString());
                }
            });
            votes = new ArrayList<>();
            adapter.refreshDatas(votes);
        }
        myVoteRv.setAdapter(adapter);
        myVoteRv.setItemAnimator(new DefaultItemAnimator());
        myVoteRv.addItemDecoration(new HFLineVerComDecoration(1, Color.parseColor("#efefef")));
        myVoteRv.setLayoutManager(new LinearLayoutManager(this));
        myVoteSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myVoteSrl.setRefreshing(false);
            }
        });

        DemoApiFactory.getInstance().getVoteList(meetingId).subscribe(new Subscriber<List<MyVoteItemEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                myVoteSrl.setRefreshing(false);
            }

            @Override
            public void onNext(List<MyVoteItemEntity> myVoteItemEntities) {
                myVoteSrl.setRefreshing(false);
                if (myVoteItemEntities != null && myVoteItemEntities.size() > 0) {
                    votes.addAll(myVoteItemEntities);
                    adapter.refreshDatas(votes);
                }
            }
        });
    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {
        VoteDetailActivity.navToVoteDetail(MyVoteActivity.this, adapter.getData(adapterPosition).getVoteId());
    }

    public static void navToMyVote(Activity activity, String meetingId) {
        Intent i = new Intent(activity, MyVoteActivity.class);
        i.putExtra("meetingId", meetingId);
        activity.startActivity(i);
    }
}

package com.mcw.demo.ui.activity;

import android.os.Bundle;
import android.widget.Button;

import com.mcw.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateVoteActivity extends BaseActivity {

    @BindView(R.id.create_vote_btn)
    public Button createVoteBtn;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_create_vote;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("创建会议");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.create_vote_btn)
    public void createVote(){
        VoteDetailActivity.navToVoteDetail(this,99);
        this.finish();
    }
}

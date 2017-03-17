package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.github.lguipeng.library.animcheckbox.AnimCheckBox;
import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.config.Constant;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class CreateVoteActivity extends BaseActivity {

    @BindView(R.id.vote_context_et)
    EditText voteContextEt;
    @BindView(R.id.no_name_cb)
    AnimCheckBox noNameCb;

    @BindView(R.id.create_vote_btn)
    public Button createVoteBtn;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_create_vote;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("创建投票");
        ButterKnife.bind(this);
    }

    @OnClick(R.id.create_vote_btn)
    public void createVote() {
        Bundle bundle = new Bundle();
        bundle.putString("voteContent", voteContextEt.getText().toString());
        bundle.putBoolean("noName",noNameCb.isChecked());
        setResult(RESULT_OK,getIntent().putExtras(bundle));
        this.finish();
    }

    public static Observable<ActivityResult> navToCreateVote(Activity activity) {
        Intent navToCreateVote = new Intent(activity, CreateVoteActivity.class);
        return RxActivityResult.getInstance(DemoApplication.getInstance()).from(activity).startActivityForResult(navToCreateVote, Constant.START_ACTIVITY_FLAG_NAV_TO_CREATE_VOTE);
    }
}

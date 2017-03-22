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
import com.mcw.demo.model.MyVoteItemEntity;
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

    private boolean isReEditVote;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_create_vote;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("创建投票");
        ButterKnife.bind(this);
        isReEditVote = getIntent().getBooleanExtra("isReEditVote", false);
        if (isReEditVote) {
            String content = getIntent().getStringExtra("content");
            voteContextEt.setText(content);
            int anonymity = getIntent().getIntExtra("anonymity", 1);
            noNameCb.setChecked(anonymity == 1);
            createVoteBtn.setText("确认修改");
        }
    }

    @OnClick(R.id.create_vote_btn)
    public void createVote() {
        Intent i = getIntent();
        String content = voteContextEt.getText().toString();
        int anonymity = noNameCb.isChecked() ? 1 : 0;
        i.putExtra("voteContent", content);
        i.putExtra("anonymity", anonymity);
        setResult(RESULT_OK, i);
        this.finish();
    }

    public static Observable<ActivityResult> navToCreateVote(Activity activity) {
        Intent navToCreateVote = new Intent(activity, CreateVoteActivity.class);
        navToCreateVote.putExtra("isReEditVote", false);
        return RxActivityResult.getInstance(DemoApplication.getInstance().getApplicationContext())
                .from(activity).startActivityForResult(navToCreateVote, Constant.START_ACTIVITY_FLAG_NAV_TO_CREATE_VOTE);
    }


    public static Observable<ActivityResult> navToReEditVote(Activity activity, MyVoteItemEntity entity) {
        Intent navToCreateVote = new Intent(activity, CreateVoteActivity.class);
        navToCreateVote.putExtra("content", entity.getVoteContent());
        navToCreateVote.putExtra("anonymity", entity.getAnonymity());
        navToCreateVote.putExtra("isReEditVote", true);
        return RxActivityResult.getInstance(DemoApplication.getInstance().getApplicationContext())
                .from(activity).startActivityForResult(navToCreateVote, Constant.START_ACTIVITY_FLAG_NAV_TO_REEDIT_VOTE);
    }
}

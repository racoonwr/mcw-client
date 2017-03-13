package com.mcw.demo.ui.adapter.bean;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.igeek.hfrecyleviewlib.BaseHolderType;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.mcw.R;
import com.mcw.demo.model.VoteInfoEntity;
import com.mcw.demo.ui.adapter.MyVoteRecyclerViewAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/10
 */
public class VoteInfoType extends BaseHolderType<VoteInfoEntity, VoteInfoType.Viewholder> {

    @Override
    public BasicRecyViewHolder buildHolder(ViewGroup parent) {
        return new Viewholder(View.inflate(parent.getContext(), R.layout.layout_vote_info, null));
    }

    @Override
    public int getType(VoteInfoEntity voteInfoEntity) {
        return voteInfoEntity.getType();
    }

    @Override
    public void bindDataToHolder(Viewholder viewholder, VoteInfoEntity voteInfoEntity, int postion) {
        MyVoteRecyclerViewAdapter voteAdapter = new MyVoteRecyclerViewAdapter();
        viewholder.voteListRv.setItemAnimator(new DefaultItemAnimator());
        viewholder.voteListRv.setLayoutManager(new LinearLayoutManager(viewholder.itemView.getContext()));
        viewholder.voteListRv.setAdapter(voteAdapter);
        voteAdapter.refreshDatas(voteInfoEntity.getList());
    }

    public static class Viewholder extends BasicRecyViewHolder {
        @BindView(R.id.vote_list_rv)
        RecyclerView voteListRv;
        @BindView(R.id.add_vote_btn)
        Button addVoteBtn;

        public Viewholder(View itemView) {
            this(itemView, null, null);
        }

        public Viewholder(View itemView, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            super(itemView, clickListener, longClickListener);
            ButterKnife.bind(this, itemView);

        }
    }
}

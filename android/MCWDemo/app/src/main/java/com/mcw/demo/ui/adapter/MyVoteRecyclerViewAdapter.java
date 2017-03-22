package com.mcw.demo.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFSingleTypeRecyAdapter;
import com.mcw.R;
import com.mcw.demo.model.MyVoteItemEntity;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/6
 */
public class MyVoteRecyclerViewAdapter extends HFSingleTypeRecyAdapter<MyVoteItemEntity, MyVoteRecyclerViewAdapter.RecyViewHolder> {

    public MyVoteRecyclerViewAdapter() {
        super(R.layout.layout_recy_item_my_vote);
    }

    @Override
    public MyVoteRecyclerViewAdapter.RecyViewHolder buildViewHolder(View itemView) {
        return new RecyViewHolder(itemView);
    }

    @Override
    public void bindDataToHolder(MyVoteRecyclerViewAdapter.RecyViewHolder holder, MyVoteItemEntity myVoteItemEntity, int position) {
        holder.voteTitle.setText(holder.voteTitle.getText().toString().replaceFirst("@",myVoteItemEntity.getVoteContent()));
//        holder.voteCreatorName.setText(holder.voteCreatorName.getText().toString().replaceFirst("@",myVoteItemEntity.getVoteCreatorName()));
        String statusMeaning = "";
        if ("INVOTING".equals(myVoteItemEntity.getStatusCode())){
            holder.voteStatusIv.setImageResource(R.drawable.vote_icon_unfinished);
            statusMeaning = "投票中";
        }else if ("FINISHED".equals(myVoteItemEntity.getStatusCode())){
            holder.voteStatusIv.setImageResource(R.drawable.vote_icon_finished);
            statusMeaning = "已结束";
        }else if ("NEW".equals(myVoteItemEntity.getStatusCode())){
            holder.voteStatusIv.setVisibility(View.GONE);
            statusMeaning = "新建";
        }else{
            holder.voteStatusIv.setVisibility(View.GONE);
            holder.voteStatus.setVisibility(View.GONE);
        }

        holder.voteStatus.setText(holder.voteStatus.getText().toString().replaceFirst("@",statusMeaning));
        holder.toVoteDetailIv.setTag(myVoteItemEntity.getVoteId());
    }

    public static class RecyViewHolder extends BasicRecyViewHolder {
        TextView voteTitle, voteStatus;
        ImageView toVoteDetailIv,voteStatusIv;

        public RecyViewHolder(View itemView) {
            super(itemView);
            voteTitle = (TextView) itemView.findViewById(R.id.vote_title_tv);
            voteStatus = (TextView) itemView.findViewById(R.id.vote_status_tv);
            toVoteDetailIv = (ImageView) itemView.findViewById(R.id.to_vote_detail_iv);
            voteStatusIv = (ImageView) itemView.findViewById(R.id.vote_status_iv);
        }
    }
}

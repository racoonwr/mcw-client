package com.mcw.demo.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFSingleTypeRecyAdapter;
import com.mcw.R;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.model.UserInfo;
import com.mcw.demo.util.DateUtil;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public class MeetingListRecyclerViewAdapter extends HFSingleTypeRecyAdapter<MeetingListItemEntity, MeetingListRecyclerViewAdapter.RecyViewHolder> {


    public MeetingListRecyclerViewAdapter() {
        super(R.layout.layout_recy_item_meeting);
    }

    @Override
    public RecyViewHolder buildViewHolder(View itemView) {
        return new RecyViewHolder(itemView);
    }

    @Override
    public void bindDataToHolder(RecyViewHolder holder, MeetingListItemEntity entity, int position) {
        holder.meetingTitle.setText(entity.getTitle());
        holder.meetingTime.setText(DateUtil.translateDate(entity.getStartDatePlan(), DateUtil.yyyyMMddHHmm));
        holder.meetingLocation.setText(entity.getLocation());
        boolean mine = entity.getCreatedBy().equals(UserInfo.getInstance().getId());
        holder.mineIconIv.setVisibility(mine ? View.VISIBLE : View.GONE);
        String statusMeaning = "";
//        CREATED/INMEETING/SUMMARY/FINISHED
        switch (entity.getStatusCode()){
            case "CREATED":
                statusMeaning = "未开始";
                break;
            case "INMEETING":
                statusMeaning = "会议中";
                break;
            case "SUMMARY":
                statusMeaning = "待传纪要";
                break;
            case "FINISHED":
                statusMeaning = "已结束";
                break;
        }
        holder.meetingStatus.setText(statusMeaning);
    }

    public static class RecyViewHolder extends BasicRecyViewHolder {

        TextView meetingTitle, meetingTime, meetingLocation, meetingStatus;
        ImageView mineIconIv;

        public RecyViewHolder(View itemView) {
            super(itemView);
            meetingTitle = (TextView) itemView.findViewById(R.id.meeting_title_tv);
            meetingTime = (TextView) itemView.findViewById(R.id.meeting_time_tv);
            meetingLocation = (TextView) itemView.findViewById(R.id.meeting_location_tv);
            meetingStatus = (TextView) itemView.findViewById(R.id.meeting_status_tv);
            mineIconIv = (ImageView) itemView.findViewById(R.id.mine_icon_iv);
        }

    }
}

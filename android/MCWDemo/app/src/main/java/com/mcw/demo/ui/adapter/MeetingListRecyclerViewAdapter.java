package com.mcw.demo.ui.adapter;

import android.view.View;
import android.widget.TextView;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFSingleTypeRecyAdapter;
import com.mcw.R;
import com.mcw.demo.model.MeetingListItemEntity;

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
        holder.meetingTime.setText("" + entity.getStartDatePlan());
        holder.meetingLocation.setText(entity.getLocation());
        holder.meetingStatus.setText("" + entity.getStatusCode());
    }

    public static class RecyViewHolder extends BasicRecyViewHolder {

        TextView meetingTitle, meetingTime, meetingLocation, meetingStatus;

        public RecyViewHolder(View itemView) {
            super(itemView);
            meetingTitle = (TextView) itemView.findViewById(R.id.meeting_title_tv);
            meetingTime = (TextView) itemView.findViewById(R.id.meeting_time_tv);
            meetingLocation = (TextView) itemView.findViewById(R.id.meeting_location_tv);
            meetingStatus = (TextView) itemView.findViewById(R.id.meeting_status_tv);
        }

    }
}

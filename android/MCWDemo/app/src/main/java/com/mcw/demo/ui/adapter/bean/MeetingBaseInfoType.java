package com.mcw.demo.ui.adapter.bean;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.igeek.hfrecyleviewlib.BaseHolderType;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.mcw.R;
import com.mcw.demo.model.MeetingBaseInfoEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/9
 */
public class MeetingBaseInfoType extends BaseHolderType<MeetingBaseInfoEntity, MeetingBaseInfoType.Viewholder> {

    @Override
    public BasicRecyViewHolder buildHolder(ViewGroup parent) {
        return new Viewholder(View.inflate(parent.getContext(), R.layout.layout_meeting_base_info, null));
    }

    @Override
    public int getType(MeetingBaseInfoEntity meetingBaseInfoEntity) {
        return meetingBaseInfoEntity.getType();
    }

    @Override
    public void bindDataToHolder(Viewholder holder, final MeetingBaseInfoEntity meetingBaseInfoEntity, int postion) {
        holder.meetingTitleEt.setText(meetingBaseInfoEntity.getTitle());
        holder.meetingTitleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                meetingBaseInfoEntity.setTitle(s.toString());
            }
        });
        holder.meetingRequire.setText(meetingBaseInfoEntity.getRequire());
        holder.meetingRequire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                meetingBaseInfoEntity.setRequire(s.toString());
            }
        });
        holder.meetingAddressEt.setText(meetingBaseInfoEntity.getLocation());
        holder.meetingAddressEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                meetingBaseInfoEntity.setLocation(s.toString());
            }
        });
        long startDatePlan = meetingBaseInfoEntity.getStartDatePlan();
        long endDatePlan = meetingBaseInfoEntity.getEndDatePlan();


    }

    public static class Viewholder extends BasicRecyViewHolder {
        @BindView(R.id.meeting_title_et)
        EditText meetingTitleEt;
        @BindView(R.id.start_date_ms)
        MaterialSpinner startDateMs;
        @BindView(R.id.start_time_ms)
        MaterialSpinner startTimeMs;
        @BindView(R.id.end_date_ms)
        MaterialSpinner endDateMs;
        @BindView(R.id.end_time_ms)
        MaterialSpinner endTimeMs;
        @BindView(R.id.meeting_address_et)
        TextView meetingAddressEt;
        @BindView(R.id.meeting_require)
        EditText meetingRequire;

        public Viewholder(View itemView) {
            this(itemView, null, null);
        }

        public Viewholder(View itemView, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            super(itemView, clickListener, longClickListener);
            ButterKnife.bind(this, itemView);

        }
    }
}

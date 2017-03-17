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
import com.mcw.demo.ui.activity.MeetingActivity;
import com.mcw.demo.util.DateUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    private View.OnTouchListener customTimeEditViewOnTouchListener;

    public MeetingBaseInfoType(View.OnTouchListener listener) {
        this.customTimeEditViewOnTouchListener = listener;
    }

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
        if (meetingBaseInfoEntity.getModelType() != MeetingActivity.MODEL_TYPE_CREATE_MEETING) {
            holder.meetingTitleEt.setEnabled(false);
            holder.meetingRequire.setEnabled(false);
            holder.meetingAddressEt.setEnabled(false);
            holder.planStartDateEt.setOnTouchListener(null);
            holder.planStartTimeEt.setOnTouchListener(null);
            holder.planEndDateEt.setOnTouchListener(null);
            holder.planEndTimeEt.setOnTouchListener(null);
        } else {
            holder.meetingTitleEt.setEnabled(true);
            holder.meetingRequire.setEnabled(true);
            holder.meetingAddressEt.setEnabled(true);
            holder.planStartDateEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            holder.planStartTimeEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            holder.planEndDateEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            holder.planEndTimeEt.setOnTouchListener(customTimeEditViewOnTouchListener);
        }

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
        holder.meetingRequire.setText(meetingBaseInfoEntity.getMeetingRequire());
        holder.meetingRequire.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                meetingBaseInfoEntity.setMeetingRequire(s.toString());
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
        if (startDatePlan == 0l) {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            startDatePlan = now.getTimeInMillis();
            meetingBaseInfoEntity.setStartDatePlan(startDatePlan);
        }
        holder.planStartDateEt.setText(DateUtil.translateDate(startDatePlan, DateUtil.yyyyMMDD));
        holder.planStartTimeEt.setText(DateUtil.translateDate(startDatePlan, DateUtil.HHmm));

        long endDatePlan = meetingBaseInfoEntity.getEndDatePlan();
        if (startDatePlan > endDatePlan) {
            endDatePlan = startDatePlan;
        }
        if (endDatePlan == 0l) {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            endDatePlan = now.getTimeInMillis();
            meetingBaseInfoEntity.setEndDatePlan(endDatePlan);
        }

        holder.planEndDateEt.setText(DateUtil.translateDate(endDatePlan, DateUtil.yyyyMMDD));
        holder.planEndTimeEt.setText(DateUtil.translateDate(endDatePlan, DateUtil.HHmm));
    }

    public static class Viewholder extends BasicRecyViewHolder {
        @BindView(R.id.meeting_title_et)
        EditText meetingTitleEt;
        @BindView(R.id.plan_start_date_et)
        EditText planStartDateEt;
        @BindView(R.id.plan_start_time_et)
        EditText planStartTimeEt;
        @BindView(R.id.plan_end_date_et)
        EditText planEndDateEt;
        @BindView(R.id.plan_end_time_et)
        EditText planEndTimeEt;
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

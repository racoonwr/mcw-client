package com.mcw.demo.ui.adapter.bean;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.igeek.hfrecyleviewlib.BaseHolderType;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.ui.activity.MeetingActivity;
import com.mcw.demo.ui.adapter.PeopleSelectAdapterListener;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;
import com.mcw.demo.util.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.photopicker.widget.BGASortableNinePhotoLayout;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/10
 */
public class SummaryInfoType extends BaseHolderType<SummaryInfoEntity, SummaryInfoType.Viewholder> implements BGASortableNinePhotoLayout.Delegate {
    private NinePhotoLayoutListener listener;
    private View.OnTouchListener customTimeEditViewOnTouchListener;
    private BGASortableNinePhotoLayout mPicSnpl;
    private Context mContext;
    private PeopleSelectAdapterListener peopleSelectAdapterListener;
    private PeopleSelectRecyclerViewAdapter adapter;

    public SummaryInfoType(NinePhotoLayoutListener listener, View.OnTouchListener onTouchListener) {
        this.listener = listener;
        this.customTimeEditViewOnTouchListener = onTouchListener;
    }


    @Override
    public BasicRecyViewHolder buildHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new Viewholder(View.inflate(mContext, R.layout.layout_upload_meeting_summary, null));
    }

    @Override
    public int getType(SummaryInfoEntity summaryInfoEntity) {
        return summaryInfoEntity.getType();
    }

    @Override
    public void bindDataToHolder(Viewholder viewholder, final SummaryInfoEntity summaryInfoEntity, int postion) {
        if (summaryInfoEntity.getModelType() != MeetingActivity.MODEL_TYPE_INPUT_SUMMARY) {
            viewholder.meetingCompereEt.setEnabled(false);
            viewholder.meetingRecorderEt.setEnabled(false);
            viewholder.realStartDateEt.setOnTouchListener(null);
            viewholder.realStartTimeEt.setOnTouchListener(null);
            viewholder.realEndDateEt.setOnTouchListener(null);
            viewholder.realEndTimeEt.setOnTouchListener(null);
            viewholder.snplMeetingContentAddPic.setPlusEnable(false);
            viewholder.snplMeetingContentAddPic.setEditable(false);
            viewholder.snplMeetingContentAddPic.setSortable(false);
        } else {
            viewholder.meetingCompereEt.setEnabled(true);
            viewholder.meetingRecorderEt.setEnabled(true);
            viewholder.realStartDateEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            viewholder.realStartTimeEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            viewholder.realEndDateEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            viewholder.realEndTimeEt.setOnTouchListener(customTimeEditViewOnTouchListener);
            viewholder.snplMeetingContentAddPic.setPlusEnable(true);
            viewholder.snplMeetingContentAddPic.setEditable(true);
            viewholder.snplMeetingContentAddPic.setSortable(true);
        }


        viewholder.meetingCompereEt.setText(summaryInfoEntity.getMeetingCompere());
        viewholder.meetingCompereEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                summaryInfoEntity.setMeetingCompere(s.toString());
            }
        });

        viewholder.meetingRecorderEt.setText(summaryInfoEntity.getMeetingRecorder());
        viewholder.meetingRecorderEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                summaryInfoEntity.setMeetingRecorder(s.toString());
            }
        });

        mPicSnpl = viewholder.snplMeetingContentAddPic;
        if (summaryInfoEntity.getModelType() != MeetingActivity.MODEL_TYPE_INPUT_SUMMARY) {
            adapter = new PeopleSelectRecyclerViewAdapter(false, false);
        } else {
            adapter = new PeopleSelectRecyclerViewAdapter(true, true);
        }
        adapter.setItemClickListener(new BasicRecyViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int adapterPosition) {
                if (adapter.isPlusEnable() && adapterPosition == adapter.getItemCount() - 1) {
                    peopleSelectAdapterListener.onClickPlusItem();
                }
            }
        });
        viewholder.peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        viewholder.peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        viewholder.peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(viewholder.itemView.getContext(), 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        viewholder.peopleSelectRv.setLayoutManager(manager);
        adapter.refreshDatas(summaryInfoEntity.getInvitedUsersList());
        viewholder.snplMeetingContentAddPic.setMaxItemCount(9);
        viewholder.snplMeetingContentAddPic.setDelegate(this);
//        viewholder.snplMeetingContentAddPic.setData(summaryInfoEntity.getMeetingPicsList());

        long realStartDate = summaryInfoEntity.getRealStartDate();
        if (realStartDate == 0l) {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            realStartDate = now.getTimeInMillis();
            summaryInfoEntity.setRealStartDate(realStartDate);
        }
        viewholder.realStartDateEt.setText(DateUtil.translateDate(realStartDate, DateUtil.yyyyMMDD));
        viewholder.realStartTimeEt.setText(DateUtil.translateDate(realStartDate, DateUtil.HHmm));

        long realEndDate = summaryInfoEntity.getRealEndDate();
        if (realStartDate > realEndDate) {
            realEndDate = realStartDate;
        }
        if (realEndDate == 0l) {
            Calendar now = Calendar.getInstance();
            now.set(Calendar.SECOND, 0);
            realEndDate = now.getTimeInMillis();
            summaryInfoEntity.setRealEndDate(realEndDate);
        }
        viewholder.realEndDateEt.setText(DateUtil.translateDate(realEndDate, DateUtil.yyyyMMDD));
        viewholder.realEndTimeEt.setText(DateUtil.translateDate(realEndDate, DateUtil.HHmm));
    }

    public PeopleSelectAdapterListener getPeopleSelectAdapterListener() {
        return peopleSelectAdapterListener;
    }

    public void setPeopleSelectAdapterListener(PeopleSelectAdapterListener peopleSelectAdapterListener) {
        this.peopleSelectAdapterListener = peopleSelectAdapterListener;
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        listener.onMeetingPicAdd(mPicSnpl);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        listener.onMeetingPicDelete(mPicSnpl, position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        listener.onMeetingPicClick(mPicSnpl, position, model, models);
    }

    public static class Viewholder extends BasicRecyViewHolder {
        @BindView(R.id.meeting_compere_et)
        EditText meetingCompereEt;
        @BindView(R.id.meeting_recorder_et)
        EditText meetingRecorderEt;
        @BindView(R.id.real_start_date_et)
        EditText realStartDateEt;
        @BindView(R.id.real_start_time_et)
        EditText realStartTimeEt;
        @BindView(R.id.real_end_date_et)
        EditText realEndDateEt;
        @BindView(R.id.real_end_time_et)
        EditText realEndTimeEt;
        @BindView(R.id.snpl_meeting_content_add_pic)
        BGASortableNinePhotoLayout snplMeetingContentAddPic;
        @BindView(R.id.people_select_rv)
        RecyclerView peopleSelectRv;

        public Viewholder(View itemView) {
            this(itemView, null, null);
        }

        public Viewholder(View itemView, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            super(itemView, clickListener, longClickListener);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface NinePhotoLayoutListener {
        void onMeetingPicDelete(BGASortableNinePhotoLayout layout, int position);

        void onMeetingPicClick(BGASortableNinePhotoLayout layout, int position, String model, ArrayList<String> models);

        void onMeetingPicAdd(BGASortableNinePhotoLayout layout);
    }
}

package com.mcw.demo.ui.adapter.bean;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.igeek.hfrecyleviewlib.BaseHolderType;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SummaryInfoEntity;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.util.ArrayList;

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
    private BGASortableNinePhotoLayout mPicSnpl;

    public SummaryInfoType(NinePhotoLayoutListener listener) {
        this.listener = listener;
    }


    @Override
    public BasicRecyViewHolder buildHolder(ViewGroup parent) {
        return new Viewholder(View.inflate(parent.getContext(), R.layout.activity_upload_meeting_summary, null));
    }

    @Override
    public int getType(SummaryInfoEntity summaryInfoEntity) {
        return summaryInfoEntity.getType();
    }

    @Override
    public void bindDataToHolder(Viewholder viewholder, SummaryInfoEntity summaryInfoEntity, int postion) {
        mPicSnpl = viewholder.snplMeetingContentAddPic;
        PeopleSelectRecyclerViewAdapter adapter = new PeopleSelectRecyclerViewAdapter();
        viewholder.peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        viewholder.peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        viewholder.peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(viewholder.itemView.getContext(), 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        viewholder.peopleSelectRv.setLayoutManager(manager);
        adapter.refreshDatas(summaryInfoEntity.getInvitedUsers());

        viewholder.snplMeetingContentAddPic.setMaxItemCount(9);
        viewholder.snplMeetingContentAddPic.setEditable(true);
        viewholder.snplMeetingContentAddPic.setPlusEnable(true);
        viewholder.snplMeetingContentAddPic.setSortable(true);
        viewholder.snplMeetingContentAddPic.setDelegate(this);
//        viewholder.snplMeetingContentAddPic.setData(summaryInfoEntity.getMeetingPics());
    }

    @Override
    public void onClickAddNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, ArrayList<String> models) {
        listener.onItemAdd(mPicSnpl);
    }

    @Override
    public void onClickDeleteNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        listener.onItemDelete(mPicSnpl,position);
    }

    @Override
    public void onClickNinePhotoItem(BGASortableNinePhotoLayout sortableNinePhotoLayout, View view, int position, String model, ArrayList<String> models) {
        listener.onItemClick(mPicSnpl,position,model,models);
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
        void onItemDelete(BGASortableNinePhotoLayout layout,int position);

        void onItemClick(BGASortableNinePhotoLayout layout,int position, String model, ArrayList<String> models);

        void onItemAdd(BGASortableNinePhotoLayout layout);
    }

}

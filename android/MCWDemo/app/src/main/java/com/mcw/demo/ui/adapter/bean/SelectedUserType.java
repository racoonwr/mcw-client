package com.mcw.demo.ui.adapter.bean;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.igeek.hfrecyleviewlib.BaseHolderType;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.SelectedUserListEntity;
import com.mcw.demo.ui.activity.MeetingActivity;
import com.mcw.demo.ui.adapter.PeopleSelectAdapterListener;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

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
public class SelectedUserType extends BaseHolderType<SelectedUserListEntity, SelectedUserType.Viewholder> {

    private SelectedUserTypeSubViewOnClickListener listener;

    private PeopleSelectAdapterListener peopleSelectAdapterListener;
    private PeopleSelectRecyclerViewAdapter adapter;

    public SelectedUserType(SelectedUserTypeSubViewOnClickListener listener){
        this.listener = listener;
    }


    @Override
    public BasicRecyViewHolder buildHolder(ViewGroup parent) {
        return new Viewholder(View.inflate(parent.getContext(), R.layout.layout_selected_user, null));
    }

    @Override
    public int getType(SelectedUserListEntity selectedUserListEntity) {
        return selectedUserListEntity.getType();
    }

    @Override
    public void bindDataToHolder(Viewholder viewholder, SelectedUserListEntity selectedUserListEntity, int postion) {
        if (selectedUserListEntity.getModelType() != MeetingActivity.MODEL_TYPE_CREATE_MEETING) {
            adapter = new PeopleSelectRecyclerViewAdapter(false,false);
        }else{
            adapter = new PeopleSelectRecyclerViewAdapter(true,false);
        }
        viewholder.peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        viewholder.peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        viewholder.peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(viewholder.itemView.getContext(), 4);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        viewholder.peopleSelectRv.setLayoutManager(manager);
        adapter.refreshDatas(selectedUserListEntity.getList());
        adapter.addSubViewListener(R.id.delete_iv, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteUser(v);
            }
        });

        adapter.setItemClickListener(new BasicRecyViewHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int adapterPosition) {
                if (adapter.isPlusEnable()&&adapterPosition == adapter.getItemCount()-1){
                    peopleSelectAdapterListener.onClickPlusItem();
                }else{
                    peopleSelectAdapterListener.onClickNormalItem(adapter.getData(adapterPosition));
                }
            }
        });
    }

    public PeopleSelectAdapterListener getPeopleSelectAdapterListener() {
        return peopleSelectAdapterListener;
    }

    public void setPeopleSelectAdapterListener(PeopleSelectAdapterListener peopleSelectAdapterListener) {
        this.peopleSelectAdapterListener = peopleSelectAdapterListener;
    }

    public static class Viewholder extends BasicRecyViewHolder {
        @BindView(R.id.people_select_rv)
        RecyclerView peopleSelectRv;

        public Viewholder(View itemView) {
            this(itemView, null, null);
        }

        public Viewholder(View itemView, OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
            super(itemView, clickListener, longClickListener);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface SelectedUserTypeSubViewOnClickListener{
        void onDeleteUser(View view);
    }
}

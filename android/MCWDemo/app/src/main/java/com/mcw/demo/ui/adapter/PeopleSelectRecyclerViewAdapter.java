package com.mcw.demo.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFSingleTypeRecyAdapter;
import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.model.SelectedUserEntity;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/3/1
 */
public class PeopleSelectRecyclerViewAdapter extends HFSingleTypeRecyAdapter<SelectedUserEntity, PeopleSelectRecyclerViewAdapter.RecyViewHolder> {
    private boolean isEditable;
    private boolean isPlusEnable;

    public PeopleSelectRecyclerViewAdapter(boolean isEditable,boolean isPlusEnable) {
        super(R.layout.layout_recy_item_people_select);
        this.isEditable = isEditable;
        this.isPlusEnable = isPlusEnable;
    }

    @Override
    public PeopleSelectRecyclerViewAdapter.RecyViewHolder buildViewHolder(View itemView) {
        return new PeopleSelectRecyclerViewAdapter.RecyViewHolder(itemView);
    }

    @Override
    public void bindDataToHolder(PeopleSelectRecyclerViewAdapter.RecyViewHolder holder, SelectedUserEntity entity, int position) {
        if (isPlusEnable && position == getItemCount()-1) {
            holder.name.setVisibility(View.INVISIBLE);
            holder.deleteIcon.setVisibility(View.GONE);
            Glide.with(DemoApplication.getInstance().getApplicationContext()).load(R.mipmap.bga_pp_ic_plus).into(holder.photo);
            holder.photoCov.setVisibility(View.GONE);
        } else {
            holder.name.setVisibility(View.VISIBLE);
            holder.name.setText(entity.getName());
            Glide.with(DemoApplication.getInstance().getApplicationContext()).load(entity.getPhotoUrl()).into(holder.photo);
            holder.deleteIcon.setTag(position);
            if (isEditable) {
                holder.deleteIcon.setVisibility(View.VISIBLE);
            } else {
                holder.deleteIcon.setVisibility(View.GONE);
            }
            if (entity.getIsSigned() == 1){
                holder.photoCov.setVisibility(View.GONE);
            }else{
                holder.photoCov.setVisibility(View.VISIBLE);
            }
        }
    }

    public static class RecyViewHolder extends BasicRecyViewHolder {

        TextView name;
        ImageView photo;
        ImageView deleteIcon;
        View photoCov;

        public RecyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_tv);
            deleteIcon = (ImageView) itemView.findViewById(R.id.delete_iv);
            photo = (ImageView) itemView.findViewById(R.id.photo_iv);
            photoCov = itemView.findViewById(R.id.photo_cov_v);
        }
    }

    @Override
    public int getItemCount() {
        if (isPlusEnable) {
            return super.getItemCount() + 1;
        }
        return super.getItemCount();
    }

    @Override
    public SelectedUserEntity getData(int position) {
        if (isPlusEnable && position == getItemCount()-1) {
            return null;
        }
        return super.getData(position);
    }


    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
        this.notifyDataSetChanged();
    }

    public boolean isPlusEnable() {
        return isPlusEnable;
    }

    public void setPlusEnable(boolean plusEnable) {
        isPlusEnable = plusEnable;
        this.notifyDataSetChanged();
    }
}

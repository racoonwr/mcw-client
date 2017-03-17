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
    SelectedUserEntity addEntity;

    public PeopleSelectRecyclerViewAdapter() {
        super(R.layout.layout_recy_item_people_select);
        addEntity = new SelectedUserEntity();
        addEntity.setPreview(true);
        addEntity.setName("");
        addEntity.setPhone("");
        addEntity.setPhotoUrl("");
        addEntity.setUserId("11");
        appendData(addEntity);
    }

    @Override
    public PeopleSelectRecyclerViewAdapter.RecyViewHolder buildViewHolder(View itemView) {
        return new PeopleSelectRecyclerViewAdapter.RecyViewHolder(itemView);
    }


    @Override
    public void bindDataToHolder(PeopleSelectRecyclerViewAdapter.RecyViewHolder holder, SelectedUserEntity entity, int position) {
        holder.name.setText(entity.getName());
        Glide.with(DemoApplication.getInstance().getApplicationContext()).load(entity.getPhotoUrl()).into(holder.photo);
        holder.deleteIcon.setTag(position);
        if (entity.isPreview()) {
            holder.deleteIcon.setVisibility(View.GONE);
        }
    }

    public static class RecyViewHolder extends BasicRecyViewHolder {

        TextView name;
        ImageView photo;
        ImageView deleteIcon;

        public RecyViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_tv);
            deleteIcon = (ImageView) itemView.findViewById(R.id.delete_iv);
            photo = (ImageView) itemView.findViewById(R.id.photo_iv);
        }
    }
}

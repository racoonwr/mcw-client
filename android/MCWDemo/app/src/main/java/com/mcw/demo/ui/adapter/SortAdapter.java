package com.mcw.demo.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.mcw.R;
import com.mcw.demo.model.SortModelEntity;

import java.util.List;


/**
 * Created by jiapeng.chen on 2015/3/20.
 */
public class SortAdapter extends BaseAdapter implements SectionIndexer{
   private List<SortModelEntity> list = null;
    private Context context;
    public SortAdapter(Context context,List<SortModelEntity> list){
        this.list = list;
        this.context = context;
    }

    /**
     *更新list数据
     * @param list
     */
    public void updateListView(List<SortModelEntity> list){
        this.list = list;
        notifyDataSetChanged();//更新
    }
    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0 ; i < getCount();i++) {
            String sortStr = list.get(i).getSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == sectionIndex) {
                return  i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetter().charAt(0);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final SortModelEntity content = list.get(position);
        if( convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_user_pick_sort_item,null);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.title);
            viewHolder.tvLetter = (TextView) convertView.findViewById(R.id.catalog);
            convertView.setTag(viewHolder);

        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int section  = getSectionForPosition(position);
        if (position == getPositionForSection(section)){
            viewHolder.tvLetter.setVisibility(View.VISIBLE);
            viewHolder.tvLetter.setText(content.getSortLetter());

        }
        else{
            viewHolder.tvLetter.setVisibility(View.GONE);
        }
        viewHolder.tvTitle.setText(this.list.get(position).getUserName());
        viewHolder.tvTitle.setBackgroundColor(this.list.get(position).getColor());
        return convertView;
    }
    final static class ViewHolder{

        TextView tvLetter;
        TextView tvTitle;
    }
}

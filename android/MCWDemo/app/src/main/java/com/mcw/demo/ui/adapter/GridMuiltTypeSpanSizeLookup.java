package com.mcw.demo.ui.adapter;

import android.support.v7.widget.GridLayoutManager;

import com.igeek.hfrecyleviewlib.BasicHFRecyAdapter;

public class GridMuiltTypeSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {

    private GridLayoutManager layoutManager;
    private BasicHFRecyAdapter adapter;

    @Override
    public int getSpanSize(int position) {
//        if (layoutManager != null && adapter != null) {
//            if (adapter.needMatchParentWidth(position)) {
//                return layoutManager.getSpanCount();
//            } else {
//                return 1;
//            }
//        }
        return 2;
    }

    public void setLayoutManager(GridLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public BasicHFRecyAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BasicHFRecyAdapter adapter) {
        this.adapter = adapter;
    }
}

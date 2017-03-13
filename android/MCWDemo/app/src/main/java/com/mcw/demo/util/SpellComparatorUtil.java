package com.mcw.demo.util;

import com.mcw.demo.model.SortModelEntity;

import java.util.Comparator;


/**
 * Created by Hand on 2015/3/20.
 */
public class SpellComparatorUtil implements Comparator<SortModelEntity> {
    @Override
    public int compare(SortModelEntity lhs, SortModelEntity rhs) {
       if(rhs.getSortLetter().equals("#")){
           return -1;
       }
        else if(lhs.getSortLetter().equals("#")){
           return 1;
       }
       else{
           return lhs.getSortLetter().compareTo(rhs.getSortLetter());
       }
    }
}

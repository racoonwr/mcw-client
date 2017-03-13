package com.mcw.demo.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mcw.R;
import com.mcw.demo.model.SortModelEntity;
import com.mcw.demo.ui.adapter.SortAdapter;
import com.mcw.demo.ui.widght.ClearEditText;
import com.mcw.demo.ui.widght.SideBar;
import com.mcw.demo.util.Chinese2SpellUtil;
import com.mcw.demo.util.SpellComparatorUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.name;

public class UserPickerActivity extends BaseActivity {
    private ListView sortListView;
    private TextView dialog;
    private SideBar sideBar;
    private List<SortModelEntity> sortList;
    private List<SortModelEntity> selectedList;
    private SpellComparatorUtil spellComparatorUtil;
    private SortAdapter adapter;
    private String userName;
    private Button picker;
    private String requestCode;
    private int oldColorPos = -2;
    //    private ArrayList<String> users;
    private ClearEditText cet;


    private ImageButton home_bt;
    private TextView page_title_tv;
    private ProgressBar progressBar;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_user_picker;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("选择与会人员");
        initViews();
        initData();
    }

    private void initData() {

        SortModelEntity entity = new SortModelEntity();
        entity.setUserId(1);
        entity.setUserName("为什么");
        entity.setSortLetter("W");
        entity.setColor(Color.WHITE);
        sortList.add(entity);
        entity = new SortModelEntity();
        entity.setUserId(2);
        entity.setUserName("逗逼");
        entity.setSortLetter("D");
        entity.setColor(Color.WHITE);
        sortList.add(entity);
        entity = new SortModelEntity();
        entity.setUserId(3);
        entity.setUserName("狗");
        entity.setSortLetter("G");
        entity.setColor(Color.WHITE);
        sortList.add(entity);
        entity = new SortModelEntity();
        entity.setUserId(4);
        entity.setUserName("猫");
        entity.setSortLetter("M");
        entity.setColor(Color.WHITE);
        sortList.add(entity);
        adapter.notifyDataSetChanged();
    }

    private void initViews() {
        spellComparatorUtil = new SpellComparatorUtil();
        sideBar = (SideBar) findViewById(R.id.listView);
        progressBar = (ProgressBar) findViewById(R.id.user_pick_progressbar);
        picker = (Button) findViewById(R.id.picker_bt);
        picker.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          if (sortList.size() == 0) {
                                              Toast.makeText(getApplicationContext(), "请选择申请人", Toast.LENGTH_SHORT).show();
                                              return;
                                          }
                                          Intent intent = getIntent();
                                          intent.putExtra("name", name);
                                          intent.putExtra("userName", userName);
                                          setResult(RESULT_OK, intent);
                                          finish();
                                      }
                                  }

        );

        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener()

                                                   {
                                                       @Override
                                                       public void onTouchingLetterChanged(String s) {
                                                           int position = adapter.getPositionForSection(s.charAt(0));
                                                           if (position != -1) {
                                                               sortListView.setSelection(position);
                                                           }

                                                       }
                                                   }

        );

        sortListView = (ListView)

                findViewById(R.id.country_lvcountry);
        sortList = new ArrayList<>();
        selectedList = new ArrayList<>();
        adapter = new SortAdapter(UserPickerActivity.this, sortList);
        sortListView.setAdapter(adapter);
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener()

                                            {
                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                    //这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Toast.makeText(getApplication(), ((SortModelEntity) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                                                    //修改背景颜色
//                if(requestCode.equals("1")) {
                                                    if (oldColorPos != -2) {
                                                        ((SortModelEntity) adapter.getItem(oldColorPos)).setColor(Color.WHITE);
                                                    }
                                                    oldColorPos = position;

                                                    //保存用户信息
                                                    selectedList.add((SortModelEntity) adapter.getItem(position));
                                                    userName = ((SortModelEntity) adapter.getItem(position)).getUserName();
//                }
//                else if(requestCode.equals("2")){
//                    users.add(((SortModelEntity) adapter.getItem(position)).getName());
//                }
                                                    ((SortModelEntity) adapter.getItem(position)).setColor(Color.GRAY);
                                                    adapter.notifyDataSetChanged();
                                                }
                                            }

        );
        //搜索框
        cet = (ClearEditText)

                findViewById(R.id.filter_edit);

        cet.addTextChangedListener(new

                                           TextWatcher() {
                                               @Override
                                               public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                               }

                                               @Override
                                               public void onTextChanged(CharSequence s, int start, int before, int count) {
                                                   //在进行搜索之前将已被选择的item背景颜色重置
                                                   if (oldColorPos != -2) {
                                                       ((SortModelEntity) adapter.getItem(oldColorPos)).setColor(Color.WHITE);
                                                       oldColorPos = -2;
                                                   }
                                                   filterData(s.toString());
                                               }

                                               @Override
                                               public void afterTextChanged(Editable s) {

                                               }
                                           }

        );

//        new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

//    /**
//     * 为ListView填充数据
//     *
//     * @param
//     * @return
//     */
//    private List<SortModelEntity> filledData(List<EmpEntity> data) {
//        List<SortModelEntity> mSortList = new ArrayList<SortModelEntity>();
//        for (int i = 0; i < data.size(); i++) {
//            SortModelEntity sortModelEntity = new SortModelEntity();
//            sortModelEntity.setName(data.get(i).getName());
//            sortModelEntity.setUserName(data.get(i).getUserName());
//            sortModelEntity.setColor(Color.WHITE);
//            //汉字转换成拼音
//            String pinyin = Chinese2SpellUtil.converterToFirstSpell(data.get(i).getName());
//            String sortString = pinyin.substring(0, 1).toUpperCase();
//
//            // 正则表达式，判断首字母是否是英文字母
//            if (sortString.matches("[A-Z]")) {
//                sortModelEntity.setSortLetter(sortString.toUpperCase());
//            } else {
//                sortModelEntity.setSortLetter("#");
//            }
//
//            mSortList.add(sortModelEntity);
//        }
//        return mSortList;
//
//    }

    //搜索过滤
    public void filterData(String filterStr) {
        List<SortModelEntity> filterDateList = new ArrayList<>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = sortList;
        } else {
            filterDateList.clear();
            for (SortModelEntity sortModelEntity : sortList) {
                String name = sortModelEntity.getUserName();
                if (name.indexOf(filterStr.toString()) != -1 || Chinese2SpellUtil.converterToFirstSpell(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModelEntity);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, spellComparatorUtil);
        adapter.updateListView(filterDateList);

    }

//    //获取申请人列表，如果数据库中查询为空，则远程请求列表
//    private class MyAsyncTask extends AsyncTask<String, String, List<EmpEntity>> {
//        private List<EmpEntity> empEntities;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressBar.setVisibility(View.VISIBLE);
//        }
//
//        @Override
//        protected List<EmpEntity> doInBackground(String... params) {
//
////            empEntities = new Select().from(EmpEntity.class).execute();
//            if (empEntities == null || empEntities.size() == 0) {
//                Type type = new TypeToken<List<EmpEntity>>() {
//                }.getType();
//                //requestCode： 1为所有员工，2为驾驶员
//                if ("1".equals(requestCode)) {
//                    empEntities = (List<EmpEntity>) HttpDataTaskHelper.getListDataTask(type, URIHelper.EMP_INFO_LIST);
//                } else if ("2".equals(requestCode)) {
//                    empEntities = (List<EmpEntity>) HttpDataTaskHelper.getListDataTask(type, URIHelper.BPM_VEHICLE_APPLY_DRIVERS);
//                } else {
//                    return null;
//                }
//
//                if (empEntities != null && empEntities.size() > 0) {
//                    ActiveAndroid.beginTransaction();
//                    for (int i = 0; i < empEntities.size(); i++) {
//                        empEntities.get(i).save();
//                    }
//                    ActiveAndroid.setTransactionSuccessful();
//                    ActiveAndroid.endTransaction();
//                } else {
//                    AlertDialog.Builder builder = new AlertDialog.Builder(UserPickerActivity.this);
//                    builder.setMessage("读取申请人列表失败！");
//                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//                    builder.create().show();
//                }
//            }
//            return empEntities;
//        }
//
//        @Override
//        protected void onPostExecute(List<EmpEntity> s) {
//            super.onPostExecute(s);
//            if (s != null && s.size() > 0) {
//                sortList = filledData(s);
//                Collections.sort(sortList, spellComparatorUtil);
//                adapter = new SortAdapter(UserPickerActivity.this, sortList);
//                sortListView.setAdapter(adapter);
//            }
//            progressBar.setVisibility(View.GONE);
//        }
//    }
}

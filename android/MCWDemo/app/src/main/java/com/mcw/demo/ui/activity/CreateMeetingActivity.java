package com.mcw.demo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFGridSpanSizeLookup;
import com.igeek.hfrecyleviewlib.HFGridVerDecoration;
import com.mcw.R;
import com.mcw.demo.model.MyVoteItemEntity;
import com.mcw.demo.ui.adapter.MyVoteRecyclerViewAdapter;
import com.mcw.demo.ui.adapter.PeopleSelectRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CreateMeetingActivity extends BaseActivity implements BasicRecyViewHolder.OnItemClickListener, CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {

    private static final String FRAG_TAG_DATE_PICKER = "fragment_date_picker_name";
    private static final String FRAG_TAG_TIME_PICKER = "timePickerDialogFragment";

    private static String[] startDateItems = {"今天(2017-3-5)", "明天(2017-3-6)", "后天(2017-3-7)", "其他时间"};
    private static String[] startTimeItems = {"上午(09:00)", "中午(13:00)", "傍晚(17:00)", "其他时间"};
    private static String[] endTimeItems = {"上午(09:00)", "中午(13:00)", "傍晚(17:00)", "其他时间"};
    private static String[] endDateItems = {"今天(2017-3-5)", "明天(2017-3-6)", "后天(2017-3-7)", "其他时间"};

    private RecyclerView peopleSelectRv;
    PeopleSelectRecyclerViewAdapter adapter;

    private RecyclerView voteListRv;
    private MyVoteRecyclerViewAdapter voteAdapter;

    private MaterialSpinner startDateMs;
    private ArrayAdapter<String> startDateAdapter;
    private MaterialSpinner startTimeMs;
    private ArrayAdapter<String> startTimeAdapter;
    private MaterialSpinner endDateMs;
    private ArrayAdapter<String> endDateAdapter;
    private MaterialSpinner endTimeMs;
    private ArrayAdapter<String> endTimeAdapter;

    private Button createMeetingBtn;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_create_meeting;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        setTitle("新建会议");
        createMeetingBtn = (Button) findViewById(R.id.meeting_action_btn);
        createMeetingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent previewMeeting = new Intent(CreateMeetingActivity.this, PreviewMeetingActivity.class);
                CreateMeetingActivity.this.startActivity(previewMeeting);
            }
        });

        startDateMs = (MaterialSpinner) findViewById(R.id.start_date_ms);
        startTimeMs = (MaterialSpinner) findViewById(R.id.start_time_ms);
        endDateMs = (MaterialSpinner) findViewById(R.id.end_date_ms);
        endTimeMs = (MaterialSpinner) findViewById(R.id.end_time_ms);

        startDateAdapter = new ArrayAdapter<>(this, R.layout.spinner_custom_style, startDateItems);
        startTimeAdapter = new ArrayAdapter<>(this, R.layout.spinner_custom_style, startTimeItems);
        endDateAdapter = new ArrayAdapter<>(this, R.layout.spinner_custom_style, endDateItems);
        endTimeAdapter = new ArrayAdapter<>(this, R.layout.spinner_custom_style, endTimeItems);

        startDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        startTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endDateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        endTimeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        startDateMs.setAdapter(startDateAdapter);
        startDateMs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == startDateItems.length - 1) {
                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                            .setThemeLight()
                            .setOnDateSetListener(CreateMeetingActivity.this);
                    cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        startTimeMs.setAdapter(startTimeAdapter);
        startTimeMs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == startTimeItems.length - 1) {
                    RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                            .setOnTimeSetListener(CreateMeetingActivity.this)
                            .setThemeLight();
                    rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        endDateMs.setAdapter(endDateAdapter);
        endDateMs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == endDateItems.length - 1) {
                    CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                            .setThemeLight()
                            .setOnDateSetListener(CreateMeetingActivity.this);
                    cdp.show(getSupportFragmentManager(), FRAG_TAG_DATE_PICKER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        endTimeMs.setAdapter(endTimeAdapter);
        endTimeMs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == endTimeItems.length - 1) {
                    RadialTimePickerDialogFragment rtpd = new RadialTimePickerDialogFragment()
                            .setOnTimeSetListener(CreateMeetingActivity.this)
                            .setThemeLight();
                    rtpd.show(getSupportFragmentManager(), FRAG_TAG_TIME_PICKER);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        peopleSelectRv = (RecyclerView) findViewById(R.id.people_select_rv);

        if (adapter == null) {
            adapter = new PeopleSelectRecyclerViewAdapter();
            adapter.setItemClickListener(this);
            adapter.addSubViewListener(R.id.delete_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(CreateMeetingActivity.this, " 你点击了第 " + view.getTag().toString() + " 个删除button", Toast.LENGTH_LONG).show();
                    adapter.removeData(Integer.parseInt(view.getTag().toString()));
                }
            });
        }

        peopleSelectRv.addItemDecoration(new HFGridVerDecoration());
        peopleSelectRv.setItemAnimator(new DefaultItemAnimator());
        peopleSelectRv.setAdapter(adapter);
        GridLayoutManager manager = new GridLayoutManager(this, 5);
        HFGridSpanSizeLookup spanSizeLookup = new HFGridSpanSizeLookup();
        spanSizeLookup.setAdapter(adapter);
        spanSizeLookup.setLayoutManager(manager);
        manager.setSpanSizeLookup(spanSizeLookup);
        peopleSelectRv.setLayoutManager(manager);


        voteListRv = (RecyclerView) findViewById(R.id.vote_list_rv);
        List<MyVoteItemEntity> votes = new ArrayList<>();
        if (voteAdapter == null) {
            voteAdapter = new MyVoteRecyclerViewAdapter();
            voteAdapter.setItemClickListener(this);
            voteAdapter.addSubViewListener(R.id.to_vote_detail_iv, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    VoteDetailActivity.navToVoteDetail(CreateMeetingActivity.this, Integer.parseInt(view.getTag().toString()));
                }
            });
            MyVoteItemEntity entity = new MyVoteItemEntity();
            entity.setVoteCreatorName("z1");
            entity.setVoteId(1);
            entity.setVoteStatus(1);
            entity.setVoteTitle("投票1");
            votes.add(entity);
            entity = new MyVoteItemEntity();
            entity.setVoteCreatorName("z2");
            entity.setVoteId(2);
            entity.setVoteStatus(2);
            entity.setVoteTitle("投票2");
            votes.add(entity);
        }
        voteListRv.setItemAnimator(new DefaultItemAnimator());
        voteListRv.setLayoutManager(new LinearLayoutManager(this));
        voteListRv.setAdapter(voteAdapter);
        voteAdapter.refreshDatas(votes);
    }

    @Override
    public void OnItemClick(View v, int adapterPosition) {
        if (adapterPosition == adapter.getDataCount() - 1) {
            startActivity(new Intent(this, UserPickerActivity.class));
        } else {
            Toast.makeText(CreateMeetingActivity.this, " 你点击了第 " + adapterPosition + " 个item", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {

    }

}

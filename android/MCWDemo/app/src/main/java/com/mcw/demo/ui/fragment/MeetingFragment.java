package com.mcw.demo.ui.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.igeek.hfrecyleviewlib.BasicRecyViewHolder;
import com.igeek.hfrecyleviewlib.HFLineVerComDecoration;
import com.igeek.hfrecyleviewlib.RecycleScrollListener;
import com.mcw.R;
import com.mcw.demo.model.MeetingListItemEntity;
import com.mcw.demo.presenter.MeetingFragmentPresenter;
import com.mcw.demo.ui.activity.CreateMeetingV2Activity;
import com.mcw.demo.ui.activity.MeetingDetailActivity;
import com.mcw.demo.ui.adapter.MeetingListRecyclerViewAdapter;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;

/**
 * 类描述
 * <p>
 * version 1.0
 *
 * @author racoon
 * @email wurun@zizizizizi.com
 * @date 2017/2/28
 */
public class MeetingFragment extends BaseFragment implements IMeetingFragment, BasicRecyViewHolder.OnItemClickListener {

    private View group;//缓存Fragment view

    private RecyclerView meetingListRv;
    private SwipeRefreshLayout meetingSrl;
    private MeetingListRecyclerViewAdapter adapter;
    private List<MeetingListItemEntity> meetings;

    View loadingView;
    View nodataView;

    private MeetingFragmentPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter = new MeetingFragmentPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (group == null) {
            group = inflater.inflate(R.layout.fragment_meeting, null);
        } else {
            return group;
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) group.getParent();
        if (parent != null) {
            parent.removeView(group);
        }
        initData();
        initView(group);
        presenter.loadLocalData();
        return group;
    }

    @Override
    public void gotoMeetingDetail(int meetingId) {

    }

    @Override
    public void loadMoreMeetingInfo(List<MeetingListItemEntity> newMeetings) {
        meetings.addAll(newMeetings);

    }

    @Override
    public void newMeeting() {
    }

    @Override
    public void finishLoadLocalMeetingList(List<MeetingListItemEntity> localMeetings, boolean hasMore) {
        adapter.appendDatas(localMeetings);
        if (hasMore) {
            adapter.footView.setVisibility(View.GONE);
        } else {
            adapter.footView.setVisibility(View.VISIBLE);
            adapter.updateFootView(nodataView);
        }
    }

    @Override
    public void noMoreLocalMeetingData() {
        adapter.updateFootView(nodataView);
    }

    @Override
    public void startLoadLocalMeetingList() {
        adapter.updateFootView(loadingView);
    }

    @Override
    public void finishLoadNetMeetingData(List<MeetingListItemEntity> newMeetingData) {
        adapter.insertDatas(0, newMeetingData);
        meetingSrl.setRefreshing(false);
    }

    @Override
    public void initData() {
        meetings = new ArrayList<>();
    }

    @Override
    public void initView(View view) {
        meetingListRv = (RecyclerView) view.findViewById(R.id.meeting_list_rv);
        meetingSrl = (SwipeRefreshLayout) view.findViewById(R.id.meeting_srl);
        loadingView = getActivity().getLayoutInflater().inflate(R.layout.layout_listbottom_loadingview, null);
        nodataView = getActivity().getLayoutInflater().inflate(R.layout.layout_list_nodata, null);
        if (adapter == null) {
            adapter = new MeetingListRecyclerViewAdapter();
            adapter.setFootView(loadingView);
            adapter.setItemClickListener(this);
        }
        meetingListRv.setAdapter(adapter);
        meetingListRv.addItemDecoration(new HFLineVerComDecoration(1, Color.parseColor("#929292")));
        meetingListRv.addOnScrollListener(scrollListener);
        meetingListRv.setItemAnimator(new DefaultItemAnimator());
        meetingListRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.refreshDatas(meetings);
        meetingSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadNetData();
            }
        });
    }

    public RecycleScrollListener scrollListener = new RecycleScrollListener() {
        @Override
        public void loadMore() {
            System.out.println("loadMore");
            presenter.loadLocalData();
        }

        @Override
        public void refresh() {
            System.out.println("refresh");
        }
    };

    @Override
    public void OnItemClick(View v, int adapterPosition) {
        MeetingDetailActivity.navToMeetingDetail(getActivity(), adapter.getData(adapterPosition).getMeetingId(),
                adapter.getData(adapterPosition).getCreator());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_activity_action, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.new_meeting) {
//            Intent intent = new Intent(getActivity(), CreateMeetingActivity.class);
            Intent intent = new Intent(getActivity(), CreateMeetingV2Activity.class);
            RxActivityResult.getInstance(getActivity()).from(getActivity()).startActivityForResult(intent, 1).subscribe(new Subscriber<ActivityResult>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(ActivityResult activityResult) {
                    MeetingListItemEntity entity = new MeetingListItemEntity();
                    entity.setMeetingDate(1111111l);
                    entity.setCreationDate(1111111l);
                    entity.setMeetingId(5);
                    entity.setMeetingLocation("会议室2");
                    entity.setMeetingStatus(1);
                    entity.setCreator(1);
                    adapter.insertData(0, entity);
                    meetingListRv.smoothScrollToPosition(0);
                }
            });
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
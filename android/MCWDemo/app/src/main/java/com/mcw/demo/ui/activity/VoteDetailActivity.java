package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mcw.R;
import com.mcw.demo.DemoApplication;
import com.mcw.demo.api.DemoApiFactory;
import com.mcw.demo.config.Constant;
import com.mcw.demo.model.VoteDetailEntity;
import com.mcw.demo.util.StringUtils;
import com.mcw.demo.util.ToastMaster;
import com.mcw.demo.util.rxjavaresult.ActivityResult;
import com.mcw.demo.util.rxjavaresult.RxActivityResult;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;

public class VoteDetailActivity extends BaseActivity implements OnChartValueSelectedListener {

    @BindView(R.id.btns_ll)
    LinearLayout btnsLl;
    private VoteDetailActivity mContext;
    protected String[] mParties = new String[]{
            "同意", "反对", "保留", "弃权"
    };

    private int[] counts;
    @BindView(R.id.vote_result_tv)
    TextView voteResultTv;

    private String voteId;
    private Typeface tf;

    @BindView(R.id.vote_chart)
    public PieChart voteChart;

    @BindView(R.id.vote_context_tv)
    public TextView voteContextTv;

    @BindView(R.id.chart_detail_tv)
    public TextView chartDetailTv;

    @Override
    protected int getContentViewLayoutResources() {
        return R.layout.activity_vote_detail;
    }

    @Override
    protected void initResource(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        mContext = this;
        setTitle("投票详情");
        voteId = getIntent().getStringExtra("voteId");
        voteContextTv.setText(voteId);


        voteChart.setUsePercentValues(true);
        voteChart.getDescription().setEnabled(false);
        voteChart.setExtraOffsets(5, 10, 5, 5);

        voteChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        voteChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
//        voteChart.setCenterText(generateCenterSpannableText());

        voteChart.setExtraOffsets(20.f, 0.f, 20.f, 0.f);

        voteChart.setDrawHoleEnabled(false);
        voteChart.setHoleColor(Color.WHITE);

        voteChart.setTransparentCircleColor(Color.WHITE);
        voteChart.setTransparentCircleAlpha(110);

        voteChart.setHoleRadius(58f);
        voteChart.setTransparentCircleRadius(61f);

        voteChart.setDrawCenterText(true);

        voteChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        voteChart.setRotationEnabled(true);
        voteChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        voteChart.setOnChartValueSelectedListener(this);

        voteChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = voteChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        DemoApiFactory.getInstance().getVoteDetail(voteId).subscribe(new Subscriber<List<VoteDetailEntity>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastMaster.popToast(mContext, "获取投票详情失败");
            }

            @Override
            public void onNext(List<VoteDetailEntity> voteDetailEntities) {
                if (voteDetailEntities != null && voteDetailEntities.size() > 0) {
                    VoteDetailEntity entity = voteDetailEntities.get(0);
                    counts = new int[]{entity.getCountAgree(),
                            entity.getCountReject(),
                            entity.getCountKeep(),
                            entity.getCountGiveup()};

                    voteContextTv.setText(entity.getVoteContent());
                    setData(counts);
                    if (!StringUtils.isEmpty(entity.getResultCode())) {//已投票
                        btnsLl.setVisibility(View.GONE);
                        voteResultTv.setVisibility(View.VISIBLE);
//                        AGREE/REJECT/GIVEUP/KEEP
                        String resultMeaning = "";
                        switch (entity.getResultCode()){
                            case "AGREE":
                                resultMeaning= "同意";
                                break;
                            case "REJECT":
                                resultMeaning= "反对";
                                break;
                            case "GIVEUP":
                                resultMeaning= "弃权";
                                break;
                            case "KEEP":
                                resultMeaning= "保留意见";
                                break;
                        }
                        voteResultTv.setText("已选择：" + resultMeaning);
                    } else {//未投票
                        voteResultTv.setVisibility(View.GONE);
                    }
                    chartDetailTv.setVisibility(entity.getAnonymity() == 1 ? View.GONE : View.VISIBLE);
                } else {
                    ToastMaster.popToast(mContext, "获取投票详情失败");
                }
            }
        });
    }

    private void setData(int[] counts) {
        int total = counts[0] + counts[1] + counts[2] + counts[3];
        if (total == 0) {
            return;
        }
//        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        entries.add(new PieEntry(Float.parseFloat(df.format(counts[0] / total)), mParties[0]));
        entries.add(new PieEntry(Float.parseFloat(df.format(counts[1] / total)), mParties[1]));
        entries.add(new PieEntry(Float.parseFloat(df.format(counts[2] / total)), mParties[2]));
        entries.add(new PieEntry(Float.parseFloat(df.format(counts[3] / total)), mParties[3]));

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.2f);
        dataSet.setValueLinePart2Length(0.4f);
        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        voteChart.setData(data);

        // undo all highlights
        voteChart.highlightValues(null);

        voteChart.invalidate();
    }

//    private SpannableString generateCenterSpannableText() {
//
//        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
//        s.setSpan(new RelativeSizeSpan(1.5f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.65f), 14, s.length() - 15, 0);
//        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
//        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
//        return s;
//    }

    public static Observable<ActivityResult> navToVoteDetail(Activity activity, String voteId) {
        Intent toDetail = new Intent(activity, VoteDetailActivity.class);
        toDetail.putExtra("voteId", voteId);
        return RxActivityResult.getInstance(DemoApplication.getInstance()).from(activity).startActivityForResult(toDetail, Constant.START_ACTIVITY_FLAG_NAV_TO_VOTE_DETAIL);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @OnClick(R.id.chart_detail_tv)
    public void navToChartDetail() {
        ChartDetailActivity.navToChartDetail(this, voteId);
    }

    private String resultString = "";

    @OnClick({R.id.reject_btn, R.id.giveup_btn, R.id.keep_btn, R.id.agree_btn})
    public void onClick(View view) {
        String resultCode = "";
        switch (view.getId()) {
            case R.id.reject_btn:
                counts[1]++;
                resultCode = "REJECT";
                break;
            case R.id.giveup_btn:
                resultCode = "GIVEUP";
                counts[3]++;
                break;
            case R.id.keep_btn:
                counts[2]++;
                resultCode = "KEEP";
                break;
            case R.id.agree_btn:
                counts[0]++;
                resultCode = "AGREE";
                break;
            default:
                break;
        }
        resultString = ((Button) view).getText().toString();
        DemoApiFactory.getInstance().createVoteRecord(resultCode, voteId).subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ToastMaster.popToast(mContext, "投票失败");
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if (aBoolean.booleanValue()) {
                    ToastMaster.popToast(mContext, "投票成功");
                    btnsLl.setVisibility(View.GONE);
                    voteResultTv.setVisibility(View.VISIBLE);
                    voteResultTv.setText("已选择：" + resultString);
                    setData(counts);
                } else {
                    ToastMaster.popToast(mContext, "投票失败");
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.setResult(RESULT_CANCELED);
                this.finish(); // back button
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

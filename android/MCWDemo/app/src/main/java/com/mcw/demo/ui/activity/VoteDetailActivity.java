package com.mcw.demo.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoteDetailActivity extends BaseActivity implements OnChartValueSelectedListener {
    protected String[] mParties = new String[] {
            "同意", "反对", "保留", "弃权"
    };

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
        setTitle("投票详情");
        voteId = getIntent().getStringExtra("voteId");
        voteContextTv.setText(""+voteId);


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

        setData(4, 100);

        voteChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = voteChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (Math.random() * mult) + mult / 5, mParties[i % mParties.length]));
        }

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

    public static void navToVoteDetail(Activity activity, String voteId) {
        Intent toDetail = new Intent(activity, VoteDetailActivity.class);
        toDetail.putExtra("voteId", voteId);
        activity.startActivity(toDetail);
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @OnClick(R.id.chart_detail_tv)
    public void navToChartDetail(){
        ChartDetailActivity.navToChartDetail(this,voteId);
    }
}

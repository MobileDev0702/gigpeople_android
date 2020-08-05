package com.gigpeople.app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gigpeople.app.R;
import com.gigpeople.app.activity.DemoBase;
import com.gigpeople.app.api.ApiService;
import com.gigpeople.app.api.RetrofitSingleton;
import com.gigpeople.app.apiModel.AnalyticsReportResponse;
import com.gigpeople.app.custom.DayAxisValueFormatter;
import com.gigpeople.app.custom.XYMarkerView;
import com.gigpeople.app.model.BarChartModel;
import com.gigpeople.app.model.MonthName;
import com.gigpeople.app.utils.GlobalMethods;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link AnalyticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnalyticsFragment extends DemoBase implements OnChartValueSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    Unbinder unbinder;
    @BindView(R.id.txt_orders_created)
    TextView txtOrdersCreated;
    @BindView(R.id.txt_avg_sellingprice)
    TextView txtAvgSellingprice;
    @BindView(R.id.txt_postiverating)
    TextView txtPostiverating;
    @BindView(R.id.txt_neworders)
    TextView txtNeworders;
    @BindView(R.id.txt_multiple_orders)
    TextView txtMultipleOrders;
    @BindView(R.id.txt_gis_orders_extra)
    TextView txtGisOrdersExtra;
    @BindView(R.id.txt_deliverd_orders)
    TextView txtDeliverdOrders;
    @BindView(R.id.chart1)
    PieChart chart1;
    @BindView(R.id.txt_postivarewies)
    TextView txtPostivarewies;
    @BindView(R.id.txt_negativerevies)
    TextView txtNegativerevies;
    @BindView(R.id.txt_rated)
    TextView txtRated;
    @BindView(R.id.txt_order_completed)
    TextView txtOrderCompleted;


    @BindView(R.id.seekBar2)
    SeekBar seekBar2;
    @BindView(R.id.seekBar1)
    SeekBar seekBar1;
    @BindView(R.id.tvXMax)
    TextView tvXMax;
    @BindView(R.id.tvYMax)
    TextView tvYMax;
    @BindView(R.id.txt_totalearned)
    TextView txtTotalearned;
    @BindView(R.id.txt_order_canceld)
    TextView txtOrderCanceld;
    @BindView(R.id.progressbar_postivarewies)
    ProgressBar progressbarPostivarewies;
    @BindView(R.id.progressbar_negativerevies)
    ProgressBar progressbarNegativerevies;
    @BindView(R.id.progressbar_rated)
    ProgressBar progressbarRated;
    @BindView(R.id.progressbar_order_completed)
    ProgressBar progressbarOrderCompleted;
    @BindView(R.id.progress_ordercanceled)
    ProgressBar progressOrdercanceled;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private PieChart chart;
    BarChart barchart1;
    List<String> xAXIS;
    List<Integer> yAxis;
    int k = 0;
    List<Integer> listy;
    ApiService apiService;
    ProgressDialog progressDialog;
    Context context;

    //  private BarChart barChartVertical, barChartVerticalweek;
//    private BarChart barChartHorizontal;
    BarChartModel barChartModel = new BarChartModel();
    Random rnd;
    int graphstatus;
    List<MonthName> months;

    private SeekBar seekBarX, seekBarY;
    private TextView tvX, tvY;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavouriteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AnalyticsFragment newInstance(String param1, String param2) {
        AnalyticsFragment fragment = new AnalyticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    protected void saveToGallery() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_analytics, container, false);
        unbinder = ButterKnife.bind(this, view);

        context = getContext();
       /* cartAdapter = new CartAdapter(cartItemActivity.this);
        recycleView.setAdapter(cartAdapter);
        recycleView.setLayoutManager(new LinearLayoutManager(cartItemActivity.this));*/
        apiService = RetrofitSingleton.createService(ApiService.class);
        tocall_Analytic_Reports(GlobalMethods.GetUserID(context));
        barchart1 = (BarChart) view.findViewById(R.id.barchart1);
        listy = new ArrayList<>();
        chart = (PieChart) view.findViewById(R.id.chart1);

        //chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 30, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);


        // chart.setCenterText(generateCenterSpannableText());

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);

        chart.setDrawCenterText(true);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.animateY(1400, (Easing.EaseInOutQuad));
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        // entry label styling
        chart.setEntryLabelColor(Color.TRANSPARENT);
        // chart.setEntryLabelTextSize(12f);
        chart.setDrawEntryLabels(false);
        chart.getLegend().setEnabled(false);




        months = new ArrayList<>();
        months.add(new MonthName("JAN"));
        months.add(new MonthName("FEB"));
        months.add(new MonthName("MAR"));
        months.add(new MonthName("APR"));
        months.add(new MonthName("MAY"));

        months.add(new MonthName("JUN"));
        months.add(new MonthName("JUL"));
        months.add(new MonthName("AUG"));

        months.add(new MonthName("SEP"));
        months.add(new MonthName("OCT"));
        months.add(new MonthName("NOV"));
        months.add(new MonthName("DEC"));

        tvX = view.findViewById(R.id.tvXMax);
        tvY = view.findViewById(R.id.tvYMax);

        seekBarX = view.findViewById(R.id.seekBar1);
        seekBarY = view.findViewById(R.id.seekBar2);
        //barchart();

        return view;
    }

    private void barchart() {
    final ArrayList<PieEntry> xLabel = new ArrayList<>();
       /* xLabel.add("50");
        xLabel.add("10");
        xLabel.add("5");
        xLabel.add("15");
        xLabel.add("15");




     */


        /*float postiveReview_F =Float.parseFloat(postiveReview);
        float negativereviewF =Float.parseFloat(negativereview);
        float ratedF =Float.parseFloat(rated);
        float ordercompletedF=Float.parseFloat(ordercompleted);
        float orderCancelledF=Float.parseFloat(orderCancelled);



        xLabel.add(new PieEntry((postiveReview_F),"Positive Reviews",getResources().getDrawable(R.drawable.progress_1)));
        xLabel.add(new PieEntry((negativereviewF),"Negative Reviews",getResources().getDrawable(R.drawable.progress_2)));
        xLabel.add(new PieEntry((ratedF),"Rated",getResources().getDrawable(R.drawable.progress_3)));
        xLabel.add(new PieEntry((ordercompletedF),"Order Completed",getResources().getDrawable(R.drawable.progress_4)));
        xLabel.add(new PieEntry((orderCancelledF),"Order Cancelled",getResources().getDrawable(R.drawable.progress_5)));
*/
        barchart1.setDrawBarShadow(false);
        barchart1.setDrawValueAboveBar(true);
        barchart1.getDescription().setEnabled(false);
        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barchart1.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        barchart1.setPinchZoom(false);
        barchart1.setDoubleTapToZoomEnabled(false);


        barchart1.getAxisLeft().setDrawGridLines(false);
        barchart1.getXAxis().setDrawGridLines(false);
        barchart1.getAxisRight().setDrawTopYLabelEntry(false);
        barchart1.getAxisRight().setDrawGridLines(false);
        barchart1.setDrawGridBackground(false);
        // chart.setDrawYLabels(false);

        ValueFormatter xAxisFormatter = new DayAxisValueFormatter(barchart1);

        XAxis xAxis = barchart1.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // only intervals of 1 day
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(xAxisFormatter);

       /* xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return xLabel.get((int)value);
            }
        });
*/
        //ValueFormatter custom = new MyValueFormatter("$");
        barchart1.setDrawGridBackground(false);
        barchart1.setDrawBorders(false);
        barchart1.setScaleYEnabled(false);
        barchart1.setAutoScaleMinMaxEnabled(false);


        //YAxis rightAxis = barchart1.getAxisRight();
        //rightAxis.setDrawGridLines(false);

        //rightAxis.setLabelCount(8, false);
        //  rightAxis.setValueFormatter(custom);
        // rightAxis.setSpaceTop(15f);
        // rightAxis.setAxisMinimum(7f); // this replaces setStartAtZero(true)
        Legend l = barchart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        // l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(9f);
        l.setTextSize(11f);
        l.setXEntrySpace(10f);

        YAxis yAxis = barchart1.getAxisRight();
        yAxis.setDrawAxisLine(false);
        yAxis.setLabelCount(0, false);
        YAxis XAxis1 = barchart1.getAxisLeft();
        XAxis1.setDrawAxisLine(false);
        XAxis1.setLabelCount(0, false);
        XAxis1.setLabelCount(0, false);

        xAxis.setDrawLabels(true);
        yAxis.setLabelCount(0, false);


        XYMarkerView mv = new XYMarkerView(getActivity(), xAxisFormatter);
        mv.setChartView(barchart1);

        barchart1.setMarker(mv);

        setData1(12, 1);
        // barchart1.invalidate();
    }

    private void setData(int count, float range,String postiveReview, String negativereview,String rated,String ordercompleted,String orderCancelled ) {
        ArrayList<PieEntry> entries = new ArrayList<>();


float postiveReview_F =Float.parseFloat(postiveReview);
        float negativereviewF =Float.parseFloat(negativereview);
        float ratedF =Float.parseFloat(rated);
        float ordercompletedF=Float.parseFloat(ordercompleted);
        float orderCancelledF=Float.parseFloat(orderCancelled);



        entries.add(new PieEntry((postiveReview_F),"Positive Reviews",getResources().getDrawable(R.drawable.progress_1)));
        entries.add(new PieEntry((negativereviewF),"Negative Reviews",getResources().getDrawable(R.drawable.progress_2)));
        entries.add(new PieEntry((ratedF),"Rated",getResources().getDrawable(R.drawable.progress_3)));
        entries.add(new PieEntry((ordercompletedF),"Order Completed",getResources().getDrawable(R.drawable.progress_4)));
        entries.add(new PieEntry((orderCancelledF),"Order Cancelled",getResources().getDrawable(R.drawable.progress_5)));

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
 /*   for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 4),
                    parties[i % parties.length],
                    getResources().getDrawable(R.drawable.fav_icon)));
        }*/

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(0f);
        dataSet.setIconsOffset(new MPPointF(30, 20));
        dataSet.setSelectionShift(15f);
        dataSet.setDrawValues(false);


        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

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

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(chart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);

        chart.invalidate();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData1(int count, float range) {

        float start = 10000f;
        range = 1000;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 100));

            if (Math.random() * 1000 < 250) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.sale2)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        for (int i = 0; i < months.size(); i++) {

            String month = months.get(i).getMonth();
            // values.add(new BarEntry(month, i));

        }


        BarDataSet set1;


        if (barchart1.getData() != null &&
                barchart1.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barchart1.getData().getDataSetByIndex(0);
            set1.setValues(values);
            barchart1.getData().notifyDataChanged();
            barchart1.notifyDataSetChanged();
            set1.setDrawValues(false);

        } else {
            set1 = new BarDataSet(values, " ");

            set1.setDrawIcons(false);

            //set1.setColors(ColorTemplate.MATERIAL_COLORS);

            // int startColor = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_dark);
            // int endColor = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_bright);
            //  set1.setGradientColor(startColor, endColor);


            int startColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int startColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light);
            int startColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int startColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_light);
            int startColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);
            int endColor1 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int endColor2 = ContextCompat.getColor(getActivity(), android.R.color.holo_blue_light);
            int endColor3 = ContextCompat.getColor(getActivity(), android.R.color.holo_orange_light);
            int endColor4 = ContextCompat.getColor(getActivity(), android.R.color.holo_green_light);
            int endColor5 = ContextCompat.getColor(getActivity(), android.R.color.holo_red_light);

            List<GradientColor> gradientColors = new ArrayList<>();
            gradientColors.add(new GradientColor(startColor1, endColor1));
            gradientColors.add(new GradientColor(startColor2, endColor2));
            gradientColors.add(new GradientColor(startColor3, endColor3));
            gradientColors.add(new GradientColor(startColor4, endColor4));
            gradientColors.add(new GradientColor(startColor5, endColor5));

            set1.setGradientColors(gradientColors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);


            data.setBarWidth(0.9f);


            set1.setDrawValues(false);
            barchart1.setData(data);
        }
    }


    private void tocall_Analytic_Reports(String userId) {

        progressDialog = ProgressDialog.show(context, "", "", true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.dialog_progress);

        Call<AnalyticsReportResponse> call = apiService.calllAnalyticsReport(userId);
        call.enqueue(new Callback<AnalyticsReportResponse>() {


            @Override
            public void onResponse(Call<AnalyticsReportResponse> call, Response<AnalyticsReportResponse> response) {
                Log.e("AnalyticsResp", new Gson().toJson(response.body()));
                progressDialog.dismiss();
                {
                    AnalyticsReportResponse resp = response.body();

                    if (resp.getStatus().equals("1")) {


                        layoutsetup(resp);
                    } else {
                    }
                }
            }


            public void onFailure(Call<AnalyticsReportResponse> call, Throwable t) {
                Log.e("AnalyticsRespfail", t.getMessage());
                progressDialog.dismiss();
            }
        });
    }

    private void layoutsetup(AnalyticsReportResponse response) {

        txtOrdersCreated.setText(response.getAnalyticsReport().getCreatedOrders());
        txtTotalearned.setText("$"+response.getAnalyticsReport().getMonthEarned());
        txtAvgSellingprice.setText("$"+response.getAnalyticsReport().getAvgSellingPrice());
        txtPostiverating.setText(response.getAnalyticsReport().getPositiveRating()+"%");
        txtNeworders.setText(response.getAnalyticsReport().getNewOrders());
        txtGisOrdersExtra.setText(response.getAnalyticsReport().getExtraOrders());
        txtMultipleOrders.setText(response.getAnalyticsReport().getMultipleOrders());
        txtDeliverdOrders.setText(response.getAnalyticsReport().getCancelledOrders());
        // regarding reviews
        txtPostivarewies.setText(response.getAnalyticsReport().getPositiveReviews());
        txtNegativerevies.setText(response.getAnalyticsReport().getNegativeReviews());
        txtRated.setText(response.getAnalyticsReport().getRated());
        txtOrderCompleted.setText(response.getAnalyticsReport().getCompletedOrders());
        txtOrderCanceld.setText(response.getAnalyticsReport().getCancelledOrders());
        barchart();

        setData(5, 10,response.getAnalyticsReport().getPositiveReviews(),response.getAnalyticsReport().getNegativeReviews(),response.getAnalyticsReport().getRated(),response.getAnalyticsReport().getCompletedOrders(),response.getAnalyticsReport().getCancelledOrders());
        //barchart(String postiveReview,String negativereview,String rated,String ordercompleted,String orderCancelled)

        progressbarPostivarewies.setProgress(Integer.parseInt(response.getAnalyticsReport().getPositiveReviews()));
        progressbarNegativerevies.setProgress(Integer.parseInt(response.getAnalyticsReport().getNegativeReviews()));
        progressbarRated.setProgress(Integer.parseInt(response.getAnalyticsReport().getRated()));
        progressbarOrderCompleted.setProgress(Integer.parseInt(response.getAnalyticsReport().getCompletedOrders()));
        progressOrdercanceled.setProgress(Integer.parseInt(response.getAnalyticsReport().getCancelledOrders()));
    }

}

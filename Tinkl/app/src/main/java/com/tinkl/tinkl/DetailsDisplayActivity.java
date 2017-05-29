package com.tinkl.tinkl;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.*;
import com.tinkl.tinkl.formatter.TemperatureFormatter;
import com.tinkl.tinkl.formatter.TurbidityFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric1221bday on 4/22/17.
 */
public class DetailsDisplayActivity extends AppCompatActivity {

    private List<Sample> details;
    private LineChart temperatureChart;
    private LineChart turbidityChart;
    private BarChart colorChart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_display);

        Toolbar mainToolbar = (Toolbar) findViewById(R.id.tinkl_toolbar);
        setSupportActionBar(mainToolbar);

        Intent intent = getIntent();
        ArrayList<byte[]> samples = new ArrayList<>();
        int size = intent.getIntExtra("size", 0);
        for (int i = 0; i < size; i++) {
            samples.add(intent.getByteArrayExtra("sample" + i));
        }

        details = new ArrayList<>();
        try {
            for(byte[] b : samples) {
                details.add(Sample.parseFrom(b));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        temperatureChart = (LineChart) findViewById(R.id.temperature_chart);
        turbidityChart = (LineChart) findViewById(R.id.turbidity_chart);
        colorChart = (BarChart) findViewById(R.id.color_chart);
        List<Entry> temperatureEntries = new ArrayList<>();
        List<Entry> turbidityEntries = new ArrayList<>();
        List<BarEntry> colorEntries = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        for (int i = 0; i < details.size(); i++) {
            temperatureEntries.add(new Entry(i, details.get(i).getTemperature()));
            turbidityEntries.add(new Entry(i, details.get(i).getTurbidity()));
            colorEntries.add(new BarEntry(i, 1));
            float r = details.get(i).getColor().getR();
            float g = details.get(i).getColor().getG();
            float b = details.get(i).getColor().getB();
            float max = Math.max(Math.max(r, g), b);
            r = r/max*255;
            g = g/max*255;
            b = b/max*255;
            colors.add(android.graphics.Color.argb(255, (int) r, (int) g, (int) b));
        }
        initTemperatureChart(temperatureEntries);
        initTurbidityChart(turbidityEntries);
        initColorChart(colorEntries, colors);
        temperatureChart.invalidate();
        turbidityChart.invalidate();
        colorChart.invalidate();
    }

    private void initTemperatureChart(List<Entry> temperatureEntries) {
        LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "temperature");
        temperatureDataSet.setLineWidth(3);
        temperatureDataSet.setValueTextSize(10);
        temperatureDataSet.setCircleHoleRadius(20);
        temperatureDataSet.setCircleRadius(5);
        temperatureDataSet.setColor(Color.parseColor("#A61646"));
        temperatureDataSet.setCircleColor(Color.parseColor("#A61646"));
        temperatureDataSet.setValueFormatter(new TemperatureFormatter());
        LineData temperatureLineData = new LineData(temperatureDataSet);
        temperatureChart.setData(temperatureLineData);
        temperatureChart.getXAxis().setDrawGridLines(false);
        temperatureChart.getAxisLeft().setDrawGridLines(false);
        temperatureChart.getAxisRight().setEnabled(false);
        temperatureChart.getAxisLeft().setAxisMinimum(0);
        temperatureChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        temperatureChart.getXAxis().setSpaceMin(0.2f);
        temperatureChart.getXAxis().setSpaceMax(0.2f);
        temperatureChart.getXAxis().setDrawLabels(false);
        temperatureChart.getLegend().setEnabled(false);
        temperatureChart.getDescription().setEnabled(false);
    }

    private void initTurbidityChart(List<Entry> turbidityEntries) {
        LineDataSet turbidityDataSet = new LineDataSet(turbidityEntries, "turbidity");
        turbidityDataSet.setLineWidth(3);
        turbidityDataSet.setValueTextSize(10);
        turbidityDataSet.setCircleHoleRadius(20);
        turbidityDataSet.setCircleRadius(5);
        turbidityDataSet.setColor(Color.parseColor("#3F51B5"));
        turbidityDataSet.setCircleColor(Color.parseColor("#303F9F"));
        turbidityDataSet.setValueFormatter(new TurbidityFormatter());
        LineData turbidityLineData = new LineData(turbidityDataSet);
        turbidityChart.setData(turbidityLineData);
        turbidityChart.getXAxis().setDrawGridLines(false);
        turbidityChart.getAxisLeft().setDrawGridLines(false);
        turbidityChart.getAxisRight().setEnabled(false);
        turbidityChart.getAxisLeft().setAxisMinimum(0);
        turbidityChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        turbidityChart.getXAxis().setSpaceMin(0.2f);
        turbidityChart.getXAxis().setSpaceMax(0.2f);
        turbidityChart.getXAxis().setDrawLabels(false);
        turbidityChart.getLegend().setEnabled(false);
        turbidityChart.getDescription().setEnabled(false);
    }

    private void initColorChart(List<BarEntry> colorEntries, List<Integer> colors) {
        BarDataSet colorDataSet = new BarDataSet(colorEntries, "colors");
        colorDataSet.setColors(colors);
        colorDataSet.setDrawValues(false);
        BarData colorBarData = new BarData(colorDataSet);
        colorChart.setData(colorBarData);
        colorChart.getXAxis().setDrawGridLines(false);
        colorChart.getAxisLeft().setDrawGridLines(false);
        colorChart.getAxisRight().setDrawGridLines(false);
        colorChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        colorChart.getXAxis().setDrawLabels(false);
        colorChart.getAxisLeft().setDrawLabels(false);
        colorChart.getAxisRight().setDrawLabels(false);
        colorChart.getAxisLeft().setAxisMaximum(1);
        colorChart.getLegend().setEnabled(false);
        colorChart.getDescription().setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.current:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

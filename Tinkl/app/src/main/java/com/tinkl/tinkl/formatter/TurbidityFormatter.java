package com.tinkl.tinkl.formatter;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by eric1221bday on 4/18/17.
 */
public class TurbidityFormatter implements IValueFormatter {

    private DecimalFormat mFormat;

    public TurbidityFormatter() {
        mFormat = new DecimalFormat("###,###,##0.0"); // use one decimal
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        // write your logic here
        return mFormat.format(value) + " NTU"; // e.g. append a dollar-sign
    }
}

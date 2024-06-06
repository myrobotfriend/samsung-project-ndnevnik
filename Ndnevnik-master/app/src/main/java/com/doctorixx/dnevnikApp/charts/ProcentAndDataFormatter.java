package com.doctorixx.dnevnikApp.charts;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

public class ProcentAndDataFormatter implements ValueFormatter {

    private final String format;
    private final String postfix;
    private final DecimalFormat procentFormat = new DecimalFormat(".#");

    public ProcentAndDataFormatter() {
        format = "%s чел (%s";
        postfix = "%)";
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return String.format(format, (int) entry.getVal(), procentFormat.format(value)) + postfix;
    }
}

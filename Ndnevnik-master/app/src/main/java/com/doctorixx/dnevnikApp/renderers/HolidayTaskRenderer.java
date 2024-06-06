package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctorixx.easyDnevnik.stuctures.HomeTask;
import com.doctroixx.NDnevnik.R;

import java.util.Map;

public class HolidayTaskRenderer {

    private final Context ctx;
    private final Map<String, HomeTask> holidayTaskMap;
    private final LinearLayout parent;
    private final LayoutInflater lInflater;

    public HolidayTaskRenderer(Context ctx, Map<String, HomeTask> holidaytaks, LinearLayout parent) {
        this.ctx = ctx;
        this.holidayTaskMap = holidaytaks;
        this.parent = parent;

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void render() {
        parent.removeAllViews();
        if (holidayTaskMap == null) return;
        if (holidayTaskMap.size() == 0) return;

        renderText();

        for (String subjectKey : holidayTaskMap.keySet()) {

            HomeTask homeTask = holidayTaskMap.get(subjectKey);

            View view = lInflater.inflate(R.layout.holiday_task_element, null);

            ((TextView) view.findViewById(R.id.subject)).setText(subjectKey);
            ((TextView) view.findViewById(R.id.homeTaskText)).setText(homeTask.getData());


            parent.addView(view);
        }
    }

    private void renderText() {
        TextView text = new TextView(ctx);

        text.setText("Задание на каникулы");
        text.setTextColor(Color.RED);
        text.setTextSize(20);

        parent.addView(text);

    }

}



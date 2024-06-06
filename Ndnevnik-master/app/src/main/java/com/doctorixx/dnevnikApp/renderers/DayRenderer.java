package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctorixx.easyDnevnik.stuctures.Day;
import com.doctorixx.easyDnevnik.stuctures.Week;
import com.doctroixx.NDnevnik.R;

public class DayRenderer {
    private final Context ctx;
    private final Week week;
    private final LinearLayout parent;
    private final LayoutInflater lInflater;

    public DayRenderer(Context ctx, Week week, LinearLayout parent) {
        this.ctx = ctx;
        this.week = week;
        this.parent = parent;

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void renderWeek() {
        parent.removeAllViews();
        if (week == null) return;
        for (Day day : week.getDays()) {

            View dayView = lInflater.inflate(R.layout.day_element, null);
            ((TextView) dayView.findViewById(R.id.day_name)).setText(
                    String.format("%s, %s", day.getDate(), day.getName())
            );
            ((TextView) dayView.findViewById(R.id.description)).setText(day.getDescription());

            new LessonsRenderer(ctx, day, dayView.findViewById(R.id.lessons)).render();
            parent.addView(dayView);
        }
    }

}

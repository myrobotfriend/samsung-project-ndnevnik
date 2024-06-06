package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class GradeRenderer {
    private LayoutInflater lInflater;

    private Context ctx;
    private ViewGroup parent;
    private List<Grade> grades;

    public GradeRenderer(List<Grade> grades, Context ctx, ViewGroup view) {
        this.parent = view;
        this.grades = grades;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public void render() {
        render(15);
    }

    public void render(int textSize) {
//        parent.removeAllViews();

        for (Grade grade : grades) {
            View tempView = lInflater.inflate(R.layout.grade_element, null);

            TextView gradeText = tempView.findViewById(R.id.gradeText);
            gradeText.setTextSize(textSize);
            gradeText.setText(GradeUtils.toNormalGradeFormat(grade));

            WidgetUtils.addGradeColor(gradeText, grade, ctx);
            parent.addView(tempView);

        }


    }


}


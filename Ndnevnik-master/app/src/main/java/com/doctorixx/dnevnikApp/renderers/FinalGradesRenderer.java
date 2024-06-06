package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class FinalGradesRenderer {

    private final Context ctx;
    private final FinalGades finalGades;
    private final TableLayout parent;
    private final LayoutInflater lInflater;

    public FinalGradesRenderer(Context ctx, FinalGades finalGades, TableLayout parent) {
        this.ctx = ctx;
        this.finalGades = finalGades;
        this.parent = parent;

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void render() {
        parent.removeAllViews();

        List<String> headers = finalGades.getOutHeaders();
        View headerview = lInflater.inflate(R.layout.table_rows, null);
        try {
            ((TextView) headerview.findViewById(R.id.colomn_2)).setText(headers.get(0));
            ((TextView) headerview.findViewById(R.id.colomn_3)).setText(headers.get(1));
            ((TextView) headerview.findViewById(R.id.colomn_4)).setText(headers.get(2));
            ((TextView) headerview.findViewById(R.id.colomn_5)).setText(headers.get(3));
            ((TextView) headerview.findViewById(R.id.colomn_6)).setText("Средняя");
        } catch (IndexOutOfBoundsException ignored) {

        }

        parent.addView(headerview);


        for (String subjectKey : finalGades.getOut().keySet()) {

            List<Grade> grades = finalGades.getOut().get(subjectKey);

            View view = lInflater.inflate(R.layout.table_rows, null);
            try {
                ((TextView) view.findViewById(R.id.colomn_1)).setText(subjectKey);
                renderGrade(R.id.colomn_2, grades.get(0), view);
                renderGrade(R.id.colomn_3, grades.get(1), view);
                renderGrade(R.id.colomn_4, grades.get(2), view);
                renderGrade(R.id.colomn_5, grades.get(3), view);


            } catch (IndexOutOfBoundsException ignored) {

            }
            renderGrade(R.id.colomn_6, GradeUtils.averageGrades(grades), view);


            parent.addView(view);
        }
    }

    private void renderGrade(int id, Grade grade, View view) {
        TextView textView = view.findViewById(id);
        textView.setText(grade.getStringGrade());
        WidgetUtils.addGradeColor(textView, grade, ctx);

    }

    private void renderGrade(int id, float grade, View view) {
        TextView textView = view.findViewById(id);
        textView.setText(String.valueOf(grade));
        WidgetUtils.addGradeColor(textView, grade, ctx);

    }

}

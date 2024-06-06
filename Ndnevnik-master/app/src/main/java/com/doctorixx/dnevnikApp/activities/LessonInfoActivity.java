package com.doctorixx.dnevnikApp.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doctorixx.dnevnikApp.charts.ProcentAndDataFormatter;
import com.doctorixx.dnevnikApp.renderers.GradeRenderer;
import com.doctorixx.dnevnikApp.singeltons.containers.LessonInfoData;
import com.doctorixx.dnevnikApp.tasks.analitics.GetGradesByDateTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.stuctures.Lesson;
import com.doctroixx.NDnevnik.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LessonInfoActivity extends AppCompatActivity implements OnSuccess<Map<String, Integer>> {

    private PieChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_info);

        Lesson lesson = LessonInfoData.getInstance().getLesson();
        new GradeRenderer(lesson.getGrades(), getApplicationContext(), findViewById(R.id.your_grades)).render(25);

        ((TextView) findViewById(R.id.teacher)).setText("Ошибка имени");
        ((TextView) findViewById(R.id.lesson_info)).setText(lesson.getSubject());

        chart = findViewById(R.id.chart);
        initChart(chart);
//        setData();
        new GetGradesByDateTask(this, lesson.getSubject(), LessonInfoData.getInstance().getDate()).execute();
    }

    private void initChart(PieChart chart) {
        chart.setUsePercentValues(true);

        chart.setExtraOffsets(5, 10, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(false);
        chart.spin(1400, 0, 360, Easing.EasingOption.EaseInOutQuad);

        chart.setRotationAngle(0);
        chart.setNoDataText("Нет оценок для отображения");
        chart.setDescription("");

        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(false);
    }

    private void setData(Map<String, Integer> grades) {
        ArrayList<Entry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        int i = 0;
        for (String key : grades.keySet()) {
            labels.add("Оценка " + key);
            entries.add(new Entry(grades.get(key), i));
            i++;
        }
//
//        labels.add("Оценка 5");
//        labels.add("Оценка 3");
//        labels.add("Оценка 4");
//        labels.add("Оценка 2");
//
//        entries.add(new Entry(1, 0));
//        entries.add(new Entry(4, 1));
//        entries.add(new Entry(2, 2));
//        entries.add(new Entry(1, 3));

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(ColorTemplate.getHoloBlue());
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

        dataSet.setColors(colors);

        PieData data = new PieData(labels, dataSet);
        data.setValueFormatter(new ProcentAndDataFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);

        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public void run(Map<String, Integer> stringIntegerMap) {
        System.out.println("hello");
        setData(stringIntegerMap);
    }
}
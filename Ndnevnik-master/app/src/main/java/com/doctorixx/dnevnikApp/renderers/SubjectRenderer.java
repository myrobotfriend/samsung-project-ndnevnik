package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.SubjectAverageConatiner;
import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.Subject;
import com.doctroixx.NDnevnik.R;

import java.util.HashMap;
import java.util.List;

public class SubjectRenderer {

    private LayoutInflater lInflater;

    private Context ctx;
    private Subject subject;
    private ViewGroup parent;
    private List<Grade> grades;

    public SubjectRenderer(Subject subject, List<Grade> grades, Context ctx, ViewGroup view) {
        this.subject = subject;
        this.parent = view;
        this.grades = grades;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //parent.removeAllViews();
    }

    public void render() {
        View view = lInflater.inflate(R.layout.subject_grades_element, null);
        ViewGroup linearLayout = view.findViewById(R.id.gradesLayout);


        new GradeRenderer(grades, ctx, linearLayout).render();

        float average = GradeUtils.averageGrades(grades);
        String strAvverage = String.format("%,.2f", average);
        if (average == 0) strAvverage = "    ";
        saveAverage(average);

        TextView avverageText = view.findViewById(R.id.avverage);

        ((TextView) view.findViewById(R.id.subject)).setText(subject.getName());
        avverageText.setText(strAvverage);

        WidgetUtils.addGradeColor(avverageText, average, ctx);

        parent.addView(view);


    }


    private void saveAverage(float average) {
        DataSerializer<SubjectAverageConatiner> serializer = new DataSerializer<>(SubjectAverageConatiner.class, ctx);
        SubjectAverageConatiner conatiner = serializer.openData(SerializationFilesNames.SUBJECT_AVERAGES_CONTAINER_PATH);
        if (conatiner == null) conatiner = new SubjectAverageConatiner(new HashMap<>());
        conatiner.getAverages().put(subject.getName(), average);
        serializer.saveData(conatiner, SerializationFilesNames.SUBJECT_AVERAGES_CONTAINER_PATH);
    }


}

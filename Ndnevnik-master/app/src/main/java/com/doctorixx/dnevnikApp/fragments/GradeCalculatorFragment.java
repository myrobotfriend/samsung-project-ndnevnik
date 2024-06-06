package com.doctorixx.dnevnikApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.GradeRenderer;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.dnevnikApp.utils.GradeCalculator;
import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctroixx.NDnevnik.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class GradeCalculatorFragment extends Fragment implements View.OnClickListener {

    private TextView averageText;
    private int gradeNeed;

    private LinearLayout variantsList;

    private List<Grade> grades;
    private String m_subject = "";
    private float average;

    private Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grade_calculator, container, false);

        Button button = view.findViewById(R.id.button_result);
        SeekBar seekBar = view.findViewById(R.id.seekBar);
        TextView gradeText = view.findViewById(R.id.needGradeText);
        averageText = view.findViewById(R.id.averageGrade);
        variantsList = view.findViewById(R.id.variants_list);

        button.setOnClickListener(this);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (gradeNeed != i) clearRender();
                gradeText.setText(String.valueOf(i));
                WidgetUtils.addGradeColor(gradeText, new Grade(String.valueOf(i), null, null), ctx);
                gradeNeed = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Spinner spinner = view.findViewById(R.id.spinner);

        GradeContainer data = new DataSerializer<>(GradeContainer.class, ctx).openData(SerializationFilesNames.GRADE_CONTAINER_PATH);
        ArrayList<String> subjects = new ArrayList<>(data.getGrades().keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, subjects);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = subjects.get(i);
                if (!m_subject.equals(subject)) clearRender();
                List<Grade> grades = data.getGrades().get(subject);
                float average = GradeUtils.averageGrades(grades);

                renderAverage(grades);
                setData(grades, average, subject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void setData(List<Grade> grades, float average, String subject) {
        this.grades = grades;
        this.average = average;
        this.m_subject = subject;
    }

    private void renderAverage(List<Grade> grades) {
        float average = GradeUtils.averageGrades(grades);
        String strAvverage = String.format("%,.2f", average);
        Grade grade = null;
        if (average == 0) {
            grade = new Grade("Н", null, null);
        } else {
            grade = new Grade(String.valueOf(Math.round(average)), null, null);
        }


        WidgetUtils.addGradeColor(averageText, grade, getContext());
        averageText.setText(strAvverage);
    }


    private void clearRender() {
        variantsList.removeAllViews();
    }


    @Override
    public void onClick(View view) {
        float minimalGrade = (float) (gradeNeed - 1 + 0.5);

        clearRender();

        if (average == 0) {
            Snackbar.make(view, "Нет оценок", Snackbar.LENGTH_LONG).show();
        } else if (average >= minimalGrade) {
            Snackbar.make(view, "Текущая оценка выше или равна требуемой", Snackbar.LENGTH_LONG).show();
        } else {
            List<List<Grade>> gradesVariants = GradeCalculator.calculateGrades(grades, minimalGrade);

            if (gradesVariants.size() == 0) {
                Snackbar.make(view,
                        "Вам нужно набрать слишком много оценок, поэтому они не показываются",
                        Snackbar.LENGTH_LONG).show();
            } else {
                for (List<Grade> variant : gradesVariants) {
                    if (variant == null) continue;

                    LinearLayout variantLayout = new LinearLayout(ctx);
                    variantLayout.setOrientation(LinearLayout.VERTICAL);

                    TextView textView = new TextView(ctx);
                    textView.setText("Вариант:");

                    LinearLayout gradesLayout = new LinearLayout(ctx);
                    new GradeRenderer(variant, ctx, gradesLayout).render();

                    variantLayout.addView(textView);
                    variantLayout.addView(gradesLayout);

                    variantsList.addView(variantLayout);
                }
            }


        }
    }
}

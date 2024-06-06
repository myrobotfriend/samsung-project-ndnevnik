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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.SubjectRenderer;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.Subject;
import com.doctroixx.NDnevnik.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GradeSimulatorFragment extends Fragment implements View.OnClickListener {

    private int gradeNeed;

    private LinearLayout variantsList;
    private List<Grade> grades;
    private List<Grade> modificalGrades;
    private List<Grade> simulatedGrades;
    private RadioGroup radioGroup;
    private String m_subject = "";

    private String checkedGrade = "1";

    private Button addButton;
    private Button clearButton;

    private Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ctx = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_grade_simulator, container, false);

        variantsList = view.findViewById(R.id.variants_list);
        radioGroup = view.findViewById(R.id.radioGroup);
        addButton = view.findViewById(R.id.button_add);
        clearButton = view.findViewById(R.id.button_clear);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                final int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton button1 = view.findViewById(checkedRadioButtonId);
                final String button1Text = button1.getText().toString();
                Logger.getInstance().info(button1Text);
                checkedGrade = button1Text;
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificalGrades.add(new Grade(checkedGrade, null, null));
                simulatedGrades.add(new Grade(checkedGrade, null, null));
                GradeSimulatorFragment.this.onClick(view);
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modificalGrades = new ArrayList<>(grades);
                simulatedGrades = new ArrayList<>();
                GradeSimulatorFragment.this.onClick(view);
            }
        });



        Spinner spinner = view.findViewById(R.id.spinner);

        GradeContainer data = new DataSerializer<>(GradeContainer.class, ctx).openData(SerializationFilesNames.GRADE_CONTAINER_PATH);
        List<String> subjects = new LinkedList<>(data.getGrades().keySet());
        subjects.add(0,"Не выбрано");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ctx, android.R.layout.simple_spinner_dropdown_item, subjects);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String subject = subjects.get(i);
                if (!m_subject.equals(subject)) clearRender();
                List<Grade> grades = data.getGrades().get(subject);
                if (grades == null) grades = new ArrayList<>();




                setData(grades, subject);
                onClick(null);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }

    private void setData(List<Grade> grades,  String subject) {
        this.grades = grades;
        this.modificalGrades = new ArrayList<>(grades);
        this.simulatedGrades = new ArrayList<>();
        this.m_subject = subject;
    }


    private void clearRender() {
        variantsList.removeAllViews();
    }


    @Override
    public void onClick(View view) {
        float minimalGrade = (float) (gradeNeed - 1 + 0.5);


        clearRender();

        new SubjectRenderer(new Subject("Настоящие оценки"), grades, getContext(),
                variantsList).render();

        new SubjectRenderer(new Subject("Добавленные оценки"), simulatedGrades, getContext(),
                variantsList).render();


        new SubjectRenderer(new Subject("Симуляция оценок"), modificalGrades, getContext(),
                variantsList).render();



    }

}

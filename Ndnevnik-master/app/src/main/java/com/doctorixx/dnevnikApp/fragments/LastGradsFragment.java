package com.doctorixx.dnevnikApp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorixx.dnevnikApp.adapters.LastGradesAdapter;
import com.doctorixx.dnevnikApp.other.Date;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.GradeContainer;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctroixx.NDnevnik.R;

import java.util.ArrayList;
import java.util.List;


public class LastGradsFragment extends Fragment {

    private List<Grade> grades = new ArrayList<>();

    public LastGradsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_last_grads, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.last_grades_rlist);
        LastGradesAdapter adapter = new LastGradesAdapter(grades, getContext());


        recyclerView.setAdapter(adapter);

        return view;
    }

    private void initData() {
//        for (int i = 0; i < 100; i++) {
//            grades.add(new Grade("5+", "11.11.21", new Subject("Алгебра")));
//        }
        GradeContainer gradeContainer = new DataSerializer<>(GradeContainer.class, getContext())
                .openData(SerializationFilesNames.GRADE_CONTAINER_PATH);
        if (gradeContainer != null) {
            for (List<Grade> tempGrades : gradeContainer.getGrades().values()) {
                grades.addAll(tempGrades);
            }
        }
        sortGrades();
        System.out.println();

    }


    private void sortGrades() {
        for (int i = 0; i < grades.size(); i++) {
            for (int j = 0; j < grades.size() - 1; j++) {
                Grade grade = grades.get(j);
                Grade nextGrade = grades.get(j + 1);

                Date gradeDate = new Date(grade.getDate(), "-");
                Date nextGradeeDate = new Date(nextGrade.getDate(), "-");
                if (gradeDate.before(nextGradeeDate)) {
                    Grade tempGrade = grade;
                    grades.set(j, nextGrade);
                    grades.set(j + 1, tempGrade);
                }


            }
        }
    }

}
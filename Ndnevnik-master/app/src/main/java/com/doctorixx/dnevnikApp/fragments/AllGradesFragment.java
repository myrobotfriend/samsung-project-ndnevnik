package com.doctorixx.dnevnikApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.SubjectRenderer;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetGradesTask;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.Subject;
import com.doctroixx.NDnevnik.R;

import java.util.List;
import java.util.Map;

public class AllGradesFragment extends Fragment {

    private LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_grades, container, false);
        linearLayout = view.findViewById(R.id.subjectsAndGradesList);

        //for delete debug views
        linearLayout.removeAllViews();

        new GetGradesTask(new Runnable() {
            @Override
            public void run() {
                Context ctx = getContext();
                if(ctx == null) {
                    return;
                }

                Map<String, List<Grade>> grades = DnevnikAction.getInstance().getGrades().getGrades();
                linearLayout.removeAllViews();

                for (String subjectName : grades.keySet()) {
                    List<Grade> gradesTempList = grades.get(subjectName);
                    if (gradesTempList.isEmpty()) continue;


                    new SubjectRenderer(new Subject(subjectName), gradesTempList, ctx,
                            linearLayout).render();
                }
            }
        }).execute();

        return view;
    }
}
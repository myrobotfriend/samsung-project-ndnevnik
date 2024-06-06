package com.doctorixx.dnevnikApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.FinalGradesRenderer;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetFinalGradesTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;
import com.doctroixx.NDnevnik.R;

public class FinalGradesFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_final_grades, container, false);

        TableLayout table = view.findViewById(R.id.table);

        new GetFinalGradesTask(new OnSuccess<FinalGades>() {
            @Override
            public void run(FinalGades finalGades) {
                Context ctx = getContext();
                if (ctx == null) return;
                new FinalGradesRenderer(ctx, finalGades, table).render();
            }
        }).execute();

        return view;
    }
}
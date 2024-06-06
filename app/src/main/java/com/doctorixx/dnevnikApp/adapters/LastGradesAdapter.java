package com.doctorixx.dnevnikApp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class LastGradesAdapter extends  RecyclerView.Adapter<LastGradesViewHolder>{

    private final LayoutInflater inflater;
    private final Context context;
    private final List<Grade> grades;

    public LastGradesAdapter(List<Grade> grades, Context context){
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.grades = grades;
    }

    @NonNull
    @Override
    public LastGradesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.last_grade_element, parent, false);
        return new LastGradesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LastGradesViewHolder holder, int position) {
        Grade grade = grades.get(position);
        holder.date.setText(grade.getDate());
        holder.subject.setText(grade.getSubject().getName());
        WidgetUtils.addGradeColor(holder.grade,grade, context);
        holder.grade.setText(GradeUtils.toNormalGradeFormat(grade));
    }

    @Override
    public int getItemCount() {
        return grades.size();
    }
}

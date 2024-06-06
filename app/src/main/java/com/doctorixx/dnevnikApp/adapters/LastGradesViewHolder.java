package com.doctorixx.dnevnikApp.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doctroixx.NDnevnik.R;

public class LastGradesViewHolder extends RecyclerView.ViewHolder {
    final TextView date, subject,grade;

    LastGradesViewHolder(View view) {
        super(view);
        date = view.findViewById(R.id.date_text);
        subject = view.findViewById(R.id.subject_text);
        grade = view.findViewById(R.id.grade);
    }
}

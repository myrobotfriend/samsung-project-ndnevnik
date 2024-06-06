package com.doctorixx.dnevnikApp.utils;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatImageButton;

import com.doctorixx.dnevnikApp.activities.LessonInfoActivity;
import com.doctorixx.dnevnikApp.activities.MoreAnnouncementActivity;
import com.doctorixx.dnevnikApp.activities.MoreMessageActivity;
import com.doctorixx.dnevnikApp.exceptions.UnknownGradeException;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.singeltons.HomeTaskCompliter;
import com.doctorixx.dnevnikApp.singeltons.Messager;
import com.doctorixx.dnevnikApp.singeltons.containers.Announmenter;
import com.doctorixx.dnevnikApp.singeltons.containers.LessonInfoData;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.HomeTask;
import com.doctorixx.easyDnevnik.stuctures.Lesson;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;
import com.doctroixx.NDnevnik.R;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public final class WidgetUtils {

    private WidgetUtils() {
    }


    public static void addHomeTaskFunctional(View view, HomeTask homeTask) {
        HomeTaskCompliter homeTaskCompliter = HomeTaskCompliter.getInstance(DnevnikAction.getInstance().getContext());

        TextView homeTaskText = view.findViewById(R.id.homeTaskText);
        CheckBox checkBox = view.findViewById(R.id.checkBox);

        View.OnClickListener onClickCheckBox = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {
                    homeTaskCompliter.doneHomeWork(homeTask);
                    homeTaskText.setPaintFlags(homeTaskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    homeTaskCompliter.undoHomeWork(homeTask);
                    homeTaskText.setPaintFlags(homeTaskText.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                }


            }
        };

        if (homeTaskCompliter.isDone(homeTask)) {
            checkBox.setChecked(true);
            onClickCheckBox.onClick(checkBox);
        }


        checkBox.setOnClickListener(onClickCheckBox);
    }

    public static void addNoteFunctional(View view, boolean done, String subjectId, ViewGroup parent) {

        TextView homeTaskText = view.findViewById(R.id.noteText);
        CheckBox checkBox = view.findViewById(R.id.checkBox);
        AppCompatImageButton deleteBtn = view.findViewById(R.id.deleteBtn);


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(parent.getContext(), "ddwewdd", Toast.LENGTH_SHORT).show();
                parent.removeView(view);
            }
        });
        View.OnClickListener onClickCheckBox = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox.isChecked()) {

                    homeTaskText.setPaintFlags(homeTaskText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    homeTaskText.setPaintFlags(homeTaskText.getPaintFlags() ^ Paint.STRIKE_THRU_TEXT_FLAG);
                }


            }
        };


        if (done) {
            checkBox.setChecked(true);
            onClickCheckBox.onClick(checkBox);
        }


        checkBox.setOnClickListener(onClickCheckBox);
    }

    public static void addGradeColor(TextView textView, float average, Context context) {
        Grade grade = new Grade(String.valueOf(Math.round(average)), null, null);
        addGradeColor(textView, grade, context);

    }

    public static void addGradeColor(TextView textView, Grade grade, Context context) {
        Resources resources = context.getResources();

        String stringGrade = GradeUtils.getGradeNormalString(grade).toLowerCase();

        switch (stringGrade) {
            case "5":
            case "осв":
            case "зач":
                textView.setTextColor(resources.getColor(R.color.grade_5));
                break;
            case "4":
                textView.setTextColor(resources.getColor((R.color.grade_4)));
                break;
            case "3":
                textView.setTextColor(resources.getColor((R.color.grade_3)));
                break;
            case "2":
                textView.setTextColor(resources.getColor((R.color.grade_2)));
                break;
            case "1":
                textView.setTextColor(resources.getColor((R.color.grade_1)));
                break;
            case "н":
            case "м":
                textView.setTextColor(resources.getColor((R.color.grade_H)));
                break;
            case "0":
                //Do not remove; Used on SubjectRenderer
                break;
            default:
                textView.setTextColor(resources.getColor((R.color.errored_grade)));
                FirebaseCrashlytics.getInstance().recordException(new UnknownGradeException(grade.getStringGrade()));
                break;
        }
        textView.beginBatchEdit();
    }

    public static int getGradeColor(float average, Context context) {
        Resources resources = context.getResources();
        Grade grade = new Grade(String.valueOf(Math.round(average)), null, null);

        String stringGrade = GradeUtils.getGradeNormalString(grade).toLowerCase();

        switch (stringGrade) {
            case "5":
            case "осв":
            case "зач":
                return resources.getColor(R.color.grade_5);
            case "4":
                return resources.getColor(R.color.grade_4);
            case "3":
                return resources.getColor(R.color.grade_3);
            case "2":
                return resources.getColor(R.color.grade_2);
            case "1":
                return resources.getColor(R.color.grade_1);
            case "н":
            case "м":
                return resources.getColor(R.color.grade_H);
            case "0":
                //Do not remove; Used on SubjectRenderer
                break;
            default:
                return resources.getColor(R.color.errored_grade);
        }
        return 0;
    }

    public static void addOnAnnouncementClick(View view, Announcement announcement, Context ctx) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ctx, MoreAnnouncementActivity.class);
                Announmenter.getInstance().setAnnouncement(announcement);
                ctx.startActivity(intent);
            }
        });
    }

    public static void addOnLessonClick(View view, Lesson lesson, Context ctx, String date) {
        String[] splitedDate = date.split("[\\.]");
        String day = splitedDate[0];
        String mouth = splitedDate[1];

        String formattedDate = String.format("2023-%s-%s", mouth, day);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LessonInfoData.getInstance().setLesson(lesson);
                LessonInfoData.getInstance().setDate(formattedDate);
                Intent intent = new Intent(ctx, LessonInfoActivity.class);
                ctx.startActivity(intent);
            }
        });
    }

    public static void addOnMessageClicker(View view, Message message) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MoreMessageActivity.class);
                Messager.getInstance().setMessage(message);
                view.getContext().startActivity(intent);
            }
        });
    }
}

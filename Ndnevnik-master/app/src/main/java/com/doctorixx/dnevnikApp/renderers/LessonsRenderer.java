package com.doctorixx.dnevnikApp.renderers;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.SubjectAverageConatiner;
import com.doctorixx.dnevnikApp.utils.GradeUtils;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Day;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.HomeTask;
import com.doctorixx.easyDnevnik.stuctures.Lesson;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class LessonsRenderer {

    private Context ctx;
    private Day day;

    private LayoutInflater lInflater;
    private List<Lesson> lessons;
    private LinearLayout parent;

    public LessonsRenderer(Context context, Day day, LinearLayout parent) {
        ctx = context;
        this.parent = parent;
        this.day = day;
        this.lessons = day.getLessons();
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public void render() {
        renderViews();
    }

    private void renderViews() {
        for (Lesson lesson : lessons) {

            View view = lInflater.inflate(R.layout.lesson_element, null);

            WidgetUtils.addOnLessonClick(view, lesson, ctx, day.getDate());

            ((TextView) view.findViewById(R.id.subject)).setText(lesson.getSubject());
            ((TextView) view.findViewById(R.id.lessonTime)).setText(lesson.getTime());
            ((TextView) view.findViewById(R.id.lessonNumber)).setText(lesson.getStringLessonNumber());



            ImageView lineColorCode = view.findViewById(R.id.subjectAverageColor);

            SubjectAverageConatiner conatiner = null;

            if (SubjectAverageConatiner.getInstance() != null){
                conatiner = SubjectAverageConatiner.getInstance();
            }else {
                conatiner =  new DataSerializer<>(SubjectAverageConatiner.class, ctx)
                        .openData(SerializationFilesNames.SUBJECT_AVERAGES_CONTAINER_PATH);
                SubjectAverageConatiner.setInstance(conatiner);
            }

            if (conatiner != null) {
                if (conatiner.getAverages().containsKey(lesson.getSubject())) {
                    float floatColor = conatiner.getAverages().get(lesson.getSubject());
                    int color = WidgetUtils.getGradeColor(floatColor, ctx);
                    lineColorCode.setColorFilter(color);
                }

            }


            String subjectNodeId = String.format("%s%s", day.getDate(), lesson.getSubject());

            new NotesElementRenderer(view.findViewById(R.id.note_parent), subjectNodeId, ctx);

            // Hometasks
            LinearLayout hometaskList = view.findViewById(R.id.homeTaskList);

            for (HomeTask homeTask : lesson.getHomeTasks()) {
                View tempView = lInflater.inflate(R.layout.homework_element, null);
                ((TextView) tempView.findViewById(R.id.homeTaskText)).setText(homeTask.getData());
                WidgetUtils.addHomeTaskFunctional(tempView, homeTask);
                hometaskList.addView(tempView);
            }

            //Grades

            LinearLayout gradesList = view.findViewById(R.id.gradesList);

            for (Grade grade : lesson.getGrades()) {
                View tempView = lInflater.inflate(R.layout.grade_element, null);
                TextView gradeText = tempView.findViewById(R.id.gradeText);
                gradeText.setText(GradeUtils.toNormalGradeFormat(grade));
                WidgetUtils.addGradeColor(gradeText, grade, ctx);
                gradesList.addView(tempView);
            }

            parent.addView(view);
        }

    }
}

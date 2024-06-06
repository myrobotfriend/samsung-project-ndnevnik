package com.doctorixx.easyDnevnik.instances;

import com.doctorixx.easyDnevnik.Announced;
import com.doctorixx.easyDnevnik.Dnevnik;
import com.doctorixx.easyDnevnik.Messageble;
import com.doctorixx.easyDnevnik.exceptions.DnevnikAuthException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctorixx.easyDnevnik.stuctures.Day;
import com.doctorixx.easyDnevnik.stuctures.EducationClass;
import com.doctorixx.easyDnevnik.stuctures.FinalGades;
import com.doctorixx.easyDnevnik.stuctures.Grade;
import com.doctorixx.easyDnevnik.stuctures.HomeTask;
import com.doctorixx.easyDnevnik.stuctures.HomeTaskType;
import com.doctorixx.easyDnevnik.stuctures.Lesson;
import com.doctorixx.easyDnevnik.stuctures.ProfileInfo;
import com.doctorixx.easyDnevnik.stuctures.Subject;
import com.doctorixx.easyDnevnik.stuctures.Week;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDnevnik implements Dnevnik, Messageble, Announced {


    public static final String DEFAULT_DATE = "02-02-2023";

    @Override
    public void auth(String login, String password) throws DnevnikAuthException, DnevnikNoInternetConnection, DnevnikNotOnlineException {

    }

    @Override
    public Map<String, List<Grade>> getGrades() throws DnevnikConnectException {
        Map<String, List<Grade>> out = new HashMap<>();
        List<Grade> grades = new ArrayList<>();

        for (int i = 0; i < 26; i++) {
            grades.add(new Grade(String.valueOf(i / 5), DEFAULT_DATE, new Subject("Кактусоведенье")));
        }

        grades.add(new Grade("42", DEFAULT_DATE, new Subject("Кактусоведенье")));

        out.put("Кактусоведенье", grades);

        return out;
    }

    @Override
    public FinalGades getFinalGrades() throws DnevnikConnectException {

        final List<String> headers = List.of("I", "II", "III", "IV");

        final Map<String, List<Grade>> out = Map.of(
                "Алгебра", List.of(
                        new Grade("5", "", new Subject(""))
                ),
                "Геометрия", List.of(
                        new Grade("н/а", "", new Subject("")),
                        new Grade("5", "", new Subject(""))
                )
        );

        return new FinalGades(out, headers);
    }


    @Override
    public ProfileInfo getProfileInfo() throws DnevnikConnectException {
        return new ProfileInfo(
                "test test",
                "test test",
                "test test",
                new EducationClass("1A")
        );
    }

    @Override
    public Week getWeek() throws DnevnikConnectException {
        return getWeekByIndex(0);
    }

    @Override
    public Week getWeekByIndex(int index) throws DnevnikConnectException {

        if (index % 2 == 0) return getWeek1();
        else return getWeek2();
    }

    @Override
    public Week getWeekByDate(Calendar date) {
        return null;
    }

    private Week getWeek1() {
        List<Lesson> lessons = new ArrayList<>();
        List<HomeTask> homeTasks = new ArrayList<>();
        List<Grade> grades = new ArrayList<>();

        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "Нет дз"));
        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "Презентация"));
        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "ТПО с 25"));

        grades.add(new Grade("4", "", new Subject("")));
        grades.add(new Grade("4", "", new Subject("")));
        grades.add(new Grade("5", "", new Subject("")));
        grades.add(new Grade("4", "", new Subject("")));

        lessons.add(new Lesson(0, "Биология", "8:00-9:00", grades, homeTasks.subList(2, 3)));
        lessons.add(new Lesson(1, "География", "8:00-9:00", grades, homeTasks));
        lessons.add(new Lesson(2, "Англ. яз", "8:00-9:00", grades, homeTasks.subList(1, 2)));
        lessons.add(new Lesson(3, "Русский яз.", "8:00-9:00", grades, homeTasks));
        lessons.add(new Lesson(4, "ОБЖ", "8:00-9:00", grades, homeTasks.subList(0, 1)));
        Day day = new Day(lessons, "01.02");
        day.setName("День недели");

        return new Week(day, day, day, day, day, day, day);
    }

    private Week getWeek2() {
        List<Lesson> lessons = new ArrayList<>();
        List<HomeTask> homeTasks = new ArrayList<>();
        List<Grade> grades = new ArrayList<>();

        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "Параграф 3"));
        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "выучить карту"));
        homeTasks.add(new HomeTask(HomeTaskType.TEXT, "дз в тетради"));

        grades.add(new Grade("2", "", new Subject("")));
        grades.add(new Grade("Н", "", new Subject("")));
        grades.add(new Grade("4", "", new Subject("")));
        grades.add(new Grade("5", "", new Subject("")));

        lessons.add(new Lesson(0, "Биология", "8:00-9:00", grades, homeTasks.subList(2, 3)));
        lessons.add(new Lesson(1, "География", "8:00-9:00", grades, homeTasks));
        lessons.add(new Lesson(2, "Англ. яз", "8:00-9:00", grades, homeTasks.subList(1, 2)));
        lessons.add(new Lesson(3, "Русский яз.", "8:00-9:00", grades, homeTasks));
        lessons.add(new Lesson(4, "ОБЖ", "8:00-9:00", grades, homeTasks.subList(0, 1)));

        Day day = new Day(lessons, "02.02");
        day.setName("День недели");

        return new Week(day, day, day, day, day, day, day);
    }

    @Override
    public List<Message> getMessages() throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        return new ArrayList<>();
    }

    @Override
    public List<Announcement> getAllAnnouncements() throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        return new ArrayList<>();
    }
}
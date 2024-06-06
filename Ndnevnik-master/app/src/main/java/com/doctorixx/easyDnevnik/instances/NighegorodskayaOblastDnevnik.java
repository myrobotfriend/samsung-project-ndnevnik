package com.doctorixx.easyDnevnik.instances;

import android.content.Context;

import com.doctorixx.easyDnevnik.Announced;
import com.doctorixx.easyDnevnik.Dnevnik;
import com.doctorixx.easyDnevnik.Messageble;
import com.doctorixx.easyDnevnik.RegexPattern;
import com.doctorixx.easyDnevnik.exceptions.DnevnikAuthException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikConnectException;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctorixx.easyDnevnik.stuctures.Day;
import com.doctorixx.easyDnevnik.stuctures.DayType;
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
import com.doctorixx.easyDnevnik.stuctures.messages.MessageStatus;
import com.doctorixx.easyDnevnik.stuctures.messages.MessageUser;
import com.doctorixx.network.RequestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.RequestBody;


public class NighegorodskayaOblastDnevnik implements Dnevnik, Announced, Messageble {

    private final RequestClient httpclient;


    public NighegorodskayaOblastDnevnik(Context ctx) {
        httpclient = new RequestClient(ctx);
    }

    @Override
    public void auth(String login, String password) throws DnevnikAuthException, DnevnikNoInternetConnection, DnevnikNotOnlineException {

        RequestBody body = new FormBody.Builder().add("username", login).add("password", password).add("return_uri", "/").build();


        String resp = httpclient.post("https://edu.gounn.ru/ajaxauthorize", body);
        try {
            JSONObject json = new JSONObject(resp);
            if (!json.getBoolean("result")) {
                throw new DnevnikAuthException(json.getJSONArray("errors").getJSONObject(0).getString("text"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public Map<String, List<Grade>> getGrades() throws DnevnikNoInternetConnection, DnevnikNotOnlineException {

        Map<String, List<Grade>> gradeMap = new LinkedHashMap<>();

        String resp = httpclient.get("https://edu.gounn.ru/journal-student-grades-action/");
        Document responeDocument = Jsoup.parse(resp);
        Elements gradeCells = responeDocument.select("div.cell");

        String grade_period = responeDocument.selectFirst("#periods-context-button").text();

        for (Element gradeElem : gradeCells) {
            if (gradeElem.childNodeSize() == 0) continue;

            String subjectName = gradeElem.attr("name");
            Element elementGrade = gradeElem.selectFirst("div.cell-data");
            String markDate = gradeElem.attr("mark_date");

            if (elementGrade == null) continue;
            if (elementGrade.text() == " " || elementGrade.text() == "") continue;

            if (!gradeMap.containsKey(subjectName)) {
                gradeMap.put(subjectName, new ArrayList<>());
            }

            Grade grade = new Grade(elementGrade.text(), markDate, new Subject(subjectName));
            grade.setPeriod(grade_period);
            if (grade.isValid()) gradeMap.get(subjectName).add(grade);

        }


        return gradeMap;
    }

    @Override
    public FinalGades getFinalGrades() throws DnevnikConnectException {
        Map<String, List<Grade>> out = new LinkedHashMap<>();
        List<String> outHeaders = new ArrayList<>();

        try {
            String response = httpclient.get("https://edu.gounn.ru/journal-student-resultmarks-action/");
            Document document = Jsoup.parse(response);

            Elements subjectsElements = document.select("div.cells_rows").get(1).select("div.cell");
            Elements subjectsMarksElementsTrased = document.select("div.cells_marks");

            List<Elements> subjectMarksColomns = new ArrayList<>();

            for (Element element : subjectsMarksElementsTrased) {
                if (element.className().equals("cells cells_marks"))
                    subjectMarksColomns.add(element.children());
                else if (element.className().equals("cell  cells_marks cells_header")) {
                    outHeaders.add(element.text());
                }
            }

            for (Element subjectElement : subjectsElements) {
                String name = subjectElement.attr("name");

                out.put(name, new ArrayList<>());
            }

            for (Elements marks : subjectMarksColomns) {
                for (Element mark : marks) {
                    String name = mark.attr("name");
                    Element gradeText = mark.selectFirst("div.cell-data");
                    Grade grade = new Grade(gradeText.text(), "_", new Subject(name));
                    if (grade.isValid()) out.get(name).add(grade);
                }
            }
        } catch (Exception ignored) {
        }
        return new FinalGades(out, outHeaders);
    }

    @Override
    public ProfileInfo getProfileInfo() throws DnevnikNoInternetConnection, DnevnikNotOnlineException {

        String responceProfile = httpclient.get("https://edu.gounn.ru/journal-user-preferences-action/");
        String responceClass = httpclient.get("https://edu.gounn.ru/journal-schedule-action/");


        Document responeProfileDocument = Jsoup.parse(responceProfile);
        Document responeClassDocument = Jsoup.parse(responceClass);

        String lastName = responeProfileDocument.selectFirst("input#lastname").val();
        String firstName = responeProfileDocument.selectFirst("input#firstname").val();
        String schoolName = responeProfileDocument.select("meta[name=og:site_name]").attr("content");
        String className = null;
        try {
            className = responeClassDocument.selectFirst("div.menu2 ").selectFirst("h1").text();
        } catch (NullPointerException e) {
            className = null;
        }

        return new ProfileInfo(firstName, lastName, schoolName, new EducationClass(className));
    }

    @Override
    public Week getWeek() throws DnevnikConnectException {
        return getWeekByIndex(0);
    }

    @Override
    public Week getWeekByIndex(int index) throws DnevnikConnectException {
        Week outWeek = null;

        String resp = httpclient.get(String.format("https://edu.gounn.ru/journal-app/week.%d", index));
        if (resp == null) return null;
        Document weekDocument = Jsoup.parse(resp);
        List<Day> daysList = new ArrayList<>();

        Elements daysElements = weekDocument.select("div.dnevnik-day");

        for (Element dayElement : daysElements) {
            String[] dateAndName = dayElement.selectFirst("div.dnevnik-day__title").text().split(", ");
            String date = dateAndName[1];
            String name = dateAndName[0];

            List<Lesson> lessons = new ArrayList<>();

            Element EmptydiscriptionElement = dayElement.selectFirst(".page-empty");
            Element HolidaydiscriptionElement = dayElement.selectFirst(".dnevnik-day__holiday");
            if (EmptydiscriptionElement != null) {
                Day tempDay = new Day(EmptydiscriptionElement.text(), date, DayType.NOLESSON);
                tempDay.setName(name);
                daysList.add(tempDay);
            } else if (HolidaydiscriptionElement != null) {

                Day tempDay = new Day(HolidaydiscriptionElement.text(), date, DayType.HOLIDAY);
                tempDay.setName(name);
                daysList.add(tempDay);
            } else {
                Elements lessonsElements = dayElement.select("div.dnevnik-lesson");

                for (Element lessonElement : lessonsElements) {

                    List<HomeTask> homeTasks = new ArrayList<>();
                    List<Grade> grades = new ArrayList<>();

                    String lessonNumberString = lessonElement.selectFirst(".dnevnik-lesson__number ").text();

                    int lessonNumber = -1;
                    if (lessonNumberString.length() != 0) {
                        lessonNumber = Integer.parseInt(lessonNumberString.substring(0, lessonNumberString.length() - 1));
                    }
                    String lessonName = lessonElement.selectFirst(".js-rt_licey-dnevnik-subject").text();
                    String lessonTime = lessonElement.selectFirst(".dnevnik-lesson__time").text();

                    Elements homeTasksElements = lessonElement.select(".dnevnik-lesson__task");
                    Elements gradesElements = lessonElement.select(".dnevnik-mark__value");
                    for (Element homeTasksElement : homeTasksElements) {
                        Element tryGetLink = homeTasksElement.selectFirst("a");
                        if (tryGetLink != null) {
//                            homeTasks.add(new HomeTask(HomeTaskType.FILE, tryGetLink.attr("href")));
                            String currentText = homeTasksElement.text();
                            currentText = currentText.replaceAll(RegexPattern.URL, "[ССЫЛКА]");

                            String homeTaskText = currentText + "\n\n" + tryGetLink.attr("href");
                            homeTasks.add(new HomeTask(HomeTaskType.FILE, homeTaskText));
                        } else {
                            String homeTaskText = homeTasksElement.text();
                            homeTasks.add(new HomeTask(HomeTaskType.TEXT, homeTaskText));
                        }
                    }
                    for (Element gradeElement : gradesElements) {
                        grades.add(new Grade(gradeElement.text(), date, new Subject(lessonName)));
                    }

                    lessons.add(new Lesson(lessonNumber, lessonName, lessonTime, grades, homeTasks));

                }

                Day tempDay = new Day(lessons, date);
                tempDay.setName(name);
                daysList.add(tempDay);
            }


        }

        Map<String, HomeTask> homeTaskMap = new HashMap<>();
        Elements holidayTasks = weekDocument.select("li.ej-accordion");

        for (Element holidayTask : holidayTasks) {
            String subject = holidayTask.selectFirst("div.ej-accordion__title").text();
            String hometaskText = holidayTask.selectFirst("div.dnevnik-lesson__task").text();

            homeTaskMap.put(subject, new HomeTask(HomeTaskType.TEXT, hometaskText));
        }

        outWeek = new Week(daysList);
        outWeek.setTasks(homeTaskMap);

        return outWeek;

    }

    @Override
    public Week getWeekByDate(Calendar date) {

        //TODO: getWeekByDate(...)

        return null;
    }


    @Override
    public List<Announcement> getAllAnnouncements() throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        List<Announcement> out = new LinkedList<>();
        String announcementsPage = httpclient.get("https://edu.gounn.ru/journal-board-action");

        Document announcementsPageDocument = Jsoup.parse(announcementsPage);
        Elements announcementsElements = announcementsPageDocument.select("div.board-item");

        for (Element announcement : announcementsElements) {
            String dates = announcement.selectFirst("div.board-item__dates").text();
            String footer = announcement.selectFirst("div.board-item__footer").text();
            String msg = announcement.selectFirst("div.board-item__content").text();
            String title = announcement.selectFirst("div.board-item__title").text();

            out.add(new Announcement(dates, footer, msg, title));
        }

        return out;
    }

    @Override
    public List<Message> getMessages() throws DnevnikNotOnlineException, DnevnikNoInternetConnection {
        List<Message> msgs = new ArrayList<>();
        try {
            String resp = httpclient.get(
                    "https://edu.gounn.ru/journal-messages-ajax-action?method=messages.get_list&0=inbox&1=&2=20&3=0&4=0&5=&6=&7=0"
            );

            JSONObject jsonResp = new JSONObject(resp);
            JSONArray messages = jsonResp.getJSONArray("list");

            for (int i = 0; i < messages.length(); i++) {
                JSONObject obj = (JSONObject) messages.get(i);

                String theme = obj.getString("subject");
                String body = obj.getString("body");
                String date = obj.getString("msg_date");
                String statusString = obj.getString("status");
                String senderName = obj.getString("fromUserHuman");
                int senderId = Integer.parseInt(obj.getString("from_user"));
                int id = Integer.parseInt(obj.getString("id"));

                MessageUser user = new MessageUser(senderName, senderId);
                MessageStatus status = MessageStatus.ERROR;
                switch (statusString) {
                    case "read":
                        status = MessageStatus.READ;
                        break;
                    case "unread":
                        status = MessageStatus.UNREAD;
                        break;
                }

                msgs.add(new Message(id, theme, date, body, user, status));
            }
        } catch (JSONException ignored) {
            return null;
        }

        return msgs;
    }
}

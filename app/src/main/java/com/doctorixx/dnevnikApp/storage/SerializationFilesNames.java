package com.doctorixx.dnevnikApp.storage;

public enum SerializationFilesNames {

    COOKIE_BAR("cookie_bar"),

    WEEK_CONTAINER_PATH("week_container"),
    DONE_HOMETASK_CONTAINER_PATH("done_hometasks_container"),
    DONE_NOTES_CONTAINER_PATH("done_notes_container"),
    GRADE_CONTAINER_PATH("grade_container"),
    PROFILE_PATH("profile_container"),
    LAST_GRADE_CONTAINER_PATH("last_grade_container"),
    SUBJECT_AVERAGES_CONTAINER_PATH("averages_grade_container"),
    MESSAGES_CONTAINER_PATH("messages_container"),
    ANNOUNCEMENT_CONTAINER_PATH("announcement_container"),
    FINAL_GRADE_PATH("final_grade_container"),
    ;


    private final String text;

    SerializationFilesNames(String text) {

        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}

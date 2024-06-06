package com.doctorixx.easyDnevnik.stuctures;

import java.io.Serializable;
import java.util.Objects;

public class Announcement implements Serializable {

    private final String date, header, message, title;

    public Announcement(String date, String author, String message, String title) {
        this.date = date;
        this.header = author;
        this.message = message;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public String getHeader() {
        return header;
    }

    public String getMessage() {
        return message;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Announcement that = (Announcement) o;
        return Objects.equals(date, that.date) && Objects.equals(header, that.header)
                && Objects.equals(message, that.message) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, header, message, title);
    }
}

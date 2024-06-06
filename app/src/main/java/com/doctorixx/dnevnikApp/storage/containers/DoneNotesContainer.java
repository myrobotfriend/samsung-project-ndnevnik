package com.doctorixx.dnevnikApp.storage.containers;

import java.util.List;
import java.util.Map;

public class DoneNotesContainer {
    private final Map<String, List<Note>> notes;

    public DoneNotesContainer(Map<String, List<Note>> notes) {
        this.notes = notes;

    }

    public Map<String, List<Note>> getNotes() {
        return notes;
    }

    public static class Note {
        String note;
        boolean done;

        public Note(String note, boolean done) {
            this.done = done;
            this.note = note;

        }


        public String getNote() {
            return note;
        }

        public boolean isDone() {
            return done;
        }
    }
}

package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageButton;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.DoneNotesContainer;
import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctroixx.NDnevnik.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotesElementRenderer implements View.OnClickListener {
    private final LayoutInflater lInflater;
    private final DataSerializer<DoneNotesContainer> serializer;
    private ViewGroup parent;
    private Button cancelBtn, agreeBtn;

    private AppCompatImageButton addNoteBtn;

    private TextInputEditText noteText;

    private ViewGroup addNoteMenuLayout;
    private String subjectId;
    private Context ctx;

    public NotesElementRenderer(ViewGroup parent, String subjectId, Context ctx) {
        this.parent = parent;
        this.subjectId = subjectId;
        this.ctx = ctx;

        cancelBtn = parent.findViewById(R.id.button_cancel);
        agreeBtn = parent.findViewById(R.id.button_save);
        addNoteBtn = parent.findViewById(R.id.deleteBtn);
        noteText = parent.findViewById(R.id.note_text);

        addNoteMenuLayout = parent.findViewById(R.id.add_note_text_layout);

        addNoteMenuLayout.setVisibility(View.GONE);

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        serializer = new DataSerializer<>(DoneNotesContainer.class, ctx);

        cancelBtn.setOnClickListener(this);
        agreeBtn.setOnClickListener(this);
        addNoteBtn.setOnClickListener(this);


        renderSavedNotes();

    }

    private void renderSavedNotes() {
        DoneNotesContainer notesContainer = serializer.openData(SerializationFilesNames.DONE_NOTES_CONTAINER_PATH);
        if (notesContainer == null) return;
        List<DoneNotesContainer.Note> list = notesContainer.getNotes().get(subjectId);
        if (list == null) return;
        for (DoneNotesContainer.Note note : list) {
            addNote(note.getNote(), note.isDone());
        }
    }

    private void saveNote(String text, boolean checked) {
        DoneNotesContainer notesContainer = serializer.openData(SerializationFilesNames.DONE_NOTES_CONTAINER_PATH);
        if (notesContainer == null) notesContainer = new DoneNotesContainer(new HashMap<>());
        List<DoneNotesContainer.Note> list = notesContainer.getNotes().get(subjectId);
        if (list != null) {
            list.add(new DoneNotesContainer.Note(text, checked));
        } else {
            list = new ArrayList<>();
            list.add(new DoneNotesContainer.Note(text, checked));
            notesContainer.getNotes().put(subjectId, list);
        }
        serializer.saveData(notesContainer, SerializationFilesNames.DONE_NOTES_CONTAINER_PATH);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deleteBtn:
                addNoteMenuLayout.setVisibility(View.VISIBLE);
                addNoteBtn.setVisibility(View.GONE);
                break;
            case R.id.button_save:
                String noteUserInput = noteText.getText().toString();
                if (!noteUserInput.isEmpty()) {
                    addNote(noteUserInput);
                }
                noteText.setText("");
            case R.id.button_cancel:
                addNoteMenuLayout.setVisibility(View.GONE);
                addNoteBtn.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void addNote(String text) {
        addNote(text, false);
        saveNote(text, false);
    }

    private void addNote(String text, boolean enabled) {
        View note = lInflater.inflate(R.layout.element_note, null);
        ((TextView) note.findViewById(R.id.noteText)).setText(text);
        WidgetUtils.addNoteFunctional(note, enabled,subjectId, parent);
        parent.addView(note);
    }

}

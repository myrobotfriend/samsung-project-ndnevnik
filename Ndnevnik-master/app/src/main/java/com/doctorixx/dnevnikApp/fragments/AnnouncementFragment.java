package com.doctorixx.dnevnikApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.AnnouncementsRenderer;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetAnnouncementsTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.AnnouncementListener;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class AnnouncementFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_announcement, container, false);
        LinearLayout layout = view.findViewById(R.id.linearLayout);

        Fragment fragment = this;
        Context ctx = getContext();

        new GetAnnouncementsTask(new AnnouncementListener() {
            @Override
            public void run(List<Announcement> announcements) {
                new AnnouncementsRenderer(announcements, layout, ctx).render();
            }
        }).execute();

        return view;
    }
}
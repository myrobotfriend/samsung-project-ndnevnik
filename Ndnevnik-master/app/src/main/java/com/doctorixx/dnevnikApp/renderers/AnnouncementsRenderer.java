package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.Announcement;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class AnnouncementsRenderer {
    private final List<Announcement> announcements;
    private final LinearLayout parent;
    private final LayoutInflater lInflater;
    private final Context ctx;


    public AnnouncementsRenderer(List<Announcement> announcements, LinearLayout parent, Context ctx) {
        this.announcements = announcements;
        this.parent = parent;
        this.ctx = ctx;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void render() {

        parent.removeAllViews();
        for (Announcement announcement : announcements) {
            View view = lInflater.inflate(R.layout.announcement_card_element, null);
            ((TextView) view.findViewById(R.id.theme)).setText(announcement.getTitle());
            ((TextView) view.findViewById(R.id.author)).setText(announcement.getHeader());
            ((TextView) view.findViewById(R.id.date)).setText(announcement.getDate());
            WidgetUtils.addOnAnnouncementClick(view, announcement, ctx);
            parent.addView(view);
        }


    }
}


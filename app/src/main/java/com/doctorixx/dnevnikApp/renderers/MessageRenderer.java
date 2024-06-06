package com.doctorixx.dnevnikApp.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.doctorixx.dnevnikApp.utils.WidgetUtils;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;
import com.doctroixx.NDnevnik.R;

import java.util.List;

public class MessageRenderer {
    private final Context ctx;
    private final List<Message> messages;
    private final LinearLayout parent;
    private final LayoutInflater lInflater;

    public MessageRenderer(Context ctx, List<Message> messages, LinearLayout parent) {
        this.ctx = ctx;
        this.messages = messages;
        this.parent = parent;

        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void render() {
        parent.removeAllViews();
        if (messages == null) return;
        for (Message message : messages) {
            View view = lInflater.inflate(R.layout.message_card_element, null);

            ((TextView) view.findViewById(R.id.theme)).setText(message.getTheme());
            ((TextView) view.findViewById(R.id.date)).setText(message.getDate());
            ((TextView) view.findViewById(R.id.user)).setText(message.getUser().getName());

            WidgetUtils.addOnMessageClicker(view, message);

            parent.addView(view);
        }
    }
}

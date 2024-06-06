package com.doctorixx.dnevnikApp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.doctorixx.dnevnikApp.renderers.MessageRenderer;
import com.doctorixx.dnevnikApp.tasks.dnevnik.GetMessagesTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnSuccess;
import com.doctorixx.easyDnevnik.stuctures.messages.Message;
import com.doctroixx.NDnevnik.R;

import java.util.List;


public class MessageFragment extends Fragment implements OnSuccess<List<Message>> {


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }


    @Override
    public void onStart() {
        super.onStart();
        new GetMessagesTask(this).execute();
    }

    @Override
    public void run(List<Message> messages) {
        View view = getView();
        if (view == null) return;
        new MessageRenderer(
                getContext(),
                messages,
                view.findViewById(R.id.linearLayout)).render();
    }
}
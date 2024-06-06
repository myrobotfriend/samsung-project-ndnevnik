package com.doctorixx.dnevnikApp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.doctorixx.dnevnikApp.renderers.DayRenderer;
import com.doctorixx.dnevnikApp.renderers.HolidayTaskRenderer;
import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctorixx.dnevnikApp.tasks.dnevnik.WeekTask;
import com.doctorixx.dnevnikApp.tasks.dataclasses.OnTaskEndedListener;
import com.doctorixx.easyDnevnik.stuctures.Week;
import com.doctroixx.NDnevnik.R;

public class WeekFragment extends Fragment {

    private static final int WEEK_INDEX = 1;

    private int weekIndex;
    private SwipeRefreshLayout swipeLayout;
    private OnTaskEndedListener onUpdateTaskEnd;

    public WeekFragment() {
        weekIndex = WEEK_INDEX;
    }

    public WeekFragment(int weekIndex) {
        this.weekIndex = weekIndex;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.getInstance().debug(String.format("Week fragment started (week index %s)", weekIndex));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.week_fragment, container, false);

        LinearLayout parentWeek = view.findViewById(R.id.days);
        LinearLayout parentTasks = view.findViewById(R.id.tasks);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        Context context = getContext();



        setIsRefresh(true);
        addReloadListener();

        onUpdateTaskEnd = new OnTaskEndedListener() {
            @Override
            public void onEnded() {

                Week week = DnevnikAction.getInstance().getWeekbyIndex(weekIndex);

                new DayRenderer(context, week, parentWeek).renderWeek();
                new HolidayTaskRenderer(context, week.getTasks(), parentTasks).render();

                setIsRefresh(false);
            }
        };

        if (DnevnikAction.getInstance(context).isContrainWeekIndex(weekIndex)) {
            onUpdateTaskEnd.onEnded();
        }

        new WeekTask(onUpdateTaskEnd, weekIndex).execute();
        new WeekTask(weekIndex - 1).execute();
        new WeekTask(weekIndex + 1).execute();


        return view;
    }

    private void addReloadListener() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                setIsRefresh(true);

                Runnable onError = new Runnable() {
                    @Override
                    public void run() {
                        setIsRefresh(false);
                    }
                };

                new WeekTask(onUpdateTaskEnd,onError, weekIndex).execute();
            }
        });
    }

    private void setIsRefresh(boolean active){
        swipeLayout.setRefreshing(active);
    }
}
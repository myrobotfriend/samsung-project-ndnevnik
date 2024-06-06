package com.doctorixx.dnevnikApp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.doctorixx.changelog.ChangeLogStorage;
import com.doctorixx.dnevnikApp.dialogs.AboutDialog;
import com.doctorixx.dnevnikApp.dialogs.ChangeLogDialog;
import com.doctorixx.dnevnikApp.dialogs.ExitDialog;
import com.doctorixx.dnevnikApp.fragments.AllGradesFragment;
import com.doctorixx.dnevnikApp.fragments.AnnouncementFragment;
import com.doctorixx.dnevnikApp.fragments.FinalGradesFragment;
import com.doctorixx.dnevnikApp.fragments.GradeCalculatorFragment;
import com.doctorixx.dnevnikApp.fragments.GradeSimulatorFragment;
import com.doctorixx.dnevnikApp.fragments.LastGradsFragment;
import com.doctorixx.dnevnikApp.fragments.MessageFragment;
import com.doctorixx.dnevnikApp.fragments.WeekFragment;
import com.doctorixx.dnevnikApp.other.FragmentsVariants;
import com.doctorixx.dnevnikApp.singeltons.Logger;
import com.doctroixx.NDnevnik.BuildConfig;
import com.doctroixx.NDnevnik.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class MainActivity extends ResumableAppCompatActiviy {

    FragmentManager fragmentManager = getSupportFragmentManager();
    DrawerLayout drawler;

    private static final int WEEK_INDEX = 0;

    private int weekIndex = WEEK_INDEX;
    private boolean r_firstStart = true;
    private FragmentsVariants r_lastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week);

        if (savedInstanceState != null) {
            this.weekIndex = savedInstanceState.getInt("weekIndex");
            this.r_firstStart = savedInstanceState.getBoolean("r_firstStart");

            this.r_lastFragment = FragmentsVariants.getFragmentById(
                    savedInstanceState.getInt("r_lastFragment")
            );

            startFragmentWithFragmentVariant(r_lastFragment);
        } else {
            showChangelogDialog();

            checkSetNewWeek();
            startWeekFragmentByWeekIndex(weekIndex);
        }


        drawler = findViewById(R.id.drawler);

        Logger.getInstance().debug("Week started");
        setOnMenuClick();


        r_firstStart = false;
    }

    @SuppressLint("NonConstantResourceId")
    public void weekButtonClick(View view) {
        switch (view.getId()) {
            case R.id.prevWeekButton:
                weekIndex += 1;
                startWeekFragmentByWeekIndex(weekIndex);
                break;
            case R.id.nextWeekButton:
                weekIndex -= 1;
                startWeekFragmentByWeekIndex(weekIndex);
                break;
        }
    }

    public void onMenuButtonClick(View view) {
        drawler.open();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("weekIndex", weekIndex);
        outState.putBoolean("r_firstStart", r_firstStart);
        outState.putInt("r_lastFragment", r_lastFragment.getId());
    }


    private void startWeekFragmentByWeekIndex(int index) {
        r_lastFragment = FragmentsVariants.WEEK_FRAGMENT;
        FragmentTransaction transaction = fragmentManager.beginTransaction()
                .replace(R.id.frag, new WeekFragment(index));
        transaction.commit();
    }

    private void setOnMenuClick() {
        NavigationView navView = findViewById(R.id.navigation_bar);

        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                boolean select = true;
                switch (item.getItemId()) {
                    case R.id.your_grades:
                        startFragment(new AllGradesFragment(), FragmentsVariants.GRADES_FRAGMENT);
                        break;
                    case R.id.menu_week:
                        startWeekFragmentByWeekIndex(weekIndex);
                        break;
                    case R.id.announcement:
                        startFragment(new AnnouncementFragment(), FragmentsVariants.ANNOUNCEMENT_FRAGMENT);
                        break;
                    case R.id.messaging:
                        startFragment(new MessageFragment(), FragmentsVariants.MESSAGES_FRAGMENT);
                        break;
                    case R.id.last_grades:
                        startFragment(new LastGradsFragment(), FragmentsVariants.LAST_GRADES_FRAGMENT);
                        break;
                    case R.id.final_grades:
                        startFragment(new FinalGradesFragment(), FragmentsVariants.FINAl_GRADES_FRAGMENT);
                        break;
                    case R.id.settings:
                        select = false;
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                        break;
                    case R.id.menu_grade_calculator:
//                        startActivity(new Intent(getApplicationContext(), GradeCalculatorActivity.class));
                        startFragment(new GradeCalculatorFragment(), FragmentsVariants.GRADE_CALCULATOR_FRAGMENT);
                        break;
                    case R.id.menu_grade_simulator:
//                        startActivity(new Intent(getApplicationContext(), GradeCalculatorActivity.class));
                        startFragment(new GradeSimulatorFragment(), FragmentsVariants.GRADE_CALCULATOR_FRAGMENT);
                        break;
                    case R.id.logout:
                        select = false;
                        showExitDialog();
                        break;
                    case R.id.about:
                        select = false;
                        showAboutDialog();
                        break;
                    case R.id.secret_menu:
                        select = false;
                        startActivity(new Intent(getApplicationContext(), SecretActivity.class));
                        break;
                    case R.id.telegram:
                        Intent browserIntent = new Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://t.me/doctorixx")
                        );
                        startActivity(browserIntent);
                        break;

                    default:
                        Snackbar.make(navView, "В разработке", Snackbar.LENGTH_LONG).show();
                        return false;
                }

                drawler.close();

                return select;
            }
        });

        navView.setCheckedItem(R.id.menu_week);

    }

    private void checkSetNewWeek() {
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) dayOfWeek = 7;
        if (dayOfWeek == 6 || dayOfWeek == 7) weekIndex--;
    }


    private void showChangelogDialog() {
        if (ChangeLogStorage.load(getApplicationContext()) < BuildConfig.VERSION_CODE) {
            DialogFragment dialog = new ChangeLogDialog();
            dialog.show(getSupportFragmentManager(), "changelog");
        }

    }

    private void showAboutDialog() {
        DialogFragment dialog = new AboutDialog();
        dialog.show(getSupportFragmentManager(), "about");
    }

    private void showExitDialog() {
        DialogFragment dialog = new ExitDialog();
        dialog.show(getSupportFragmentManager(), "exit");
    }

    private void startFragment(Fragment fragment, FragmentsVariants fragmentName) {
        r_lastFragment = fragmentName;
        fragmentManager.beginTransaction()
                .replace(R.id.frag, fragment)
                .commit();
    }

    private void startFragmentWithFragmentVariant(FragmentsVariants variant) {
        switch (variant) {
            case WEEK_FRAGMENT:
                startWeekFragmentByWeekIndex(weekIndex);
                break;
            case GRADES_FRAGMENT:
                startFragment(new AllGradesFragment(), variant);
                break;
            case MESSAGES_FRAGMENT:
                startFragment(new MessageFragment(), variant);
                break;
            case ANNOUNCEMENT_FRAGMENT:
                startFragment(new AnnouncementFragment(), variant);
                break;
            case LAST_GRADES_FRAGMENT:
                startFragment(new LastGradsFragment(), variant);
                break;
            case FINAl_GRADES_FRAGMENT:
                startFragment(new FinalGradesFragment(), variant);
                break;
            case GRADE_CALCULATOR_FRAGMENT:
                startFragment(new GradeCalculatorFragment(), variant);
                break;
            case GRADE_SIMULATOR_FRAGMENT:
                startFragment(new GradeSimulatorFragment(), variant);
                break;
        }
    }
}
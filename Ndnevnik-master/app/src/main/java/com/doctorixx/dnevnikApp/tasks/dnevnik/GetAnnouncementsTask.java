package com.doctorixx.dnevnikApp.tasks.dnevnik;

import android.os.AsyncTask;

import com.doctorixx.dnevnikApp.singeltons.DnevnikAction;
import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;
import com.doctorixx.dnevnikApp.storage.containers.AnnouncementsContainer;
import com.doctorixx.dnevnikApp.tasks.dataclasses.AnnouncementListener;
import com.doctorixx.easyDnevnik.Announced;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;
import com.doctorixx.easyDnevnik.stuctures.Announcement;

import java.util.List;

public class GetAnnouncementsTask extends AsyncTask<Void, Void, List<Announcement>> {


    private final AnnouncementListener action;

    public GetAnnouncementsTask(AnnouncementListener action) {
        this.action = action;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        List<Announcement> announcements = openData();
        if (announcements != null) action.run(announcements);
    }

    @Override
    protected List<Announcement> doInBackground(Void... voids) {
        Announced dnevnik = (Announced) DnevnikAction.getInstance().getDnevnik();
        try {
            return dnevnik.getAllAnnouncements();
        } catch (DnevnikNotOnlineException | DnevnikNoInternetConnection e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Announcement> announcements) {
        super.onPostExecute(announcements);

        if (announcements != null) {
            action.run(announcements);
            saveData(announcements);
        }
    }

    private void saveData(List<Announcement> announcements){
        new DataSerializer<>(AnnouncementsContainer.class,DnevnikAction.getInstance().getContext())
                .saveData(
                        new AnnouncementsContainer(announcements),
                        SerializationFilesNames.ANNOUNCEMENT_CONTAINER_PATH
                );
    }

    private List<Announcement> openData(){
        AnnouncementsContainer container =  new DataSerializer<>(
                AnnouncementsContainer.class,DnevnikAction.getInstance().getContext())
                .openData(SerializationFilesNames.ANNOUNCEMENT_CONTAINER_PATH);
        if (container != null) return container.getAnnouncements();

        return null;
    }
}

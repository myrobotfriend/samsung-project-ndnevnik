package com.doctorixx.network;

import android.content.Context;

import com.doctorixx.dnevnikApp.storage.DataSerializer;
import com.doctorixx.dnevnikApp.storage.SerializationFilesNames;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SaverCookieJar implements CookieJar {

    private List<Cookie> cookies;
    private CookieJar cookieJar;
    private final DataSerializer<CookieConatiner> serializer;

    public SaverCookieJar(Context ctx) {
        serializer = new DataSerializer<>(CookieConatiner.class, ctx);
        cookies = restoreCookie();
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> responseCookies) {
        cookies = restoreCookie();
    try{
        for (Cookie newCookie : responseCookies) {
            for (Cookie maybeDublicateCookie : cookies) {
                if (maybeDublicateCookie.name().equals(newCookie.name())) {
                    cookies.remove(maybeDublicateCookie);
                }
            }
            cookies.add(newCookie);
        }

        serializer.saveData(new CookieConatiner(cookies), SerializationFilesNames.COOKIE_BAR);
    }catch (ConcurrentModificationException e){
        e.printStackTrace();
    }

    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        return restoreCookie();
    }

    private List<Cookie> restoreCookie() {
        CookieConatiner conatiner = serializer.openData(SerializationFilesNames.COOKIE_BAR);
        if (conatiner == null) return new ArrayList<>();

        return conatiner.getCookies();
    }

    public static void clearData(Context ctx) {
        new DataSerializer<>(CookieConatiner.class, ctx).saveData(
                new CookieConatiner(new ArrayList<>()),
                SerializationFilesNames.COOKIE_BAR);

    }


}

package com.doctorixx.network;

import java.util.List;

import okhttp3.Cookie;

public class CookieConatiner {
    private final List<Cookie> cookies;

    public CookieConatiner(List<Cookie> cookies) {
        this.cookies = cookies;
    }

    public List<Cookie> getCookies() {
        return cookies;
    }
}

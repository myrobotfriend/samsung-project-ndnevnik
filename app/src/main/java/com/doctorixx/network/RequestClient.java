package com.doctorixx.network;

import android.content.Context;

import com.doctorixx.easyDnevnik.exceptions.DnevnikNoInternetConnection;
import com.doctorixx.easyDnevnik.exceptions.DnevnikNotOnlineException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestClient {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient httpClient;

    public RequestClient(Context context) {
        httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .cookieJar(new SaverCookieJar(context))
                .build();
    }


    public String get(String url) throws DnevnikNoInternetConnection, DnevnikNotOnlineException {
        return request(url, "GET", null);
    }

    public String post(String url, RequestBody body) throws DnevnikNoInternetConnection, DnevnikNotOnlineException {
        return request(url, "POST", body);

    }

    public String get(String url, String json) throws DnevnikNoInternetConnection, DnevnikNotOnlineException {
        return request(url, "GET", RequestBody.create(json, JSON));
    }

    public String post(String url, String json) throws DnevnikNoInternetConnection, DnevnikNotOnlineException {
        return request(url, "POST", RequestBody.create(json, JSON));
    }

    private String request(String url, String method, RequestBody body) throws DnevnikNoInternetConnection, DnevnikNotOnlineException {
        Request request = new Request.Builder()
                .url(url)
                .method(method, body).
                build();
        Response response;
        try {
            response = httpClient.newCall(request).execute();
            String out = response.body().string();
            response.close();
            return out;
        } catch (UnknownHostException e) {
            throw new DnevnikNoInternetConnection();
        } catch (SocketTimeoutException e) {
            throw new DnevnikNotOnlineException();
        } catch (Exception e) {
            e.printStackTrace();
            throw new DnevnikNoInternetConnection();
        }

    }
}

package com.doctorixx.eljur_analytics.structures;

import com.doctorixx.eljur_analytics.Response;

import java.util.Map;

public class AuthResponse extends Response {
    private final String account_register;

    private final Map<String, String> data;

    private final String msg;

    public AuthResponse(String account_register, Map<String, String> data, String msg) {
        this.account_register = account_register;
        this.data = data;
        this.msg = msg;
    }

    public String getAccount_register() {
        return account_register;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }
}

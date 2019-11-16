package com.dandekar.epaper.request;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public final class AfterLogin extends StringRequest {

    public AfterLogin(String url, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
    }
}

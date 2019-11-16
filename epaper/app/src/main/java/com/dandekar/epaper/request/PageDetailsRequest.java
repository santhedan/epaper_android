package com.dandekar.epaper.request;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dandekar.epaper.data.Constants;

import java.util.HashMap;
import java.util.Map;

public final class PageDetailsRequest extends StringRequest {

    private static final String USER_AGENT = "User-Agent";
    private static final String UA_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
    private String cookie = "";

    public PageDetailsRequest(String url, String cookies, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, listener, errorListener);
        Log.d(Constants.TAG, "URL -> " + url);
        this.cookie = cookies;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<String, String>();
        Log.d(Constants.TAG, "Cookie being sent -> " + this.cookie);
        headers.put("Cookie", this.cookie);
        headers.put(USER_AGENT, UA_VALUE);
        return headers;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        for (Header header: response.allHeaders) {
            Log.d(Constants.TAG,header.toString());
        }
        String rawResponse = new String(response.data);
        Log.d(Constants.TAG, "Response -> " + rawResponse);
        return super.parseNetworkResponse(response);
    }

    @Override
    public void deliverError(VolleyError error) {
        for (Header header: error.networkResponse.allHeaders) {
            Log.d(Constants.TAG, header.toString());
        }
        super.deliverError(error);
    }
}

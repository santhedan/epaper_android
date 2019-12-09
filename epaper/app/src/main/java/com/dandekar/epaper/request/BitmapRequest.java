package com.dandekar.epaper.request;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.dandekar.epaper.data.BitmapHolder;
import com.dandekar.epaper.data.Constants;

import java.util.HashMap;
import java.util.Map;

public final class BitmapRequest extends Request<BitmapHolder> {

    private static final String USER_AGENT = "User-Agent";
    private static final String UA_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36";
    private String cookie = "";

    private final Response.Listener<BitmapHolder> listener;
    private int tag;

    public BitmapRequest(String url, int tag, String cookie, Response.Listener<BitmapHolder> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = listener;
        this.tag = tag;
        this.cookie = cookie;
        setShouldCache(false);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", this.cookie);
        headers.put(USER_AGENT, UA_VALUE);
        return headers;
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        try {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inScaled = false;   // We want to get the original image not scaled image
            Bitmap bitmap = BitmapFactory.decodeByteArray(response.data, 0, response.data.length, opt);
            BitmapHolder holder = new BitmapHolder(this.tag, null, response.data, bitmap);
            return Response.success(holder, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            return Response.error(new VolleyError("Unable to decode bitmap"));
        }
    }

    @Override
    protected void deliverResponse(BitmapHolder response) {
        listener.onResponse(response);
    }

}

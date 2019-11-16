package com.dandekar.epaper.request;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandekar.epaper.data.AccessDetails;
import com.dandekar.epaper.data.Constants;

import java.util.HashMap;
import java.util.Map;

public final class GetAccess extends GsonRequest<AccessDetails> {

    private static final String URL = "https://epaper.timesgroup.com/TOI/TimesOfIndia/GetAccess.ashx?From=TimesOfIndia";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String DELIMITER = ";";

    private String plenigoUser;

    public GetAccess(String plenigoUser, Response.Listener<AccessDetails> listener, @Nullable Response.ErrorListener errorListener) {
        super(URL, AccessDetails.class, null, listener, errorListener);
        this.plenigoUser = plenigoUser;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers = new HashMap<String, String>();
        headers.put("Cookie", plenigoUser);
        return headers;
    }

    @Override
    protected Response<AccessDetails> parseNetworkResponse(NetworkResponse response) {
        String cookieValue = "";
        Response<AccessDetails> details = null;
        try {
            for (Header header : response.allHeaders) {
                Log.d(Constants.TAG, header.toString());
                if (SET_COOKIE.equals(header.getName())) {
                    cookieValue = header.getValue().split(DELIMITER)[0];
                    Log.d(Constants.TAG, cookieValue);
                }
            }
            details = super.parseNetworkResponse(response);
            details.result.setCookie(cookieValue);
            return details;
        } catch (Exception ex) {
            return Response.error(new VolleyError("User login failed"));
        }
    }
}

package com.dandekar.epaper.request;

import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Header;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dandekar.epaper.data.Constants;

public final class PreLoginRequest extends StringRequest {

    private final static String stringToFind = "<input type=\"hidden\" name=\"synchronizeToken\" value=\"";

    private final static String URL = "https://remote.plenigo.com/remote/desktop/overlay/login?companyId=1I2pdv0agkkubDQCsUtB&loginSource=epaper.timesgroup.com&targetUrl=%2F%2Fepaper.timesgroup.com%2FOlive%2FODN%2FTimesOfIndia%2FAfterLogin.ashx&origin=%2F%2Fepaper.timesgroup.com%2FOlive%2FODN%2FTimesOfIndia%2FDefault.aspx&productIds=fEKdRs55550792930251%2C%207Huhc0k5390280358051%2C%20ajZb2n16591283152151&partnerLogin=true";

    public PreLoginRequest(Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.GET, URL, listener, errorListener);
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        for (Header header: response.allHeaders) {
            Log.d(Constants.TAG,header.toString());
        }
        String rawResponse = new String(response.data);
        Log.d(Constants.TAG, "Response -> " + rawResponse);
        // Does the response contain what we are looking for?
        if (rawResponse.contains(stringToFind)) {
            // Yes it does, Find the index
            int index = rawResponse.indexOf(stringToFind);
            // Increment the index with length of stringToFind
            index = index + stringToFind.length();
            // Get 36 chars from the index (length of UUID in String format)
            String synchronizeToken = rawResponse.substring(index, index + 36);
            Log.d(Constants.TAG, "synchronizeToken -> " + synchronizeToken);
            return Response.success(synchronizeToken,null);
        } else {
            return Response.error(new VolleyError("Token not found"));
        }
    }
}

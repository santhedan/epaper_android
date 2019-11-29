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

public class LoginRequest extends StringRequest {

    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";

    private static final String stringToFind = "parent.postMessage(JSON.stringify({cookieData: '";

    public static final String URL = "https://remote.plenigo.com/remote/desktop/overlay/login/authenticate";

    private final String keyEmail = "email";
    private final String keyPassword = "password";

    private final String keyTargetUrl = "targetUrl";
    private final String keyLoginSource = "loginSource";
    private final String keyCompanyId = "companyId";
    private final String keyCheckout = "checkout";
    private final String keySynchronizeToken = "synchronizeToken";

    private final String valueTargetUrl = "//epaper.timesgroup.com/Olive/ODN/TimesOfIndia/AfterLogin.ashx";
    private final String valueLoginSource = "epaper.timesgroup.com";
    private final String valueCompanyId = "1I2pdv0agkkubDQCsUtB";
    private final String valueCheckout = "false";

    private static final String postfix = "_1234567890";
    public static final String PLENIGO_USER = "plenigo_user=";

    private String email;
    private String password;
    private String synchronizeToken;

    public LoginRequest (String email, String password, String synchronizeToken, Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);
        this.email = email;
        this.password = password;
        this.synchronizeToken = synchronizeToken;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        final Map<String, String> bodyArgs = new HashMap<>();
        bodyArgs.put(keyEmail, email);
        bodyArgs.put(keyPassword, password);
        bodyArgs.put(keyTargetUrl, valueTargetUrl);
        bodyArgs.put(keyLoginSource, valueLoginSource);
        bodyArgs.put(keyCompanyId, valueCompanyId);
        bodyArgs.put(keyCheckout, valueCheckout);
        bodyArgs.put(keySynchronizeToken, synchronizeToken);
        return bodyArgs;
    }

    //b6869fad-a504-4185-8a97-ffc303022d1c

    @Override
    public String getBodyContentType() {
        return CONTENT_TYPE;
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String rawResponse = new String(response.data);
        // Does the response contain what we are looking for?
        if (rawResponse.contains(stringToFind)) {
            // Yes it does, Find the index
            int index = rawResponse.indexOf(stringToFind);
            // Increment the index with length of stringToFind
            index = index + stringToFind.length();
            // fine the occurance of single quite(') from index
            int nextIndex = rawResponse.indexOf("'", index);
            // Now get the content
            String plenigoUser = rawResponse.substring(index, nextIndex);
            plenigoUser = PLENIGO_USER + plenigoUser + postfix;
            return Response.success(plenigoUser,null);
        } else {
            return Response.error(new VolleyError("User login failed"));
        }
    }
}

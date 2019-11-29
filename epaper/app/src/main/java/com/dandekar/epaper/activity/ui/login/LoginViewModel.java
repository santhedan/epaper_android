package com.dandekar.epaper.activity.ui.login;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.Header;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dandekar.epaper.R;
import com.dandekar.epaper.data.AccessDetails;
import com.dandekar.epaper.data.Constants;
import com.dandekar.epaper.request.AfterLogin;
import com.dandekar.epaper.request.GetAccess;
import com.dandekar.epaper.request.LoginRequest;
import com.dandekar.epaper.request.PreLoginRequest;
import com.dandekar.epaper.util.VolleySingleton;

public class LoginViewModel extends ViewModel implements Response.ErrorListener, Response.Listener {

    public static final String BASE_URL = "https://epaper.timesgroup.com";
    public static final String SET_COOKIE = "Set-Cookie";
    public static final String DELIMITER = ";";

    private enum RequestType {
        PreLogin,
        Login,
        GetAccess,
        AfterLogin
    }

    private RequestType currentReqType;
    private String username;
    private String password;
    private VolleySingleton volley;

    private String plenigoUser;
    private String toiPlenigoId;
    private String toiAuth;

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();

    LoginViewModel() {

    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password, VolleySingleton volley) {
        // Remember the user name and password
        this.username = username;
        this.password = password;
        this.volley = volley;
        // Create PreLoginRequest
        currentReqType = RequestType.PreLogin;
        PreLoginRequest preLoginReq = new PreLoginRequest(this, this);
        this.volley.getRequestQueue().add(preLoginReq);
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 0;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (currentReqType == RequestType.PreLogin) {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        } else if (currentReqType == RequestType.AfterLogin) {
            StringBuilder cookieValue = new StringBuilder();
            for (Header header : error.networkResponse.allHeaders) {
                if (SET_COOKIE.equals(header.getName())) {
                    cookieValue.append(header.getValue().split(DELIMITER)[0]);
                    cookieValue.append(DELIMITER);
                }
            }
            toiAuth = cookieValue.toString();
            //
            loginResult.setValue(new LoginResult(new LoggedInUserView(plenigoUser, toiPlenigoId, toiAuth)));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    @Override
    public void onResponse(Object response) {
        if (currentReqType == RequestType.PreLogin) {
            // Create LoginRequest
            currentReqType = RequestType.Login;
            LoginRequest request = new LoginRequest(username, password, (String)response, this, this);
            volley.getRequestQueue().add(request);
        } else if (currentReqType == RequestType.Login) {
            plenigoUser = (String) response;
            // Create GetAccess request
            currentReqType = RequestType.GetAccess;
            GetAccess access = new GetAccess(plenigoUser, this, this);
            volley.getRequestQueue().add(access);
        } else if (currentReqType == RequestType.GetAccess) {
            // Get the response
            AccessDetails details = (AccessDetails) response;
            // Log the response
            toiPlenigoId = details.getCookie();
            // Now invoke the AfterLogin request
            currentReqType = RequestType.AfterLogin;
            AfterLogin request = new AfterLogin(BASE_URL + details.getRedirect(), this, this);
            volley.getRequestQueue().add(request);
        } else if (currentReqType == RequestType.AfterLogin) {
            // Get the response
            toiAuth = response.toString();
            //
            loginResult.setValue(new LoginResult(new LoggedInUserView(plenigoUser, toiPlenigoId, toiAuth)));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
}

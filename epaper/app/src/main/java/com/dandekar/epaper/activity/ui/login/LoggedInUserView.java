package com.dandekar.epaper.activity.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {

    private String plenigoUser;
    private String toiPlenigoId;
    private String toiAuth;

    LoggedInUserView(String plenigoUser, String toiPlenigoId, String toiAuth) {
        this.plenigoUser = plenigoUser;
        this.toiPlenigoId = toiPlenigoId;
        this.toiAuth = toiAuth;
    }

    public String getPlenigoUser() {
        return plenigoUser;
    }

    public String getToiPlenigoId() {
        return toiPlenigoId;
    }

    public String getToiAuth() {
        return toiAuth;
    }
}

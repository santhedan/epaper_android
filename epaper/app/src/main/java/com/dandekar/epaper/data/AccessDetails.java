package com.dandekar.epaper.data;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public final class AccessDetails {

    private String Country;
    private String CachedProducts;
    private boolean LoggedIn;
    private boolean phoneDefined;
    private boolean mobileDefined;
    private String MyAccount;
    private List<List<AccessData> > data = new ArrayList(new ArrayList<AccessData>());
    private boolean FoundSubscriptions;
    private String redirect;
    private List <Clearance> clearance = new ArrayList <Clearance>();
    private float clearanceLength;
    private List <String> Profile = new ArrayList <String>();
    private List <String> segments = new ArrayList <String>();
    @Expose(deserialize = false)
    private String cookie;

    public AccessDetails(String country, String cachedProducts, boolean loggedIn, boolean phoneDefined, boolean mobileDefined, String myAccount, List<List<AccessData>> data, boolean foundSubscriptions, String redirect, List<Clearance> clearance, float clearanceLength, List<String> profile, List<String> segments) {
        Country = country;
        CachedProducts = cachedProducts;
        LoggedIn = loggedIn;
        this.phoneDefined = phoneDefined;
        this.mobileDefined = mobileDefined;
        MyAccount = myAccount;
        this.data = data;
        FoundSubscriptions = foundSubscriptions;
        this.redirect = redirect;
        this.clearance = clearance;
        this.clearanceLength = clearanceLength;
        Profile = profile;
        this.segments = segments;
    }

    public String getCountry() {
        return Country;
    }

    public String getCachedProducts() {
        return CachedProducts;
    }

    public boolean isLoggedIn() {
        return LoggedIn;
    }

    public boolean isPhoneDefined() {
        return phoneDefined;
    }

    public boolean isMobileDefined() {
        return mobileDefined;
    }

    public String getMyAccount() {
        return MyAccount;
    }

    public List<List<AccessData>> getData() {
        return data;
    }

    public boolean isFoundSubscriptions() {
        return FoundSubscriptions;
    }

    public String getRedirect() {
        return redirect;
    }

    public List<Clearance> getClearance() {
        return clearance;
    }

    public float getClearanceLength() {
        return clearanceLength;
    }

    public List<String> getProfile() {
        return Profile;
    }

    public List<String> getSegments() {
        return segments;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    @Override
    public String toString() {
        return "AccessDetails{" +
                "Country='" + Country + '\'' +
                ", CachedProducts='" + CachedProducts + '\'' +
                ", LoggedIn=" + LoggedIn +
                ", phoneDefined=" + phoneDefined +
                ", mobileDefined=" + mobileDefined +
                ", MyAccount='" + MyAccount + '\'' +
                ", data=" + data +
                ", FoundSubscriptions=" + FoundSubscriptions +
                ", redirect='" + redirect + '\'' +
                ", clearance=" + clearance +
                ", clearanceLength=" + clearanceLength +
                ", Profile=" + Profile +
                ", segments=" + segments +
                ", cookie='" + cookie + '\'' +
                '}';
    }
}

package com.dandekar.epaper.data;

public final class Clearance {

    private String productId;
    private String expires;
    private String Segments;
    private String location;
    private String ValidReason;
    private boolean Valid;

    public Clearance(String productId, String expires, String segments, String location, String validReason, boolean valid) {
        this.productId = productId;
        this.expires = expires;
        Segments = segments;
        this.location = location;
        ValidReason = validReason;
        Valid = valid;
    }

    public String getProductId() {
        return productId;
    }

    public String getExpires() {
        return expires;
    }

    public String getSegments() {
        return Segments;
    }

    public String getLocation() {
        return location;
    }

    public String getValidReason() {
        return ValidReason;
    }

    public boolean isValid() {
        return Valid;
    }

    @Override
    public String toString() {
        return "Clearance{" +
                "productId='" + productId + '\'' +
                ", expires='" + expires + '\'' +
                ", Segments='" + Segments + '\'' +
                ", location='" + location + '\'' +
                ", ValidReason='" + ValidReason + '\'' +
                ", Valid=" + Valid +
                '}';
    }
}

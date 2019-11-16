package com.dandekar.epaper.data;

public final class AccessData {

    private String productId;
    private float lifeTimeEnd;
    private float nextRenewalDate;
    private boolean blocked;

    public AccessData(String productId, float lifeTimeEnd, float nextRenewalDate, boolean blocked) {
        this.productId = productId;
        this.lifeTimeEnd = lifeTimeEnd;
        this.nextRenewalDate = nextRenewalDate;
        this.blocked = blocked;
    }

    public String getProductId() {
        return productId;
    }

    public float getLifeTimeEnd() {
        return lifeTimeEnd;
    }

    public float getNextRenewalDate() {
        return nextRenewalDate;
    }

    public boolean isBlocked() {
        return blocked;
    }

    @Override
    public String toString() {
        return "AccessData{" +
                "productId='" + productId + '\'' +
                ", lifeTimeEnd=" + lifeTimeEnd +
                ", nextRenewalDate=" + nextRenewalDate +
                ", blocked=" + blocked +
                '}';
    }
}

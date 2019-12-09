package com.dandekar.epaper.data;

import android.graphics.Bitmap;

public final class BitmapHolder {

    private int intTag;
    private String stringTag;
    private byte[] bitmapData;
    private Bitmap bitmap;

    public BitmapHolder(int intTag, String stringTag, byte[] bitmapData, Bitmap bitmap) {
        this.intTag = intTag;
        this.stringTag = stringTag;
        this.bitmapData = bitmapData;
        this.bitmap = bitmap;
    }

    public void resetBitmap() {
//        bitmapData = null;
//        bitmap = null;
    }

    public int getIntTag() {
        return intTag;
    }

    public String getStringTag() {
        return stringTag;
    }

    public byte[] getBitmapData() {
        return bitmapData;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}

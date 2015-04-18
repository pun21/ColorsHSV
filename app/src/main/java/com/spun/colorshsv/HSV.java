package com.spun.colorshsv;


public class HSV {
    private float h;
    private float s;
    private float v;

    public HSV() {};
    public HSV(float[] hsv) {
        this.h = hsv[0];
        this.s = hsv[1];
        this.v = hsv[2];
    }
    public HSV(int h, int s, int v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }
    public float getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public float getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public float getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}

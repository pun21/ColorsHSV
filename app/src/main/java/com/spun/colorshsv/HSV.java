package com.spun.colorshsv;


public class HSV {
    private int h;
    private int s;
    private int v;

    public HSV() {};
    public HSV(int h, int s, int v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }
    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }
}

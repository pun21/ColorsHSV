package com.spun.colorshsv;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static int MAX_HUE = 360;
    private static int MIN_HUE = 0;
    private static int MAX_SATURATION = 1;
    private static int MIN_SATURATION = 0;
    private static int MAX_VALUE = 1;
    private static int MIN_VALUE = 0;
    private static int mTag = 0;

    private static ArrayList<Integer> rainbowList;
    private float[][] hsv;
    private String[] mFragmentTag = new String[]{"First", "Second", "Third", "Fourth"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int rainbow[] = this.getResources().getIntArray(R.array.rainbow);

        hsv = new float[13][];
        for (int i = 0; i < hsv.length; i++) {
            float[] temp = new float[]{360 - (i * 30), MAX_SATURATION, MAX_VALUE};
            hsv[i] = temp;
        }
        rainbowList = new ArrayList<>();
        ArrayList<float[]> hsvList = new ArrayList<>();

        for (int i = 0; i < hsv.length; i++) {

            hsvList.add(hsv[i]);
        }

        for (int i = 0; i < rainbow.length; i++) {
            rainbowList.add(rainbow[i]);
        }

        CustomFragment huesFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        for (int i = 0; i < hsv.length; i++) {
            bundle.putFloatArray("color "+i, hsv[i]);
        }
        huesFragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, huesFragment, mFragmentTag[mTag]);
        ft.commit();
        mTag++;
        /*The first fragment contains gradients of pure spectral hues with 100% saturation
        /*100% value.
        */




        /*The second fragment displays the same hues at varying saturation levels (100% to 0%)
        /*at a constant 100% value.
         */
    }

    public void replaceFragment() {
        CustomFragment huesFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        for (int i = 0; i < hsv.length; i++) {
            bundle.putFloatArray("color "+i, hsv[i]);
        }
        huesFragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, huesFragment, mFragmentTag[mTag]);
        ft.commit();
        Toast.makeText(this, mFragmentTag[mTag++] + " fragment replaced the first.", Toast.LENGTH_SHORT).show();
    }
}
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
    private static float MAX_SATURATION = 1;
    private static float MIN_SATURATION = 0;
    private static float MAX_VALUE = 1;
    private static float MIN_VALUE = 0;
    private static int mTag = 0;

    private float[][] hsv;
    private String[] mFragmentTag = new String[]{"First", "Second", "Third", "Fourth"};
    private float h;
    private float s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hsv = new float[10][];
        for (int i = 0; i < hsv.length; i++) {
            float[] temp = new float[]{0 + (i * 30), MAX_SATURATION, MAX_VALUE};
            hsv[i] = temp;
        }

        ArrayList<float[]> hsvList = new ArrayList<>();

        for (int i = 0; i < hsv.length; i++) {
            hsvList.add(hsv[i]);
        }

        CustomFragment huesFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        for (int i = 0; i < hsv.length; i++) {
            bundle.putFloatArray("color "+i, hsv[i]);
        }
        bundle.putString("tag", mFragmentTag[0]);
        huesFragment.setArguments(bundle);

         /*The first fragment contains gradients of pure spectral hues with 100% saturation
        /*100% value.
        */
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, huesFragment, mFragmentTag[mTag]);
        ft.addToBackStack(null);
        ft.commit();
        mTag++;
    }

    public void replaceFragment(int position) {
        /*The second fragment displays the same hues at varying saturation levels (100% to 0%)
        /*at a constant 100% value.
         */
        CustomFragment huesFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        if (mFragmentTag[mTag] == "Second") {
            for (int i = 0; i < hsv.length; i++) {
                float sat = MAX_SATURATION - i*(MAX_SATURATION/(float)hsv.length);
                float[] temp = new float[]{0 + (position * 30), MAX_SATURATION - i*(MAX_SATURATION/hsv.length), MAX_VALUE};
                hsv[i] = temp;
            }
            h = position;
        }
        else if (mFragmentTag[mTag] == "Third") {
            for (int i = 0; i < hsv.length; i++) {
                float[] temp = new float[]{0 + (h * 30), MAX_SATURATION - position*(MAX_SATURATION/hsv.length), MAX_VALUE-i*(MAX_VALUE
                /hsv.length)};
                hsv[i] = temp;
            }
            s = position;
        }
        else {
            for (int i = 0; i < hsv.length; i++) {
                float[] temp = new float[]{0, MAX_SATURATION, MAX_VALUE};
                hsv[i] = temp;
            }
            bundle.putFloat("h", 0 + (h * 30));
            bundle.putFloat("s", MAX_SATURATION - s*(MAX_SATURATION/hsv.length));
            bundle.putFloat("v", MAX_VALUE-position*(MAX_VALUE
                    /hsv.length));
        }
        for (int i = 0; i < hsv.length; i++) {
            bundle.putFloatArray("color "+i, hsv[i]);
        }
        bundle.putString("tag", mFragmentTag[mTag]);
        huesFragment.setArguments(bundle);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, huesFragment, mFragmentTag[mTag]);
        ft.addToBackStack(null);
        ft.commit();
        Toast.makeText(this, mFragmentTag[mTag++] + " fragment replaced the first.", Toast.LENGTH_SHORT).show();
    }
}
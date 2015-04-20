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
    private static int HUE_RANGE = 30;

    private CustomFragment huesFragment, saturationFragment, valuesFragment, summaryFragment;
    private float[][] hsv;
    private String[] mFragmentTag = new String[]{"First", "Second", "Third", "Fourth"};
    private int mTag = 0;
    private float h;
    private float s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        hsv = new float[10][];
        for (int i = 0; i < hsv.length; i++) {
            float[] temp = new float[]{0 + (i * HUE_RANGE), MAX_SATURATION, MAX_VALUE};
            hsv[i] = temp;
        }

        ArrayList<float[]> hsvList = new ArrayList<>();

        for (int i = 0; i < hsv.length; i++) {
            hsvList.add(hsv[i]);
        }

        huesFragment = new CustomFragment();
        Bundle bundle = new Bundle();
        for (int i = 0; i < hsv.length; i++) {
            bundle.putFloatArray("color "+i, hsv[i]);
        }
        bundle.putString("tag", mFragmentTag[0]);
        bundle.putInt("hRange", HUE_RANGE);
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
        Bundle bundle = new Bundle();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (mFragmentTag[mTag] == "Second") {
            if (saturationFragment == null)
                saturationFragment = new CustomFragment();

            for (int i = 0; i < hsv.length; i++) {
                float[] temp = new float[]{0 + (position * HUE_RANGE), MAX_SATURATION - i*(MAX_SATURATION/hsv.length), MAX_VALUE};
                hsv[i] = temp;
            }
            h = position;
            for (int i = 0; i < hsv.length; i++) {
                bundle.putFloatArray("color "+i, hsv[i]);
            }
            bundle.putString("tag", mFragmentTag[mTag]);
            if (saturationFragment.getArguments() != null)
                saturationFragment.getArguments().putBundle("bundle", bundle);
            else
                saturationFragment.setArguments(bundle);
            ft.replace(android.R.id.content, saturationFragment, mFragmentTag[mTag]);
        }
        else if (mFragmentTag[mTag] == "Third") {
            if (valuesFragment == null)
                valuesFragment = new CustomFragment();

            for (int i = 0; i < hsv.length; i++) {
                float[] temp = new float[]{0 + (h * HUE_RANGE), MAX_SATURATION - position*(MAX_SATURATION/hsv.length), MAX_VALUE-i*(MAX_VALUE
                /hsv.length)};
                hsv[i] = temp;
            }
            s = position;
            for (int i = 0; i < hsv.length; i++) {
                bundle.putFloatArray("color "+i, hsv[i]);
            }
            bundle.putString("tag", mFragmentTag[mTag]);
            if (valuesFragment.getArguments() != null)
                valuesFragment.getArguments().putBundle("bundle", bundle);
            else
                valuesFragment.setArguments(bundle);
            ft.replace(android.R.id.content, valuesFragment, mFragmentTag[mTag]);
        }
        else {
            if (summaryFragment == null)
                summaryFragment = new CustomFragment();
            for (int i = 0; i < hsv.length; i++) {
                float[] temp = new float[]{0, MAX_SATURATION, MAX_VALUE};
                hsv[i] = temp;
            }
            bundle.putFloat("h", 0 + (h * HUE_RANGE));
            bundle.putFloat("s", MAX_SATURATION - s*(MAX_SATURATION/hsv.length));
            bundle.putFloat("v", MAX_VALUE-position*(MAX_VALUE
                    /hsv.length));
            for (int i = 0; i < hsv.length; i++) {
                bundle.putFloatArray("color "+i, hsv[i]);
            }
            bundle.putString("tag", mFragmentTag[mTag]);
            if (summaryFragment.getArguments() != null)
                summaryFragment.getArguments().putBundle("bundle", bundle);
            else
                summaryFragment.setArguments(bundle);
            ft.replace(android.R.id.content, summaryFragment, mFragmentTag[mTag]);
        }

        ft.addToBackStack(null);
        ft.commit();
        Toast.makeText(this, mFragmentTag[mTag++] + " fragment replaced the first.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (mTag == 2) {
            mTag = mTag-2;
            huesFragment.setTag(mTag);
            mTag++;
        }
        else if (mTag == 3) {
            mTag = mTag-2;
            saturationFragment.setTag(mTag);
            mTag++;
        }
        else if (mTag == 4) {
            mTag = mTag-2;
            valuesFragment.setTag(mTag);
            mTag++;
        }
    }
}
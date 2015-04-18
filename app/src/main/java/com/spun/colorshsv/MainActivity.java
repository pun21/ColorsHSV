package com.spun.colorshsv;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static int MAX_HUE = 360;
    private static int MIN_HUE = 0;
    private static int MAX_SATURATION = 1;
    private static int MIN_SATURATION = 0;
    private static int MAX_VALUE = 1;
    private static int MIN_VALUE = 0;

    private static ArrayList<Integer> rainbowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.list);
        int rainbow[] = this.getResources().getIntArray(R.array.rainbow);

        float hsv[][] = new float[13][];
        for (int i = 0; i < hsv.length; i++) {
            float[] temp = new float[] {360-(i*30), MAX_SATURATION, MAX_VALUE};
            hsv[i]  = temp;
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
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(android.R.id.content, huesFragment, "First");
        ft.commit();
        /*The first fragment contains gradients of pure spectral hues with 100% saturation
        /*100% value.
        */




        /*The second fragment displays the same hues at varying saturation levels (100% to 0%)
        /*at a constant 100% value.
         */
    }
    public void replaceFragment() {
        CustomFragment huesFragment = new CustomFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(android.R.id.content, huesFragment, "Second");
        ft.commit();

        Toast.makeText(this, "Second fragment replaced the first.", Toast.LENGTH_SHORT).show();
    }
    public class LevelOneAdapter extends ArrayAdapter<float[]> {
        private int size;
        public LevelOneAdapter(Context context, ArrayList<float[]> hsvList) {
            super(context, 0, hsvList);
            size = hsvList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            float[] hsv = getItem(position);
            int colorLeft = Color.HSVToColor(hsv);
            int colorRight;

            if (position < size-1)
                colorRight = Color.HSVToColor(getItem(position+1));
            else {
                float[] color = getItem(position);
                color[0] = color[0]-30;
                colorRight = Color.HSVToColor(color);
            }


            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            //textView.setBackgroundColor(color);

            GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {colorLeft, colorRight});
            textView.setBackground(drawable);
            return convertView;
        }
    }
}

//LevelOneAdapter adapter = new LevelOneAdapter(this, hsvList);
//        listView.setAdapter(adapter);
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // When clicked, show a toast with the TextView text
//                Toast.makeText(getApplicationContext(),
//                        "Item has been clicked.", Toast.LENGTH_SHORT).show();
//
//            }
//        });

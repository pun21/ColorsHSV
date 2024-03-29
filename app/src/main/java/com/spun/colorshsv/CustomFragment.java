package com.spun.colorshsv;

import android.app.ListFragment;
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

public class CustomFragment extends ListFragment {
    private static int MAX_HUE = 360;
    private static int MIN_HUE = 0;
    private static int MAX_SATURATION = 1;
    private static int MIN_SATURATION = 0;
    private static int MAX_VALUE = 1;
    private static int MIN_VALUE = 0;
    private ArrayList<float[]> hues;
    private String mTag;
    private float h, s, v;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        float hsv[][] = new float[10][];
        for (int i = 0; i < hsv.length; i++) {
            hsv[i] = bundle.getFloatArray("color "+i);
        }

        mTag = bundle.getString("tag");

        ArrayList<float[]> hsvList = new ArrayList<>();

        for (int i = 0; i < hsv.length; i++) {
            hsvList.add(hsv[i]);
        }

        if (mTag == "Fourth") {
            h = bundle.getFloat("h");
            s = bundle.getFloat("s");
            v = bundle.getFloat("v");
        }
        setListAdapter(new LevelTwoAdapter(getActivity(), hsvList));
    }
    public CustomFragment() {}
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }
    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        super.onListItemClick(listView, v, position, id);

        int level = 0;
        if (mTag == "First")
            level = 2;
        else if (mTag == "Second")
            level = 3;
        else
            level = 4;
        if (mTag == "Third") {
            //replace with another fragment
            Toast.makeText(getActivity(), "Item has been clicked, -->Lv."+level, Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).replaceFragment(position);
        }
        else if (mTag == "Fourth")
            Toast.makeText(getActivity(), "Go back to select another color.", Toast.LENGTH_SHORT).show();
        else {
            Toast.makeText(getActivity(), "Item has been clicked, -->Lv."+level, Toast.LENGTH_SHORT).show();
            ((MainActivity)getActivity()).replaceFragment(position);
        }
    }

    public class LevelTwoAdapter extends ArrayAdapter<float[]> {
        private int size;
        public LevelTwoAdapter(Context context, ArrayList<float[]> hsvList) {
            super(context, R.layout.list_item, hsvList);
            size = hsvList.size();
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            float[] hsv = getItem(position);
            int colorLeft = Color.WHITE;
            int colorRight = Color.WHITE;

            if (mTag == "First") {
                //vary the hue, constant 100% saturation, constant 100%value*/

                colorLeft = Color.HSVToColor(hsv);
                if (position < size - 1)
                    colorRight = Color.HSVToColor(getItem(position + 1));
                else {
                    float[] color = getItem(position);
                    color[0] = color[0] + 30;
                    colorRight = Color.HSVToColor(color);
                }
                GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{colorLeft, colorRight});
                textView.setBackground(drawable);
            }
            else if (mTag == "Second") {
                /*keep the hue range the same, vary the saturation, constant 100%value*/
                colorLeft = Color.HSVToColor(hsv);
                if (position < size - 1)
                    colorRight = Color.HSVToColor(getItem(position + 1));
                else {
                    float[] color = getItem(position);
                    color[0] = color[0] - 30;
                    color[1] = color[1] - MAX_SATURATION/size;
                    colorRight = Color.HSVToColor(color);
                }
                GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{colorLeft, colorRight});
                textView.setBackground(drawable);
            }
            else if (mTag == "Third") {
                /*keep the hue range and the saturation range the same, vary the value*/
                colorLeft = Color.HSVToColor(hsv);
                if (position < size - 1)
                    colorRight = Color.HSVToColor(getItem(position + 1));
                else {
                    float[] color = getItem(position);
                    color[0] = color[0] - 30;
                    color[2] = color[2] - MAX_VALUE/size;
                    colorRight = Color.HSVToColor(color);
                }
                GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[]{colorLeft, colorRight});
                textView.setBackground(drawable);
            }
            else {
                //todo try to use the empty in a listview
                if (position == 0) {
                    textView.setText("HSV values selected:\n" +
                                     "Hue Range: " + h + "-" + (h+30) + "\n" +
                                     "Saturation: " + s + "\n" +
                                     "Value: " + v);
                    textView.setTextSize(18);
                }
            }


            return convertView;
        }
    }
}
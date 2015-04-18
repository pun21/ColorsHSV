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
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        float hsv[][] = new float[13][];
        for (int i = 0; i < hsv.length; i++) {
            hsv[i] = bundle.getFloatArray("color "+i);
        }

        ArrayList<float[]> hsvList = new ArrayList<float[]>();
        //ArrayList<HSV> hsvList = new ArrayList<HSV>();
        for (int i = 0; i < hsv.length; i++) {
            //HSV color = new HSV(hsv[i]);
            hsvList.add(hsv[i]);
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

        Toast.makeText(getActivity(), "Item has been clicked, -->Lv.2", Toast.LENGTH_SHORT).show();
        //replace with another fragment
        ((MainActivity)getActivity()).replaceFragment();
    }

    public class LevelTwoAdapter extends ArrayAdapter<float[]> {
        private int size;
        public LevelTwoAdapter(Context context, ArrayList<float[]> hsvList) {
            super(context, R.layout.list_item, hsvList);
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